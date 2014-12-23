package com.reviews.web.domain;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.reviews.web.domain.util.TagDeserializer;
import com.reviews.web.domain.util.TagSerializer;

/**
 * A Category.
 */
@Document(collection = "T_CATEGORY")
public class Category extends AbstractAuditingEntity implements Serializable {

	@Id
	private String id;

	@Field("name")
	private String name;

	@JsonSerialize(using=TagSerializer.class)
	@JsonDeserialize(using=TagDeserializer.class)
	@Field("tags")
	private Set<String> tags;

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

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Category category = (Category) o;

		if (id != null ? !id.equals(category.id) : category.id != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "Category {id : " + id + ", name : " + name + ", tags : " + tags
				+ "}";
	}

}
