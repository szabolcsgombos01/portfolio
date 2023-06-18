import React from "react";
import { useSelector, useDispatch } from "react-redux";
import { selectCurrentUser, logout } from "../../state/authSlice";
import { useGetSurveysQuery } from "../../state/surveysApiSlice";

const Profil = () => {
  const user = useSelector(selectCurrentUser);
  const dispatch = useDispatch();
  console.log(user.id);
  const { isLoading, data } = useGetSurveysQuery(user.id);
  if (isLoading) {
    return <div>Loading...</div>;
  }

  const list = data;

  const handleLogout = () => {
    dispatch(logout());
  };
  return (
    <div className="login-box" style={{ color: "white" }}>
      <div>
        <h2>Profil</h2>
      </div>
      <div className="user-box">
        <p>
          <strong>Név:</strong> {user.fullname}
        </p>
        <p>
          <strong>Email:</strong> {user.email}
        </p>
        <p>
          <strong>Kérdőívek száma:</strong> {list.length}
        </p>
      </div>
      <button onClick={handleLogout}>Kijelentkezés</button>
    </div>
  );
};

export default Profil;
