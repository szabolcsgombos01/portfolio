import React, { useState, useEffect } from "react";
import "./App.css";
import "bootstrap-icons/font/bootstrap-icons.css";
import Navbar from "./Components/Navbar";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Home from "./Components/Home";
import Reg from "./Components/Regestration";
import Login from "./Components/Login";
import Profile from "./Components/Profile";
import Surveys from "./Components/Surveys";
import SurveyForm from "./Components/SurveyForm";
import { RequireAuth } from "./state/RequireAuth";

function App() {
  const [loggedIn, setLoggedIn] = useState(false);

  useEffect(() => {
    const storedJwt = localStorage.getItem("jwt");
    if (storedJwt) {
      setLoggedIn(true);
    }
  }, []);

  const handleLogin = (jwt) => {
    setLoggedIn(true);
    localStorage.setItem("jwt", jwt);
  };

  const handleLogout = () => {
    setLoggedIn(false);
    localStorage.removeItem("jwt");
  };

  return (
    <BrowserRouter>
      <Navbar loggedIn={loggedIn} onLogout={handleLogout} />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/registration" element={<Reg />} />
        <Route
          path="/authentication"
          element={<Login onLogin={handleLogin} />}
        />
        <Route
          path="/profile"
          element={
            <RequireAuth loggedIn={loggedIn}>
              <Profile />
            </RequireAuth>
          }
        />
        <Route
          path="/surveys"
          element={
            <RequireAuth loggedIn={loggedIn}>
              <Surveys />
            </RequireAuth>
          }
        />
        <Route
          path="/newsurvey"
          element={
            <RequireAuth loggedIn={loggedIn}>
              <SurveyForm />
            </RequireAuth>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
