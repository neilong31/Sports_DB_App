package com.example.dps924_a4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.example.dps924_a4.UtilityBitmapDB.getBytes;

public class CreateActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    Spinner spinner;
    ImageView imageView;
    EditText namesET, cityET, sportsET, mvpET, stadiumET;
    Button exitButton, submitButton, loadPicture;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        spinner = findViewById(R.id.sportsSpinner);
        String [] sports = {"", "Baseball", "Basketball", "Football", "Hockey"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sports);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        imageView = findViewById(R.id.imgView);
        imageView.setImageResource(R.drawable.img_not_found);

        namesET = findViewById(R.id.nameET);
        cityET = findViewById(R.id.cityET);
        mvpET = findViewById(R.id.mvpET);

        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Load Image"), RESULT_LOAD_IMAGE);
            }
        });



        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
                String name = namesET.getText().toString();
                String city = cityET.getText().toString();
                Integer temp = spinner.getSelectedItemPosition();
                String sport = temp.toString();
                String mvp = mvpET.getText().toString();
                byte[] image = getBytes(bitmap);
                if(name.length() != 0 && city.length() != 0){
                    dbHandler.insertTeam(name, city, sport, mvp, image);
                    namesET.setText("");
                    cityET.setText("");
                    spinner.setSelection(0);
                    mvpET.setText("");
                    imageView.setImageResource(R.drawable.img_not_found);
                }
                else{
                    Toast.makeText(getApplicationContext(),"City and Name required", Toast.LENGTH_SHORT).show();
                }
            }
        });
        exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            try{
                InputStream is = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(is);

                imageView.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}
