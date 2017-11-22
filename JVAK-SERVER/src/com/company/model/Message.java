package com.company.model;
import java.util.Date;

public class Message {
    private String text;
    private Date date;
    private Date seen = null;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getSeen() {
        return seen;
    }

    public void setSeen(Date seen) {
        this.seen = seen;
    }
}
