import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { ChatResponse } from "../../interfaces/response/ChatResponse";
import { ChatService } from "../../services/chatService";
import { MessageService } from "../../services/messageService";
import { MessageResponse } from "../../interfaces/response/MessageResponse";
import { useAuth } from "../../context/AuthProvider";
import { Box, Button, List, ListItem, ListItemText, Paper, TextField, Typography } from "@mui/material";

const Chat: React.FC = () => {

    const { chatUuid } = useParams();
    const { activeUser } = useAuth();
    const [chat, setChat] = useState<ChatResponse | null>(null);
    const [messages, setMessages] = useState<MessageResponse[]>([]);
    const [message, setMessage] = useState('');

    useEffect(() => {
        fetchData();
    }, [chatUuid])

    const fetchData = async () => {
        if (chatUuid) {
            const chatResponse = await ChatService.getByUuid(chatUuid);
            setChat(chatResponse.data);

            const messagesResponse = await MessageService.getAllByChatUuid(chatUuid);
            setMessages(messagesResponse.data);
        }
    }

    const otherParticipantName 
        = activeUser?.id === chat?.participant1Id ? chat?.participant2FullName : chat?.participant1FullName

    return <Box sx={{ display: 'flex', flexDirection: 'column', height: '100vh', maxWidth: '600px', margin: '0 auto' }}>
        <Paper elevation={3} sx={{ padding: '16px', backgroundColor: '#1976d2', color: 'white' }}>
            <Typography variant="h6">{otherParticipantName}</Typography>
        </Paper>
        <Box sx={{ flex: 1, overflowY: 'auto', padding: '16px', backgroundColor: '#f5f5f5' }}>
            <List sx={{ display: 'flex', flexDirection: 'column-reverse', height: '-webkit-fill-available' }}>
                {messages.map((msg) => (
                    <ListItem 
                        key={msg.id} 
                        sx={{
                            display: 'flex',
                            justifyContent: msg.senderId === activeUser.id ? 'flex-start' : 'flex-end',
                            textAlign: msg.senderId === activeUser.id ? 'left' : 'right',
                        }}
                    >
                        <Paper sx={{ padding: '8px', backgroundColor: msg.senderId === activeUser.id ? '#e1f5fe' : '#fff' }}>
                            <ListItemText primary={msg.content} secondary={`${msg.dateSent} ${msg.timeSent}`} />
                        </Paper>
                    </ListItem>
                ))}
            </List>
        </Box>
        <Paper elevation={3} sx={{ padding: '16px', display: 'flex' }}>
            <TextField
                fullWidth
                variant="outlined"
                label="Type a message"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                onKeyDown={(e) => {
                    if (e.key === 'Enter') {
                        // send message
                    }
                }}
            />
            <Button variant="contained" color="primary" onClick={() => {}} sx={{ marginLeft: '8px' }}>
                Send
            </Button>
        </Paper>
    </Box>
}

export default Chat;