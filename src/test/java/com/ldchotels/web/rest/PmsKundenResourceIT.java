package com.ldchotels.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ldchotels.ElsaApp;
import com.ldchotels.protel.domain.PmsKunden;
import com.ldchotels.protel.repository.PmsKundenRepository;
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
 * Integration tests for the {@link PmsKundenResource} REST controller.
 */
@SpringBootTest(classes = ElsaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PmsKundenResourceIT {
    private static final String DEFAULT_NAME_1 = "AAAAAAAAAA";
    private static final String UPDATED_NAME_1 = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_2 = "AAAAAAAAAA";
    private static final String UPDATED_NAME_2 = "BBBBBBBBBB";

    @Autowired
    private PmsKundenRepository pmsKundenRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPmsKundenMockMvc;

    private PmsKunden pmsKunden;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PmsKunden createEntity(EntityManager em) {
        PmsKunden pmsKunden = new PmsKunden().name1(DEFAULT_NAME_1).name2(DEFAULT_NAME_2);
        return pmsKunden;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PmsKunden createUpdatedEntity(EntityManager em) {
        PmsKunden pmsKunden = new PmsKunden().name1(UPDATED_NAME_1).name2(UPDATED_NAME_2);
        return pmsKunden;
    }

    @BeforeEach
    public void initTest() {
        pmsKunden = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllPmsKundens() throws Exception {
        // Initialize the database
        pmsKundenRepository.saveAndFlush(pmsKunden);

        // Get all the pmsKundenList
        restPmsKundenMockMvc
            .perform(get("/api/pms-kundens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pmsKunden.getId().intValue())))
            .andExpect(jsonPath("$.[*].name1").value(hasItem(DEFAULT_NAME_1)))
            .andExpect(jsonPath("$.[*].name2").value(hasItem(DEFAULT_NAME_2)));
    }

    @Test
    @Transactional
    public void getPmsKunden() throws Exception {
        // Initialize the database
        pmsKundenRepository.saveAndFlush(pmsKunden);

        // Get the pmsKunden
        restPmsKundenMockMvc
            .perform(get("/api/pms-kundens/{id}", pmsKunden.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pmsKunden.getId().intValue()))
            .andExpect(jsonPath("$.name1").value(DEFAULT_NAME_1))
            .andExpect(jsonPath("$.name2").value(DEFAULT_NAME_2));
    }

    @Test
    @Transactional
    public void getNonExistingPmsKunden() throws Exception {
        // Get the pmsKunden
        restPmsKundenMockMvc.perform(get("/api/pms-kundens/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
