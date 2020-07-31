package taweryawer.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taweryawer.entities.User;
import taweryawer.repository.UserRepository;
import taweryawer.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String telegramId) {
        return userRepository.getUserByTelegramIdIfExistsOrCreateIfDoesnt(telegramId);
    }

    @Override
    public void changeUserNickname(String newNickname, String telegramId) {
        userRepository.changeUserNickname(newNickname, telegramId);
    }

    @Override
    public void changeUserFriendCode(String friendCode, String telegramId) {
        userRepository.changeUserFriendCode(friendCode, telegramId);
    }
}
