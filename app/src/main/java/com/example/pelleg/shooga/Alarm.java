package com.example.pelleg.shooga;

public class Alarm {

    private String code;
    private String Name;
    private String Time;
    private Boolean Repeat=false;
    private Boolean Statue=false;

    public Alarm(String code, String name, String time, Boolean repeat, Boolean statue) {
        this.code = code;
        Name = name;
        Time = time;
        Repeat = repeat;
        Statue = statue;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public Boolean getRepeat() {
        return Repeat;
    }

    public void setRepeat(Boolean repeat) {
        Repeat = repeat;
    }

    public Boolean getStatue() {
        return Statue;
    }

    public void setStatue(Boolean statue) {
        Statue = statue;
    }
}
