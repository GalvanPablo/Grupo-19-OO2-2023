export const SET_ACTIVE_MENU = 'TOGGLE_MENU';
export const SET_SCREEN_SIZE = 'SET_SCREEN_SIZE';
export const SET_CLICKED_USER_PROFILE = 'SET_CLICKED_USER_PROFILE';

export const setActiveMenu = (active) => ({
    type: SET_ACTIVE_MENU,
    active
})

export const setScreenSize = (size) => ({
    type: SET_SCREEN_SIZE,
    size
})

export const setClickedUserProfile = (clicked) => ({
    type: SET_CLICKED_USER_PROFILE,
    clicked
})