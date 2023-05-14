package com.thinktank.pts.agileservice.repository;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

import java.util.List;
import java.util.stream.Collectors;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinktank.pts.agileservice.config.AbstractPersistenceTestBase;
import com.thinktank.pts.agileservice.model.Feature;

class FeatureRepositoryTest extends AbstractPersistenceTestBase {

	@Autowired
	private FeatureRepository featureRepository;

	@BeforeEach
	void before() {
		featureRepository.deleteAll();
	}

	@AfterEach
	void after() {
		featureRepository.deleteAll();
	}

	@Test
	void findAllFeatures_success() {
		// ARRANGE
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom generator = new EasyRandom(parameters);
		Feature feature = generator.nextObject(Feature.class);
		feature.setId(null);
		featureRepository.save(feature);
		// ACTION
		List<Feature> features = featureRepository.findAll();
		// ASSERT
		assertThat(features, is(not(empty())));

	}

	// test for update Feature
	@Test
	void updateFeature_success() {
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom generator = new EasyRandom(parameters);
		Feature feature = generator.nextObject(Feature.class);
		feature.setDescription("try reposetery test");
		// ACTION
		Feature featureUpdated = featureRepository.save(feature);
		// ASSERT
		assertThat(featureUpdated, is(notNullValue()));
	}

	/**
	 * Tests the {@link FeatureRepository#findByProductIdOrderByDisplayOrderDesc(Long)}.
	 */
	@Test
	void testFindByProductIdOrderByDisplayOrderDesc_Success() {
		// Arrange
		Long productId = 1L;
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom easyRandom = new EasyRandom(parameters);
		List<Feature> features = easyRandom.objects(Feature.class, 3).collect(Collectors.toList());
		features.forEach(feature -> {
			feature.setProductId(productId);
		});
		featureRepository.saveAll(features);
		// Action
		List<Feature> result = featureRepository.findByProductIdOrderByDisplayOrderDesc(productId);

		// Assert
		assertThat(result, is(not(empty())));

	}

	/**
	 * Tests the {@link FeatureRepository#getFeatureMaxOrderByProduct(Long)}.
	 */
	@Test
	void testGetFeatureMaxOrderByProduct_success() {
		// ARRANGE
		Long productId = 1L;
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom easyRandom = new EasyRandom(parameters);
		List<Feature> features = easyRandom.objects(Feature.class, 3).collect(Collectors.toList());
		features.forEach(feature -> {
			feature.setProductId(productId);
		});
		featureRepository.saveAll(features);
		// ACTION
		Long maxOrder = featureRepository.getFeatureMaxOrderByProduct(productId);
		// ASSERT
		assertThat(maxOrder, is(not(nullValue())));
	}

	private EasyRandomParameters buildEasyRandomParameters() {
		EasyRandomParameters result = new EasyRandomParameters();
		result.excludeField(FieldPredicates.named("userStories").and(FieldPredicates.inClass(Feature.class)));
		return result;
	}
}
