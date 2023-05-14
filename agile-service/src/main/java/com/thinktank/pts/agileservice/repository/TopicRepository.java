package com.thinktank.pts.agileservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thinktank.pts.agileservice.model.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
	/**
	 * method to get max order by product id
	 * 
	 * @param productId
	 * @return max displayOrder
	 */
	@Query("SELECT MAX(t.displayOrder) FROM Topic t WHERE t.productId = :productId")
	Long getTopicMaxOrderByProduct(Long productId);

	/**
	 * method to get List of Topics by product id
	 * 
	 * @param productId
	 * @return List Topic
	 */
	List<Topic> findByProductIdOrderByDisplayOrderDesc(Long productId);
}