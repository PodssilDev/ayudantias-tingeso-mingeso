import React, { Component } from "react";
import Preguntas from "./PreguntasComponent";
import styled from "styled-components";
import Navbar from "./NavbarComponent";

class PruebaFacilComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      datas: [],
    };
  }

  componentDidMount() {
    fetch("http://localhost:8080/facil")
      .then((response) => response.json())
      .then((data) => this.setState({ datas: data }));
  }

  render() {
    return (
      <HomeStyle>
        <Navbar />

        <div className="text-center">
          <h1 className="asd">
            <b>
              <u>Prueba: Modo Básico </u>
            </b>
          </h1>
          {this.state.datas.map((datas) => (
            <Preguntas
              id={datas.id}
              enunciado={datas.enunciado}
              code={datas.codigo}
              resp={datas.respuesta}
            ></Preguntas>
          ))}
        </div>
      </HomeStyle>
    );
  }
}

export default PruebaFacilComponent;


const HomeStyle = styled.nav`
.text-center {
    justify-content: center;
    display: flex;
    flex-direction: column;
    align-items: center;
    color: #FDFEFE;
}
.asd{
    padding-top: 10px;
    padding-bottom: 30px;

}

`