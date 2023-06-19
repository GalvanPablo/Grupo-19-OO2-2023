import { SET_ACTIVE_MENU, SET_SCREEN_SIZE } from '../actions/menu.action';

const initialState = {
    activeMenu: true,
    screenSize: window.innerWidth
}

const menuReducer = (state = initialState, action) => {
    switch (action.type) {
        case SET_ACTIVE_MENU:
            return {
                ...state,
                activeMenu: action.active
            }
        case SET_SCREEN_SIZE:
            return {
                ...state,
                screenSize: action.size
            }
        default:
            return state;
    }
}

export default menuReducer;