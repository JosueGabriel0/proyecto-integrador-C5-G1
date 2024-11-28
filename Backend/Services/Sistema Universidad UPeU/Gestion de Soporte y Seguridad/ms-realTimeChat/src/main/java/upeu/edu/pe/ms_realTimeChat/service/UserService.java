package upeu.edu.pe.ms_realTimeChat.service;

import upeu.edu.pe.ms_realTimeChat.entity.User;

public interface UserService {
    public User saveOrUpdateUser(String username);
    public void disconnectUser(String username);
}
