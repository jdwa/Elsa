package com.ldchotels.web.rest;

import com.ldchotels.protel.domain.PmsBuch;
import com.ldchotels.protel.repository.PmsBuchRepository;
import com.ldchotels.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link com.ldchotels.domain.PmsBuch}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PmsBuchResource {
    private final Logger log = LoggerFactory.getLogger(PmsBuchResource.class);

    private final PmsBuchRepository pmsBuchRepository;

    public PmsBuchResource(PmsBuchRepository pmsBuchRepository) {
        this.pmsBuchRepository = pmsBuchRepository;
    }

    /**
     * {@code GET  /pms-buches} : get all the pmsBuches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pmsBuches in body.
     */
    @GetMapping("/pms-buches")
    public ResponseEntity<List<PmsBuch>> getAllPmsBuches(Pageable pageable) {
        log.debug("REST request to get a page of PmsBuches");
        Page<PmsBuch> page = pmsBuchRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pms-buches/:id} : get the "id" pmsBuch.
     *
     * @param id the id of the pmsBuch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pmsBuch, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pms-buches/{id}")
    public ResponseEntity<PmsBuch> getPmsBuch(@PathVariable Long id) {
        log.debug("REST request to get PmsBuch : {}", id);
        Optional<PmsBuch> pmsBuch = pmsBuchRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pmsBuch);
    }
}
