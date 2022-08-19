package ma.ac.um5.ensias.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ma.ac.um5.ensias.web.rest.TestUtil;

public class PoubelleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Poubelle.class);
        Poubelle poubelle1 = new Poubelle();
        poubelle1.setId(1L);
        Poubelle poubelle2 = new Poubelle();
        poubelle2.setId(poubelle1.getId());
        assertThat(poubelle1).isEqualTo(poubelle2);
        poubelle2.setId(2L);
        assertThat(poubelle1).isNotEqualTo(poubelle2);
        poubelle1.setId(null);
        assertThat(poubelle1).isNotEqualTo(poubelle2);
    }
}
