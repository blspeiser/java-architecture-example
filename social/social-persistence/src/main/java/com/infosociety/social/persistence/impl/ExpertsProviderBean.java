package com.infosociety.social.persistence.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infosociety.social.persistence.ExpertsProvider;
import com.infosociety.social.persistence.entities.AcademicDegreeEntity;
import com.infosociety.social.persistence.entities.CategoryEntity;
import com.infosociety.social.persistence.entities.CountryEntity;
import com.infosociety.social.persistence.entities.ExpertEntity;
import com.infosociety.social.persistence.entities.TopicEntity;
import com.infosociety.social.persistence.repositories.AcademicDegreeRepository;
import com.infosociety.social.persistence.repositories.CategoriesRepository;
import com.infosociety.social.persistence.repositories.CountriesRepository;
import com.infosociety.social.persistence.repositories.ExpertsRepository;
import com.infosociety.social.persistence.repositories.TopicsRepository;
import com.infosociety.social.types.AcademicDegree;
import com.infosociety.social.types.Category;
import com.infosociety.social.types.Country;
import com.infosociety.social.types.Expert;
import com.infosociety.social.types.Expertise;

/**
 * ExpertsProviderBean.
 *
 * @author Baruch Speiser, Cambium.
 */
@Component
public class ExpertsProviderBean implements ExpertsProvider {
  private static final Logger log = LoggerFactory.getLogger(ExpertsProviderBean.class);
  @Autowired
  private ExpertsRepository expertsRepository;
  @Autowired
  private AcademicDegreeRepository degreeRepository;
  @Autowired
  private CountriesRepository countriesRepository;
  @Autowired
  private CategoriesRepository categoriesRepository;
  @Autowired
  private TopicsRepository topicsRepository;

  /** @param repository */
  public void setExpertsRepository(ExpertsRepository repository) {
    this.expertsRepository = repository;
  }

  /** @param repository */
  public void setDegreeRepository(AcademicDegreeRepository repository) {
    this.degreeRepository = repository;
  }

  /** @param repository */
  public void setCountriesRepository(CountriesRepository repository) {
    this.countriesRepository = repository;
  }

  /** @param repository */
  public void setCategoriesRepository(CategoriesRepository repository) {
    this.categoriesRepository = repository;
  }

  /** @param repository */
  public void setTopicsRepository(TopicsRepository repository) {
    this.topicsRepository = repository;
  }

  @Override
  public Expert getBasicListing(long id) {
    Optional<ExpertEntity> result = this.expertsRepository.findById((int)id);
    log.trace("Found expert {}: {}", id, result.isPresent());
    if(!result.isPresent()) return null;
    ExpertEntity x = result.get();
    CountryEntity c = this.countriesRepository.getOne(x.countryId);
    //dehydrate:
    Expert expert = new Expert(
                x.id,
                x.firstName,
                x.lastName,
                x.position,
                x.company,
                x.zip,
                new Country(c.id, c.name, c.latitude, c.longitude),
                x.image,
                x.email,
                x.phone,
                null,
                null,
                Expert.BASIC_LISTING);
    return expert;
  }

  @Override
  public Expert getFullProfile(long id) {
    Optional<ExpertEntity> result = this.expertsRepository.findById((int)id);
    log.trace("Found expert {}: {}", id, result.isPresent());
    if(!result.isPresent()) return null;
    ExpertEntity x = result.get();
    CountryEntity c = this.countriesRepository.getOne(x.countryId);
    List<Expertise> expertise = getExpertiseForExpert(id);
    List<AcademicDegree> degrees = new ArrayList<>();
    Optional<AcademicDegreeEntity> d = this.degreeRepository.findByExpertId(x.id);
    if(d.isPresent()) {
      log.trace("Found degree for expert {}", x.id);
      degrees.add(new AcademicDegree(d.get().id, d.get().name));
    }
    //dehdryate:
    Expert expert = new Expert(
        x.id,
        x.firstName,
        x.lastName,
        x.position,
        x.company,
        x.zip,
        new Country(c.id, c.name, c.latitude, c.longitude),
        x.image,
        x.email,
        x.phone,
        expertise,
        degrees,
        Expert.FULL_PROFILE);
    return expert;
  }

  //deliberate package scope
  List<Expertise> getExpertiseForExpert(long id) {
    List<CategoryEntity> clist = this.categoriesRepository.findAll();
    log.trace("Found {} categories", clist.size());
    Map<Integer, CategoryEntity> cmap = new HashMap<>();
    for(CategoryEntity c : clist) {
      cmap.put(c.id, c);
    }
    List<TopicEntity> tlist = this.topicsRepository.findAllByExpertId((int) id);
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
