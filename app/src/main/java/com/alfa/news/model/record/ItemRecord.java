package com.alfa.news.model.record;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class ItemRecord {

    @Element(name = "title")
    protected String title;
    @Element(name = "link")
    protected String link;
    @Element(name = "pubDate")
    protected String date;
    @Element(name = "description")
    protected String description;

    public ItemRecord() {
    }

    public ItemRecord(String title, String link, String date, String description) {
        this.title = title;
        this.link = link;
        this.date = date;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}