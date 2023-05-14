package com.thinktank.pts.agileservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thinktank.pts.agileservice.model.Feature;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
	/**
	 * method to get max order by product id
	 * 
	 * @param productId
	 * @return max displayOrder
	 */

	@Query("SELECT MAX(f.displayOrder) FROM Feature f WHERE f.productId = :productId")
	Long getFeatureMaxOrderByProduct(Long productId);

	/**
	 * method to get List of Features by product id
	 * 
	 * @param productId
	 * 
	 * @return List Feature
	 */

	List<Feature> findByProductIdOrderByDisplayOrderDesc(Long productId);
}
