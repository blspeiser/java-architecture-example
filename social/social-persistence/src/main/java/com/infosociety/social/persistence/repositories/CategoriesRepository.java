package com.infosociety.social.persistence.repositories;

import com.infosociety.social.persistence.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CategoriesRepository.
 *
 * @author Baruch Speiser, Cambium.
 */
@Repository
public interface CategoriesRepository extends JpaRepository<CategoryEntity, Integer> {
  //base extension is sufficient
}
