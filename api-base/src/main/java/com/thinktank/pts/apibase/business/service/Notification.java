package com.thinktank.pts.apibase.business.service;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 
 * @author zouhairs
 * @since 23 Mar 2023
 *
 */
@Data
public class Notification {

	private List<String> errors;

	public Notification() {
		this.errors = new ArrayList<>();
	}

	public void addError(String message) {
		errors.add(message);
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public boolean isErrorFree() {
		return !hasErrors();
	}

	public static void copyErrors(Notification from, Notification to) {
		for (String errorMessage : from.getErrors()) {
			to.addError(errorMessage);
		}
	}

}