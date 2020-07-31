package taweryawer.util;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyKeyboardBuilder {

    private List<KeyboardRow> keyboard = new ArrayList<>();
    private KeyboardRow currentRow = new KeyboardRow();

    public void addButton(String title) {
        if(currentRow.size() >= 2) {
            keyboard.add(currentRow);
            currentRow = new KeyboardRow();
        }
        currentRow.add(new KeyboardButton(title));
    }

    public ReplyKeyboardMarkup build() {
        keyboard.add(currentRow);
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(keyboard);
        markup.setResizeKeyboard(true);
        currentRow = new KeyboardRow();
        keyboard = new ArrayList<>();
        return markup;
    }
}
