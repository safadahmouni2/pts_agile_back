package com.thinktank.pts.agileservice.api.graphql.output;

import lombok.Data;

/**
 * JSON object add to used in method getFeatureMaxOrderByProduct as response
 * 
 * @author hajjib
 * @since Mar 7, 2023
 *
 */
@Data
public class FeatureMaxOrderOutput {
	private Long maxOrder;
}
