package com.example.project.observer;

import com.example.project.interfaces.NotificationListener;
import com.example.project.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationObserver {
    private final List<NotificationListener> listeners = new ArrayList<>();

    public void addListener(NotificationListener listener) {
        listeners.add(listener);
    }

    public void notify(Notification notification) {
        for (NotificationListener listener : listeners) {
            listener.onNotificationSent(notification);
        }
    }
}
