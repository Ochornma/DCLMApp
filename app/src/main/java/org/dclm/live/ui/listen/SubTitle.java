package org.dclm.live.ui.listen;

public class SubTitle {

    private String topic;
    private String preacher;
    private String listener;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPreacher() {
        return preacher;
    }

    public void setPreacher(String preacher) {
        this.preacher = preacher;
    }

    public String getListener() {
        return listener;
    }

    public void setListener(String listener) {
        this.listener = listener;
    }

    public SubTitle(String topic, String preacher, String listener) {
        this.topic = topic;
        this.preacher = preacher;
        this.listener = listener;
    }

    public SubTitle() {
    }
}
