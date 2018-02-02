package com.wo.bu.dong.common.base;

import java.io.Serializable;
import java.lang.reflect.Type;

import org.apache.commons.lang3.SerializationUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * dto基础类
 */
public abstract class BaseDto implements Serializable, Cloneable {
    private static final long   serialVersionUID = 1L;

    //JSON格式化工具
    protected static final Gson GSON             = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

    public static String toString(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }

    @Override
    public Object clone() {
        return SerializationUtils.clone(this);
    }
}
