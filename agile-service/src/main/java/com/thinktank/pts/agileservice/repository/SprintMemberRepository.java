package com.thinktank.pts.agileservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thinktank.pts.agileservice.model.SprintMember;

/**
 * 
 * @author bettaiebs
 * @since 29 Feb 2023
 *
 */

@Repository
public interface SprintMemberRepository extends JpaRepository<SprintMember, Long> {

	List<SprintMember> findBySprintId(Long sprintId);

	List<SprintMember> findBySprintIdAndStateId(Long sprintId, long stateId);

	@Query("SELECT COUNT(sm) FROM SprintMember sm WHERE sm.sprintId=?1 and role ='Scrum master' and sm.stateId = '1030058'")
	long getCountOfScrumMasterBySprint(Long sprintId);

}
