package com.infosociety.social.persistence.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infosociety.social.persistence.ExpertiseProvider;
import com.infosociety.social.persistence.entities.CategoryEntity;
import com.infosociety.social.persistence.entities.TopicEntity;
import com.infosociety.social.persistence.repositories.CategoriesRepository;
import com.infosociety.social.persistence.repositories.TopicsRepository;
import com.infosociety.social.types.Category;
import com.infosociety.social.types.Expertise;

/**
 * ExpertiseProviderBean.
 *
 * @author Baruch Speiser, Cambium.
 */
@Component
public class ExpertiseProviderBean implements ExpertiseProvider {
  private static final Logger log = LoggerFactory.getLogger(ExpertiseProviderBean.class);
  @Autowired
  private TopicsRepository topicsRepository;
  @Autowired
  private CategoriesRepository categoriesRepository;
  
  /** @param repository */
  public void setTopicsRepository(TopicsRepository repository) {
    this.topicsRepository = repository;
  }

  /** @param repository */
  public void setCategoriesRepository(CategoriesRepository repository) {
    this.categoriesRepository = repository;
  }

  @Override
  public List<Expertise> getAllExpertise() {
    List<CategoryEntity> clist = this.categoriesRepository.findAll();
    log.trace("Found {} categories", clist.size());
    Map<Integer, CategoryEntity> cmap = new HashMap<>();
    for(CategoryEntity c : clist) {
      cmap.put(c.id, c);
    }
    List<TopicEntity> tlist = this.topicsRepository.findAll();
    log.trace("Found {} topics", tlist.size());
    //dehydrate:
    List<Expertise> expertise = new ArrayList<>(tlist.size());
    for(TopicEntity t : tlist) {
      CategoryEntity c = cmap.get(t.categoryId);
      expertise.add(new Expertise(t.id, t.name, new Category(c.id, c.name)));
    }
    return expertise;
  }
}
