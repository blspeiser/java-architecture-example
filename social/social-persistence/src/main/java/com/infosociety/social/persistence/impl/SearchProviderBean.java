package com.infosociety.social.persistence.impl;

import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infosociety.social.persistence.CountriesProvider;
import com.infosociety.social.persistence.ExpertsProvider;
import com.infosociety.social.persistence.SearchProvider;
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
import com.infosociety.social.types.SearchCriteria;
import com.infosociety.social.util.Pair.IntegerPair;

/**
 * SearchProviderBean.
 *
 * @author Baruch Speiser, Cambium.
 */
@Component
public class SearchProviderBean implements SearchProvider {
  private static final Logger log = LoggerFactory.getLogger(ExpertsProviderBean.class);
  @Autowired
  private TopicsRepository topicsRepository;
  @Autowired
  private CategoriesRepository categoriesRepository;
  @Autowired
  private CountriesRepository countriesRepository;
  @Autowired
  private ExpertsRepository expertsRepository;
  @Autowired
  private AcademicDegreeRepository degreeRepository;

  //Deliberately use implementation classes here so you have access to package scope:
  @Autowired
  private CountriesProviderBean countriesProvider;
  @Autowired
  private ExpertsProviderBean expertsProvider;

  /** @param repository */
  public void setTopicsRepository(TopicsRepository repository) {
    this.topicsRepository = repository;
  }

  /** @param repository */
  public void setCategoriesRepository(CategoriesRepository repository) {
    this.categoriesRepository = repository;
  }

  /** @param repository */
  public void setCountriesRepository(CountriesRepository repository) {
    this.countriesRepository = repository;
  }

  /** @param repository */
  public void setExpertsRepository(ExpertsRepository repository) {
    this.expertsRepository = repository;
  }

  /** @param repository */
  public void setDegreeRepository(AcademicDegreeRepository repository) {
    this.degreeRepository = repository;
  }

  /** @param provider */
  public void setCountriesProvider(CountriesProvider provider) {
    if(provider instanceof CountriesProviderBean) {
      this.countriesProvider = (CountriesProviderBean)provider;
    } 
  }

  /** @param provider */
  public void setExpertsProvider(ExpertsProvider provider) {
    if(provider instanceof ExpertsProviderBean) {
      this.expertsProvider = (ExpertsProviderBean)provider;
    }
  }

  @Override
  public List<Expert> searchExperts(SearchCriteria criteria) {
    if(null == criteria) {
      throw new IllegalArgumentException("You may not call this method without a SearchCriteria instance");
    }
    Set<IntegerPair> pairedSearches = new LinkedHashSet<>();
    Set<Integer> countrySearches = new LinkedHashSet<>();
    Set<Integer> expertiseSearches = new LinkedHashSet<>();
    //paired cases
    if(!isEmpty(criteria.countryIDs) && !isEmpty(criteria.expertiseIDs)) {
      for(int c : criteria.countryIDs) {
        for(int x : criteria.expertiseIDs) {
          pairedSearches.add(new IntegerPair(c, x));
        }
      }
    } else if(!isEmpty(criteria.countryIDs) && criteria.expertiseID != -1) {
      for(int c : criteria.countryIDs) {
        pairedSearches.add(new IntegerPair(c, criteria.expertiseID));
      }
    } else if(criteria.countryID != -1 && !isEmpty(criteria.expertiseIDs)) {
      for(int x : criteria.expertiseIDs) {
        pairedSearches.add(new IntegerPair(criteria.countryID, x));
      }
    } else if(criteria.countryID != -1 && criteria.expertiseID != -1) {
      pairedSearches.add(new IntegerPair(criteria.countryID,criteria.expertiseID));
    } else
    //single cases
    {
     if(!isEmpty(criteria.countryIDs)) for(int c : criteria.countryIDs) {
       countrySearches.add(c);
     }
     if(!isEmpty(criteria.expertiseIDs)) for(int x : criteria.expertiseIDs) {
       expertiseSearches.add(x);
     }
     if(criteria.countryID != -1) countrySearches.add(criteria.countryID);
     if(criteria.expertiseID != -1) expertiseSearches.add(criteria.expertiseID);
    }
    //Okay, at this point, we have identified all the relevant searches we want to perform.
    // These loops should end up being mutually exclusive so you shouldn't add the same expert twice.
    List<ExpertEntity> list = new ArrayList<>(pairedSearches.size() + countrySearches.size() + expertiseSearches.size());
    for(IntegerPair pair : pairedSearches) {
      List<ExpertEntity> temp = this.expertsRepository.findAllByTopicIdAndCountryId(pair.x, pair.y);
      log.debug("Found {} experts by topic {} and country {}", temp.size(), pair.x, pair.y);
      list.addAll(temp);
    }
    for(int c : countrySearches) {
      List<ExpertEntity> temp = this.expertsRepository.findAllByCountryId(c);
      log.debug("Found {} countries by country {}", temp.size(), c);
      list.addAll(temp);
    }
    for(int x : expertiseSearches) {
      List<ExpertEntity> temp = this.expertsRepository.findAllByTopicId(x);
      log.debug("Found {} experts by expertise {}", temp.size(), x);
      list.addAll(temp);
    }
    //Now, we have all the expert entities we want. Now we just convert them into core domain objects.
    // To save time, build a country map so we don't run repetitive country queries.
    List<Country> countries = this.countriesProvider.getAllCountries();
    log.trace("Found {} countries", countries.size());
    Map<Integer, Country> cmap = new HashMap<>();
    for(Country c : countries) {
      cmap.put(c.getID(), c);
    }
    //dehydrate:
    List<Expert> experts = new ArrayList<>(list.size());
    for(ExpertEntity x : list) {
      List<Expertise> expertise = this.expertsProvider.getExpertiseForExpert(x.id);
      log.debug("Found {} expertise by expert {}", expertise.size(), x.id);
      List<AcademicDegree> degrees = new ArrayList<>();
      Optional<AcademicDegreeEntity> d = this.degreeRepository.findByExpertId(x.id);
      if(d.isPresent()) {
        log.debug("Found degree for expert {}", x.id);
        degrees.add(new AcademicDegree(d.get().id, d.get().name));
      } else {
        log.debug("No degree for expert {}", x.id);
      }
      Expert expert = new Expert(
          x.id,
          x.firstName,
          x.lastName,
          x.position,
          x.company,
          x.zip,
          cmap.get(x.countryId),
          x.image,
          x.email,
          x.phone,
          expertise,
          degrees,
          Expert.BASIC_LISTING);
      experts.add(expert);
    }
    log.trace("Found {} experts", experts.size());
    return experts;
  }

  @Override
  public List<Expertise> searchExpertise(SearchCriteria criteria) {
    if(null == criteria) {
      throw new IllegalArgumentException("You may not call this method without a SearchCriteria instance");
    }
    List<CategoryEntity> clist = this.categoriesRepository.findAll();
    log.trace("Found {} categories", clist.size());
    List<Expertise> expertise = new ArrayList<>(clist.size() * 4); //assume that ratio just to provide a guesstimate for initial capacity
    for(CategoryEntity c : clist) {
      List<TopicEntity> tlist;
      if(null == criteria.expertiseName || criteria.expertiseName.isEmpty()) {
        tlist = this.topicsRepository.findAllByCategoryId(c.id);
        log.trace("Found {} topics by category {}", tlist.size(), c.id);
      } else {
        tlist = this.topicsRepository.findAllByCategoryIdAndNameContainsIgnoreCase(c.id, criteria.expertiseName);
        log.trace("Found {} topics by category {} and expertise '{}'", tlist.size(), c.id, criteria.expertiseName);
      }
      for(TopicEntity t : tlist) {
        //dehydrate:
        expertise.add(new Expertise(t.id, t.name, new Category(c.id, c.name)));
      }
    }
    log.trace("Found {} expertise", expertise.size());
    return expertise;
  }

  @Override
  public List<Country> searchCountries(SearchCriteria criteria) {
    if(null == criteria) {
      throw new IllegalArgumentException("You may not call this method without a SearchCriteria instance");
    }
    List<CountryEntity> list = (criteria.countryName == null || criteria.countryName.isEmpty())
        ? this.countriesRepository.findAll()
        : this.countriesRepository.findAllByNameContainsIgnoreCase(criteria.countryName);
    //dehydrate:
    List<Country> countries = new ArrayList<>(list.size());
    for(CountryEntity c : list) {
      countries.add(new Country(c.id, c.name, c.latitude, c.longitude));
    }
    log.trace("Found {} countries, criteria country name: {}", list.size(), criteria.countryName);
    return countries;
  }
  
}
