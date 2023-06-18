import { useSelector } from "react-redux";
import { Navigate } from "react-router-dom";
import { selectCurrentUser } from "./authSlice";
import { useLocalState } from "../util/useLS";

export const RequireAuth = ({ children }) => {
  let user = useSelector(selectCurrentUser);

  if (!user) {
    return <Navigate to="/authentication" replace />;
  }

  return children;
};
