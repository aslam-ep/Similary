package com.aslamep.similary;

import java.util.List;

class Info1 {
    public String Name;
    public String Type;

    public Info1() {
    }

    public Info1(String Name, String Type) {
        this.Name = Name;
        this.Type = Type;
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

    public Result1() {
    }

    public Result1(String Name, String Type) {
        this.Name = Name;
        this.Type = Type;
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
