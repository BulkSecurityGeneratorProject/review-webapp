package com.reviews.web.repository;

import com.reviews.web.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Category entity.
 */
public interface CategoryRepository extends MongoRepository<Category, String> {

}
