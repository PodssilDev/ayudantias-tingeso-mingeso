package com.tingeso.empleadoservice.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "empleado")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmpleadoEntity{
    @Id
    @NotNull
    private String rut;
    private String apellidos;
    private String nombres;
    private String fecha_nacimiento;
    private String categoria;
    private String fecha_ingreso;
}
