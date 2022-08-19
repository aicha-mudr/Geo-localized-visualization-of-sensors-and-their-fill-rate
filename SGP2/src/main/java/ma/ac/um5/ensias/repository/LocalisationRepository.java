package ma.ac.um5.ensias.repository;

import ma.ac.um5.ensias.domain.Localisation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Localisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocalisationRepository extends JpaRepository<Localisation, Long> {
    @Query("select l from Localisation l where l.collectivite_1= ?1 and l.collectivite_2= ?2 and l.commune= ?4 and l.adresse= ?3")
    Optional<Localisation> findByCritere(String collectivite1,String collectivite2,String adress, String commune);

    @Query("select count(l.commune) from Localisation l")
    Long totalCommune();

}
