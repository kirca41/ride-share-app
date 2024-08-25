import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Login from "../Login/Login";
import Register from "../Register/Register";
import { RideList } from "../RideList/RideList";

const router = createBrowserRouter([
  {
    path: '/',
    element: <RideList />
  },
  {
    path: '/login',
    element: <Login />
  },
  {
    path: '/register',
    element: <Register />
  }
]);


function App() {
  return (
    <RouterProvider router={router} />
  );
}

export default App;
