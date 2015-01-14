package com.reviews.web.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception to handle any requests where the product specs could not be loaded
 * 
 * @author Varun Achar
 * @since 0.0.1
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The supplied url could not be used for fetching the specifications")
public class ProductSpecNotFoundException extends RuntimeException {

}
