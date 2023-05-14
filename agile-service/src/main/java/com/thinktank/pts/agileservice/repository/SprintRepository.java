package com.thinktank.pts.agileservice.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thinktank.pts.agileservice.model.MyDailyScrumDto;
import com.thinktank.pts.agileservice.model.Sprint;

/**
 * 
 * @author laifia
 * @since 28 Feb 2023
 *
 */
@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long>, JpaSpecificationExecutor<Sprint> {

	/**
	 * method to get List of sprints by product id
	 * 
	 * @param productId
	 * @return List Topic
	 */
	List<Sprint> findByProductIdOrderByStartDateDesc(Long productId);

	Optional<Sprint> findByTicketId(Long ticketId);

	@Query("SELECT  new com.thinktank.pts.agileservice.model.MyDailyScrumDto(s.id, ds.id, s.ticketId , s.name , s.startDate, s.endDate , s.dsStartTime, s.dsDuration, ds.startedAt, ds.finishedAt, s.stateId,  s.productId ,ds.created, ds.sprint.id , "
			+ "(SELECT sm.userCode FROM SprintMember sm WHERE sm.sprintId = s.id and sm.role ='Scrum master' and sm.stateId = '1030058') , "
			+ "(COALESCE( ds.sprintProgress , (SELECT ds2.sprintProgress FROM DailyScrum ds2 WHERE ds2.sprint.id = s.id and ds2.stateId = '1017899' and ds2.id = ( SELECT max(ds3.id) FROM DailyScrum ds3 WHERE ds3.sprint.id = s.id and ds3.stateId = '1017899' GROUP by ds3.sprint.id))))) "
			+ "FROM Sprint s LEFT  JOIN DailyScrum ds ON ds.sprint.id = s.id and ds.created BETWEEN ?1 and ?2  "
			+ "WHERE s.productId in ( ?3) and s.stateId = ?4 order by s.dsStartTime desc, ds.startedAt desc ")

	List<MyDailyScrumDto> getMyDailyScrums(LocalDateTime startOfDay, LocalDateTime endOfDate, List<Long> productIds,
			Long stateId);

	@Query(value = "SELECT COALESCE(SUM(T.story_points*T.progress)/(SUM(T.story_points)*100),0) FROM ( SELECT COALESCE(S.progress*S.story_points,100) story_points, COALESCE(S.progress,0) progress FROM pts_agile.user_story AS S WHERE S.sprint_id = ?1 ) AS T", nativeQuery = true)
	double getSprintProgress(Long sprintId);

}
