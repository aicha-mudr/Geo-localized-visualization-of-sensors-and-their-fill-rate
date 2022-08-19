package ma.ac.um5.ensias.web.rest;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import ma.ac.um5.ensias.domain.CommunePoubelleCount;
import ma.ac.um5.ensias.domain.Localisation;
import ma.ac.um5.ensias.domain.Poubelle;
import ma.ac.um5.ensias.domain.Situation;
import ma.ac.um5.ensias.repository.LocalisationRepository;
import ma.ac.um5.ensias.repository.PoubelleRepository;
import ma.ac.um5.ensias.repository.SituationRepository;
import ma.ac.um5.ensias.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ma.ac.um5.ensias.domain.Poubelle}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PoubelleResource {

    private final Logger log = LoggerFactory.getLogger(PoubelleResource.class);

    private static final String ENTITY_NAME = "poubelle";

    @Autowired
    private LocalisationRepository localisationRepository;

    @Autowired
    private SituationRepository situationRepository;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PoubelleRepository poubelleRepository;

    public PoubelleResource(PoubelleRepository poubelleRepository) {
        this.poubelleRepository = poubelleRepository;
    }

    /**
     * {@code POST  /poubelles} : Create a new poubelle.
     *
     * @param poubelle the poubelle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new poubelle, or with status {@code 400 (Bad Request)} if the poubelle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/poubelles")
    public ResponseEntity<Poubelle> createPoubelle(@Valid @RequestBody Poubelle poubelle) throws URISyntaxException {
        log.debug("REST request to save Poubelle : {}", poubelle);
        if (poubelle.getId() != null) {
            throw new BadRequestAlertException("A new poubelle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Poubelle result = poubelleRepository.save(poubelle);
        return ResponseEntity.created(new URI("/api/poubelles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /poubelles} : Updates an existing poubelle.
     *
     * @param poubelle the poubelle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated poubelle,
     * or with status {@code 400 (Bad Request)} if the poubelle is not valid,
     * or with status {@code 500 (Internal Server Error)} if the poubelle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/poubelles")
    public ResponseEntity<Poubelle> updatePoubelle(@Valid @RequestBody Poubelle poubelle) throws URISyntaxException {
        log.debug("REST request to update Poubelle : {}", poubelle);
        if (poubelle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Poubelle result = poubelleRepository.save(poubelle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, poubelle.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /poubelles} : get all the poubelles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of poubelles in body.
     */
    @GetMapping("/poubelles")
    public ResponseEntity<List<Poubelle>> getAllPoubelles(Pageable pageable) {
        log.debug("REST request to get a page of Poubelles");
        Page<Poubelle> page = poubelleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /poubelles/:id} : get the "id" poubelle.
     *
     * @param id the id of the poubelle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the poubelle, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/poubelles/{id}")
    public ResponseEntity<Poubelle> getPoubelle(@PathVariable Long id) {
        log.debug("REST request to get Poubelle : {}", id);
        Optional<Poubelle> poubelle = poubelleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(poubelle);
    }

    /**
     * {@code DELETE  /poubelles/:id} : delete the "id" poubelle.
     *
     * @param id the id of the poubelle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/poubelles/{id}")
    public ResponseEntity<Void> deletePoubelle(@PathVariable Long id) {
        log.debug("REST request to delete Poubelle : {}", id);
        poubelleRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    @PostMapping(value = "/poubelles/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Poubelle>> upload(@RequestBody MultipartFile file) throws IOException
    {
        InputStream inputStream =  new BufferedInputStream(file.getInputStream());

        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
            .datePattern("dd-mm-yyyy")
            .addListDelimiter(";")
            .build();
        List<Localisation> localisations = Poiji.fromExcel( inputStream, PoijiExcelType.XLSX,Localisation.class,options);
//        List<Poubelle> poubelles = Poiji.fromExcel( inputStream, PoijiExcelType.XLSX,Poubelle.class,options);
        localisations.forEach(localisation -> {
            Optional<Localisation>  optionalLocalisation = localisationRepository.findByCritere(localisation.getCollectivite_1(),
                localisation.getCollectivite_2(), localisation.getAdresse(), localisation.getCommune()
            );

            if(optionalLocalisation.isPresent()) localisation.setId(optionalLocalisation.get().getId());
            Localisation savedLocalisation = localisationRepository.saveAndFlush(localisation);
            Poubelle poubelle = new Poubelle(
                localisation.getRef_okko(),localisation.getRef_mineris(),localisation.getRef_produit(),savedLocalisation
            );
            Optional<Poubelle> optionalPoubelle= poubelleRepository.findByRefOKKo(poubelle.getRef_okko());
            if(optionalPoubelle.isPresent())
            {
                poubelle.setId(optionalPoubelle.get().getId());
            }
            DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("d-MM-yyyy");
            Poubelle savedPoubelle=poubelleRepository.save(poubelle);
            Situation situation = new Situation(LocalDate.parse(localisation.getDate(),dateTimeFormatter),localisation.getVolume(),localisation.getRemplissage(),savedPoubelle);
            Optional<Situation> optionalSituation = situationRepository.findByDateAndPoubelle(situation.getDate(),situation.getPoubelle());
            if(optionalSituation.isPresent()) situation.setId(optionalSituation.get().getId());
            situationRepository.save(situation);
        });

//        poubelles.forEach(poubelle -> {
//            Optional<Poubelle> optionalPoubelle= poubelleRepository.findByRefOKKo(poubelle.getRef_okko());
//            if(optionalPoubelle.isPresent())
//            {
//                poubelle.setId(optionalPoubelle.get().getId());
//            }
//            poubelleRepository.save(poubelle);
//        });

        return ResponseEntity.ok().body(new ArrayList<>());

    }

    @GetMapping("/poubelles/total-poubelle-by-commune")
    public ResponseEntity<List<CommunePoubelleCount>> getTotalPoubelleByCommune() {
        log.debug("REST request to get total Poubelle by commune : {}");
        List<CommunePoubelleCount> communePoubelleCounts = poubelleRepository.getTotalPoubelleByCommune();
        return ResponseEntity.ok().body(communePoubelleCounts);
    }

    @GetMapping("/poubelles/all")
    public ResponseEntity<List<Poubelle>> createPoubelle()   {
        log.debug("REST request to get all Poubelle : {}");
        return ResponseEntity.ok()
            .body(poubelleRepository.findAll());
    }

}
