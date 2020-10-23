package com.ldchotels.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ldchotels.ElsaApp;
import com.ldchotels.protel.domain.PmsBuch;
import com.ldchotels.protel.repository.PmsBuchRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PmsBuchResource} REST controller.
 */
@SpringBootTest(classes = ElsaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PmsBuchResourceIT {
    private static final LocalDate DEFAULT_DATUMVON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMVON = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUMBIS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUMBIS = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PmsBuchRepository pmsBuchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPmsBuchMockMvc;

    private PmsBuch pmsBuch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PmsBuch createEntity(EntityManager em) {
        PmsBuch pmsBuch = new PmsBuch().datumvon(DEFAULT_DATUMVON).datumbis(DEFAULT_DATUMBIS);
        return pmsBuch;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PmsBuch createUpdatedEntity(EntityManager em) {
        PmsBuch pmsBuch = new PmsBuch().datumvon(UPDATED_DATUMVON).datumbis(UPDATED_DATUMBIS);
        return pmsBuch;
    }

    @BeforeEach
    public void initTest() {
        pmsBuch = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllPmsBuches() throws Exception {
        // Initialize the database
        pmsBuchRepository.saveAndFlush(pmsBuch);

        // Get all the pmsBuchList
        restPmsBuchMockMvc
            .perform(get("/api/pms-buches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pmsBuch.getId().intValue())))
            .andExpect(jsonPath("$.[*].datumvon").value(hasItem(DEFAULT_DATUMVON.toString())))
            .andExpect(jsonPath("$.[*].datumbis").value(hasItem(DEFAULT_DATUMBIS.toString())));
    }

    @Test
    @Transactional
    public void getPmsBuch() throws Exception {
        // Initialize the database
        pmsBuchRepository.saveAndFlush(pmsBuch);

        // Get the pmsBuch
        restPmsBuchMockMvc
            .perform(get("/api/pms-buches/{id}", pmsBuch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pmsBuch.getId().intValue()))
            .andExpect(jsonPath("$.datumvon").value(DEFAULT_DATUMVON.toString()))
            .andExpect(jsonPath("$.datumbis").value(DEFAULT_DATUMBIS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPmsBuch() throws Exception {
        // Get the pmsBuch
        restPmsBuchMockMvc.perform(get("/api/pms-buches/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
