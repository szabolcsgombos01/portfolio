import React from "react";
import { Link, NavLink } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { selectCurrentUser, logout } from "../../state/authSlice";
import "./navbar.css";

const Navbar = () => {
  const user = useSelector(selectCurrentUser);
  const dispatch = useDispatch();
  return (
    <nav className="nav">
      <div
        className="logo"
        onClick={() => (window.location.href = "/")}
        style={{ cursor: "pointer" }}
      >
        Kérdőívek
      </div>
      <ul>
        {!user ? (
          <>
            <li>
              <NavLink className="a" to="/registration">
                Regisztráció
              </NavLink>
            </li>
            <li>
              <NavLink className="a" to="/authentication">
                Bejelentkezés
              </NavLink>
            </li>
          </>
        ) : (
          <>
            <li>
              <NavLink className="a" to="/surveys">
                Kérdőíveim
              </NavLink>
            </li>
            <li>
              <NavLink className="a" to="/newsurvey">
                Új kérdőív
              </NavLink>
            </li>
            <li>
              <NavLink className="a" to="/profile">
                Profil
              </NavLink>
            </li>
            <li>
              <NavLink className="a" to="/" onClick={() => dispatch(logout())}>
                Kijelentkezés
              </NavLink>
            </li>
          </>
        )}
      </ul>
    </nav>
  );
};

export default Navbar;
