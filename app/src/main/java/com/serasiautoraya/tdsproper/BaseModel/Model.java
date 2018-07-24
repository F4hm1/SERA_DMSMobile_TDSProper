package com.serasiautoraya.tdsproper.BaseModel;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Randi Dwi Nandra on 21/03/2017.
 */

public class Model {

    public HashMap<String,String> getHashMapType(){
//        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();
        Type stringStringMap = new TypeToken<Map<String, String>>(){}.getType();
        Log.d("TAGSSS", getJSONType());
        Map<String,String> map = gson.fromJson(getJSONType(), stringStringMap);
        return new HashMap<String, String>(map);
    }


    public HashMap<String,Object> getHashMapTypeList(){
//        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();
        Type stringStringMap = new TypeToken<Map<String, Object>>(){}.getType();
        Log.d("TAGSSS", getJSONType());
        Map<String,Object> map = gson.fromJson(getJSONType(), stringStringMap);
        return new HashMap<String, Object>(map);
    }

    public String getJSONType(){
//        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }

    public String getJSONType(Object object){
//        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

    public static <T> T getModelInstance(Object object, Class<T> cls){
//        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(gson.toJson(object), cls);
    }

    public static <T> T getModelInstanceFromString(String jsonString, Class<T> cls){
//        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(jsonString, cls);
    }

    public static String getNonNullable(String text){
        if(text == null){
            text = "null";
        }
        return text;
    }
}
