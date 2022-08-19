package ma.ac.um5.ensias.web.rest;

import ma.ac.um5.ensias.SgpDbApp;
import ma.ac.um5.ensias.domain.Situation;
import ma.ac.um5.ensias.repository.SituationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SituationResource} REST controller.
 */
@SpringBootTest(classes = SgpDbApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SituationResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_VOLUME = 1F;
    private static final Float UPDATED_VOLUME = 2F;

    private static final Float DEFAULT_REMPLISSAGE = 1F;
    private static final Float UPDATED_REMPLISSAGE = 2F;

    @Autowired
    private SituationRepository situationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSituationMockMvc;

    private Situation situation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Situation createEntity(EntityManager em) {
        Situation situation = new Situation()
            .date(DEFAULT_DATE)
            .volume(DEFAULT_VOLUME)
            .remplissage(DEFAULT_REMPLISSAGE);
        return situation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Situation createUpdatedEntity(EntityManager em) {
        Situation situation = new Situation()
            .date(UPDATED_DATE)
            .volume(UPDATED_VOLUME)
            .remplissage(UPDATED_REMPLISSAGE);
        return situation;
    }

    @BeforeEach
    public void initTest() {
        situation = createEntity(em);
    }

    @Test
    @Transactional
    public void createSituation() throws Exception {
        int databaseSizeBeforeCreate = situationRepository.findAll().size();
        // Create the Situation
        restSituationMockMvc.perform(post("/api/situations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(situation)))
            .andExpect(status().isCreated());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeCreate + 1);
        Situation testSituation = situationList.get(situationList.size() - 1);
        assertThat(testSituation.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSituation.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testSituation.getRemplissage()).isEqualTo(DEFAULT_REMPLISSAGE);
    }

    @Test
    @Transactional
    public void createSituationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = situationRepository.findAll().size();

        // Create the Situation with an existing ID
        situation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSituationMockMvc.perform(post("/api/situations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(situation)))
            .andExpect(status().isBadRequest());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSituations() throws Exception {
        // Initialize the database
        situationRepository.saveAndFlush(situation);

        // Get all the situationList
        restSituationMockMvc.perform(get("/api/situations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(situation.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].remplissage").value(hasItem(DEFAULT_REMPLISSAGE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getSituation() throws Exception {
        // Initialize the database
        situationRepository.saveAndFlush(situation);

        // Get the situation
        restSituationMockMvc.perform(get("/api/situations/{id}", situation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(situation.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.remplissage").value(DEFAULT_REMPLISSAGE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSituation() throws Exception {
        // Get the situation
        restSituationMockMvc.perform(get("/api/situations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSituation() throws Exception {
        // Initialize the database
        situationRepository.saveAndFlush(situation);

        int databaseSizeBeforeUpdate = situationRepository.findAll().size();

        // Update the situation
        Situation updatedSituation = situationRepository.findById(situation.getId()).get();
        // Disconnect from session so that the updates on updatedSituation are not directly saved in db
        em.detach(updatedSituation);
        updatedSituation
            .date(UPDATED_DATE)
            .volume(UPDATED_VOLUME)
            .remplissage(UPDATED_REMPLISSAGE);

        restSituationMockMvc.perform(put("/api/situations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSituation)))
            .andExpect(status().isOk());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeUpdate);
        Situation testSituation = situationList.get(situationList.size() - 1);
        assertThat(testSituation.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSituation.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testSituation.getRemplissage()).isEqualTo(UPDATED_REMPLISSAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingSituation() throws Exception {
        int databaseSizeBeforeUpdate = situationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSituationMockMvc.perform(put("/api/situations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(situation)))
            .andExpect(status().isBadRequest());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSituation() throws Exception {
        // Initialize the database
        situationRepository.saveAndFlush(situation);

        int databaseSizeBeforeDelete = situationRepository.findAll().size();

        // Delete the situation
        restSituationMockMvc.perform(delete("/api/situations/{id}", situation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
