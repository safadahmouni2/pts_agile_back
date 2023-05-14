package com.thinktank.pts.agileservice.repository.specs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.criteria.JoinType;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import com.thinktank.pts.agileservice.model.Sprint;

/**
 * Specification generator. If you use string comparison make sure to lower both the search-expression and the
 * attribute.
 * 
 * @author karabakaa
 * @since Apr 20, 2023
 *
 */
public class SprintSpecs {

	private SprintSpecs() {
	}

	public static Specification<Sprint> existInProductIds(List<Long> productIds) {
		if (CollectionUtils.isNotEmpty(productIds)) {
			return (sprint, cq, cb) -> sprint.get("productId").in(productIds);
		}
		return null;
	}

	public static Specification<Sprint> equalsToSprintStateId(Long stateId) {
		return (sprint, cq, cb) -> cb.equal(sprint.get("stateId"), stateId);
	}

	public static Specification<Sprint> dsInCurrentDay() {
		LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
		LocalDateTime endOfDate = startOfDay.toLocalDate().atTime(LocalTime.MAX);

		return (sprint, cq, cb) -> cb.between(sprint.join("dailyScrums", JoinType.LEFT).get("created"),
				startOfDay, endOfDate);
	}
}
