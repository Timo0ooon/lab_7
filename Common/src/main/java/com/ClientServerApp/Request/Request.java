package com.ClientServerApp.Request;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;

import java.io.Serial;
import java.io.Serializable;

import java.util.Arrays;
import java.util.Hashtable;

public class Request implements Serializable {

    @Serial
    private static final long serialVersionUID = 2022L;

    private String message;
    private String[] options;
    private HumanBeing[] objects;
    private Hashtable<Integer, HumanBeing> collection;
    private HumanBeing humanBeing;

    public Request(String message) {
        this.message = message;
    }

    public Request(String message, String[] options) {
        this.message = message;
        this.options = options;
    }

    public Request(String message, String[] options, HumanBeing[] objects) {
        this.message = message;
        this.options = options;
        this.objects = objects;
    }

    public String getMessage() {
        return message;
    }

    public String[] getOptions() {
        return options;
    }

    public Hashtable<Integer, HumanBeing> getCollection() {
        return collection;
    }

    public HumanBeing getHumanBeing() {
        return humanBeing;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public void setCollection(Hashtable<Integer, HumanBeing> collection) {
        this.collection = collection;
    }

    public void setHumanBeing(HumanBeing humanBeing) {
        this.humanBeing = humanBeing;
    }

    public HumanBeing[] getObjects() {
        return objects;
    }

    public void setObjects(HumanBeing[] objects) {
        this.objects = objects;
    }

    @Override
    public String toString() {
        return "Request{" +
                "message='" + message + '\'' +
                ", options=" + Arrays.toString(options) +
                ", collection=" + collection +
                ", humanBeing=" + humanBeing +
                '}';
    }

}
