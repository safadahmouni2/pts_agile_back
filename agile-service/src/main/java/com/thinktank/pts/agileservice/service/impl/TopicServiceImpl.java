package com.thinktank.pts.agileservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinktank.pts.agileservice.api.graphql.input.TopicInput;
import com.thinktank.pts.agileservice.api.graphql.input.mapper.TopicInputMapper;
import com.thinktank.pts.agileservice.api.graphql.output.TopicMaxOrderOutput;
import com.thinktank.pts.agileservice.model.Topic;
import com.thinktank.pts.agileservice.repository.TopicRepository;
import com.thinktank.pts.agileservice.service.TopicService;
import com.thinktank.pts.agileservice.service.TopicStateValidatorService;
import com.thinktank.pts.apibase.business.service.Notification;
import com.thinktank.pts.apibase.graphql.exception.EntityValidationException;
import com.thinktank.pts.apibase.graphql.exception.UnknownIDException;

@Service
public class TopicServiceImpl implements TopicService {

	@Autowired
	private TopicRepository topicRepository;

	private TopicInputMapper topicInputMapper = new TopicInputMapper();

	@Autowired
	private TopicStateValidatorService topicStateValidatorService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Topic> findAll() {
		return topicRepository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TopicMaxOrderOutput getTopicMaxOrderByProduct(Long productId) {
		Long maxOrder = topicRepository.getTopicMaxOrderByProduct(productId);
		TopicMaxOrderOutput maxOrderOutput = new TopicMaxOrderOutput();
		maxOrderOutput.setMaxOrder(maxOrder);
		return maxOrderOutput;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Topic getById(Long id) {
		return topicRepository.getById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Topic> findById(Long id) {
		return topicRepository.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Topic save(Topic topic) {
		Notification notification = topicStateValidatorService.validateStatus(topic);
		if (notification.isErrorFree()) {
			return topicRepository.save(topic);
		} else {
			throw new EntityValidationException(notification);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Topic> getTopicsByProductId(Long productId) {
		return topicRepository.findByProductIdOrderByDisplayOrderDesc(productId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public Topic update(Long id, TopicInput topicInput) {
		Topic topic = topicRepository.findById(id).orElseThrow(() -> new UnknownIDException(id));
		topic = topicInputMapper.patch(topicInput, topic);
		return save(topic);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Topic create(TopicInput topicInput) {
		Topic topicPayload = topicInputMapper.create(topicInput);
		return topicRepository.save(topicPayload);
	}
}