package com.bytebeats.zookeeper.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-08 19:52
 */
public class JsonUtils {
    private static final Gson GSON = new GsonBuilder().create();

    public static <T> T fromJson(String json, Type typeOfT){
        return GSON.fromJson(json, typeOfT);
    }

    public static <T> T fromJson(String json, Class<T> type){
        return GSON.fromJson(json, type);
    }

    public static String toJson(Object obj){
        return GSON.toJson(obj);
    }
}
