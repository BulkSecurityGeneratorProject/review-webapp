package com.reviews.web.domain;

import java.io.Serializable;
import java.util.List;

import org.joda.time.DateTime;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsonorg.JSONObjectDeserializer;
import com.fasterxml.jackson.datatype.jsonorg.JSONObjectSerializer;
import com.reviews.web.domain.util.CustomDateTimeDeserializer;
import com.reviews.web.domain.util.CustomDateTimeSerializer;

/**
 * A Product.
 */
@Document(collection = "T_PRODUCT")
public class Product implements Serializable {

	@Id
	private String id;

	@Field("name")
	private String name;

	@DBRef
	private Manufacturer manufacturer;

	@Field("image")
	private String image;

	@Field("wiki")
	private String wiki;

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	@Field("created_date")
	private DateTime createdDate;

	@JsonSerialize(using = JSONObjectSerializer.class)
	@JsonDeserialize(using = JSONObjectDeserializer.class)
	@Field("specs")
	private JSONObject specs;

	@Field("expert_reviews")
	private List<ExpertReview> expertReviews;

	@Field("reviews")
	private List<Review> reviews;

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	@Field("updated_date")
	private DateTime updatedDate;

	@DBRef
	private User updatedBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
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

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
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

	public DateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(DateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
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
				+ ", createdDate : " + createdDate + ", specs : " + specs
				+ ", expertReviews : " + expertReviews + ", reviews : "
				+ reviews + "}";
	}

}
