package org.oosd.teamorange_milestoneone.application.system;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.concurrent.ConcurrentHashMap;

// Handles keyboard input using ConcurrentHashMap
public class KeyInputHandler implements EventHandler<KeyEvent> {
    private final ConcurrentHashMap<KeyCode, Boolean> keys = new ConcurrentHashMap<>();

    @Override
    public void handle(KeyEvent e) {
        if (e.getEventType() == KeyEvent.KEY_PRESSED) keys.put(e.getCode(), true);
        else if (e.getEventType() == KeyEvent.KEY_RELEASED) keys.put(e.getCode(), false);
    }

    boolean isPressed(KeyCode code) {
        return Boolean.TRUE.equals(keys.get(code));
    }

    boolean consumeRelease(KeyCode code) {
        Boolean state = keys.get(code);
        if (state != null && !state) {
            keys.remove(code);
            return true;
        }
        return false;
    }
}