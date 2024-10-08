import MenuIcon from '@mui/icons-material/Menu';
import NotificationsIcon from '@mui/icons-material/Notifications';
import { AppBar, Avatar, Badge, Button, Divider, Drawer, IconButton, List, ListItem, ListItemButton, ListItemText, Theme, Toolbar, Typography, useMediaQuery } from "@mui/material";
import { Client } from '@stomp/stompjs';
import React, { useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";
import { useAuth } from '../../context/AuthProvider';
import { BellNotificationResponse } from '../../interfaces/response/BellNotificationResponse';

const AppMenu: React.FC = () => {
    const [drawerOpen, setDrawerOpen] = useState(false);
    const [unreadMessagesNumber, setUnreadMessagesNumber] = useState(0);
    const [notificationDrawerOpen, setNotificationDrawerOpen] = useState(false);
    const [notifications, setNotifications] = useState<BellNotificationResponse[]>([]);
    const isSmallScreen = useMediaQuery((theme: Theme) => theme.breakpoints.down('sm'));
    const stompClient = useRef<Client | null>(null);
    const { activeUser } = useAuth();

    useEffect(() => {
        if (activeUser) {
            stompClient.current = new Client({
                brokerURL: `ws://localhost:8080/bell?fullName=${activeUser.fullName}`,
                reconnectDelay: 5000,
                onConnect: () => {
                    stompClient.current?.subscribe(`/topic/bell/${activeUser.id}`, (msg: any) => {
                        const receivedNotification = JSON.parse(msg.body) as BellNotificationResponse;
                        setNotifications((prevNotifications) => [receivedNotification, ...prevNotifications]);
                        setUnreadMessagesNumber((prev) => prev + 1);
                    });
                }
            });

            stompClient.current.activate();
        }

        return () => {
            stompClient.current?.deactivate();
        };
    }, [activeUser]);

    const getActiveUserInitials = () => {
        return activeUser.firstName[0] + activeUser.lastName[0];
    }

    const menuItems = [
        {
            label: 'My Rides',
            link: '/my-rides',
            isPublic: false
        },
        {
            label: 'My Bookings',
            link: '/my-bookings',
            isPublic: false
        },
        {
            label: 'Publish Ride',
            link: '/publish-ride',
            isPublic: false
        },
        {
            label: 'Chat',
            link: '/chat',
            isPublic: false
        },
        {
            label: activeUser ? 'Logout' : 'Login',
            link: activeUser ? '/logout' : '/login',
            isPublic: true
        },
        {
            label: activeUser ? <Avatar sx={{ width: 35, height: 35 }}>{getActiveUserInitials()}</Avatar> : '',
            link: activeUser?.id ? `/user-profile/${activeUser.id}` : '',
            isPublic: true
        }
    ];

    const toggleDrawer = (open: boolean) => () => {
        setDrawerOpen(open);
    };

    const toggleNotificationDrawer = (open: boolean) => () => {
        setNotificationDrawerOpen(open);
        if (open) setUnreadMessagesNumber(0);
    };

    const renderedMenuItems = menuItems.map((item) => {
        if (!activeUser && !item.isPublic)
            return <></>;

        if (isSmallScreen) {
            return <ListItemButton component={Link} to={item.link} key={item.link}>
                <ListItemText primary={item.label} />
            </ListItemButton >
        }

        return <Link to={item.link} style={{ textDecoration: 'none', color: 'white' }} key={item.link}>
            <Button key={item.link} color="inherit">
                {item.label}
            </Button>
        </Link>
    });

    return (
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h6" sx={{ flexGrow: 1 }}>
                    <Link to='/' style={{ textDecoration: 'none', color: 'white' }}>WheelShare</Link>
                </Typography>
                {activeUser && <IconButton color="inherit" onClick={toggleNotificationDrawer(true)}>
                    <Badge badgeContent={unreadMessagesNumber} color="error">
                        <NotificationsIcon />
                    </Badge>
                </IconButton>}
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
                                {renderedMenuItems}
                            </List>
                        </Drawer>
                    </>
                ) : (
                    renderedMenuItems
                )}
                <Drawer anchor="bottom" open={notificationDrawerOpen} onClose={toggleNotificationDrawer(false)}>
                    <List>
                        <Typography variant="h6" sx={{ padding: 2 }}>Notifications</Typography>
                        <Divider />
                        {notifications.length > 0 ? (
                            notifications.map((notification, index) => (
                                <ListItemButton component={Link} to={`/chat/${notification.chatUuid}`} key={index}>
                                    <ListItemText
                                        primary={`New message from ${notification.senderFullName} (${notification.sentOnDate} ${notification.sentAtTime}):`}
                                        secondary={notification.content}
                                    />
                                </ListItemButton>
                            ))
                        ) : (
                            <ListItem>
                                <ListItemText primary="No new notifications" />
                            </ListItem>
                        )}
                    </List>
                </Drawer>
            </Toolbar>
        </AppBar>
    );
}

export default AppMenu;