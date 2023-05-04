package com.tingeso.oficinaRRHHservice.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "reporte_planilla")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OficinaRRHHEntity {
    @Id
    @NotNull
    private String rut;
    private String nombre_empleado;
    private String categoria;
    private Integer dedicacion;
    private Integer sueldo_mensual;
    private double bonificacion_dedicacion;
    private double horas_extras;
    private double descuentos;
    private double sueldo_bruto;
    private double previsional;
    private double salud;
    private double sueldo_final;
}