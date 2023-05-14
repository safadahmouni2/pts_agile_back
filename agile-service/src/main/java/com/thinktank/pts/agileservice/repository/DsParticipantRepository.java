package com.thinktank.pts.agileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thinktank.pts.agileservice.model.DsParticipant;

@Repository
public interface DsParticipantRepository extends JpaRepository<DsParticipant, Long> {

}
