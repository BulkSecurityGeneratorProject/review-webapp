package com.reviews.web.repository;

import com.reviews.web.domain.Manufacturer;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Manufacturer entity.
 */
public interface ManufacturerRepository extends MongoRepository<Manufacturer, String> {

}
