package com.thinktank.pts.agileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thinktank.pts.agileservice.model.DefaultTopic;

@Repository
public interface DefaultTopicRepository extends JpaRepository<DefaultTopic, Long> {
	/**
	 * method to delete by product id
	 * 
	 * @param productId
	 */
	long deleteByProductId(Long productId);

	/**
	 * Method used to retrieve the DefaultTopic by productId
	 * 
	 * @param productId
	 * @return DefaultTopic
	 */
	DefaultTopic findByProductId(Long productId);

	/**
	 * Method used to retrieve the existence of the DefaultTopic by topicId
	 * 
	 * @param topicId
	 * @return boolean
	 */
	boolean existsByTopicId(Long topicId);

}
