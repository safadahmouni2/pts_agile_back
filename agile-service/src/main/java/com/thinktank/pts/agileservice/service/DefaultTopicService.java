package com.thinktank.pts.agileservice.service;

import com.thinktank.pts.agileservice.model.DefaultTopic;

public interface DefaultTopicService {
	/**
	 * 
	 * @param defaultTopic
	 * @return DefaultTopic created {@link com.thinktank.pts.agileservice.model.DefaultTopic}
	 */
	DefaultTopic save(DefaultTopic defaultTopic);

	/**
	 * 
	 * @param productId
	 * @return DefaultTopic deleted {@link com.thinktank.pts.agileservice.model.DefaultTopic}
	 */
	long deleteByProductId(Long productId);

	/**
	 * 
	 * @param productId
	 * @return DefaultTopic by Given product id {@link com.thinktank.pts.agileservice.model.DefaultTopic}
	 */
	DefaultTopic getByProductId(Long productId);

	/**
	 * 
	 * @param topicId
	 * @return Method used to retrieve the existence of the DefaultTopic by topicId
	 *         {@link com.thinktank.pts.agileservice.model.DefaultTopic}
	 */
	boolean isDefaultTopic(Long topicId);

}
