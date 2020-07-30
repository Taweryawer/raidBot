package taweryawer.service;

import taweryawer.entities.User;

public interface UserService {

    User getUser(String telegramId);
    void changeUserNickname(String newNickname, String telegramId);
    void changeUserFriendCode(String friendCode, String telegramId);
}
