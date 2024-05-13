package com.infosociety.social.api.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosociety.social.api.ExpertiseService;
import com.infosociety.social.persistence.ExpertiseProvider;
import com.infosociety.social.types.Category;
import com.infosociety.social.types.Expertise;

/**
 * ExpertiseServiceBean.
 *
 * @author Baruch Speiser, Cambium.
 */
@Service
public class ExpertiseServiceBean implements ExpertiseService {
  private static final Logger log = LoggerFactory.getLogger(ExpertiseServiceBean.class);
  @Autowired
  private ExpertiseProvider provider;
  private List<Category> categories;
  private List<Expertise> expertise;
  
  /** @param provider */
  public void setProvider(ExpertiseProvider provider) {
    this.provider = provider;
  }

  //We have this mildly complex approach because we want to cache the data in a convenient way.
  @PostConstruct
  void onInit() {
    this.expertise = this.provider.getAllExpertise();
    if(this.expertise.isEmpty()) {
      log.warn("Provider did return any expertise - has the database been properly populated?");
      return;
    }
    //Do not assume the categories will be sorted alphabetically
    this.expertise.sort(new Comparator<Expertise>() {
      @Override
      public int compare(Expertise a, Expertise b) {
        return a.getName().compareTo(b.getName());
      }
    });
    //Crosslink from category to expertise: first organize the category data from the flat expertise array
    Map<String, Category> categoryMap = new TreeMap<>();
    Map<Integer, List<Expertise>> expertiseMap = new HashMap<>();
    for(Expertise x : this.expertise) {
      categoryMap.put(x.getCategory().getName(), x.getCategory());
      List<Expertise> list = expertiseMap.get(x.getCategory().getID());
      if(null == list) list = new ArrayList<>();
      list.add(x);
      expertiseMap.put(x.getCategory().getID(), list);
    }
    //Now build the category list (the category map is alphabetically sorted already).
    // Note that our categories didn't start off with their arrays inside, which is why we had to do all of this.
    for(Category c : categoryMap.values()) {
      List<Expertise> expertise = expertiseMap.get(c.getID());
      Category category = new Category(c.getID(), c.getName(), expertise);
      this.categories.add(category);
    }
  }

  @Override
  public List<Category> listAllCategories() {
    List<Category> shallowList = new ArrayList<>(this.categories.size());
    for(Category c : this.categories) {
      Category copy = new Category(c.getID(), c.getName());
      shallowList.add(copy);
    }
    return Collections.unmodifiableList(shallowList);
  }

  @Override
  public List<Category> getAllCategories() {
    return Collections.unmodifiableList(this.categories);
  }

  @Override
  public List<Expertise> getAllExpertise() {
    return Collections.unmodifiableList(this.expertise);
  }

  @Override
  public Category lookupCategory(int id) {
    //Array search is efficient enough for data size
    for(Category c : this.categories) {
      if(c.getID() == id) return c;
    }
    return null;
  }

  @Override
  public Expertise lookupExpertise(int id) {
    //Array search is efficient enough for data size
    for(Expertise x : this.expertise) {
      if(x.getID() == id) return x;
    }
    return null;
  }
}
