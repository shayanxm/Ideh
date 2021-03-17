package com.click.pc;

import java.util.ArrayList;
import java.util.List;

public class IdeaModel {
    private List<Integer> SubCategory3XidOne;
    private int page;
    private int Start;
    private int End;
    private int NumberIdeas;
    private int TotalCount_NumberIdeas;
    private List<Integer> IdeaNumber;
    private List<String> IdeaUserName;
    private List<String[]> IdeaTitle;
    private List<String[]> IdeaText;
    private List<String[]> IdeaKW;
    private List<String[]> SubCategoryTitlenew;


    public List<Integer> getSubCategory3XidOne() {
        return SubCategory3XidOne;
    }

    public void setSubCategory3XidOne(List<Integer> subCategory3XidOne) {
        SubCategory3XidOne = subCategory3XidOne;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getStart() {
        return Start;
    }

    public void setStart(int start) {
        Start = start;
    }

    public int getEnd() {
        return End;
    }

    public void setEnd(int end) {
        End = end;
    }

    public int getNumberIdeas() {
        return NumberIdeas;
    }

    public void setNumberIdeas(int numberIdeas) {
        NumberIdeas = numberIdeas;
    }

    public List<Integer> getIdeaNumber() {
        return IdeaNumber;
    }

    public void setIdeaNumber(List<Integer> ideaNumber) {
        IdeaNumber = ideaNumber;
    }

    public List<String> getIdeaUserName() {
        return IdeaUserName;
    }

    public void setIdeaUserName(List<String> ideaUserName) {
        IdeaUserName = ideaUserName;
    }

    public List<String[]> getIdeaTitle() {
        return IdeaTitle;
    }

    public void setIdeaTitle(List<String[]> ideaTitle) {
        IdeaTitle = ideaTitle;
    }

    public List<String[]> getIdeaText() {
        return IdeaText;
    }

    public void setIdeaText(List<String[]> ideaText) {
        IdeaText = ideaText;
    }

    public List<String[]> getIdeaKW() {
        return IdeaKW;
    }

    public void setIdeaKW(List<String[]> ideaKW) {
        IdeaKW = ideaKW;
    }

    public List<String[]> getSubCategoryTitlenew() {
        return SubCategoryTitlenew;
    }

    public void setSubCategoryTitlenew(List<String[]> subCategoryTitlenew) {
        SubCategoryTitlenew = subCategoryTitlenew;
    }

    public int getTotalCount_NumberIdeas() {
        return TotalCount_NumberIdeas;
    }

    public void setTotalCount_NumberIdeas(int totalCount_NumberIdeas) {
        TotalCount_NumberIdeas = totalCount_NumberIdeas;
    }
}
