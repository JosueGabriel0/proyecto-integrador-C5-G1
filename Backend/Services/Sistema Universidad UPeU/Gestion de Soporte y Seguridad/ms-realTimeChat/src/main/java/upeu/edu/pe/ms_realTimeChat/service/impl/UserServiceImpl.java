package upeu.edu.pe.ms_realTimeChat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import upeu.edu.pe.ms_realTimeChat.entity.User;
import upeu.edu.pe.ms_realTimeChat.repository.UserRepository;
import upeu.edu.pe.ms_realTimeChat.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User saveOrUpdateUser(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setConnected(true);
            return userRepository.save(user);
        }
        User newUser = User.builder().username(username).connected(true).build();
        return userRepository.save(newUser);
    }

    public void disconnectUser(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            user.setConnected(false);
            userRepository.save(user);
        });
    }
}