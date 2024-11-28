package upeu.edu.pe.ms_realTimeChat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import upeu.edu.pe.ms_realTimeChat.entity.Message;
import upeu.edu.pe.ms_realTimeChat.repository.MessageRepository;
import upeu.edu.pe.ms_realTimeChat.service.MessageService;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }
}