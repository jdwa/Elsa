package com.ldchotels.web.rest;

import com.ldchotels.domain.AppFunction;
import com.ldchotels.repository.AppFunctionRepository;
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
 * REST controller for managing {@link com.ldchotels.domain.AppFunction}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AppFunctionResource {
    private final Logger log = LoggerFactory.getLogger(AppFunctionResource.class);

    private static final String ENTITY_NAME = "appFunction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppFunctionRepository appFunctionRepository;

    public AppFunctionResource(AppFunctionRepository appFunctionRepository) {
        this.appFunctionRepository = appFunctionRepository;
    }

    /**
     * {@code POST  /app-functions} : Create a new appFunction.
     *
     * @param appFunction the appFunction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appFunction, or with status {@code 400 (Bad Request)} if the appFunction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-functions")
    public ResponseEntity<AppFunction> createAppFunction(@RequestBody AppFunction appFunction) throws URISyntaxException {
        log.debug("REST request to save AppFunction : {}", appFunction);
        if (appFunction.getId() != null) {
            throw new BadRequestAlertException("A new appFunction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppFunction result = appFunctionRepository.save(appFunction);
        return ResponseEntity
            .created(new URI("/api/app-functions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-functions} : Updates an existing appFunction.
     *
     * @param appFunction the appFunction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appFunction,
     * or with status {@code 400 (Bad Request)} if the appFunction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appFunction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-functions")
    public ResponseEntity<AppFunction> updateAppFunction(@RequestBody AppFunction appFunction) throws URISyntaxException {
        log.debug("REST request to update AppFunction : {}", appFunction);
        if (appFunction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AppFunction result = appFunctionRepository.save(appFunction);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appFunction.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /app-functions} : get all the appFunctions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appFunctions in body.
     */
    @GetMapping("/app-functions")
    public ResponseEntity<List<AppFunction>> getAllAppFunctions(Pageable pageable) {
        log.debug("REST request to get a page of AppFunctions");
        Page<AppFunction> page = appFunctionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /app-functions/:id} : get the "id" appFunction.
     *
     * @param id the id of the appFunction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appFunction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-functions/{id}")
    public ResponseEntity<AppFunction> getAppFunction(@PathVariable Long id) {
        log.debug("REST request to get AppFunction : {}", id);
        Optional<AppFunction> appFunction = appFunctionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(appFunction);
    }

    /**
     * {@code DELETE  /app-functions/:id} : delete the "id" appFunction.
     *
     * @param id the id of the appFunction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-functions/{id}")
    public ResponseEntity<Void> deleteAppFunction(@PathVariable Long id) {
        log.debug("REST request to delete AppFunction : {}", id);
        appFunctionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
