package taweryawer.service;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import taweryawer.handlers.ErrorAction;
import taweryawer.handlers.PersistFriendCodeAction;
import taweryawer.handlers.PersistNicknameAction;
import taweryawer.handlers.ShowEnterNicknameAction;

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
}
