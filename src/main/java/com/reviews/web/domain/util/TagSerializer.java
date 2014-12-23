/**
 * 
 */
package com.reviews.web.domain.util;

import java.io.IOException;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Serialise Set of tags into a semi-colon separated string
 * @author Varun Achar
 *
 */
public class TagSerializer extends JsonSerializer<Set<String>>{

	@Override
	public void serialize(Set<String> tags, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		
		StringBuilder builder = new StringBuilder();
		tags.forEach(tag -> builder.append(tag).append(";"));
		jgen.writeString(builder.toString());
	}

}
