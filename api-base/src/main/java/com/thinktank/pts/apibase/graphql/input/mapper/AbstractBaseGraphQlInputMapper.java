package com.thinktank.pts.apibase.graphql.input.mapper;

import org.springframework.util.Assert;

import com.thinktank.pts.apibase.graphql.AbstractGraphQlArgumentAwareBaseInput;
import com.thinktank.pts.apibase.model.base.BaseEntity;

/**
 * 
 * The parent of all graphql mappers.
 *
 * @param <D>
 * @param <E>
 */
public abstract class AbstractBaseGraphQlInputMapper<D extends AbstractGraphQlArgumentAwareBaseInput, E extends BaseEntity>
		extends GraphQlInputMapper {

	/**
	 * Builds new entity from given input.
	 * 
	 * @param input
	 * @return
	 */
	public abstract E create(D input);

	/**
	 * Patches an entity. When input is null, then null is returned, else if entity is null then call create else
	 * patchFields is called.
	 * 
	 * @param input
	 * @param entity
	 * @return patched entity
	 */
	public final E patch(D input, E entity) {
		E result = entity;

		if (input != null) {
			if (result == null) {
				result = create(input);
			} else {
				Assert.notNull(result, "patch without entity is not possible!");
				patchFields(input, entity);
			}

		} else {
			result = null;
		}

		return result;
	}

	/**
	 * 
	 * *************************************************************************************************************
	 * This method can't be called (except in case of related embeded entity) / it is called only by local patch method
	 * *************************************************************************************************************
	 * 
	 * method used to fill entity fields from input fields No logic about input or entity state is allowed inside this
	 * method except canPatch logic
	 * 
	 * @param input
	 * @param entity
	 * @return
	 */
	protected abstract E patchFields(D input, E entity);
}
