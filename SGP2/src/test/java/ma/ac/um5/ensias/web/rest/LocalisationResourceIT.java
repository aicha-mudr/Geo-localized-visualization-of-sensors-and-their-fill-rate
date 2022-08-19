package ma.ac.um5.ensias.web.rest;

import ma.ac.um5.ensias.SgpDbApp;
import ma.ac.um5.ensias.domain.Localisation;
import ma.ac.um5.ensias.repository.LocalisationRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LocalisationResource} REST controller.
 */
@SpringBootTest(classes = SgpDbApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LocalisationResourceIT {

    private static final String DEFAULT_COLLECTIVITE_1 = "AAAAAAAAAA";
    private static final String UPDATED_COLLECTIVITE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_COLLECTIVITE_2 = "AAAAAAAAAA";
    private static final String UPDATED_COLLECTIVITE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_COMMUNE = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final Float DEFAULT_LOGITUDE = 1F;
    private static final Float UPDATED_LOGITUDE = 2F;

    private static final Float DEFAULT_LATITUDE = 1F;
    private static final Float UPDATED_LATITUDE = 2F;

    @Autowired
    private LocalisationRepository localisationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocalisationMockMvc;

    private Localisation localisation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localisation createEntity(EntityManager em) {
        Localisation localisation = new Localisation()
            .collectivite_1(DEFAULT_COLLECTIVITE_1)
            .collectivite_2(DEFAULT_COLLECTIVITE_2)
            .commune(DEFAULT_COMMUNE)
            .adresse(DEFAULT_ADRESSE)
            .logitude(DEFAULT_LOGITUDE)
            .latitude(DEFAULT_LATITUDE);
        return localisation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localisation createUpdatedEntity(EntityManager em) {
        Localisation localisation = new Localisation()
            .collectivite_1(UPDATED_COLLECTIVITE_1)
            .collectivite_2(UPDATED_COLLECTIVITE_2)
            .commune(UPDATED_COMMUNE)
            .adresse(UPDATED_ADRESSE)
            .logitude(UPDATED_LOGITUDE)
            .latitude(UPDATED_LATITUDE);
        return localisation;
    }

    @BeforeEach
    public void initTest() {
        localisation = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalisation() throws Exception {
        int databaseSizeBeforeCreate = localisationRepository.findAll().size();
        // Create the Localisation
        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isCreated());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeCreate + 1);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getCollectivite_1()).isEqualTo(DEFAULT_COLLECTIVITE_1);
        assertThat(testLocalisation.getCollectivite_2()).isEqualTo(DEFAULT_COLLECTIVITE_2);
        assertThat(testLocalisation.getCommune()).isEqualTo(DEFAULT_COMMUNE);
        assertThat(testLocalisation.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testLocalisation.getLogitude()).isEqualTo(DEFAULT_LOGITUDE);
        assertThat(testLocalisation.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
    }

    @Test
    @Transactional
    public void createLocalisationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localisationRepository.findAll().size();

        // Create the Localisation with an existing ID
        localisation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLocalisations() throws Exception {
        // Initialize the database
        localisationRepository.saveAndFlush(localisation);

        // Get all the localisationList
        restLocalisationMockMvc.perform(get("/api/localisations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].collectivite_1").value(hasItem(DEFAULT_COLLECTIVITE_1)))
            .andExpect(jsonPath("$.[*].collectivite_2").value(hasItem(DEFAULT_COLLECTIVITE_2)))
            .andExpect(jsonPath("$.[*].commune").value(hasItem(DEFAULT_COMMUNE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].logitude").value(hasItem(DEFAULT_LOGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getLocalisation() throws Exception {
        // Initialize the database
        localisationRepository.saveAndFlush(localisation);

        // Get the localisation
        restLocalisationMockMvc.perform(get("/api/localisations/{id}", localisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(localisation.getId().intValue()))
            .andExpect(jsonPath("$.collectivite_1").value(DEFAULT_COLLECTIVITE_1))
            .andExpect(jsonPath("$.collectivite_2").value(DEFAULT_COLLECTIVITE_2))
            .andExpect(jsonPath("$.commune").value(DEFAULT_COMMUNE))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.logitude").value(DEFAULT_LOGITUDE.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingLocalisation() throws Exception {
        // Get the localisation
        restLocalisationMockMvc.perform(get("/api/localisations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalisation() throws Exception {
        // Initialize the database
        localisationRepository.saveAndFlush(localisation);

        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();

        // Update the localisation
        Localisation updatedLocalisation = localisationRepository.findById(localisation.getId()).get();
        // Disconnect from session so that the updates on updatedLocalisation are not directly saved in db
        em.detach(updatedLocalisation);
        updatedLocalisation
            .collectivite_1(UPDATED_COLLECTIVITE_1)
            .collectivite_2(UPDATED_COLLECTIVITE_2)
            .commune(UPDATED_COMMUNE)
            .adresse(UPDATED_ADRESSE)
            .logitude(UPDATED_LOGITUDE)
            .latitude(UPDATED_LATITUDE);

        restLocalisationMockMvc.perform(put("/api/localisations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocalisation)))
            .andExpect(status().isOk());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getCollectivite_1()).isEqualTo(UPDATED_COLLECTIVITE_1);
        assertThat(testLocalisation.getCollectivite_2()).isEqualTo(UPDATED_COLLECTIVITE_2);
        assertThat(testLocalisation.getCommune()).isEqualTo(UPDATED_COMMUNE);
        assertThat(testLocalisation.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testLocalisation.getLogitude()).isEqualTo(UPDATED_LOGITUDE);
        assertThat(testLocalisation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalisationMockMvc.perform(put("/api/localisations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localisation)))
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocalisation() throws Exception {
        // Initialize the database
        localisationRepository.saveAndFlush(localisation);

        int databaseSizeBeforeDelete = localisationRepository.findAll().size();

        // Delete the localisation
        restLocalisationMockMvc.perform(delete("/api/localisations/{id}", localisation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
