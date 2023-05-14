package com.thinktank.pts.agileservice.service;

import java.util.List;
import java.util.Optional;

import com.thinktank.pts.agileservice.api.graphql.input.DailyScrumInput;
import com.thinktank.pts.agileservice.model.DailyScrum;
import com.thinktank.pts.agileservice.model.MyDailyScrumDto;

/**
 * 
 * @author karabakaa
 * @since Apr 5, 2023
 *
 */
public interface DailyScrumService {

	/**
	 * 
	 * @param dailyScrum
	 * @return DailyScrum created {@link com.thinktank.pts.agileservice.model.DailyScrum}
	 */
	DailyScrum create(DailyScrum dailyScrum);

	/**
	 * 
	 * @param id
	 * @param dailyScrum
	 *            input
	 * @return DailyScrum updated {@link com.thinktank.pts.agileservice.model.DailyScrum}
	 */
	DailyScrum update(Long id, DailyScrumInput input);

	/**
	 * 
	 * @param dailyScrum
	 * @return DailyScrum created {@link com.thinktank.pts.agileservice.model.DailyScrum}
	 */
	DailyScrum save(DailyScrum dailyScrum);

	/**
	 * Method used to retrieve the daily scrum by id
	 * 
	 * @param id
	 * @return Optional DailyScrum {@link com.thinktank.pts.agileservice.model.DailyScrum}
	 */
	Optional<DailyScrum> getById(Long id);

	/**
	 * 
	 * @param sprintId
	 * @return started daily scrum by sprint id {@link com.thinktank.pts.agileservice.model.DailyScrum}
	 */
	DailyScrum getStartedDailyScrumBySprintId(Long sprintId);

	/**
	 * 
	 * @param productIds
	 * @return load list of current user's daily scrums done or need to be done in the current day for his in-progress
	 *         sprints for his products order by ds finished and started time
	 */
	List<MyDailyScrumDto> getMyDailyScrums(List<Long> productIds);

}
