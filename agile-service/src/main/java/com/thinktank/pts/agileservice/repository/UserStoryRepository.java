package com.thinktank.pts.agileservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thinktank.pts.agileservice.model.StateCountDto;
import com.thinktank.pts.agileservice.model.UserStory;

/**
 * 
 * @author lamiam
 * @since 6 Apr 2023
 *
 */
@Repository
public interface UserStoryRepository extends JpaRepository<UserStory, Long> {

	List<UserStory> findBySprintId(Long sprintId);

	List<UserStory> findByProductId(Long productId);

	List<UserStory> findBySprintIdAndStateIdIn(Long sprintId, List<Long> states);

	@Query("SELECT new com.thinktank.pts.agileservice.model.StateCountDto(stateId,count(p)) FROM UserStory p WHERE p.sprint.id = ?1 GROUP BY p.stateId")
	List<StateCountDto> getCountUsPerStateBySprint(Long sprintId);

	Optional<UserStory> findByUsId(Long usId);

	@Query(value = "SELECT MAX(us.order_by_topic) FROM user_story us WHERE us.topic_id = ?1", nativeQuery = true)
	Long getUserStoryMaxOrder(Long topicId);

	@Query(value = "SELECT SUM(us.story_points) FROM user_story us WHERE us.sprint_id = ?1", nativeQuery = true)
	Long getUserstoryPointsDsbySprint(Long sprintId);

	@Query("SELECT us FROM UserStory us WHERE us.feature.id = :featureId ORDER BY us.orderByTopic ASC")
	List<UserStory> findByFeatureId(Long featureId);

	@Query("SELECT us FROM UserStory us WHERE us.topic.id = :topicId ORDER BY us.orderByTopic ASC")
    List<UserStory> findByTopicId(Long topicId);
	
	List<UserStory> findByProductIdAndSprintIsNull(Long productId);

	List<UserStory> findByProductIdAndFeatureIsNull(Long productId);

	List<UserStory> findByProductIdAndTopicIsNull(Long productId);

}
