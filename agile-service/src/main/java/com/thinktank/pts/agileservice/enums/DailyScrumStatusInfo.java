package com.thinktank.pts.agileservice.enums;

/**
 * 
 * @author karabakaa
 * @since Apr 24, 2023
 *
 */
public enum DailyScrumStatusInfo {
	// @formatter:off
	NOT_DONE("Not done"), 
	PLANNED("Planned"), 
	NOW("Now"), 
	DONE("Done"), 
	DONE_EARLIER("Done earlier");
	// @formatter:on

	private String value;

	private DailyScrumStatusInfo(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
