package com.thinktank.pts.agileservice.service;

import java.util.List;

import com.thinktank.pts.agileservice.api.graphql.input.SprintMemberInput;
import com.thinktank.pts.agileservice.model.SprintMember;

public interface SprintMemberService {

	/**
	 * 
	 * @param sprintMember
	 * @return SprintMember created {@link com.thinktank.pts.agileservice.model.SprintMember}
	 */
	SprintMember create(SprintMember sprintMember);

	/**
	 * 
	 * @param id
	 * @param sprintMember
	 *            input
	 * @return SprintMember updated {@link com.thinktank.pts.agileservice.model.SprintMember}
	 */
	SprintMember update(Long id, SprintMemberInput input);

	/**
	 * Method used to save a sprint member
	 * 
	 * @param sprintMember
	 * @return SprintMember created {@link com.thinktank.pts.agileservice.model.SprintMember}
	 */
	SprintMember save(SprintMember sprintMember);

	/**
	 * Method used to retrieve the list of sprint members by sprintId
	 * 
	 * @param sprintId
	 * @return List<SprintMember> created {@link com.thinktank.pts.agileservice.model.SprintMember}
	 */
	List<SprintMember> findBySprintId(Long sprintId);

	/**
	 * Method used to retrieve the list of sprint members filtered by sprintId and stateId
	 * 
	 * @param sprintId
	 * @param stateId
	 * @return List<SprintMember> created {@link com.thinktank.pts.agileservice.model.SprintMember}
	 */
	List<SprintMember> filterSprintMembers(Long sprintId, Long stateId);

	/**
	 * Method used to retrieve the sprint member by id
	 * 
	 * @param id
	 * @return SprintMember created {@link com.thinktank.pts.agileservice.model.SprintMember}
	 */
	SprintMember getById(Long id);

}
