package com.manios.oasthdbcreator.model;

public class BusStop {

    private int uid;
    private String name;
    private String nameEng;
    private GeoPosition position;

    public BusStop() {
    }

    public BusStop(BusStop bs) {
        this.uid = bs.uid;
        this.name = bs.name;
        this.nameEng = bs.nameEng;
        this.position = bs.position;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public GeoPosition getPosition() {
        return position;
    }

    public void setPosition(GeoPosition position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "BusStop{" + "uid=" + uid + ", name=" + name + ", nameEng=" + nameEng + ", position=" + position + '}';
    }
}
