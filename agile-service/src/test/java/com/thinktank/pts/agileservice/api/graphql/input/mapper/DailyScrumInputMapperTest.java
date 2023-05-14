package com.thinktank.pts.agileservice.api.graphql.input.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import com.thinktank.pts.agileservice.api.graphql.input.DailyScrumInput;
import com.thinktank.pts.agileservice.model.DailyScrum;

/**
 * 
 * @author karabakaa
 * @since Apr 10, 2023
 *
 */
class DailyScrumInputMapperTest {

	private DailyScrumInputMapper DailyScrumInputMapper = new DailyScrumInputMapper();

	/**
	 * {@link DailyScrumInputMapper#create(DailyScrumInput)}
	 */
	@Test
	void createDailyScrumTest() {

		EasyRandom generator = new EasyRandom();
		DailyScrumInput dailyScrumInput = generator.nextObject(DailyScrumInput.class);
		DailyScrum createdDailyScrum = DailyScrumInputMapper.create(dailyScrumInput);
		assertThat(createdDailyScrum.getStartedAt()).isEqualTo(dailyScrumInput.getStartedAt());
		assertThat(createdDailyScrum.getStateId()).isEqualTo(dailyScrumInput.getStateId());
	}

	/**
	 * {@link DailyScrumInputMapper#patchFields(DailyScrumInput, DailyScrum)}
	 */
	@Test
	void patchFieldsTest() {
		Map<String, Object> arg = new HashMap<>();

		EasyRandom generator = new EasyRandom();
		DailyScrum dailyScrum = generator.nextObject(DailyScrum.class);
		DailyScrumInput dailyScrumInput = generator.nextObject(DailyScrumInput.class);
		arg.put("finishedAt", "finishedAt");
		arg.put("stateId", "stateId");
		dailyScrumInput.setArguments(arg);
		DailyScrumInputMapper.patchFields(dailyScrumInput, dailyScrum);
		assertThat(dailyScrum.getFinishedAt()).isEqualTo(dailyScrumInput.getFinishedAt());
		assertThat(dailyScrum.getStateId()).isEqualTo(dailyScrumInput.getStateId());
	}
}
