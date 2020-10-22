package com.firdadev.runsheetv2.model.prarunsheet;

import java.util.Date;

public class TempData {

    public int id;
    public String praRunsheetNo, cNoteNo, drsheetStts, drsheetReceiver, flag, uid, zone;
    public Date drsheetDate, udate, createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPraRunsheetNo() {
        return praRunsheetNo;
    }

    public void setPraRunsheetNo(String praRunsheetNo) {
        this.praRunsheetNo = praRunsheetNo;
    }

    public String getcNoteNo() {
        return cNoteNo;
    }

    public void setcNoteNo(String cNoteNo) {
        this.cNoteNo = cNoteNo;
    }

    public String getDrsheetStts() {
        return drsheetStts;
    }

    public void setDrsheetStts(String drsheetStts) {
        this.drsheetStts = drsheetStts;
    }

    public String getDrsheetReceiver() {
        return drsheetReceiver;
    }

    public void setDrsheetReceiver(String drsheetReceiver) {
        this.drsheetReceiver = drsheetReceiver;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Date getDrsheetDate() {
        return drsheetDate;
    }

    public void setDrsheetDate(Date drsheetDate) {
        this.drsheetDate = drsheetDate;
    }

    public Date getUdate() {
        return udate;
    }

    public void setUdate(Date udate) {
        this.udate = udate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
