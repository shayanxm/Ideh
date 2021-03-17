package com.click.pc;

import java.util.List;

public class DataInfoModel {
    private String status;
    private String message;
    private String SubCategoryTitleOne;
    private List<String> SubCategoryTitleTow;
    private List<String> KeyWrd;
    private List<String> MenuItem;
    private List<String> MenuItemNumber;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubCategoryTitleOne() {
        return SubCategoryTitleOne;
    }

    public void setSubCategoryTitleOne(String subCategoryTitleOne) {
        SubCategoryTitleOne = subCategoryTitleOne;
    }

    public List<String> getSubCategoryTitleTow() {
        return SubCategoryTitleTow;
    }

    public void setSubCategoryTitleTow(List<String> subCategoryTitleTow) {
        SubCategoryTitleTow = subCategoryTitleTow;
    }

    public List<String> getKeyWrd() {
        return KeyWrd;
    }

    public void setKeyWrd(List<String> keyWrd) {
        KeyWrd = keyWrd;
    }

    public List<String> getMenuItem() {
        return MenuItem;
    }

    public void setMenuItem(List<String> menuItem) {
        MenuItem = menuItem;
    }

    public List<String> getMenuItemNumber() {
        return MenuItemNumber;
    }

    public void setMenuItemNumber(List<String> menuItemNumber) {
        MenuItemNumber = menuItemNumber;
    }
}
