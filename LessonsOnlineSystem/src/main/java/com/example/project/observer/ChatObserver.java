package com.example.project.observer;

import com.example.project.interfaces.ChatMessageListener;

import java.util.ArrayList;
import java.util.List;

public class ChatObserver {
    private final List<ChatMessageListener> listeners = new ArrayList<>();

    public void addListener(ChatMessageListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners(String user, String message) {
        for (ChatMessageListener listener : listeners) {
            listener.onMessageReceived(user, message);
        }
    }
}
