package org.dclm.live.ui.jotter.db;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;
    private String speaker;

    private String service;
    private String topic;
    private String description;
    private String point1;
    private String point1_description;

    private String point2;
    private String point2_description;

    private String point3;
    private String point3_description;

    private String point4;
    private String point4_description;

    private String decision;

    @Ignore
    public Note() {
    }

    public Note(String date, String speaker, String service, String topic, String description, String point1, String point1_description, String point2, String point2_description, String point3, String point3_description, String point4, String point4_description, String decision) {
        this.date = date;
        this.speaker = speaker;
        this.service = service;
        this.topic = topic;
        this.description = description;
        this.point1 = point1;
        this.point1_description = point1_description;
        this.point2 = point2;
        this.point2_description = point2_description;
        this.point3 = point3;
        this.point3_description = point3_description;
        this.point4 = point4;
        this.point4_description = point4_description;
        this.decision = decision;
    }
@Ignore
    public Note(int id, String date, String speaker, String service, String topic, String description, String point1, String point1_description, String point2, String point2_description, String point3, String point3_description, String point4, String point4_description, String decision) {
        this.id = id;
        this.date = date;
        this.speaker = speaker;
        this.service = service;
        this.topic = topic;
        this.description = description;
        this.point1 = point1;
        this.point1_description = point1_description;
        this.point2 = point2;
        this.point2_description = point2_description;
        this.point3 = point3;
        this.point3_description = point3_description;
        this.point4 = point4;
        this.point4_description = point4_description;
        this.decision = decision;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getService() {
        return service;
    }




    public String getPoint1() {
        return point1;
    }

    public String getPoint1_description() {
        return point1_description;
    }

    public String getPoint2() {
        return point2;
    }

    public String getPoint2_description() {
        return point2_description;
    }

    public String getPoint3() {
        return point3;
    }

    public String getPoint3_description() {
        return point3_description;
    }

    public String getPoint4() {
        return point4;
    }

    public String getPoint4_description() {
        return point4_description;
    }

    public String getDecision(){
        return decision;
    }
}
