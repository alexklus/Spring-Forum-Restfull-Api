package com.omega.backend.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omega.backend.forum.model.entity.VereficationToken;

public interface VereficationTokenRepository extends JpaRepository<VereficationToken, Long>{

}
