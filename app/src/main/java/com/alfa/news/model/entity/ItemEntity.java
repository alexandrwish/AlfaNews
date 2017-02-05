package com.alfa.news.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "item", indexes = {@Index(value = "title, date", unique = true)})
public class ItemEntity {

    @Id(autoincrement = true)
    private Long id;
    @Index(unique = true)
    private String title;
    private String link;
    @Index(unique = true)
    private String date;
    private String description;
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getLink() {
        return this.link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 2139966601)
    public ItemEntity(Long id, String title, String link, String date,
            String description) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.date = date;
        this.description = description;
    }
    @Generated(hash = 365170573)
    public ItemEntity() {
    }
}