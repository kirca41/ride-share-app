import MenuIcon from '@mui/icons-material/Menu';
import { AppBar, Avatar, Button, Drawer, IconButton, List, ListItemButton, ListItemText, Theme, Toolbar, Typography, useMediaQuery } from "@mui/material";
import React, { useState } from "react";
import { Link } from "react-router-dom";
import { useAuth } from '../../context/AuthProvider';

const AppMenu: React.FC = () => {
    const [drawerOpen, setDrawerOpen] = useState(false);
    const isSmallScreen = useMediaQuery((theme: Theme) => theme.breakpoints.down('sm'));
    const { activeUser } = useAuth();

    const getActiveUserInitials = () => {
        return activeUser.firstName[0] + activeUser.lastName[0];
    }

    const menuItems = [
        {
            label: 'Rides',
            link: '/'
        },
        {
            label: 'Publish Ride',
            link: '/publish-ride'
        },
        {
            label: 'Logout',
            link: '/logout',
        },
        {
            label: activeUser ? <Avatar sx={{ width: 35, height: 35 }}>{getActiveUserInitials()}</Avatar> : '',
            link: '/user'
        }
    ];

    const toggleDrawer = (open: boolean) => () => {
        setDrawerOpen(open);
    };

    const renderMenuItems = () => {
        if (isSmallScreen) {

            return menuItems.map((item) => (
                <ListItemButton component={Link} to={item.link} key={item.link}>
                    <ListItemText primary={item.label} />
                </ListItemButton >
            ));
        }

        return menuItems.map((item) => (
            <Link to={item.link} style={{ textDecoration: 'none', color: 'white' }}>
                <Button key={item.link} color="inherit">
                    {item.label}
                </Button>
            </Link>
        ));

    };

    return (
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h6" sx={{ flexGrow: 1 }}>
                    <Link to='/' style={{ textDecoration: 'none', color: 'white' }}>RideShare</Link>
                </Typography>
                {isSmallScreen ? (
                    <>
                        <IconButton
                            color="inherit"
                            edge="start"
                            onClick={toggleDrawer(true)}
                            aria-label="menu"
                        >
                            <MenuIcon />
                        </IconButton>
                        <Drawer anchor="bottom" open={drawerOpen} onClose={toggleDrawer(false)}>
                            <List>
                                {renderMenuItems()}
                            </List>
                        </Drawer>
                    </>
                ) : (
                    renderMenuItems()
                )}
            </Toolbar>
        </AppBar>
    );
}

export default AppMenu;