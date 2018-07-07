package com.example.spinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.spinder.utils.RestWebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AddData extends AppCompatActivity {

    private String game, loc, time;
    private EditText editTextGame, editTextLoc, editTextTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
    }

    public void addDataWithRest(View view){
        game = loc = time = "";

        editTextGame = (EditText)findViewById(R.id.inputGame);
        editTextLoc = (EditText)findViewById(R.id.inputLoc);
        editTextTime = (EditText)findViewById(R.id.inputTime);

        game = editTextGame.getText().toString();
        loc = editTextLoc.getText().toString();
        time = editTextTime.getText().toString();

        JSONObject gameDetails = new JSONObject();
        try {
            gameDetails.put("title", game);
            gameDetails.put("location", loc);
            gameDetails.put("time", time);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RestWebService.postDataToService(gameDetails, this.getApplicationContext());
        Log.d("TAG", game + " " + loc + " " + time);
    }
}
