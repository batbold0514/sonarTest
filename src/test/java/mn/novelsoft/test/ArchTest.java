package mn.novelsoft.test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("mn.novelsoft.test");

        noClasses()
            .that()
            .resideInAnyPackage("mn.novelsoft.test.service..")
            .or()
            .resideInAnyPackage("mn.novelsoft.test.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..mn.novelsoft.test.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
