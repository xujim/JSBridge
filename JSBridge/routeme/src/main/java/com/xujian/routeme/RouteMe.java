package com.xujian.routeme;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yujie on 15/12/5.
 */
public class RouteMe {
    private JsonObject mRouteMap;
    private Context mApplicationContext;

    public void setContext(Context ctx){
        mApplicationContext = ctx;
    }
    private static class SingletonHolder {
        public static final RouteMe instance = new RouteMe();
    }

    public interface IRouteCompleteCallBack{
        public void onComplete(Intent intent);
    }
    public interface IRouteMeCompleteCallBack{
        public void onComplete(String targetType, Bundle params);
    }

    public static RouteMe getInstance() {
        return SingletonHolder.instance;
    }

    public void loadConfig(String filePath){
        JsonParser parser = new JsonParser();
            try {
                System.out.println("Reading JSON file from Java program");
                FileReader fileReader = new FileReader(filePath);
                mRouteMap = (JsonObject) parser.parse(fileReader).getAsJsonObject();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }

    public void loadConfigFrom(InputStream is){
        JsonParser parser = new JsonParser();
        InputStreamReader isreader = new InputStreamReader(is);
        mRouteMap = (JsonObject) parser.parse(isreader).getAsJsonObject();
    }

    public void routeTo(String targetUrl, IRouteCompleteCallBack callback){
        Uri filePathUri = Uri.parse(targetUrl);
        Intent targetIntent = null;
        if (filePathUri.getScheme().equalsIgnoreCase("http") || filePathUri.getScheme().equalsIgnoreCase("https")){
            targetIntent = new Intent("com.siva4u.main.WebViewActivity");
            targetIntent.setClassName("com.siva4u.main", "com.siva4u.main.WebViewActivity");
        }else if(filePathUri.getScheme().equalsIgnoreCase("RouteMe")) {
//        String file_name = filePathUri.getLastPathSegment().toString();
            String file_path = filePathUri.getLastPathSegment();
            JsonObject config = mRouteMap.getAsJsonObject(file_path);
            if (config != null) {
                JsonElement targetObj = config.get("targetType");
                String targetType = targetObj.getAsString();
                JsonObject params = config.getAsJsonObject("parameters");
//            try {
//                Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(targetType);
//                clazz.newInstance();
//            }catch (Exception ex){
//
//            }
                targetIntent = new Intent(targetType);
                String packageName = targetType.substring(0, targetType.lastIndexOf("."));
                targetIntent.setClassName(packageName, targetType);
                Set<Map.Entry<String, JsonElement>> entrySet = params.entrySet();
                for (Map.Entry<String, JsonElement> entry : entrySet) {
                    String key = entry.getKey();
                    targetIntent.putExtra(key, params.get(key).getAsString());
                }
            }
        }
        if (callback!=null){
            targetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            callback.onComplete(targetIntent);
        }
    }

    public void routeMeTo(String targetUrl, IRouteMeCompleteCallBack callback){
        Uri filePathUri = Uri.parse(targetUrl);
        String targetType = "";
        Bundle bundle = new Bundle();
        if (filePathUri.getScheme().equalsIgnoreCase("http") || filePathUri.getScheme().equalsIgnoreCase("https")){
            targetType = "com.siva4u.main.WebViewActivity";
            bundle.putString("htmlUrl", targetUrl);
        }else if(filePathUri.getScheme().equalsIgnoreCase("RouteMe")) {
            String file_path = filePathUri.getLastPathSegment();
            UrlQuerySanitizer sanitizer = new UrlQuerySanitizer();
            sanitizer.setAllowUnregisteredParamaters(true);
            sanitizer.parseUrl(targetUrl);
            List<UrlQuerySanitizer.ParameterValuePair> queryparams = sanitizer.getParameterList();
            JsonObject config = mRouteMap.getAsJsonObject(file_path);
            if (config != null) {
                JsonElement targetObj = config.get("targetType");
                targetType = targetObj.getAsString();
                JsonObject params = config.getAsJsonObject("parameters");
                Set<Map.Entry<String, JsonElement>> entrySet = params.entrySet();
                for (Map.Entry<String, JsonElement> entry : entrySet) {
                    String key = entry.getKey();
                    bundle.putString(key, params.get(key).getAsString());
                }
                for (UrlQuerySanitizer.ParameterValuePair pair:queryparams){
                    bundle.putString(pair.mParameter, pair.mValue);
                }
            }
        }

        if (callback!=null){
            callback.onComplete(targetType, bundle);
        }
    }
}
