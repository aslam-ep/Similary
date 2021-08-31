package com.aslamep.similary;

import java.util.List;

class Info1 {
    public String Name;
    public String Type;
    public String wTeaser;
    public String wUrl;
    public String yUrl;
    public String yID;

    public Info1() {
    }

    public Info1(String name, String type, String wTeaser, String wUrl, String yUrl, String yID) {
        Name = name;
        Type = type;
        this.wTeaser = wTeaser;
        this.wUrl = wUrl;
        this.yUrl = yUrl;
        this.yID = yID;
    }
}

class Similar1{
    public List<Info1> Info;
    public List<Result1> Results;

    public Similar1() {
    }

    public Similar1(List<Info1> Info, List<Result1> Results) {
        this.Info = Info;
        this.Results = Results;
    }
}

class Result1 {
    public String Name;
    public String Type;
    public String wTeaser;
    public String wUrl;
    public String yUrl;
    public String yID;

    public Result1() {
    }

    public Result1(String name, String type, String wTeaser, String wUrl, String yUrl, String yID) {
        Name = name;
        Type = type;
        this.wTeaser = wTeaser;
        this.wUrl = wUrl;
        this.yUrl = yUrl;
        this.yID = yID;
    }
}


public class TasteDiveResponse{
    public Similar1 Similar;

    public TasteDiveResponse() {
    }

    public TasteDiveResponse(Similar1 Similar) {
        this.Similar = Similar;
    }
}
