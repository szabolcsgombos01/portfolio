import React, { useState } from "react";
import {
  useCreateSurveyMutation,
  useGetSurveysQuery,
} from "../../state/surveysApiSlice";
import { useSelector, useDispatch } from "react-redux";
import { selectCurrentUser, logout } from "../../state/authSlice";
function QuestionnaireForm() {
  const [questionnaire, setQuestionnaire] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [createSurvey, { isLoading }] = useCreateSurveyMutation();
  const user = useSelector(selectCurrentUser);
  const { refetch } = useGetSurveysQuery(user.id);

  const handleInputChange = (event) => {
    setQuestionnaire(event.target.value);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    // Ellenőrzések
    if (questionnaire.trim() === "") {
      setErrorMessage("A kérdőív nem lehet üres!");
      return;
    }

    const lines = questionnaire.split("\n");
    const name = lines[0];
    const content = lines.slice(1).join("\n");

    const pages = questionnaire.split("\n\n");
    if (pages.length < 2) {
      setErrorMessage(
        "A kérdőívnek legalább egy címre és egy lapra van szüksége!"
      );
      return;
    }

    let isValid = true;
    let pageIndex = 1;

    while (pageIndex < pages.length) {
      const lines = pages[pageIndex].split("\n");

      if (lines.length < 2) {
        setErrorMessage(
          "Minden lapnak legalább egy címet és egy kérdést kell tartalmaznia!"
        );
        isValid = false;
        break;
      }

      if (lines[0].trim() === "") {
        setErrorMessage("A lapnak szüksége van egy címre!");
        isValid = false;
        break;
      }

      if (lines.length === 1) {
        setErrorMessage(
          "Minden lapnak legalább egy kérdést kell tartalmaznia!"
        );
        isValid = false;
        break;
      }

      pageIndex++;
    }

    if (!isValid) {
      return;
    }
    try {
      const response = await createSurvey({
        name,
        content,
      });

      // Az egyedi azonosító a válaszból generálható
      const surveyId = response.data.id;
      console.log("Kérdőív sikeresen mentve. Azonosító:", surveyId);

      setQuestionnaire("");
      setErrorMessage("");
      refetch();
    } catch (error) {
      console.error("Hiba a kérdőív mentése közben:", error);
    }

    setQuestionnaire("");
    setErrorMessage("");
  };

  return (
    <div className="login-box">
      <h2>Kérdőív létrehozása</h2>
      <form onSubmit={handleSubmit}>
        <textarea
          value={questionnaire}
          onChange={handleInputChange}
          placeholder="Kérdőív szövege..."
          rows={10}
        />
        <button type="submit">Kérdőív mentése</button>
      </form>
      {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
    </div>
  );
}

export default QuestionnaireForm;
