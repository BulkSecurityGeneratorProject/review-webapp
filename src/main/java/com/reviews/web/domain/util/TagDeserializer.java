package com.reviews.web.domain.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Deserialiser for converting semi-colon separated tags to a Set 
 * @author Varun Achar
 *
 */
public class TagDeserializer extends JsonDeserializer<Set<String>> {

	@Override
	public Set<String> deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		Set<String> tags = new HashSet<>();
		String tagList = jp.getText();
		if (!StringUtils.isEmpty(tagList)) {
			String[] splitTags = tagList.split(";");
			for (String tag : splitTags) {
				tags.add(tag.trim());
			}
		}
		return tags;
	}

}
