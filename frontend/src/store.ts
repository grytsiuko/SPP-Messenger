import { createStore } from 'redux';
import rootReducer from './reducers/index';

/* eslint-disable no-underscore-dangle */
export const store = createStore(
    rootReducer,
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);
/* eslint-enable */
