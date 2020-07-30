package taweryawer.repository;

import taweryawer.entities.User;

public interface UserRepository {

    User getUserByTelegramIdIfExistsOrCreateIfDoesnt(String telegramId);
    void changeUserNickname(String newNickname, String telegramId);
    void changeUserFriendCode(String newFriendCode, String telegramId);
}
