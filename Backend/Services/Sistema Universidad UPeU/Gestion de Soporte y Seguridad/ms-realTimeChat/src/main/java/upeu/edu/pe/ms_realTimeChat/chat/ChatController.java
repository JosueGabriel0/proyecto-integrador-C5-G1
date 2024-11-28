package upeu.edu.pe.ms_realTimeChat.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import upeu.edu.pe.ms_realTimeChat.entity.Message;
import upeu.edu.pe.ms_realTimeChat.entity.User;
import upeu.edu.pe.ms_realTimeChat.service.MessageService;
import upeu.edu.pe.ms_realTimeChat.service.UserService;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final UserService userService;
    private final MessageService messageService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage
    ) {
        User sender = userService.saveOrUpdateUser(chatMessage.getSender());
        Message message = Message.builder()
                .sender(sender)
                .content(chatMessage.getContent())
                .type(chatMessage.getType())
                .build();
        messageService.saveMessage(message);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        userService.saveOrUpdateUser(chatMessage.getSender());
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}