package com.alfa.news.model.record;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class RssRecord {

    @Element(name = "channel")
    protected ChannelRecord channel;

    public ChannelRecord getChannel() {
        return channel;
    }

    public void setChannel(ChannelRecord channel) {
        this.channel = channel;
    }
}