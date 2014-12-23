package com.reviews.web.domain;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.reviews.web.domain.util.CustomDateTimeDeserializer;
import com.reviews.web.domain.util.CustomDateTimeSerializer;

import java.io.Serializable;

/**
 * A ExpertReview.
 */
@Document(collection = "T_EXPERTREVIEW")
public class ExpertReview extends AbstractAuditingEntity implements Serializable {

    @Id
    private String id;

    @Field("url")
    private String url;

    @Field("summary")
    private String summary;

    @Field("source")
    private String source;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExpertReview expertReview = (ExpertReview) o;

        if (id != null ? !id.equals(expertReview.id) : expertReview.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
	public String toString() {
		return "ExpertReview {id : " + id + ", url : " + url + ", summary : "
				+ summary + ", source : " + source + "}";
	}
}
