package taweryawer.service;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import taweryawer.handlers.*;

@Service
public class ActionFactory {

    @Lookup
    public PersistNicknameAction persistNicknameAction(){
        return null;
    }

    @Lookup
    public ShowEnterNicknameAction showEnterNicknameAction() {
        return null;
    }

    @Lookup
    public ErrorAction errorAction() {
        return null;
    }

    @Lookup
    public PersistFriendCodeAction friendCodeAction(){
        return null;
    }

    @Lookup
    public MessageAction messageAction() { return null; }

    @Lookup
    public ProfileAction profileAction() {
        return null;
    }

    @Lookup
    public ShowMainKeyboardAction showMainKeyboardAction() {
        return null;
    }
}
