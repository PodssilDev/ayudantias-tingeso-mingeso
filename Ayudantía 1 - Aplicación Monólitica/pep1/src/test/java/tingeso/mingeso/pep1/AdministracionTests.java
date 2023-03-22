package tingeso.mingeso.pep1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tingeso.mingeso.pep1.services.AdministracionService;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AdministracionTests {

    @Autowired
    AdministracionService administracionService;

    @Test
    void testSueldoCategoria1(){
        int sueldo = administracionService.sueldoCategoria("A");
        assertEquals(20000, sueldo, 0.0);
    }

    @Test
    void testSueldoCategoria2(){
        int sueldo = administracionService.sueldoCategoria("B");
        assertEquals(25000, sueldo, 0.0);
    }
}
