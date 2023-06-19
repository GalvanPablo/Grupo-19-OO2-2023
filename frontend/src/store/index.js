import { createStore, combineReducers } from 'redux';

// REDUCERS
import menuReducer from './reducers/menu.reducer';
import authReducer from './reducers/auth.reducer';

const RootReducer = combineReducers({
    menu: menuReducer,
    auth: authReducer
});

export default createStore(RootReducer);