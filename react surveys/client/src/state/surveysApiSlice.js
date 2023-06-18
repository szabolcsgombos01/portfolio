import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

const BASE_URL = "http://localhost:3030";

const baseQuery = fetchBaseQuery({
  baseUrl: BASE_URL,
  prepareHeaders: (headers, { getState }) => {
    const token = getState().auth.token;
    if (token) {
      headers.set("Authorization", `Bearer ${token}`);
    }
    console.log(headers);
    return headers;
  },
});
export const surveysApi = createApi({
  reducerPath: "surveysApi",
  baseQuery,
  endpoints: (builder) => ({
    getSurveys: builder.query({
      query: (userId) => `surveys?userId=${userId}`,
      transformResponse: (response) => response.data,
    }),
    createSurvey: builder.mutation({
      query: (survey) => ({
        url: "surveys",
        method: "POST",
        body: survey,
      }),
    }),
    deleteSurvey: builder.mutation({
      query: (surveyId) => ({
        url: `surveys/${surveyId}`,
        method: "DELETE",
      }),
    }),
  }),
});

export const {
  useGetSurveysQuery,
  useCreateSurveyMutation,
  useDeleteSurveyMutation,
} = surveysApi;
