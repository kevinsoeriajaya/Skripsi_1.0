package com.example.spinder.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.spinder.R;
import com.example.spinder.interfaces.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RestWebService {

    static Toast mToast;

    public static void postDataToService(JSONObject gameDetails, final Context context){
        //RequestQueue queue = SingletonInstance.getInstance(context).
        //        getRequestQueue();

        String url ="http://spinder-v2-spinder-test.193b.starter-ca-central-1.openshiftapps.com/games/addJSON";
        String message = "";

        Log.d("TAG", "FINAL URL:" + url);

        // Request a string response from the provided URL.
        try {
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, gameDetails,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("RESPONSE", response.toString());
                            mToast = Toast.makeText(context, "Games submitted", Toast.LENGTH_SHORT);
                            mToast.show();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("TAG", "POST ERROR");
                    mToast = Toast.makeText(context, "Games submit failed", Toast.LENGTH_SHORT);
                    mToast.show();
                    //Log.d("TAG", error.toString());
                }
            });
            // Add the request to the RequestQueue.
            //queue.add(stringRequest);


            // Add a request (in this example, called stringRequest) to your RequestQueue.
            SingletonInstance.getInstance(context).addToRequestQueue(jsonObjReq);
        }
        catch (Exception e){
            Log.d("TAG", "CATCH");
            //mToast = Toast.makeText(context, "ERROR", Toast.LENGTH_LONG);
            e.printStackTrace();
        }

    }

    public static void getJSONData(String index, final Context context, final VolleyCallback volleyCallback){
        String url ="http://spinder-v2-spinder-test.193b.starter-ca-central-1.openshiftapps.com/games/recent";
        if(!index.equals("")){
            url += ("/" + index);
        }

        String message = "";

        Log.d("TAG", "FINAL URL ARRAY JSON:" + url);

        // Request a string response from the provided URL.
        try {
            JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            //Log.d("RESPONSE", response.toString());
                            volleyCallback.onSuccess(response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("TAG", "GET JSON ERROR");
                            volleyCallback.onFailed();
                            //Log.d("TAG", error.getMessage().toString());
                        }
            });

            SingletonInstance.getInstance(context).addToRequestQueue(jsonObjectReq);
        }
        catch (Exception e){
            Log.d("TAG", "CATCH");
            e.printStackTrace();
        }
    }
}
