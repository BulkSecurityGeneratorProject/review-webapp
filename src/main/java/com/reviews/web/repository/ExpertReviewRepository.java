package com.reviews.web.repository;

import com.reviews.web.domain.ExpertReview;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ExpertReview entity.
 */
public interface ExpertReviewRepository extends MongoRepository<ExpertReview, String> {

}
