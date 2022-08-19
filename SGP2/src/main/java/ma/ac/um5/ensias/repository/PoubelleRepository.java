package ma.ac.um5.ensias.repository;

import ma.ac.um5.ensias.domain.CommunePoubelleCount;
import ma.ac.um5.ensias.domain.Poubelle;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Poubelle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoubelleRepository extends JpaRepository<Poubelle, Long> {
    @Query("select p from Poubelle p where p.ref_okko like ?1")
    Optional<Poubelle> findByRefOKKo(String ref_Okko);


    @Query("select new ma.ac.um5.ensias.domain.CommunePoubelleCount(p.localisation.commune,count(p.ref_okko)) from Poubelle p group by p.localisation.commune")
    public List<CommunePoubelleCount> getTotalPoubelleByCommune();




}
