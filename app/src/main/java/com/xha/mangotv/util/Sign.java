package com.xha.mangotv.util;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Sign {
    public static String getSign(Map<String, String> params, String secret) {
        Log.e("TAG","秘钥:::::"+secret);
        ArrayList<String> strings = new ArrayList<String>();
        Set<Map.Entry<String, String>> set = params.entrySet();
        for (Map.Entry<String, String> entry : set) {
            strings.add(entry.getKey());
        }
        String[] keys = strings.toArray(new String[strings.size()]);
        Arrays.sort(keys);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            sb.append(key).append("=").append(params.get(key)).append("&");
        }
        String message = sb.substring(0, sb.length() - 1).toLowerCase();
        System.out.println("str=" + message);
        try {
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
            sha256Hmac.init(secretKey);
            String sign = Base64.encodeToString(sha256Hmac.doFinal(message.getBytes("UTF-8")), Base64.DEFAULT);
            return sign.replace("\n", "").replace("\r", "");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
