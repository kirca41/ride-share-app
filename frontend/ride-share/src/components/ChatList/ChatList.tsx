import { Avatar, Divider, List, ListItemAvatar, ListItemButton, ListItemText, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthProvider';
import { ChatResponse } from '../../interfaces/response/ChatResponse';
import { ChatService } from '../../services/chatService';

const ChatList: React.FC = () => {

    const [chats, setChats] = useState<ChatResponse[]>([]);
    const { activeUser } = useAuth();

    useEffect(() => {
        if (activeUser) {
            fetchChats();
        }
    }, [activeUser]);

    const fetchChats = async () => {
        const chatsResponse = await ChatService.getAllForParticipant(activeUser.id);
        setChats(chatsResponse.data);
    }

    const getParticipantInitials = (fullName: string) => {
        return fullName.split(" ").map(w => w[0]).join("");
    }

    const renderedChats = chats.map(chat => {
        const otherParticipantName =
            (activeUser && chat && activeUser.id === chat.participant1Id) ? chat?.participant2FullName : chat?.participant1FullName
        const lastMessageSender =
            (activeUser && chat && chat.lastMessage && activeUser.id === chat.lastMessage.senderId) ? 'You: ' : '';

        return <React.Fragment key={chat.id}>
            <ListItemButton component={Link} to={`/chat/${chat.uuid}`} sx={{ pt: '0' }} alignItems="flex-start">
                <ListItemAvatar>
                    <Avatar sx={{ width: 35, height: 35 }}>{getParticipantInitials(otherParticipantName)}</Avatar>
                </ListItemAvatar>
                <ListItemText
                    primary={<Typography color="primary" variant="h6">{otherParticipantName}</Typography>}
                    secondary={
                        chat.lastMessage && <React.Fragment>
                            <Typography
                                component="span"
                                variant="body2"
                                color="textPrimary"
                            >
                                {lastMessageSender}{chat.lastMessage.content}
                            </Typography>
                            {' — '}
                            <Typography component="span" variant="caption" color="textSecondary">
                                {chat.lastMessage.dateSent} {chat.lastMessage.timeSent}
                            </Typography>
                        </React.Fragment>
                    }
                />
            </ListItemButton>
            <Divider component="li" />
        </React.Fragment>
    });

    return (
        <List>
            {renderedChats}
        </List>
    );
};

export default ChatList;
