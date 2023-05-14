package com.thinktank.pts.agileservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinktank.pts.agileservice.api.graphql.input.FeatureInput;
import com.thinktank.pts.agileservice.api.graphql.input.mapper.FeatureInputMapper;
import com.thinktank.pts.agileservice.api.graphql.output.FeatureMaxOrderOutput;
import com.thinktank.pts.agileservice.model.Feature;
import com.thinktank.pts.agileservice.repository.FeatureRepository;
import com.thinktank.pts.agileservice.service.FeatureService;
import com.thinktank.pts.agileservice.service.FeatureStateValidatorService;
import com.thinktank.pts.apibase.business.service.Notification;
import com.thinktank.pts.apibase.graphql.exception.EntityValidationException;
import com.thinktank.pts.apibase.graphql.exception.UnknownIDException;

/**
 * 
 * @author hajjib
 * @since Mar 7, 2023
 *
 */
@Service
public class FeatureServiceImpl implements FeatureService {

	@Autowired
	private FeatureRepository featureRepository;

	@Autowired
	private FeatureStateValidatorService featureStateValidatorService;

	private FeatureInputMapper featureInputMapper = new FeatureInputMapper();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Feature> findAll() {
		return featureRepository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FeatureMaxOrderOutput getFeatureMaxOrderByProduct(Long productId) {
		Long maxOrder = featureRepository.getFeatureMaxOrderByProduct(productId);
		FeatureMaxOrderOutput maxOrderOutput = new FeatureMaxOrderOutput();
		maxOrderOutput.setMaxOrder(maxOrder);
		return maxOrderOutput;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Feature getById(Long id) {
		return featureRepository.getById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Feature> findById(Long id) {
		return featureRepository.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Feature save(Feature feature) {
		Notification notification = featureStateValidatorService.validateStatus(feature);
		if (notification.isErrorFree()) {
			return featureRepository.save(feature);
		} else {
			throw new EntityValidationException(notification);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Feature> getFeaturesByProductId(Long productId) {
		return featureRepository.findByProductIdOrderByDisplayOrderDesc(productId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Feature create(FeatureInput featureInput) {
		Feature feature = featureInputMapper.create(featureInput);
		return save(feature);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Feature update(Long id, FeatureInput featureInput) {
		Feature feature = featureRepository.findById(id).orElseThrow(() -> new UnknownIDException(id));
		feature = featureInputMapper.patch(featureInput, feature);
		return save(feature);
	}

}
