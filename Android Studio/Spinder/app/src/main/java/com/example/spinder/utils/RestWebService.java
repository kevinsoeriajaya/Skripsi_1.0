package com.example.spinder.utils;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.Map;

public class RestWebService {

    static Toast mToast;
    private static final String TAG = "TAG";
    private static final String token = "4a0e8ef2cf1f27731a5778e54ad84fcae8cd5139";

    public static void postDataToService(JSONObject gameDetails, final Context context){
        //RequestQueue queue = SingletonInstance.getInstance(context).
        //        getRequestQueue();

        //String url ="http://spinder-v2-spinder-test.193b.starter-ca-central-1.openshiftapps.com/games/addJSON";
        String url = "https://spinder-spring-1.herokuapp.com/games";
        String message = "";

        Log.d("TAG", "FINAL URL:" + url);

        // Request a string response from the provided URL.
        try {
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, gameDetails,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                    Log.d("RESPONSE", response.toString());
                    Log.d(TAG, response.toString());
                    mToast = Toast.makeText(context, "Games submitted", Toast.LENGTH_SHORT);
                    mToast.show();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("TAG", "POST ERROR");
                    Log.d(TAG, error.toString());
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
        //String url ="http://spinder-v2-spinder-test.193b.starter-ca-central-1.openshiftapps.com/games/recent";
        //String url = "https://spinder-rest-heroku-1.herokuapp.com/games/recent";
        String url = "https://spinder-spring-1.herokuapp.com/games";

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

    public static void getImageList(final Context context, final VolleyCallback volleyCallback){
        String url ="https://api.imgur.com/3/account/me/images/ids";

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
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("Authorization", context.getString(R.string.access_token));

                    return params;
                }
            };

            SingletonInstance.getInstance(context).addToRequestQueue(jsonObjectReq);
        }
        catch (Exception e){
            Log.d("TAG", "CATCH");
            e.printStackTrace();
        }
    }

    public static void uploadImageToImgur(final Bitmap bmp, Context context, final VolleyCallback volleyCallback){
        Log.i(TAG,"start upload");
        String url = "https://api.imgur.com/3/image";
        StringRequest uploadRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "finished image upload");
                Log.d(TAG, response.toString());

                volleyCallback.onSuccessImgur(response);
                Log.d(TAG, "finished rest upload");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
                Log.e(TAG,"finish/error upload");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("image", SpinderUtility.get64BaseImage(bmp));
                params.put("title", "title");
                //params.put(AppConst.IMGUR_TAG_NAME, String.valueOf(System.currentTimeMillis()));
                return params;
            }
        };

        SingletonInstance.getInstance(context).addToRequestQueue(uploadRequest);

    }
}
