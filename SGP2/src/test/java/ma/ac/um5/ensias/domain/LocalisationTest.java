package ma.ac.um5.ensias.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ma.ac.um5.ensias.web.rest.TestUtil;

public class LocalisationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Localisation.class);
        Localisation localisation1 = new Localisation();
        localisation1.setId(1L);
        Localisation localisation2 = new Localisation();
        localisation2.setId(localisation1.getId());
        assertThat(localisation1).isEqualTo(localisation2);
        localisation2.setId(2L);
        assertThat(localisation1).isNotEqualTo(localisation2);
        localisation1.setId(null);
        assertThat(localisation1).isNotEqualTo(localisation2);
    }
}
