const create_puntaje = (puntaje) => {
    localStorage.setItem("puntaje", puntaje);
}

export default function add_to_puntaje(puntaje) {
    if(!localStorage.getItem("puntaje")) { 
      create_puntaje(puntaje);
    }
    else { 
      let score = Number(localStorage.getItem("puntaje"));
      score += puntaje;
      localStorage.setItem("puntaje", score);
      localStorage.setItem("restantes", (localStorage.getItem("restantes") - 1));
      if((localStorage.getItem("restantes") == 0)){
        localStorage.setItem("puntaje", score / 4,0);
      }
    }
    return localStorage.getItem("puntaje");
}
