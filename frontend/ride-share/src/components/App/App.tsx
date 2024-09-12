import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Login from "../Login/Login";
import Register from "../Register/Register";
import RideList from "../RideList/RideList";
import CreateBooking from "../CreateBooking/CreateBooking";
import { SnackbarProvider } from 'notistack';
import PublishRide from "../PublishRide/PublishRide";
import MyRides from "../MyRides/MyRides";
import Bookings from "../BookingRequests/Bookings";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { CssBaseline } from "@mui/material";

const router = createBrowserRouter([
  {
    path: '/',
    element: <RideList />
  },
  {
    path: '/rides',
    element: <RideList />
  },
  {
    path: '/rides/:rideId/bookings',
    element: <Bookings />
  },
  {
    path: '/publish-ride',
    element: <PublishRide />
  },
  {
    path: '/book-ride/:rideId',
    element: <CreateBooking />
  },
  {
    path: '/my-rides',
    element: <MyRides />
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

const theme = createTheme();

function App() {
  return (
    <SnackbarProvider>
      <CssBaseline />
      <ThemeProvider theme={theme}>
        <RouterProvider router={router} />
      </ThemeProvider>
    </SnackbarProvider>
  );
}

export default App;
