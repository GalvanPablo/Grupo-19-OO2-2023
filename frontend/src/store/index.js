import { createStore, combineReducers, applyMiddleware } from 'redux';
import thunk from 'redux-thunk';

// REDUCERS
import menuReducer from './reducers/menu.reducer';
import authReducer from './reducers/auth.reducer';

const RootReducer = combineReducers({
    menu: menuReducer,
    auth: authReducer
});

export default createStore(RootReducer, applyMiddleware(thunk));