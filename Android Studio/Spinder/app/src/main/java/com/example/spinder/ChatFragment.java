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
import com.example.spinder.interfaces.VolleyCallback;
import com.example.spinder.utils.RestWebService;
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

        EditText indexView = getView().findViewById(R.id.editText);
        mTextView.setText("");

        RestWebService.getJSONData(indexView.getText().toString(), this.getContext(), new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {

                String index, game, loc, time;
                index = game = loc = time = "";


                JSONArray gamesList = null;
                JSONObject singleGame = null;
                boolean isAllGames = true;
                try {
                    gamesList = jsonObject.getJSONArray("games");
                } catch (JSONException e) {
                    try {
                        singleGame = jsonObject.getJSONObject("games");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    isAllGames = false;
                }

                if(isAllGames) {
                    for (int i = 0; i < gamesList.length(); i++) {
                        try {
                            JSONObject eachGame = null;
                            eachGame = gamesList.getJSONObject(i);
                            Log.d("ALL GAME", eachGame.toString());

                            index = eachGame.getString("id").toString();
                            game = eachGame.getString("title").toString();
                            loc = eachGame.getString("location").toString();
                            time = eachGame.getString("time").toString();
                            mTextView.append(index + " " + game + " " + loc + " " + time + "\n");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    Log.d("SINGLE GAME", singleGame.toString());
                    try {
                        index = singleGame.getString("id").toString();
                        game = singleGame.getString("title").toString();
                        loc = singleGame.getString("location").toString();
                        time = singleGame.getString("time").toString();
                        mTextView.append(index + " " + game + " " + loc + " " + time);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onSuccessImgur(String responseString) {

            }

            @Override
            public void onFailed(){
                mTextView.append("Wrong Input");
            }
        });

        // Add a request (in this example, called stringRequest) to your RequestQueue.
        //SingletonInstance.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    public void createService(){
        Intent intent = new Intent(getContext(), AddData.class);
        startActivity(intent);
    }
}
