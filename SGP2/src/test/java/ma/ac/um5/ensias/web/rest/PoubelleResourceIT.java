package ma.ac.um5.ensias.web.rest;

import ma.ac.um5.ensias.SgpDbApp;
import ma.ac.um5.ensias.domain.Poubelle;
import ma.ac.um5.ensias.repository.PoubelleRepository;

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
 * Integration tests for the {@link PoubelleResource} REST controller.
 */
@SpringBootTest(classes = SgpDbApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PoubelleResourceIT {

    private static final String DEFAULT_REF_OKKO = "AAAAAAAAAA";
    private static final String UPDATED_REF_OKKO = "BBBBBBBBBB";

    private static final String DEFAULT_REF_MINERIS = "AAAAAAAAAA";
    private static final String UPDATED_REF_MINERIS = "BBBBBBBBBB";

    private static final String DEFAULT_REF_PRODUIT = "AAAAAAAAAA";
    private static final String UPDATED_REF_PRODUIT = "BBBBBBBBBB";

    @Autowired
    private PoubelleRepository poubelleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPoubelleMockMvc;

    private Poubelle poubelle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Poubelle createEntity(EntityManager em) {
        Poubelle poubelle = new Poubelle()
            .ref_okko(DEFAULT_REF_OKKO)
            .ref_mineris(DEFAULT_REF_MINERIS)
            .ref_produit(DEFAULT_REF_PRODUIT);
        return poubelle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Poubelle createUpdatedEntity(EntityManager em) {
        Poubelle poubelle = new Poubelle()
            .ref_okko(UPDATED_REF_OKKO)
            .ref_mineris(UPDATED_REF_MINERIS)
            .ref_produit(UPDATED_REF_PRODUIT);
        return poubelle;
    }

    @BeforeEach
    public void initTest() {
        poubelle = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoubelle() throws Exception {
        int databaseSizeBeforeCreate = poubelleRepository.findAll().size();
        // Create the Poubelle
        restPoubelleMockMvc.perform(post("/api/poubelles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(poubelle)))
            .andExpect(status().isCreated());

        // Validate the Poubelle in the database
        List<Poubelle> poubelleList = poubelleRepository.findAll();
        assertThat(poubelleList).hasSize(databaseSizeBeforeCreate + 1);
        Poubelle testPoubelle = poubelleList.get(poubelleList.size() - 1);
        assertThat(testPoubelle.getRef_okko()).isEqualTo(DEFAULT_REF_OKKO);
        assertThat(testPoubelle.getRef_mineris()).isEqualTo(DEFAULT_REF_MINERIS);
        assertThat(testPoubelle.getRef_produit()).isEqualTo(DEFAULT_REF_PRODUIT);
    }

    @Test
    @Transactional
    public void createPoubelleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = poubelleRepository.findAll().size();

        // Create the Poubelle with an existing ID
        poubelle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoubelleMockMvc.perform(post("/api/poubelles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(poubelle)))
            .andExpect(status().isBadRequest());

        // Validate the Poubelle in the database
        List<Poubelle> poubelleList = poubelleRepository.findAll();
        assertThat(poubelleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPoubelles() throws Exception {
        // Initialize the database
        poubelleRepository.saveAndFlush(poubelle);

        // Get all the poubelleList
        restPoubelleMockMvc.perform(get("/api/poubelles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poubelle.getId().intValue())))
            .andExpect(jsonPath("$.[*].ref_okko").value(hasItem(DEFAULT_REF_OKKO)))
            .andExpect(jsonPath("$.[*].ref_mineris").value(hasItem(DEFAULT_REF_MINERIS)))
            .andExpect(jsonPath("$.[*].ref_produit").value(hasItem(DEFAULT_REF_PRODUIT)));
    }
    
    @Test
    @Transactional
    public void getPoubelle() throws Exception {
        // Initialize the database
        poubelleRepository.saveAndFlush(poubelle);

        // Get the poubelle
        restPoubelleMockMvc.perform(get("/api/poubelles/{id}", poubelle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(poubelle.getId().intValue()))
            .andExpect(jsonPath("$.ref_okko").value(DEFAULT_REF_OKKO))
            .andExpect(jsonPath("$.ref_mineris").value(DEFAULT_REF_MINERIS))
            .andExpect(jsonPath("$.ref_produit").value(DEFAULT_REF_PRODUIT));
    }
    @Test
    @Transactional
    public void getNonExistingPoubelle() throws Exception {
        // Get the poubelle
        restPoubelleMockMvc.perform(get("/api/poubelles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoubelle() throws Exception {
        // Initialize the database
        poubelleRepository.saveAndFlush(poubelle);

        int databaseSizeBeforeUpdate = poubelleRepository.findAll().size();

        // Update the poubelle
        Poubelle updatedPoubelle = poubelleRepository.findById(poubelle.getId()).get();
        // Disconnect from session so that the updates on updatedPoubelle are not directly saved in db
        em.detach(updatedPoubelle);
        updatedPoubelle
            .ref_okko(UPDATED_REF_OKKO)
            .ref_mineris(UPDATED_REF_MINERIS)
            .ref_produit(UPDATED_REF_PRODUIT);

        restPoubelleMockMvc.perform(put("/api/poubelles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPoubelle)))
            .andExpect(status().isOk());

        // Validate the Poubelle in the database
        List<Poubelle> poubelleList = poubelleRepository.findAll();
        assertThat(poubelleList).hasSize(databaseSizeBeforeUpdate);
        Poubelle testPoubelle = poubelleList.get(poubelleList.size() - 1);
        assertThat(testPoubelle.getRef_okko()).isEqualTo(UPDATED_REF_OKKO);
        assertThat(testPoubelle.getRef_mineris()).isEqualTo(UPDATED_REF_MINERIS);
        assertThat(testPoubelle.getRef_produit()).isEqualTo(UPDATED_REF_PRODUIT);
    }

    @Test
    @Transactional
    public void updateNonExistingPoubelle() throws Exception {
        int databaseSizeBeforeUpdate = poubelleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoubelleMockMvc.perform(put("/api/poubelles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(poubelle)))
            .andExpect(status().isBadRequest());

        // Validate the Poubelle in the database
        List<Poubelle> poubelleList = poubelleRepository.findAll();
        assertThat(poubelleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePoubelle() throws Exception {
        // Initialize the database
        poubelleRepository.saveAndFlush(poubelle);

        int databaseSizeBeforeDelete = poubelleRepository.findAll().size();

        // Delete the poubelle
        restPoubelleMockMvc.perform(delete("/api/poubelles/{id}", poubelle.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Poubelle> poubelleList = poubelleRepository.findAll();
        assertThat(poubelleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
