package com.thinktank.pts.agileservice.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.List;
import java.util.stream.Collectors;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinktank.pts.agileservice.api.graphql.output.FeatureMaxOrderOutput;
import com.thinktank.pts.agileservice.config.AbstractPersistenceTestBase;
import com.thinktank.pts.agileservice.model.Feature;
import com.thinktank.pts.agileservice.repository.FeatureRepository;

class FeatureServiceTest extends AbstractPersistenceTestBase {

	@Autowired
	private FeatureRepository featureRepository;

	@BeforeEach
	void before() {
		featureRepository.deleteAll();

	}

	@Autowired
	private FeatureService featureService;

	@Test
	void saveFeatureTest() {
		// ARRANGE
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom generator = new EasyRandom(parameters);
		Feature feature = generator.nextObject(Feature.class);
		feature.setId(null);
		// ACTION
		Feature created = featureService.save(feature);
		// ASSERT
		assertThat(created.getId(), is(notNullValue()));

	}

	@Test
	void getFeatureById() {
		// ARRANGE
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom generator = new EasyRandom(parameters);
		Feature feature = generator.nextObject(Feature.class);
		feature.setId(null);
		feature = featureService.save(feature);
		// ACTION
		feature = featureService.getById(feature.getId());
		// ASSERT
		assertThat(feature.getId(), is(notNullValue()));
	}

	/**
	 * {@link FeatureService#getFeaturesByProductId(Long)}
	 */
	@Test
	void testGetFeaturesByProductId_success() {
		// ARRANGE
		Long productId = 1L;
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom easyRandom = new EasyRandom(parameters);
		List<Feature> features = easyRandom.objects(Feature.class, 10).collect(Collectors.toList());
		features.forEach(feature -> {
			feature.setId(null);
			feature.setProductId(productId);
			feature = featureService.save(feature);
		});
		// Action
		List<Feature> result = featureService.getFeaturesByProductId(productId);
		// ASSERT
		assertThat(result, is(notNullValue()));
		assertThat(result.size(), is(equalTo(features.size())));
		assertThat(result.get(0).getProductId(), is(equalTo(productId)));
	}

	/**
	 * {@link FeatureService#getFeatureMaxOrderByProduct(Long)}
	 */
	@Test
	void testGetFeatureMaxOrderByProduct_success() {
		// ARRANGE
		Long productId = 1L;
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom easyRandom = new EasyRandom(parameters);
		List<Feature> features = easyRandom.objects(Feature.class, 3).collect(Collectors.toList());
		features.forEach(feature -> {
			feature.setId(null);
			feature.setProductId(productId);
			feature = featureService.save(feature);
		});
		// Action
		FeatureMaxOrderOutput result = featureService.getFeatureMaxOrderByProduct(productId);
		// ASSERT
		assertThat(result, is(notNullValue()));
		assertThat(result.getMaxOrder(),
				is(equalTo(features.stream().mapToLong(Feature::getDisplayOrder).max().orElse(-1))));
	}

	private EasyRandomParameters buildEasyRandomParameters() {
		EasyRandomParameters result = new EasyRandomParameters();
		result.excludeField(FieldPredicates.named("userStories").and(FieldPredicates.inClass(Feature.class)));
		return result;
	}
}
