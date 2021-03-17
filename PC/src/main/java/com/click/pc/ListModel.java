package com.click.pc;

import java.io.Serializable;

public class ListModel implements Serializable {
    private String id;
    private String title;
    private boolean selected;

    public ListModel() {
    }

    public ListModel(String title, boolean selected) {
        this.title = title;
        this.selected = selected;
    }

    public ListModel(String id, String title, boolean selected) {
        this.id = id;
        this.title = title;
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
