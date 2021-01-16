package pl.kamilkoszarny.iotmanager;

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
            .importPackages("pl.kamilkoszarny.iotmanager");

        noClasses()
            .that()
                .resideInAnyPackage("pl.kamilkoszarny.iotmanager.service..")
            .or()
                .resideInAnyPackage("pl.kamilkoszarny.iotmanager.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..pl.kamilkoszarny.iotmanager.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
