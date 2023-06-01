import axios from "axios";

const API_URL = "http://localhost:8080/oficinaRRHH";

class SueldosService {
    getSueldos() {
        return axios.get(API_URL);
    }
}

export default new SueldosService();