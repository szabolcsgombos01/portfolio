import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

const BASE_URL = "http://localhost:3030/";

export const authApiSlice = createApi({
  reducerPath: "authApi",
  baseQuery: fetchBaseQuery({
    baseUrl: BASE_URL,
  }),
  endpoints: (build) => ({
    register: build.mutation({
      query: (body) => {
        return {
          url: "users",
          method: "POST",
          body,
        };
      },
    }),
    login: build.mutation({
      query: (body) => {
        return {
          url: "authentication",
          method: "POST",
          body,
        };
      },
    }),
  }),
});

export const { useRegisterMutation, useLoginMutation } = authApiSlice;

// export default authApiSlice.reducer;
