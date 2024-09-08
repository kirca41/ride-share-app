import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Login from "../Login/Login";
import Register from "../Register/Register";
import RideList from "../RideList/RideList";
import CreateBooking from "../CreateBooking/CreateBooking";
import { SnackbarProvider } from 'notistack';

const router = createBrowserRouter([
  {
    path: '/',
    element: <RideList />
  },
  {
    path: '/book-ride/:rideId',
    element: <CreateBooking />
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
    <SnackbarProvider>
      <RouterProvider router={router} />
    </SnackbarProvider>
  );
}

export default App;
