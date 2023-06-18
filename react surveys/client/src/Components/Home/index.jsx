// import React, { useState } from "react";
import "./home.css";
import { useDispatch, useSelector } from "react-redux";
import { selectCurrentUser, logout } from "../../state/authSlice";

function Home() {
  const currentUser = useSelector(selectCurrentUser);
  return (
    <div className="homediv">
      <div className="hometitle">
        <h1>Üdvözöllek a Kérdőívek alkalmazásban!</h1>
      </div>
      <div className="homesection">
        <p>
          Ez az alkalmazás lehetővé teszi számodra, hogy többlépéses kérdőíveket
          hozz létre.
        </p>
        <p>
          Az elkészített kérdőíveket áttekintheted, módosíthatod, törölheted és
          megoszthatod másokkal.
        </p>
        <p>
          Emellett megtekintheted a kérdőívek kitöltésekor beérkező válaszokat
          is.
        </p>
        <p>
          Regisztráció és bejelentkezés után személyre szabott funkciókhoz is
          hozzáférhetsz, mint például a saját kérdőívek kezelése és a profilod
          szerkesztése.
        </p>
      </div>
    </div>
  );
}

export default Home;
