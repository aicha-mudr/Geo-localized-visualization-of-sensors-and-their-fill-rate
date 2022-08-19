package ma.ac.um5.ensias.web.rest;

import ma.ac.um5.ensias.domain.Data;
import ma.ac.um5.ensias.domain.Moyenne;
import ma.ac.um5.ensias.domain.Poubelle;
import ma.ac.um5.ensias.domain.Situation;
import ma.ac.um5.ensias.repository.SituationRepository;
import ma.ac.um5.ensias.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link ma.ac.um5.ensias.domain.Situation}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SituationResource {

    private final Logger log = LoggerFactory.getLogger(SituationResource.class);

    private static final String ENTITY_NAME = "situation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SituationRepository situationRepository;

    public SituationResource(SituationRepository situationRepository) {
        this.situationRepository = situationRepository;
    }

    /**
     * {@code POST  /situations} : Create a new situation.
     *
     * @param situation the situation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new situation, or with status {@code 400 (Bad Request)} if the situation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/situations")
    public ResponseEntity<Situation> createSituation(@RequestBody Situation situation) throws URISyntaxException {
        log.debug("REST request to save Situation : {}", situation);
        if (situation.getId() != null) {
            throw new BadRequestAlertException("A new situation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Situation result = situationRepository.save(situation);
        return ResponseEntity.created(new URI("/api/situations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /situations} : Updates an existing situation.
     *
     * @param situation the situation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated situation,
     * or with status {@code 400 (Bad Request)} if the situation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the situation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/situations")
    public ResponseEntity<Situation> updateSituation(@RequestBody Situation situation) throws URISyntaxException {
        log.debug("REST request to update Situation : {}", situation);
        if (situation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Situation result = situationRepository.save(situation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, situation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /situations} : get all the situations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of situations in body.
     */
    @GetMapping("/situations")
    public ResponseEntity<List<Situation>> getAllSituations(Pageable pageable) {
        log.debug("REST request to get a page of Situations");
        Page<Situation> page = situationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /situations/:id} : get the "id" situation.
     *
     * @param id the id of the situation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the situation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/situations/{id}")
    public ResponseEntity<Situation> getSituation(@PathVariable Long id) {
        log.debug("REST request to get Situation : {}", id);
        Optional<Situation> situation = situationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(situation);
    }

    /**
     * {@code DELETE  /situations/:id} : delete the "id" situation.
     *
     * @param id the id of the situation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/situations/{id}")
    public ResponseEntity<Void> deleteSituation(@PathVariable Long id) {
        log.debug("REST request to delete Situation : {}", id);
        situationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/situations/poubelle")
    public ResponseEntity<List<Situation>> getSituationPoubelle(@RequestBody Data data)
    {
        log.debug("REST request to get Situation of poubelle : {}", data.getIdPoubelle());
        return ResponseEntity.ok().body(situationRepository.situationPoubelleByDate(data.getMois(),data.getAnne(),data.getIdPoubelle()));

    }

    @GetMapping("/situations/by-date/{date}")
    public ResponseEntity<List<Situation>> getSituationByDate(@PathVariable String date)
    {
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Situation> situations=situationRepository.findAllByDate(LocalDate.parse(date,formatter ));
        return ResponseEntity.ok().body(situations);

    }

    @PostMapping("/situations/moyen")
    public ResponseEntity<List<Moyenne>> getMoyenneByPoubelleAndYear(@RequestBody Data data)
    {
        List<Situation> situations = situationRepository.situationPoubelleByYear(data.getAnne(),data.getIdPoubelle());
        Poubelle poubelle =situations.isEmpty()?new Poubelle():situations.get(0).getPoubelle();
        List<Moyenne> moyennes = new ArrayList<>();
        int total;
        for(int i =1; i<=12; i++)
        {
            final int mois= i;
            Float somme = (float) 0;
            List<Situation> situationList = situations.stream().filter(situation -> situation.getDate().getMonthValue()==mois).collect(Collectors.toList());
            for (Situation situation:situationList)
            {
                somme=somme+situation.getRemplissage();
            }
            total=situationList.size();
            moyennes.add(new Moyenne(poubelle.getRef_okko(), total==0?(float) 0:somme/total,data.getAnne(),(long) i));
        }
        return ResponseEntity.ok().body(moyennes);
    }
    @GetMapping("/situations/all")
    public ResponseEntity<List<Situation>> createSituation()   {
        log.debug("REST request to get all Situation : {}");
        return ResponseEntity.ok()
            .body(situationRepository.findAll());
    }
}
