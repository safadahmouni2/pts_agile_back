package com.thinktank.pts.agileservice.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinktank.pts.agileservice.api.graphql.input.DailyScrumInput;
import com.thinktank.pts.agileservice.api.graphql.input.mapper.DailyScrumInputMapper;
import com.thinktank.pts.agileservice.enums.DailyScrumStatusInfo;
import com.thinktank.pts.agileservice.model.DailyScrum;
import com.thinktank.pts.agileservice.model.MyDailyScrumDto;
import com.thinktank.pts.agileservice.repository.DailyScrumRepository;
import com.thinktank.pts.agileservice.repository.SprintRepository;
import com.thinktank.pts.agileservice.service.DailyScrumService;
import com.thinktank.pts.agileservice.service.DailyScrumStateValidatorService;
import com.thinktank.pts.apibase.business.service.Notification;
import com.thinktank.pts.apibase.graphql.exception.EntityValidationException;
import com.thinktank.pts.apibase.graphql.exception.UnknownIDException;

/**
 * 
 * @author karabakaa
 * @since Apr 5, 2023
 *
 */
@Service
public class DailyScrumServiceImpl implements DailyScrumService {

	private static final Long STARTED_DS_STATE_ID = 1017898L;
	private static final Long IN_PROGRESS_SPRINT_STATE_ID = 1017798L;

	@Autowired
	private DailyScrumRepository dailyScrumRepository;

	@Autowired
	private SprintRepository sprintRepository;

	@Autowired
	private DailyScrumStateValidatorService dailyScrumStateValidatorService;

	private DailyScrumInputMapper dailyScrumInputMapper = new DailyScrumInputMapper();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DailyScrum create(DailyScrum dailyScrum) {
		return dailyScrumRepository.save(dailyScrum);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	// TODO removed to load the ds participant list (due to no session exception)
	// @Transactional(propagation = Propagation.REQUIRES_NEW)
	public DailyScrum update(Long id, DailyScrumInput input) {

		DailyScrum ds = getById(id).orElseThrow(() -> new UnknownIDException(id));

		DailyScrum dailyScrum = dailyScrumInputMapper.patch(input, ds);

		return save(dailyScrum);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DailyScrum save(DailyScrum dailyScrum) {
		Notification notification = dailyScrumStateValidatorService.validateStatus(dailyScrum);
		if (notification.isErrorFree()) {
			return dailyScrumRepository.save(dailyScrum);
		} else {
			throw new EntityValidationException(notification);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<DailyScrum> getById(Long id) {
		return dailyScrumRepository.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DailyScrum getStartedDailyScrumBySprintId(Long sprintId) {
		return dailyScrumRepository.findFirstBySprintIdAndStateIdOrderByIdDesc(sprintId, STARTED_DS_STATE_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<MyDailyScrumDto> getMyDailyScrums(List<Long> productIds) {

		LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
		LocalDateTime endOfDate = startOfDay.toLocalDate().atTime(LocalTime.MAX);

		List<MyDailyScrumDto> result = sprintRepository.getMyDailyScrums(startOfDay, endOfDate, productIds,
				IN_PROGRESS_SPRINT_STATE_ID);

		for (MyDailyScrumDto myDs : result) {
			LocalTime dsEndTime = LocalTime.MAX;

			if (myDs.getDsStartTime() != null) {
				if (myDs.getDsDuration() != null) {
					dsEndTime = myDs.getDsStartTime().plusMinutes(myDs.getDsDuration());
				} else {
					dsEndTime = myDs.getDsStartTime();
				}

			}

			myDs.setDsEndTime(dsEndTime);
			myDs.setDsStatusInfo(getDsStatusInfo(myDs));

		}

		return result;
	}

	private String getDsStatusInfo(MyDailyScrumDto myDs) {
		String result = null;

		// ds not started
		if (myDs.getDsId() == null) {
			if (myDs.getDsEndTime().isBefore(LocalTime.now())) {

				result = DailyScrumStatusInfo.NOT_DONE.getValue();
			} else {
				result = DailyScrumStatusInfo.PLANNED.getValue();
			}

		} else {
			if (myDs.getDsFinishedAt() == null) {
				result = DailyScrumStatusInfo.NOW.getValue();
			} else {
				if (myDs.getDsEndTime().isBefore(myDs.getDsFinishedAt().toLocalTime())) {
					result = DailyScrumStatusInfo.DONE.getValue();
				} else {
					result = DailyScrumStatusInfo.DONE_EARLIER.getValue();
				}
			}
		}
		return result;

	}

}