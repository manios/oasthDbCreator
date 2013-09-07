package com.manios.oasthdbcreator.model;

public class BusLine {

    private int uid;
    private int groupUid;
    private String lineNumber;
    private String name;
    private String nameEng;
    private boolean circular;

    public BusLine() {
    }

    public BusLine(BusLine bli) {
        this.uid = bli.uid;
        this.groupUid = bli.groupUid;
        this.lineNumber = bli.lineNumber;
        this.name = bli.name;
        this.nameEng = bli.nameEng;
        this.circular = bli.circular;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getGroupUid() {
        return groupUid;
    }

    public void setGroupUid(int groupUid) {
        this.groupUid = groupUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public boolean isCircular() {
        return circular;
    }

    public void setCircular(boolean circular) {
        this.circular = circular;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return "BusLine{" + "uid=" + uid + ", groupUid=" + groupUid + ", lineNumber=" + lineNumber + ", name=" + name + ", nameEng=" + nameEng + ", circular=" + circular + '}';
    }
}
