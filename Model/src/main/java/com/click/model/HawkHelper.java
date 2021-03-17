package com.click.model;

import android.content.Context;

import com.google.gson.JsonObject;
import com.click.pc.Constant;
import com.orhanobut.hawk.Hawk;

public class HawkHelper {
    public static void setData(Context context, JsonObject result) {
        String token = result.get("token").getAsString();

        Hawk.init(context).build();
        Hawk.put(Constant._TOKEN, token);
    }

    public static void setData(Context context, String name, Object data) {

        Hawk.init(context).build();
        Hawk.put(name, data);
    }

    public static void setData(Context context, String firstName, String lastName, String mobile) {

        Hawk.init(context).build();
        Hawk.put("firstName", firstName);
        Hawk.put("lastName", lastName);
        Hawk.put("mobile", mobile);
    }

    public static void clearData(Context context) {
        Hawk.init(context).build();
        Hawk.deleteAll();
    }

    public static boolean checkToken(Context context) {
        Hawk.init(context).build();

        String token = Hawk.get(Constant._TOKEN);

        if (token == null || token.matches("")) {
            return false;
        } else {
            return true;
        }
    }

    public static String getToken(Context context) {
        Hawk.init(context).build();

        String token = Hawk.get(Constant._TOKEN);

        return token;
    }

    public static JsonObject getProfile(Context context) {
        JsonObject dataObject = new JsonObject();

        try {
            Hawk.init(context).build();

            String firstName = Hawk.get("firstName");
            String lastName = Hawk.get("lastName");
            String mobile = Hawk.get("mobile");

            dataObject.addProperty("firstName", firstName);
            dataObject.addProperty("lastName", lastName);
            dataObject.addProperty("mobile", mobile);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return dataObject;
    }

    public static Object getData(Context context, String name) {
        Hawk.init(context).build();

        Object token = Hawk.get(name);

        return token;
    }
}
