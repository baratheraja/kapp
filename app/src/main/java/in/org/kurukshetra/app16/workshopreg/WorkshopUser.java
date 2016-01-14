package in.org.kurukshetra.app16.workshopreg;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kishore on 7/1/16.
 */
public class WorkshopUser {

    @SerializedName("displayName")
    String displayName;
    @SerializedName("email")
    String email;
    @SerializedName("college")
    String college;
    @SerializedName("course")
    String course;
    @SerializedName("year")
    String year;
    @SerializedName("contact")
    String contact;

    public WorkshopUser() {
    }

    public WorkshopUser(String name, String email, String college, String course, String year, String contact) {
        this.displayName = name;
        this.email = email;
        this.college = college;
        this.course = course;
        this.year = year;
        this.contact = contact;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
