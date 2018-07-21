package com.example.spinder.interfaces;

import org.json.JSONArray;
import org.json.JSONObject;

public interface VolleyCallback {
    void onSuccess(JSONObject jsonObject);
    void onSuccessImgur(String responseString);
    void onFailed();
}
