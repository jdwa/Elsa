package com.ldchotels.protel.repository;

import com.ldchotels.protel.domain.PmsKunden;
import javax.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data  repository for the PmsKunden entity.
 */
@SuppressWarnings("unused")
@Repository
@Transactional(value = "protelTransactionManager", readOnly = true)
@PersistenceContext(name = "protelEntityManagerFactory")
public interface PmsKundenRepository extends JpaRepository<PmsKunden, Long> {}
