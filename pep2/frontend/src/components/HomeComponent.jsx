import React, { Component } from "react";
import styled from "styled-components";
import { createGlobalStyle } from 'styled-components'


export default function Home(){
    
    return (
        <div>
            <GlobalStyle />
            <HomeStyle>
                <h1 className="text-center"> <b>MueblesStgo</b></h1>
                <div class="box-area">
                    <div class="single-box">
                        <a href="/subir-archivo">
                            <div class="img-area">
                            </div>
                        </a>
                        <div class="img-text">
                            <span class="header-text"><strong>Cargar DATA.txt</strong></span>
                        </div>
                    </div>
                    <div class="single-box">
                        <a href="/lista-empleados">
                            <div class="img-area">
                            </div>
                        </a>
                        <div class="img-text">
                            <span class="header-text"><strong>Ver empleados</strong></span>
                        </div>
                    </div>
                    <div class="single-box">
                        <a href="/planilla-sueldos">
                            <div class="img-area"></div>
                        </a>
                        <div class="img-text">
                            <span class="header-text"><strong>Planilla de sueldos</strong></span>
                            <p></p>
                        </div>
                    </div>
                    <div class="single-box">
                        <a href="/justificativo">
                            <div class="img-area"></div>
                        </a>
                        <div class="img-text">
                            <span class="header-text"><strong>Ingresar justificativos</strong></span>
                        </div>
                    </div>
                    <div class="single-box">
                        <a href="/autorizacion">
                            <div class="img-area"></div>
                        </a>
                        <div class="img-text">
                            <span class="header-text"><strong>Ingresar autorizaciones</strong></span>
                        </div>
                    </div>
                </div>
            </HomeStyle>
        </div>
    );
}



const GlobalStyle = createGlobalStyle`
    body { 
        background-color: #262626;
`
const HomeStyle = styled.nav`

.text-center {
    text-align: center;
    justify-content: center;
    padding-top: 8px;
    color: #fff;
}

.box-area{
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
}

.single-box{
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 400px;
    height: auto;
    border-radius: 4px;
    background-color: #fff;
    text-align: center;
    margin: 20px;
    padding: 20px;
    transition: .3s
}

.img-area{
    display: flex;
    justify-content: center;
    align-items: center;
    width: 80px;
    height: 80px;
    border: 6px solid #ddd;
    border-radius: 50%;
    padding: 20px;
    -webkit-background-size: cover;
    background-size: cover;
    background-position: center center;
}

.single-box:nth-child(1) .img-area{
    background-image: url(https://img.freepik.com/vector-premium/chat-archivo-documento-texto-comentando-o-editando-documentos-linea-ilustracion-dibujos-animados-plana_101884-838.jpg)
}

.header-text{
    font-size: 23px;
    font-weight: 500;
    line-height: 48px;
}
.img-text p{
    font-size: 15px;
    font-weight: 400;
    line-height: 30px;
}
.single-box:hover{
    background: #e84393;
    color: #fff;}

.single-box:nth-child(2) .img-area{
        background-image: url(http://static1.squarespace.com/static/55c7a3e2e4b0fa365689d8aa/55e0aceae4b0643202e59629/55e322ade4b077beb0266329/1590769127854/?format=1500w)
}
.single-box:nth-child(3) .img-area{
        background-image: url(https://img.freepik.com/vector-gratis/ingresos-netos-calculando-ilustracion-concepto-abstracto-calculo-sueldos-formula-ingresos-netos-salario-neto-contabilidad-corporativa-calculo-ganancias-estimacion-ganancias_335657-1238.jpg?w=2000)
}
.single-box:nth-child(4) .img-area{
        background-image: url(https://previews.123rf.com/images/magurok/magurok1606/magurok160600092/60046224-lado-la-celebraci%C3%B3n-de-solicitud-de-trabajo-aprobado-aprobado-cv-con-el-sello-la-l%C3%ADnea-delgada-plana.jpg)
}
.single-box:nth-child(5) .img-area{
        background-image: url(https://media.istockphoto.com/vectors/agreement-contract-and-offer-color-line-icon-proposal-linear-vector-vector-id1271477227?k=20&m=1271477227&s=612x612&w=0&h=XOSF2ISnfGJZ7bb-fU7rRdDJzTKehDmOF9kcJ5gIEmA=)
}

.single-box:nth-child(6) .img-area{
    background-image: url(https://media.kasperskydaily.com/wp-content/uploads/sites/88/2015/12/05222030/passwords-10x10-FB-1000x1000.jpg)
}
.login-box{
    cursor: pointer;
}
`