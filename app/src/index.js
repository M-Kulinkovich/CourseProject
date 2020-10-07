import React, {lazy, Suspense} from "react";
import ReactDOM from "react-dom";
import {Provider} from "react-redux";
import {store} from "./Redux/store";

const App = lazy(() => import("./component/AppContainer"));
ReactDOM.render(
    <Provider store={store}>
        <Suspense fallback={<p>loading....</p>}>
            <App/>
        </Suspense>
    </Provider>,
    document.querySelector("#root")
);
