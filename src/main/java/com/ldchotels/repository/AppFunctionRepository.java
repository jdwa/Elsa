package com.ldchotels.repository;

import com.ldchotels.domain.AppFunction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AppFunction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppFunctionRepository extends JpaRepository<AppFunction, Long> {}
