package com.reviews.web.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsonorg.JSONObjectDeserializer;
import com.fasterxml.jackson.datatype.jsonorg.JSONObjectSerializer;
import com.reviews.web.domain.util.TagDeserializer;
import com.reviews.web.domain.util.TagSerializer;

/**
 * A Product.
 */
@Document(collection = "T_PRODUCT")
public class Product extends AbstractAuditingEntity implements Serializable {

	@Id
	private String id;

	@Field("name")
	private String name;

	@DBRef
	private Manufacturer manufacturer;

	@DBRef
	private Category category;

	@Field("tags")
	@JsonSerialize(using = TagSerializer.class)
	@JsonDeserialize(using = TagDeserializer.class)
	private Set<String> tags = new HashSet<>();

	@Field("image")
	private String image;

	@Field("wiki")
	private String wiki;

	@JsonSerialize(using = JSONObjectSerializer.class)
	@JsonDeserialize(using = JSONObjectDeserializer.class)
	@Field("specs")
	private JSONObject specs;

	@Field("expert_reviews")
	private List<ExpertReview> expertReviews = Collections.emptyList();

	@Field("reviews")
	private List<Review> reviews = Collections.emptyList();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
		this.tags.addAll(category.getTags());
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		if (tags.size() > 0) {
			this.tags.addAll(tags);
		} else {
			this.tags = tags;
		}
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getWiki() {
		return wiki;
	}

	public void setWiki(String wiki) {
		this.wiki = wiki;
	}

	public JSONObject getSpecs() {
		return specs;
	}

	public void setSpecs(JSONObject specs) {
		this.specs = specs;
	}

	public List<ExpertReview> getExpertReviews() {
		return expertReviews;
	}

	public void setExpertReviews(List<ExpertReview> expertReviews) {
		this.expertReviews = expertReviews;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Product product = (Product) o;

		if (id != null ? !id.equals(product.id) : product.id != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "Product {id : " + id + ", name : " + name + ", manufacturer : "
				+ manufacturer + ", image : " + image + ", wiki : " + wiki
				+ ", createdDate : " + super.getCreatedDate() + ", specs : "
				+ specs + ", expertReviews : " + expertReviews + ", reviews : "
				+ reviews + "}";
	}

}
