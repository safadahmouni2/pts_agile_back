package com.thinktank.pts.agileservice.service;

import java.util.List;
import java.util.Optional;

import com.thinktank.pts.agileservice.api.graphql.input.FeatureInput;
import com.thinktank.pts.agileservice.api.graphql.output.FeatureMaxOrderOutput;
import com.thinktank.pts.agileservice.model.Feature;

/**
 * 
 * @author hajjib
 * @since Mar 7, 2023
 *
 */
public interface FeatureService {

	/**
	 * Method to return all list of Features
	 * 
	 * @return list of Feature {@link com.thinktank.pts.agileservice.model.Feature}
	 */
	List<Feature> findAll();

	/**
	 * 
	 * @param feature
	 * @return Feature created {@link com.thinktank.pts.agileservice.model.Feature}
	 */
	Feature save(Feature feature);

	/**
	 * This method retrieves the maximum order of Features for the specified product by invoking the
	 * `getFeatureMaxOrderByProduct` method of the `FeatureRepository`.
	 *
	 * @param productId
	 *            the ID of the product for which to retrieve the maximum order of Features
	 * @return a {@link com.thinktank.pts.agileservice.api.graphql.output.FeatureMaxOrderOutput} object representing the
	 *         maximum order number of Features for the specified product
	 */
	FeatureMaxOrderOutput getFeatureMaxOrderByProduct(Long productId);

	/**
	 * This method retrieves the list of Features by productId by invoking the `findByProductIdOrderByDisplayOrderDesc`
	 * method of the `FeatureRepository`.
	 *
	 * @param productId
	 *            the ID of the product for which to retrieve the list of Features sorted by 'displayOrder' Desc
	 * @return list of Feature By productId {@link com.thinktank.pts.agileservice.model.Feature}
	 * 
	 */
	List<Feature> getFeaturesByProductId(Long productId);

	/**
	 * Returns a Feature with the given identifier.
	 *
	 * @param id
	 *            must not be {@literal null}.
	 * @return the entity Feature with the given identifier.
	 */
	Feature getById(Long id);

	/**
	 * Method used to create feature
	 * 
	 * @param featureInput
	 * @return Feature created {@link com.thinktank.pts.agileservice.model.Feature}
	 */
	Feature create(FeatureInput featureInput);

	/**
	 * 
	 * @param id
	 * @param featureInput
	 *            data to be updated
	 * @return Feature updated {@link com.thinktank.pts.agileservice.model.Feature}
	 */
	Feature update(Long id, FeatureInput featureInput);

	/**
	 * this method is used to retrieve feature by id
	 * 
	 * @param id
	 * @return Optional of feature {@link com.thinktank.pts.agileservice.model.Feature}
	 */
	Optional<Feature> findById(Long id);

}
