package com.thinktank.pts.agileservice.service;

import java.util.List;
import java.util.Optional;

import com.thinktank.pts.agileservice.api.graphql.input.TopicInput;
import com.thinktank.pts.agileservice.api.graphql.output.TopicMaxOrderOutput;
import com.thinktank.pts.agileservice.model.Topic;

public interface TopicService {

	/**
	 * Method to return all list of Topics
	 * 
	 * @return list of Topic {@link com.thinktank.pts.agileservice.model.Topic}
	 */
	List<Topic> findAll();

	/**
	 * 
	 * @param topicInput
	 * @return Topic created {@link com.thinktank.pts.agileservice.model.Topic}
	 */
	Topic create(TopicInput topicInput);

	/**
	 * This method retrieves the maximum order of topics for the specified product by invoking the
	 * `getTopicMaxOrderByProduct` method of the `topicRepository`.
	 *
	 * @param productId
	 *            the ID of the product for which to retrieve the maximum order of topics
	 * @return a {@link com.thinktank.pts.agileservice.api.graphql.output.TopicMaxOrderOutput} object representing the
	 *         maximum order number of topics for the specified product
	 */
	TopicMaxOrderOutput getTopicMaxOrderByProduct(Long productId);

	/**
	 * This method retrieves the list of topics by productId by invoking the `findByProductIdOrderByDisplayOrderDesc`
	 * method of the `topicRepository`.
	 *
	 * @param productId
	 *            the ID of the product for which to retrieve the list of topics sorted by 'displayOrder' Desc
	 * @return list of Topic By productId {@link com.thinktank.pts.agileservice.model.Topic}
	 * 
	 */
	List<Topic> getTopicsByProductId(Long productId);

	/**
	 * Method to retrieve the Topic Entity by given id
	 * 
	 * @param id
	 * @return Topic {@link com.thinktank.pts.agileservice.model.Topic}
	 */
	Topic getById(Long id);

	/**
	 * 
	 * @param topicInput
	 * @return Topic updated {@link com.thinktank.pts.agileservice.model.Topic}
	 */
	Topic update(Long id, TopicInput topicInput);

	/**
	 * Method used to save topic
	 * 
	 * @param Topic
	 * @return Topic created {@link com.thinktank.pts.agileservice.model.Topic}
	 */
	Topic save(Topic topic);

	/**
	 * this method is used to retrieve topic by id
	 * 
	 * @param id
	 * @return Optional of topic {@link com.thinktank.pts.agileservice.model.Topic}
	 */
	Optional<Topic> findById(Long id);

}
