package com.tingeso.marcasrelojservice.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "marcasreloj")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MarcasRelojEntity{
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    private String fecha;
    private String hora;
    private String rut;
}