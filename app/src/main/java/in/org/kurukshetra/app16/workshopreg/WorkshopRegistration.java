package in.org.kurukshetra.app16.workshopreg;

import java.util.List;


/**
 * Created by kishore on 7/1/16.
 */
public class WorkshopRegistration {

    public List<WorkshopUser> getWorkshopUsers() {
        return workshopUsers;
    }

    public void setWorkshopUsers(List<WorkshopUser> workshopUsers) {
        this.workshopUsers = workshopUsers;
    }

    public String getWork_id() {
        return work_id;
    }

    public void setWork_id(String work_id) {
        this.work_id = work_id;
    }

    public Boolean isFb() {
        return fb;
    }

    public void setFb(boolean fb) {
        this.fb = fb;
    }

    public Boolean isWh() {
        return wh;
    }

    public void setWh(boolean wh) {
        this.wh = wh;
    }

    public Boolean isSa() {
        return sa;
    }

    public void setSa(boolean sa) {
        this.sa = sa;
    }

    public Boolean isWeb() {
        return web;
    }

    public void setWeb(boolean web) {
        this.web = web;
    }

    public Boolean isOther() {
        return other;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    public String getOther_detail() {
        return other_detail;
    }

    public void setOther_detail(String other_detail) {
        this.other_detail = other_detail;
    }

    public String getSa_Id() {
        return sa_Id;
    }

    public void setSa_Id(String sa_Id) {
        this.sa_Id = sa_Id;
    }

    public List<WorkshopUser> workshopUsers;

    public String work_id;

    public boolean fb;
    public boolean wh;
    public boolean sa;
    public boolean web;
    public boolean other;

    public String other_detail;
    public String sa_Id;

    public WorkshopRegistration(List<WorkshopUser> workshopUsers, String work_id, boolean fb, boolean wh, boolean sa, boolean web, boolean other, String other_detail, String sa_Id) {
        this.workshopUsers = workshopUsers;
        this.work_id = work_id;
        this.fb = fb;
        this.wh = wh;
        this.sa = sa;
        this.web = web;
        this.other = other;
        this.other_detail = other_detail;
        this.sa_Id = sa_Id;
    }
}
