package ma.ac.um5.ensias;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("ma.ac.um5.ensias");

        noClasses()
            .that()
                .resideInAnyPackage("ma.ac.um5.ensias.service..")
            .or()
                .resideInAnyPackage("ma.ac.um5.ensias.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..ma.ac.um5.ensias.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
