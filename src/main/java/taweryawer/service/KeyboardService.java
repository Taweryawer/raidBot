package taweryawer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import taweryawer.util.ReplyKeyboardBuilder;

@Service
public class KeyboardService {

    private ReplyKeyboardBuilder replyKeyboardBuilder;

    @Autowired
    public KeyboardService(ReplyKeyboardBuilder replyKeyboardBuilder) {
        this.replyKeyboardBuilder = replyKeyboardBuilder;
    }

    @Cacheable("keyboard")
    public ReplyKeyboardMarkup getMainKeyboard() {
        replyKeyboardBuilder.addButton("Profile\uD83D\uDC64");
        return replyKeyboardBuilder.build();
    }
}
