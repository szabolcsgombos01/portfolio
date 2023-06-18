import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { useRegisterMutation } from "../../state/authApiSlice";
import { useNavigate } from "react-router-dom";

function Reg() {
  const [fullName, setFullName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [data, setData] = useState({
    name: "",
    username: "",
    password: "",
  });
  const [errors, setErrors] = useState("");
  const [authReg] = useRegisterMutation();
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const { name, username, password } = data;
    const newErrors = {};

    // Validáció
    if (username === "") {
      newErrors.username = "Username is required";
    }
    if (password === "") {
      newErrors.password = "Password is required";
    }
    if (name === "") {
      newErrors.password = "Name is required";
    }

    setErrors(newErrors);

    if (Object.values(newErrors).length > 0) {
      return;
    }
    console.log(username);
    try {
      const result = await authReg({
        fullname: name,
        email: username,
        password: password,
      }).unwrap();
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
          <h2>Regisztráció</h2>
        </div>
        <form onSubmit={handleSubmit}>
          <div className="user-box">
            <input
              type="text"
              name="name"
              value={data.name}
              onChange={handleChange}
              required
            />
            <label>Teljes név:</label>
          </div>
          <div className="user-box">
            <input
              type="email"
              name="username"
              value={data.username}
              onChange={handleChange}
              required
            />
            <label>Email cím:</label>
          </div>
          <div className="user-box">
            <input
              type="password"
              name="password"
              value={data.password}
              onChange={handleChange}
              required
            />
            <label>Jelszó:</label>
          </div>
          <button type="submit">Regisztráció</button>
        </form>
      </div>
    </div>
  );
}

export default Reg;
