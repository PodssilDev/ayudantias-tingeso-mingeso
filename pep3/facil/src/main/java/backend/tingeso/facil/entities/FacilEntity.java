package backend.tingeso.facil.entities;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "prueba_facil")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FacilEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String enunciado;
    private String codigo;
    private String respuesta;
}
