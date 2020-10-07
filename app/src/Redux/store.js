import {createStore, applyMiddleware} from 'redux'
import {rootReducer} from './Reducers/rootReducer';
import thunk from 'redux-thunk';
import logger from "redux-logger";

export const store = createStore(rootReducer, applyMiddleware(thunk, logger));