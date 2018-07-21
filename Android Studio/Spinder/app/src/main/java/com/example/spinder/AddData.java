package com.example.spinder;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.spinder.interfaces.VolleyCallback;
import com.example.spinder.utils.RestWebService;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddData extends AppCompatActivity {

    private String game, loc, time;
    private EditText editTextGame, editTextLoc, editTextTime;
    private Bitmap mSelectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
    }

    public void addDataWithRest(String url){
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
            gameDetails.put("url", url);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RestWebService.postDataToService(gameDetails, this.getApplicationContext());
        Log.d("TAG", game + " " + loc + " " + time + " " + url);
    }

    private static int RESULT_LOAD_IMAGE = 1;
    public void addPhoto(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream;
                imageStream = getContentResolver().openInputStream(imageUri);
                mSelectedImage = BitmapFactory.decodeStream(imageStream);

                ImageView imageView = (ImageView) findViewById(R.id.imageUpload);
                imageView.setImageBitmap(mSelectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public void submitGames(View view){
        RestWebService.uploadImageToImgur(mSelectedImage, getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
            }

            @Override
            public void onSuccessImgur(String responseString) {
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    JSONObject body = jsonObject.getJSONObject("data");
                    String imgUrl = body.getString("link").toString();
                    addDataWithRest(imgUrl);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed() {

            }
        });
    }
}
