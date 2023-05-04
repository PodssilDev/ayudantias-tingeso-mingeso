package com.tingeso.oficinaRRHHservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoModel {
    private String rut;
    private String apellidos;
    private String nombres;
    private String fecha_nacimiento;
    private String categoria;
    private String fecha_ingreso;
}