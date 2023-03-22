package tingeso.mingeso.pep1.services;

import org.springframework.stereotype.Service;

@Service
public class AdministracionService {
    public int sueldoCategoria(String categoria){
        switch(categoria){
            case "A":
                return (2500 * 8);
            case "B":
                return (2500 * 6);
            case "C":
                return (2500 * 4);
            case "D":
                return (2500 * 2);
            default:
                return 0;
        }
    }
}
