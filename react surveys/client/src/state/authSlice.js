import { createSlice } from "@reduxjs/toolkit";
// import { useLocalState } from "../util/useLS";

// const [jwt, setJwt] = useLocalState("", "jwt");
const storedUser = JSON.parse(localStorage.getItem("user"));
const storedToken = localStorage.getItem("token");

const initialState = {
  user: storedUser ? storedUser : null,
  token: storedToken ? storedToken : null,
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    login: (state, { payload: { user, accessToken: token } }) => {
      state.user = user;
      state.token = token;
      localStorage.setItem("user", JSON.stringify(user));
      localStorage.setItem("token", token);
    },
    logout: (state) => {
      state.user = null;
      state.token = null;
      localStorage.removeItem("user");
      localStorage.removeItem("token");
    },
  },
});

export const { login, logout } = authSlice.actions;

export const authReducer = authSlice.reducer;

export const selectCurrentUser = (state) => state.auth.user;
export const selectToken = (state) => state.auth.token;
