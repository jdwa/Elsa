package com.ldchotels.protel.repository;

import com.ldchotels.protel.domain.PmsBuch;
import javax.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data  repository for the PmsBuch entity.
 */
@SuppressWarnings("unused")
@Repository
@Transactional(value = "protelTransactionManager", readOnly = true)
@PersistenceContext(name = "protelEntityManagerFactory")
public interface PmsBuchRepository extends JpaRepository<PmsBuch, Long> {}
