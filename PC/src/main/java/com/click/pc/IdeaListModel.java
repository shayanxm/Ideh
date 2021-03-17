package com.click.pc;

public class IdeaListModel {
    private int id;
    private String title;
    private String description;
    private String[] genreArray;
    private String[] keyWordArray;
    private int total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getGenreArray() {
        return genreArray;
    }

    public void setGenreArray(String[] genreArray) {
        this.genreArray = genreArray;
    }

    public String[] getKeyWordArray() {
        return keyWordArray;
    }

    public void setKeyWordArray(String[] keyWordArray) {
        this.keyWordArray = keyWordArray;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
