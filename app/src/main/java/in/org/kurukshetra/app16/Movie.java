package in.org.kurukshetra.app16;

import java.util.ArrayList;

public class Movie {
    private String event,round;
    private String position;

    public Movie() {
    }

    public Movie(String event, String round, String position) {

        this.event = event;
        this.round=round;
        this.position=position;

    }

    public String getTitle() {
        return event;
    }

    public void setTitle(String name) {
        this.event = name;
    }

    public String getThumbnailUrl() {
        return round;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.round = thumbnailUrl;
    }

    public String getYear() {
        return position;
    }

    public void setYear(String year) {
        this.position = year;
    }


}