package com.tingeso.autorizacionservice.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "autorizacion")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AutorizacionEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String rut;
    private String fecha;
}