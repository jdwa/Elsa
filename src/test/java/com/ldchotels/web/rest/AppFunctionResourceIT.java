package com.ldchotels.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ldchotels.ElsaApp;
import com.ldchotels.domain.AppFunction;
import com.ldchotels.repository.AppFunctionRepository;
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
 * Integration tests for the {@link AppFunctionResource} REST controller.
 */
@SpringBootTest(classes = ElsaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AppFunctionResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AppFunctionRepository appFunctionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppFunctionMockMvc;

    private AppFunction appFunction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppFunction createEntity(EntityManager em) {
        AppFunction appFunction = new AppFunction().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return appFunction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppFunction createUpdatedEntity(EntityManager em) {
        AppFunction appFunction = new AppFunction().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return appFunction;
    }

    @BeforeEach
    public void initTest() {
        appFunction = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppFunction() throws Exception {
        int databaseSizeBeforeCreate = appFunctionRepository.findAll().size();
        // Create the AppFunction
        restAppFunctionMockMvc
            .perform(
                post("/api/app-functions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appFunction))
            )
            .andExpect(status().isCreated());

        // Validate the AppFunction in the database
        List<AppFunction> appFunctionList = appFunctionRepository.findAll();
        assertThat(appFunctionList).hasSize(databaseSizeBeforeCreate + 1);
        AppFunction testAppFunction = appFunctionList.get(appFunctionList.size() - 1);
        assertThat(testAppFunction.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAppFunction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createAppFunctionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appFunctionRepository.findAll().size();

        // Create the AppFunction with an existing ID
        appFunction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppFunctionMockMvc
            .perform(
                post("/api/app-functions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appFunction))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppFunction in the database
        List<AppFunction> appFunctionList = appFunctionRepository.findAll();
        assertThat(appFunctionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAppFunctions() throws Exception {
        // Initialize the database
        appFunctionRepository.saveAndFlush(appFunction);

        // Get all the appFunctionList
        restAppFunctionMockMvc
            .perform(get("/api/app-functions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appFunction.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void getAppFunction() throws Exception {
        // Initialize the database
        appFunctionRepository.saveAndFlush(appFunction);

        // Get the appFunction
        restAppFunctionMockMvc
            .perform(get("/api/app-functions/{id}", appFunction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appFunction.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingAppFunction() throws Exception {
        // Get the appFunction
        restAppFunctionMockMvc.perform(get("/api/app-functions/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppFunction() throws Exception {
        // Initialize the database
        appFunctionRepository.saveAndFlush(appFunction);

        int databaseSizeBeforeUpdate = appFunctionRepository.findAll().size();

        // Update the appFunction
        AppFunction updatedAppFunction = appFunctionRepository.findById(appFunction.getId()).get();
        // Disconnect from session so that the updates on updatedAppFunction are not directly saved in db
        em.detach(updatedAppFunction);
        updatedAppFunction.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restAppFunctionMockMvc
            .perform(
                put("/api/app-functions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAppFunction))
            )
            .andExpect(status().isOk());

        // Validate the AppFunction in the database
        List<AppFunction> appFunctionList = appFunctionRepository.findAll();
        assertThat(appFunctionList).hasSize(databaseSizeBeforeUpdate);
        AppFunction testAppFunction = appFunctionList.get(appFunctionList.size() - 1);
        assertThat(testAppFunction.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppFunction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingAppFunction() throws Exception {
        int databaseSizeBeforeUpdate = appFunctionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppFunctionMockMvc
            .perform(
                put("/api/app-functions").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appFunction))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppFunction in the database
        List<AppFunction> appFunctionList = appFunctionRepository.findAll();
        assertThat(appFunctionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppFunction() throws Exception {
        // Initialize the database
        appFunctionRepository.saveAndFlush(appFunction);

        int databaseSizeBeforeDelete = appFunctionRepository.findAll().size();

        // Delete the appFunction
        restAppFunctionMockMvc
            .perform(delete("/api/app-functions/{id}", appFunction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppFunction> appFunctionList = appFunctionRepository.findAll();
        assertThat(appFunctionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
