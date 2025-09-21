package com.example.project.observer;

import com.example.project.interfaces.LessonUpdateListener;
import com.example.project.model.Lesson;

import java.util.ArrayList;
import java.util.List;

public class LessonObserver {
    private final List<LessonUpdateListener> listeners = new ArrayList<>();

    public void addListener(LessonUpdateListener listener) {
        listeners.add(listener);
    }

    public void notifyLessonAdded(Lesson lesson) {
        for (LessonUpdateListener listener : listeners) {
            listener.onLessonAdded(lesson);
        }
    }
}
