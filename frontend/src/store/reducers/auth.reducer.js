import { LOGIN_SUCCESS, LOGIN_FAILURE, LOGOUT } from './../actions/auth.action';

// TODO ver como mantener la session si se recarga la pagina o se cierra el navegador

const initialState = {
    token: null,
    isAuthenticated: false,
    user: null,
    role: null,
    error: null
};

const authReducer = (state = initialState, action) => {
    switch (action.type) {
        case LOGIN_SUCCESS:
            return {
                ...state,
                token: action.payload.token,
                isAuthenticated: true,
                user: action.payload.user,
                error: null
            };
        case LOGIN_FAILURE:
            return {
                ...state,
                token: null,
                isAuthenticated: false,
                user: null,
                error: action.payload
            };
        case LOGOUT:
            return {
                ...state,
                token: null,
                isAuthenticated: false,
                user: null,
                error: null
            };
        
        case 'FORCE_LOGIN':
            return {
                ...state,
                isAuthenticated: true,
            };
        case 'FORCE_LOGOUT':
            return {
                ...state,
                isAuthenticated: false,
            };


        default:
            return state;
    }
};

export default authReducer;
