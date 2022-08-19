package ma.ac.um5.ensias.repository;

import ma.ac.um5.ensias.domain.Poubelle;
import ma.ac.um5.ensias.domain.Situation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Situation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SituationRepository extends JpaRepository<Situation, Long> {

    Optional<Situation> findByDateAndPoubelle(LocalDate date, Poubelle poubelle);

    @Query(value = "select * from situation s where extract(month from s.date)= ?1 and extract(year from s.date) = ?2 and s.poubelle_id= ?3",nativeQuery = true)
    List<Situation> situationPoubelleByDate(Long mois,Long anne, Long idPoubelle);


    List<Situation> findAllByDate(LocalDate date);


    @Query(value = "select * from situation s where extract(year from s.date) = ?1 and s.poubelle_id= ?2 ",nativeQuery = true)
    List<Situation> situationPoubelleByYear(Long anne, Long idPoubelle);


    @Query(value = "select p.ref_okko,AVG(s.remplissage),extract(month from s.date) from situation s " +
        "join poubelle p on p.id = s.poubelle_id" +
        "where extract(year from s.date) = ?2 and  s.poubelle_id = ?1 " +
        "group by s.poubelle_id,p.ref_okko,extract(month from s.date)",
        nativeQuery = true)
    List<Object[]> getMoyenRemplissage(Long idPoubelle, Long year);

}
