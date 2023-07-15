import React, { useState, useEffect } from 'react';
import styled from 'styled-components';

function Cronometro() {
  const [segundos, setSegundos] = useState(0);
  const [minutos, setMinutos] = useState(0);
  const [horas, setHoras] = useState(0);

  useEffect(() => {
    const cronometroID = setInterval(() => {
      setSegundos(segundos => segundos + 1);
    }, 1000);

    return () => {
      clearInterval(cronometroID);
    };
  }, []);

  useEffect(() => {
    if (segundos >= 60) {
      setSegundos(0);
      setMinutos(minutos => minutos + 1);
    }
  }, [segundos]);

  useEffect(() => {
    if (minutos >= 60) {
      setMinutos(0);
      setHoras(horas => horas + 1);
    }
  }, [minutos]);

  useEffect(() => {
    // Guardar tiempo en el localStorage
    localStorage.setItem('tiempoCronometro', JSON.stringify({ horas, minutos, segundos }));
  }, [horas, minutos, segundos]);
  localStorage.setItem("segundos", segundos);
  localStorage.setItem("minutos", minutos);
  localStorage.setItem("horas", horas);

  const reiniciarCronometro = () => {
    setSegundos(0);
    setMinutos(0);
    setHoras(0);
  };

  const formatoTiempo = valor => {
    return valor < 10 ? `0${valor}` : valor;
  };

  return (
    <div>
        <HomeStyle>
      <div className='cronometro'>{`${formatoTiempo(horas)}:${formatoTiempo(minutos)}:${formatoTiempo(segundos)}`}</div>
      </HomeStyle>
    </div>
  );
}

export default Cronometro;

const HomeStyle = styled.nav`
.cronometro {
    font-family: Arial, sans-serif;
    font-size: 48px;
    text-align: center;
    margin-bottom: 20px;
  }
  
  button {
    background-color: #4CAF50;
    border: none;
    color: white;
    padding: 10px 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 10px;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #45a049;
  }
  
  button:active {
    background-color: #3e8e41;
  }  
`;