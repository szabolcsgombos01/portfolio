import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import "bootstrap/dist/css/bootstrap.min.css";
import { Button, Card, Col, Row } from "react-bootstrap";
import paginationFactory from "react-bootstrap-table2-paginator";
import { selectCurrentUser, logout } from "../../state/authSlice";
import {
  useGetSurveysQuery,
  useDeleteSurveyMutation,
} from "../../state/surveysApiSlice";

const MySurveys = () => {
  const [deleteSurvey, response] = useDeleteSurveyMutation();
  const user = useSelector(selectCurrentUser);
  const { isLoading, data: surveys, refetch } = useGetSurveysQuery(user.id);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  const pagination = paginationFactory({
    page: 1,
    sizePerPage: 5,
    sizePerPageList: [5, 10, 25],
    lastPageText: ">>",
    firstPageText: "<<",
    nextPageText: ">",
    prePageText: "<",
    showTotal: true,
    alwaysShowAllBtns: true,
  });

  const handleDeleteSurvey = async (surveyId) => {
    try {
      const { data } = await deleteSurvey(surveyId);
      if (data) {
        console.log("sikeres.");
        refetch();
      } else {
        console.log("sikertelen.");
      }
    } catch (error) {
      console.log("hibaa : " + error);
    }
  };
  const handleCopyLink = (surveyId) => {
    const surveyLink = `${window.location.origin}/survey/${surveyId}`;

    navigator.clipboard
      .writeText(surveyLink)
      .then(() => {
        console.log("Link másolása sikeres volt", surveyLink);
      })
      .catch((error) => {
        console.error("Link másolása nem sikerült", error);
      });
  };

  const handleViewAnswers = (surveyId) => {
    console.log("Viewing answers for survey:", surveyId);
  };

  const handleEdit = (surveyId) => {
    console.log("Edit:", surveyId);
  };

  const formatCreatedAt = (timestamp) => {
    const date = new Date(parseInt(timestamp));
    return date.toLocaleDateString();
  };

  return (
    <div style={{ textAlign: "center", color: "white" }}>
      <h2>Kérdőíveim</h2>
      {surveys.length === 0 ? (
        <p>Nincsenek elérhető kérdőívek.</p>
      ) : (
        <table style={{ width: "100%", borderCollapse: "collapse" }}>
          <thead>
            <tr>
              <th
                style={{
                  backgroundColor: "lightgray",
                  padding: "8px",
                  color: "black",
                }}
              >
                Név
              </th>
              <th
                style={{
                  backgroundColor: "lightgray",
                  padding: "8px",
                  color: "black",
                }}
              >
                Létrehozás dátuma
              </th>
              <th
                style={{
                  backgroundColor: "lightgray",
                  padding: "8px",
                  color: "black",
                }}
              >
                Műveletek
              </th>
            </tr>
          </thead>
          <tbody>
            {surveys?.map((survey, index) => (
              <tr
                key={survey.id}
                style={{
                  backgroundColor: index % 2 === 0 ? "white" : "lightgray",
                }}
              >
                <td style={{ padding: "8px", color: "black" }}>
                  {survey.name}
                </td>
                <td style={{ padding: "8px", color: "black" }}>
                  {formatCreatedAt(survey.createdAt)}
                </td>
                <td style={{ padding: "8px", color: "black" }}>
                  <Button
                    variant="outline-danger"
                    onClick={() => handleDeleteSurvey(survey.id)}
                  >
                    <i className="bi bi-trash"></i>
                  </Button>
                  <Button
                    variant="warning"
                    onClick={() => handleCopyLink(survey.id)}
                  >
                    <i className="bi bi-clipboard"></i>
                  </Button>
                  <Button
                    variant="outline-info"
                    onClick={() => handleViewAnswers(survey.id)}
                  >
                    <i className="bi bi-chat-left-fill"></i>
                  </Button>
                  <Button
                    variant="warning"
                    onClick={() => handleEdit(survey.id)}
                  >
                    <i className="bi bi-pencil"></i>
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default MySurveys;
