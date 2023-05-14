package com.thinktank.pts.agileservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinktank.pts.agileservice.model.DefaultTopic;
import com.thinktank.pts.agileservice.repository.DefaultTopicRepository;
import com.thinktank.pts.agileservice.service.DefaultTopicService;

@Service
public class DefaultTopicServiceImpl implements DefaultTopicService {

	@Autowired
	DefaultTopicRepository defaultTopicRepository;

	/**
	 * 
	 * @param defaultTopic
	 * @return DefaultTopic created {@link com.thinktank.pts.agileservice.model.DefaultTopic}
	 */
	@Override
	public DefaultTopic save(DefaultTopic defaultTopic) {
		return defaultTopicRepository.save(defaultTopic);
	}

	/**
	 * 
	 * @param productId
	 * @return DefaultTopic deleted {@link com.thinktank.pts.agileservice.model.DefaultTopic}
	 */
	@Override
	public long deleteByProductId(Long productId) {
		return defaultTopicRepository.deleteByProductId(productId);
	}

	/**
	 * 
	 * @param productId
	 * @return DefaultTopic by Given product id {@link com.thinktank.pts.agileservice.model.DefaultTopic}
	 */
	@Override
	public DefaultTopic getByProductId(Long productId) {
		return defaultTopicRepository.findByProductId(productId);
	}

	/**
	 * 
	 * @param topicId
	 * @return true if DefaultTopic exists by Given topic id , false else :method to save status of isDefault checkbox
	 *         field {@link com.thinktank.pts.agileservice.model.DefaultTopic}
	 */
	@Override
	public boolean isDefaultTopic(Long topicId) {
		return defaultTopicRepository.existsByTopicId(topicId);
	}

}
