import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { useLoginMutation } from "../../state/authApiSlice";
import { useNavigate } from "react-router-dom";
import { login } from "../../state/authSlice";
import { useLocalState } from "../../util/useLS";
import "./login.css";

const Login = () => {
  const [jwt, setJwt] = useLocalState("", "jwt");
  const [data, setData] = useState({
    username: "",
    password: "",
  });
  const [errors, setErrors] = useState({});
  const [authLogin] = useLoginMutation();
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const { username, password } = data;
    const newErrors = {};

    if (username === "") {
      newErrors.username = "Username is required";
    }
    if (password === "") {
      newErrors.password = "Password is required";
    }

    setErrors(newErrors);

    if (Object.values(newErrors).length > 0) {
      return;
    }
    try {
      const result = await authLogin({
        strategy: "local",
        email: username,
        password: password,
      }).unwrap();
      dispatch(login(result));
      console.log(result.accessToken);
      // setJwt(result.accessToken);
      navigate("/", { replace: true });
    } catch (error) {
      newErrors.username = "Login error";
      setErrors(newErrors);
    }
  };

  const handleChange = (e) => {
    setData({
      ...data,
      [e.target.name]: e.target.value,
    });
  };

  return (
    <div className="">
      <div className="login-box">
        <div>
          <h2>Login</h2>
        </div>
        <form onSubmit={handleSubmit}>
          <div className="user-box">
            <input
              type="text"
              id="username"
              name="username"
              value={data.username}
              onChange={handleChange}
              required
            />
            <label> Email</label>
          </div>
          <div className="user-box">
            <input
              type="password"
              id="password"
              name="password"
              value={data.password}
              onChange={handleChange}
              required
            />
            <label htmlFor="password"> Password</label>
          </div>
          <button type="submit" href="#">
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            Submit
          </button>
        </form>
      </div>
    </div>
  );
};

export default Login;
