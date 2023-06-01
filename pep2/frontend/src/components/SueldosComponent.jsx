import React, { Component } from "react";
import NavbarComponent3 from "./NavbarComponent3";
import styled from "styled-components";

class SueldosComponent extends Component{
    constructor(props) {
        super(props);
        this.state = {
          sueldos: [],
        };
    }

    componentDidMount() {
        fetch("http://localhost:8080/oficinaRRHH")
          .then((response) => response.json())
          .then((data) => this.setState({ sueldos: data }));
      }

      render(){
        return(
            <div className="home">
                <NavbarComponent3 />
                <Styles>
                    <h1 className="text-center"> <b>Reporte de Planilla de Sueldos ($CLP)</b></h1>
                    <div className="f">
                        <table border="1" class="content-table">
                            <thead>

                                <tr><th width="10%">Rut</th>
                                    <th>Nombre</th>
                                    <th>Categoría</th>
                                    <th>Años de servicio</th>
                                    <th>Sueldo fijo mensual</th>
                                    <th>Bonificación años de servicio</th>
                                    <th>Pago horas extras</th>
                                    <th>Monto descuentos</th>
                                    <th>Sueldo bruto</th>
                                    <th>Cotización Previsional</th>
                                    <th>Cotización Salud</th>
                                    <th>Sueldo Final</th>
                                </tr>
                            </thead>
                            <tbody>
                                {this.state.sueldos.map((sueldos) => (
                                    <tr key={sueldos.rut}>
                                        <td>{sueldos.rut}</td>
                                        <td>{sueldos.nombre_empleado}</td>
                                        <td>{sueldos.categoria}</td>
                                        <td>{sueldos.dedicacion}</td>
                                        <td>{sueldos.sueldo_mensual}</td>
                                        <td>{sueldos.bonificacion_dedicacion}</td>
                                        <td>{sueldos.horas_extras}</td>
                                        <td>{sueldos.descuentos}</td>
                                        <td>{sueldos.sueldo_bruto}</td>
                                        <td>{sueldos.previsional}</td>
                                        <td>{sueldos.salud}</td>
                                        <td>{sueldos.sueldo_final}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </Styles>
            </div>
        )
    }
}

export default SueldosComponent;

const Styles = styled.div`


.text-center {
    text-align: center;
    justify-content: center;
    padding-top: 8px;
    font-family: "Arial Black", Gadget, sans-serif;
    font-size: 30px;
    letter-spacing: 0px;
    word-spacing: 2px;
    color: #000000;
    font-weight: 700;
    text-decoration: none solid rgb(68, 68, 68);
    font-style: normal;
    font-variant: normal;
    text-transform: uppercase;
}

.f{
    justify-content: center;
    align-items: center;
    display: flex;
}
*{
    font-family: sans-serif;
    box-sizing: content-box;
    margin: 0;
    padding: 0;
}
.content-table{
    border-collapse: collapse;
    margin: 25px 0;
    font-size: 0.8em;
    min-width: 200px;
    border-radius: 5px 5px 0 0;
    overflow: hidden;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
    margin-left: 4%;
    margin-right: 4%;
}
.content-table thead tr{
    background-color: #009879;
    color: #ffffff;
    text-align: center;
    font-weight: bold;
}
.content-table th,
.content-table td{
    padding: 12px 15px;
    text-align: center;
}
.content-table tbody tr{
    border-bottom: 1px solid #dddddd;
}
.content-table tbody tr:nth-of-type(even){
    background-color: #f3f3f3;
}
.content-table tbody tr:last-of-type{
    border-bottom: 2px solid #009879;
}
.content-table tbody tr.active-row{
    font-weight: bold;
    color: #009879;
}
`