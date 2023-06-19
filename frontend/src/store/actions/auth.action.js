export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'LOGIN_FAILURE';
export const LOGOUT = 'LOGOUT';

export const login = (username, password) => {
    return async (dispatch) => {
        try {
            const response = await fetch('/api/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                const { token, user } = await response.json();

                localStorage.setItem('token', token);

                dispatch({
                    type: LOGIN_SUCCESS,
                    payload: { token, user }
                });
            } else {
                const errorData = await response.json();

                dispatch({
                    type: LOGIN_FAILURE,
                    payload: errorData.error
                });
            }
        } catch (error) {
            dispatch({
                type: LOGIN_FAILURE,
                payload: error.message
            });
        }
    };
};


export const logout = () => {
    // Eliminar el token del almacenamiento local
    localStorage.removeItem('token');

    return {
        type: LOGOUT
    };
};
