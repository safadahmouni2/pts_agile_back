package com.thinktank.pts.agileservice.service;

import java.util.List;
import java.util.Optional;

import com.thinktank.pts.agileservice.api.graphql.input.SprintInput;
import com.thinktank.pts.agileservice.model.Sprint;

/**
 * 
 * @author laifia
 * @since 28 Feb 2023
 *
 */
public interface SprintService {

	/**
	 * Method used to save sprint
	 * 
	 * @param sprint
	 * @return Sprint created {@link com.thinktank.pts.agileservice.model.Sprint}
	 */
	Sprint save(Sprint sprint);

	/**
	 * Method used to save sprint
	 * 
	 * @param sprintInput
	 * @return Sprint created {@link com.thinktank.pts.agileservice.model.Sprint}
	 */
	Sprint create(SprintInput sprintInput);

	/**
	 * This method retrieves a list of sprints for the specified product by invoking the `findByProductId`in the
	 * SprintRepository
	 * 
	 * @param productId
	 * @return List of Topics By ProductId
	 */
	List<Sprint> getSprintsByProductId(Long productId);

	/**
	 * this method is used for update to retrieve sprint by id
	 * 
	 * @param id
	 * @return Sprint created {@link com.thinktank.pts.agileservice.model.Sprint}
	 */
	Sprint getById(Long id);

	/**
	 * 
	 * @param id
	 * @param sprintInput
	 *            data to be updated
	 * @return Sprint updated {@link com.thinktank.pts.agileservice.model.Sprint}
	 */
	Sprint update(Long id, SprintInput sprintInput);

	/**
	 * this method is used to retrieve sprint by id
	 * 
	 * @param id
	 * @return Optional of sprint {@link com.thinktank.pts.agileservice.model.Sprint}
	 */
	Optional<Sprint> findById(Long id);

	/**
	 * this method is used to retrieve sprint by ticket id
	 * 
	 * @param ticketId
	 * @return Optional of sprint {@link com.thinktank.pts.agileservice.model.Sprint}
	 */
	Optional<Sprint> findByTicketId(Long ticketId);

	/**
	 * Method used to retrieve the sum of percentage of sprint by sprint ID
	 * 
	 * @param sprintId
	 * @return
	 */
	double getSprintProgress(Long sprintId);

}
