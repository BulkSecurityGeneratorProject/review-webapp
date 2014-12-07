package com.reviews.web.repository;

import com.reviews.web.domain.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Review entity.
 */
public interface ReviewRepository extends MongoRepository<Review, String> {

}
