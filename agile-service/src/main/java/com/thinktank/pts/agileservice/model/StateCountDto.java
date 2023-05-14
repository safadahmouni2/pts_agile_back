package com.thinktank.pts.agileservice.model;

import lombok.Data;

/**
 * JSON object add to used in method getCountUsPerStateBySprint as response
 *
 * @author BBO
 * @since Apr 25, 2023
 *
 */

@Data
public class StateCountDto {

	private Long stateId;
	private Long count;

	public StateCountDto(long stateId, long count) {
		this.stateId = stateId;
		this.count = count;
	}

}
