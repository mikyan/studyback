package com.example.studyforum1.domain.MessageItems;


public class TalkItem {
    private String title;
    private String text;
    private long viewNum;

    public long getViewNum() {
        return viewNum;
    }


    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }


    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setViewNum(long viewNum) {
        this.viewNum = viewNum;
    }
}
