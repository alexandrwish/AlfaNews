package com.alfa.news.model.record;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "channel", strict = false)
public class ChannelRecord {

    @Element(name = "pubDate")
    private String date;
    @ElementList(name = "item", inline = true)
    private List<ItemRecord> items;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ItemRecord> getItems() {
        return items;
    }

    public void setItems(List<ItemRecord> items) {
        this.items = items;
    }
}