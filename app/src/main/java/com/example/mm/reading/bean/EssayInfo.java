package com.example.mm.reading.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 李颖佳 on 2018/3/11.
 */

@DatabaseTable(tableName = "essay")
public class EssayInfo {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String title;

    @DatabaseField
    private String author;

    @DatabaseField
    private String content;

    public EssayInfo(){

    }


    public EssayInfo(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString() {
        return "EssayInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ",  content=" + content +
                '}';
    }

    /*
    * 重写equals方法
    */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EssayInfo){
            EssayInfo question = (EssayInfo) obj;
            return this.title.equals(question.title)
                    && this.author.equals(question.author)
                    && this.content.equals(question.content);
        }
        return super.equals(obj);
    }
}
