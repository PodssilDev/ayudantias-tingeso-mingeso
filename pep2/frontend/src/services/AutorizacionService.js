import axios from "axios";

class AutorizacionService {
    
    IngresarAutorizacion(autorizacion){
        return axios.post(`http://localhost:8080/autorizacion`, autorizacion);
    }
}

export default new AutorizacionService();