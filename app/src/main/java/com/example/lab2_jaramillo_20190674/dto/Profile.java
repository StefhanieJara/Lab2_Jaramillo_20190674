package com.example.lab2_jaramillo_20190674.dto;
import java.util.List;


public class Profile {


   List<Results> results;
   Info info;

    public void setResults(List<Results> results) {
        this.results = results;
    }
    public List<Results> getResults() {
        return results;
    }
    
    public void setInfo(Info info) {
        this.info = info;
    }
    public Info getInfo() {
        return info;
    }
    
}