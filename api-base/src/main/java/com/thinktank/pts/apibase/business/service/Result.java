package com.thinktank.pts.apibase.business.service;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author zouhairs
 * @since 23 Mar 2023
 *
 * @param <T>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Result<T> extends Notification {

	private T value;

}