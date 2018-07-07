package com.example.spinder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.spinder.utils.SingletonInstance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView mTextView = null;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        Button button = (Button) view.findViewById(R.id.hitService);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                hitRestService();
            }
        });

        Button buttonAdd = (Button) view.findViewById(R.id.createService);
        buttonAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                createService();
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //throw new RuntimeException(context.toString()
            //        + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTextView = (TextView) getView().findViewById(R.id.ChatFragmentText);
        // or  (ImageView) view.findViewById(R.id.foo);
    }

    public void hitRestService(){
        // Instantiate the RequestQueue.
        //RequestQueue queue = Volley.newRequestQueue(getContext());

        //SINGLETON RequestQueue
        // Get a RequestQueue
        //RequestQueue queue = SingletonInstance.getInstance(getContext()).
        //        getRequestQueue();




        String url ="http://spinder-v2-spinder-test.193b.starter-ca-central-1.openshiftapps.com/games/recent";

        EditText indexView = getView().findViewById(R.id.editText);
        if(indexView.getText().length() != 0){
            url += ("/" + indexView.getText());
        }
        Log.d("TAG", "FINAL URL:" + url);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        /*mTextView.setText("Response is: "+ response);*/
                        mTextView.setText("");
                        ArrayList<String> recentGames = new ArrayList<String>();

                        String responseJSONPlain = response.toString();
                        try {
                            String id, title, location, time;
                            id = title = location = time = "";

                            JSONObject jsonObj = new JSONObject(responseJSONPlain);
                            boolean isArray = true;
                            JSONArray gamesArray = null;
                            JSONObject gameSingle = null;

                            // Getting JSON Array node or object
                            try {
                                gamesArray = jsonObj.getJSONArray("games");
                            } catch (JSONException e){
                                isArray = false;
                                gameSingle = jsonObj.getJSONObject("games");
                            }


                            if(isArray) {
                                for (int i = 0; i < gamesArray.length(); i++) {
                                    JSONObject c = gamesArray.getJSONObject(i);
                                    title = c.getString("title");
                                    location = c.getString("location");
                                    time = c.getString("time");
                                    id = c.getString("id");

                                    mTextView.append(id + " " + title + " " + location + " " + time + "\n");
                                }
                            }
                            else{
                                id = gameSingle.getString("id");
                                title = gameSingle.getString("title");
                                location = gameSingle.getString("location");
                                time = gameSingle.getString("time");

                                mTextView.append(id + " " + title + " " + location + " " + time + "\n");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        //queue.add(stringRequest);


        // Add a request (in this example, called stringRequest) to your RequestQueue.
        SingletonInstance.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    public void createService(){
        Intent intent = new Intent(getContext(), AddData.class);
        startActivity(intent);
    }
}
