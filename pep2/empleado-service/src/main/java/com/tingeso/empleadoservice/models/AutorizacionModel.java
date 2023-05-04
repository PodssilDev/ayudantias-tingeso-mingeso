package com.tingeso.empleadoservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorizacionModel {
    private String rut;
    private String fecha;
}
