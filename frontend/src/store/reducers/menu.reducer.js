import { SET_ACTIVE_MENU, SET_SCREEN_SIZE, SET_CLICKED_USER_PROFILE } from '../actions/menu.action';

const initialState = {
    activeMenu: true,
    screenSize: window.innerWidth,
    isClickedUserProfile: false
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
        case SET_CLICKED_USER_PROFILE:
            return {
                ...state,
                isClickedUserProfile: action.clicked
            }
        default:
            return state;
    }
}

export default menuReducer;