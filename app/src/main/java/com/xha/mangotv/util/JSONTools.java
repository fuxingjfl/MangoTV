package com.xha.mangotv.util;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ysq on 2018/9/25.
 */

public class JSONTools {

    public static JSONObject parseMapToJson(HashMap<String, String> map) throws JSONException {
        JSONObject json = new JSONObject();
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            json.put(key,val);
        }

        return json;
    }

    public static JSONObject parseMapToJsonOBJ(HashMap<String, Object> map) throws JSONException {
        JSONObject json = new JSONObject();
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            json.put(key,val);
        }

        return json;
    }

    public static JSONObject mergeJson(JSONObject obj, String json) throws JSONException {
        HashMap map = new Gson().fromJson(json, HashMap.class);
        Iterator iterator = map.entrySet().iterator();

        while (iterator.hasNext()){

            Map.Entry next = (Map.Entry) iterator.next();

            obj.put((String) next.getKey(),next.getValue());

        }
        return obj;
    }


}
