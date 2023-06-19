import jwt_decode from 'jwt-decode';

import { LOGIN_SUCCESS, LOGIN_FAILURE, LOGOUT } from './../actions/auth.action';


// Obtener el token almacenado en el sessionStorage
const tokenFromSessionStorage = sessionStorage.getItem('token');

// Declarar variables para almacenar el token decodificado y el rol
let decodedToken = null;
let roleFromSessionStorage = null;
let emailFromSessionStorage = null;

// Verificar si existe un token en el sessionStorage
if (tokenFromSessionStorage) {
    try {
        // Decodificar el token utilizando jwt_decode
        decodedToken = jwt_decode(tokenFromSessionStorage);

        // Extraer el rol del token decodificado
        roleFromSessionStorage = decodedToken.roles;
        emailFromSessionStorage = decodedToken.sub;


        console.log({
            token: tokenFromSessionStorage,
            role: roleFromSessionStorage,
            isAuthenticated: !!tokenFromSessionStorage,
            email: emailFromSessionStorage
        });

    } catch (error) {
        // Si ocurre un error al decodificar el token, imprimir el mensaje de error y eliminar el token del sessionStorage
        console.error('Error al decodificar el JWT almacenado en sessionStorage: ', error);
        sessionStorage.removeItem('token');
    }
}

// Definir el estado inicial del reducer
const initialState = {
    token: tokenFromSessionStorage, // Almacenar el token obtenido del sessionStorage
    email: emailFromSessionStorage, // Almacenar el email obtenido del token decodificado
    role: roleFromSessionStorage, // Almacenar el rol obtenido del token decodificado
    isAuthenticated: !!tokenFromSessionStorage, // Verificar si hay un token presente para determinar si el usuario está autenticado
};

const authReducer = (state = initialState, action) => {
    switch (action.type) {
        case LOGIN_SUCCESS:
            try {
                const decodedToken = jwt_decode(action.token);
                sessionStorage.setItem('token', action.token);
                return {
                    ...state,
                    token: action.token,
                    role: decodedToken.roles,
                    isAuthenticated: true,
                    email: decodedToken.sub
                };
            } catch (error) {
                console.error('Error al decodificar el JWT: ', error);
                sessionStorage.removeItem('token');
                return {
                    ...state,
                    token: null,
                    role: null,
                    isAuthenticated: false,
                    email: null
                };
            }
        case LOGIN_FAILURE:
            sessionStorage.removeItem('token');
            return {
                ...state,
                token: null,
                role: null,
                isAuthenticated: false,
                email: null
            };
        case LOGOUT:
            sessionStorage.removeItem('token');
            return {
                ...state,
                token: null,
                role: null,
                isAuthenticated: false,
                email: null
            };

        default:
            return state;
    }
};

export default authReducer;
