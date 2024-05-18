package com.ClientServerApp.Response;

import com.ClientServerApp.Model.HumanBeing.HumanBeing;

import java.io.Serial;
import java.io.Serializable;

import java.util.Arrays;
import java.util.Hashtable;

public class Response implements Serializable {

    @Serial
    private static final long serialVersionUID = 2011L;

    private String message;
    private String[] options;
    private Hashtable<Integer, HumanBeing> collection;
    private HumanBeing humanBeing;
    private Object object;


    public Response(Object object, String message) {
        this.message = message;
        this.object = object;
    }

    public Response(String message) {
        this.message = message;
    }

    public Response(String message, Hashtable<Integer, HumanBeing> collection) {
        this.message = message;
        this.collection = collection;
    }

    public Response(String message, String[] options, Hashtable<Integer, HumanBeing> collection, HumanBeing humanBeing) {
        this.message = message;
        this.options = options;
        this.collection = collection;
        this.humanBeing = humanBeing;
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

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", options=" + Arrays.toString(options) +
                ", collection=" + collection +
                ", humanBeing=" + humanBeing +
                '}';
    }
}
