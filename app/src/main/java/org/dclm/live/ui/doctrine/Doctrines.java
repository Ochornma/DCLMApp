package org.dclm.live.ui.doctrine;

public class Doctrines {
    private String content;
    private String heading;
    private String body;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Doctrines() {
    }

    public Doctrines(String content, String heading, String body) {
        this.content = content;
        this.heading = heading;
        this.body = body;
    }
}
