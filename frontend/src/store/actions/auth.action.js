import { Report } from 'notiflix/build/notiflix-report-aio';

import { API_AUTH } from "./../../data/api";

export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'LOGIN_FAILURE';
export const LOGOUT = 'LOGOUT';

export const login = (username, password) => async (dispatch) => {
    try {
        const response = await fetch(API_AUTH.LOGIN, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email: username, password })
        });

        if (response.ok) {
            const { token } = await response.json();

            sessionStorage.setItem('token', token);

            dispatch({
                type: LOGIN_SUCCESS,
                token
            });
        } else {
            const data = response;
            if(data.status === 403){
                Report.failure(
                    'Error',
                    'Usuario o contraseña incorrectos, o cuenta inexistente',
                    'Aceptar'
                );
            } else {
                Report.failure(
                    'Error',
                    'Error desconocido al iniciar sesión',
                    'Aceptar'
                );
            }
            dispatch({
                type: LOGIN_FAILURE,
            });
        }
    } catch (error) {
        Report.failure(
            'Error',
            'Error al iniciar sesión',
            'Aceptar'
        );
        dispatch({
            type: LOGIN_FAILURE,
        });
    }
};

export const logout = () => ({
    type: LOGOUT
});
