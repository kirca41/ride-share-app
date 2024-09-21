import { CssBaseline } from "@mui/material";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { SnackbarProvider } from 'notistack';
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import AuthProvider from "../../context/AuthProvider";
import Chat from "../Chat/Chat";
import CreateBooking from "../CreateBooking/CreateBooking";
import Layout from "../Layout/Layout";
import Login from "../Login/Login";
import Logout from "../Logout/Logout";
import MyBookings from "../MyBookings/MyBookings";
import MyRides from "../MyRides/MyRides";
import PublishRide from "../PublishRide/PublishRide";
import Register from "../Register/Register";
import RideBookingsList from "../RideBookingsList/RideBookingsList";
import RideList from "../RideList/RideList";
import RideRating from "../RideRating/RideRating";

const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />,
    children: [
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
        element: <RideBookingsList />
      },
      {
        path: '/my-bookings',
        element: <MyBookings />
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
        path: '/ride/:rideUuid/rating',
        element: <RideRating />
      }
    ],
  },
  {
    path: '/chat/:chatUuid',
    element: <Chat />
  },
  {
    path: '/login',
    element: <Login />
  },
  {
    path: '/register',
    element: <Register />
  },
  {
    path: '/logout',
    element: <Logout />
  }
])

const theme = createTheme({
  palette: {
    secondary: {
      main: '#979797'
    }
  },
});

function App() {
  return (
    <SnackbarProvider>
      <CssBaseline />
      <ThemeProvider theme={theme}>
        <AuthProvider>
          <RouterProvider router={router} />
        </AuthProvider>
      </ThemeProvider>
    </SnackbarProvider>
  );
}

export default App;
