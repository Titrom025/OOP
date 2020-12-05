package ru.nsu.fit.titkov.notebook;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Getter;

public class UserNote {
    @Getter private final String title;
    @Getter private final Date date;
    private final String description;

    UserNote(String title, String description) {
        this.title = title;
        this.description = description;
        this.date = new Date();
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String stringDate = dateFormat.format(date);
        return "Title: '" + title + '\'' +
                ", description: '" + description + '\'' +
                ", date: " + stringDate;
    }
}
