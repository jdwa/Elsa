package com.ldchotels.web.rest;

import com.ldchotels.protel.domain.PmsKunden;
import com.ldchotels.protel.repository.PmsKundenRepository;
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
 * REST controller for managing {@link com.ldchotels.domain.PmsKunden}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PmsKundenResource {
    private final Logger log = LoggerFactory.getLogger(PmsKundenResource.class);

    private final PmsKundenRepository pmsKundenRepository;

    public PmsKundenResource(PmsKundenRepository pmsKundenRepository) {
        this.pmsKundenRepository = pmsKundenRepository;
    }

    /**
     * {@code GET  /pms-kundens} : get all the pmsKundens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pmsKundens in body.
     */
    @GetMapping("/pms-kundens")
    public ResponseEntity<List<PmsKunden>> getAllPmsKundens(Pageable pageable) {
        log.debug("REST request to get a page of PmsKundens");
        Page<PmsKunden> page = pmsKundenRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pms-kundens/:id} : get the "id" pmsKunden.
     *
     * @param id the id of the pmsKunden to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pmsKunden, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pms-kundens/{id}")
    public ResponseEntity<PmsKunden> getPmsKunden(@PathVariable Long id) {
        log.debug("REST request to get PmsKunden : {}", id);
        Optional<PmsKunden> pmsKunden = pmsKundenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pmsKunden);
    }
}
