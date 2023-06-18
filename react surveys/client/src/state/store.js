import { authApiSlice } from "./authApiSlice";
import { configureStore } from "@reduxjs/toolkit";
import { authReducer } from "./authSlice";
import { surveysApi } from "./surveysApiSlice";

export const store = configureStore({
  reducer: {
    auth: authReducer,
    [authApiSlice.reducerPath]: authApiSlice.reducer,
    [surveysApi.reducerPath]: surveysApi.reducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(
      authApiSlice.middleware,
      surveysApi.middleware
    ),
});
