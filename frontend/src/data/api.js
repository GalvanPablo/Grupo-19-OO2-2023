const API_URL = 'http://localhost:8080/api/';
// export const API_SECRET_KEY = 'MKYLADGA2DXKGBSKWFCLKWVM23VQK873F4587GKLB5B52PKSZKA3B8EEWX2PLPPU';

export const API_ESTACIONAMIENTO = {
    CREATE: API_URL + 'dispositivos/estacionamiento',
    DELETE: (id) => API_URL + `dispositivos/estacionamiento/${id}/baja`,
    UPDATE: (id) => API_URL + `dispositivos/estacionamiento/${id}`,
    GET_ALL: API_URL + 'dispositivos/estacionamiento',
    GET_BY_ID: (id) => API_URL + `dispositivos/estacionamiento/${id}`,

    GET_LAST_STATE: API_URL + 'mediciones/estacionamiento/ultimas',
}

export const API_RIEGO = {
    CREATE: API_URL + 'dispositivos/riego/create',
    DELETE: (id) => API_URL + `dispositivos/riego/${id}`,
    UPDATE: (id) => API_URL + `dispositivos/riego/update/${id}`,
    GET_ALL: API_URL + 'dispositivos/riego/all',
}

export const API_EVENTO = {
    GET_BY_ID_DISPOSITIVO: (id) => API_URL + `eventos/dispositivo/${id}`,
}

export const API_ZONAS = {
    GET_ALL: API_URL + 'zonas',
}

// http://localhost:8080/api/v1/auth/authenticate
export const API_AUTH = {
    LOGIN: API_URL + 'v1/auth/authenticate',
}