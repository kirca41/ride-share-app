import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Login from "../Login/Login";
import Register from "../Register/Register";
import RideList from "../RideList/RideList";
import CreateBooking from "../CreateBooking/CreateBooking";
import { SnackbarProvider } from 'notistack';
import PublishRide from "../PublishRide/PublishRide";
import MyRides from "../MyRides/MyRides";
import RideBookingsList from "../RideBookingsList/RideBookingsList";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { CssBaseline } from "@mui/material";
import MyBookings from "../MyBookings/MyBookings";
import Chat from "../Chat/Chat";
import AuthProvider from "../../context/AuthProvider";

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
  }
]);

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
