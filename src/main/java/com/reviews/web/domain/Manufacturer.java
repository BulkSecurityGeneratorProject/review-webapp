package com.reviews.web.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Manufacturer.
 */
@Document(collection = "T_MANUFACTURER")
public class Manufacturer extends AbstractAuditingEntity implements Serializable  {

	@Id
	private String id;

	@Field("name")
	private String name;

	@Field("website")
	private String website;

	@Field("wiki")
	private String wiki;

	@Field("logo")
	private String logo;
	
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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getWiki() {
		return wiki;
	}

	public void setWiki(String wiki) {
		this.wiki = wiki;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Manufacturer manufacturer = (Manufacturer) o;

		if (id != null ? !id.equals(manufacturer.id) : manufacturer.id != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "Manufacturer {id : " + id + ", name : " + name + ", website : "
				+ website + ", wiki : " + wiki + ", logo : " + logo + "}";
	}
}
