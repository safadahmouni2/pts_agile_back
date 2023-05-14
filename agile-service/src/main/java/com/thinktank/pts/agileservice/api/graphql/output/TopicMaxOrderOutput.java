package com.thinktank.pts.agileservice.api.graphql.output;

import lombok.Data;

/**
 * JSON object add to used in method getTopicMaxOrderByProduct as response
 * 
 * @author hajjib
 * @since Feb 22, 2023
 *
 */
@Data
public class TopicMaxOrderOutput {
	private Long maxOrder;
}
