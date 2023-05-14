package com.thinktank.pts.agileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thinktank.pts.agileservice.model.DailyScrum;

/**
 * 
 * @author karabakaa
 * @since Apr 5, 2023
 *
 */
@Repository
public interface DailyScrumRepository extends JpaRepository<DailyScrum, Long> {

	DailyScrum findFirstBySprintIdAndStateIdOrderByIdDesc(Long sprintId, Long stateId);

}