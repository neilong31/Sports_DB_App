package com.example.dps924_a4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import static com.example.dps924_a4.UtilityBitmapDB.getImage;

public class DetailsActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    TextView textView;
    ImageView imageView;
    Spinner spinner;
    EditText namesET, cityET, mvpET, stadiumET;
    LinearLayout topLayout, bottomLayout;
    Button exitButton, updateButton, deleteButton, loadPicture;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        namesET = findViewById(R.id.nameET);
        cityET = findViewById(R.id.cityET);
        spinner = findViewById(R.id.sportsSpinner);
        mvpET = findViewById(R.id.mvpET);
        imageView = findViewById(R.id.imgView);

        String [] sports = {"", "Baseball", "Basketball", "Football", "Hockey"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sports);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        Intent intent = getIntent();
        final Integer idExtra = intent.getIntExtra("id", -1);
        String data = intent.getStringExtra("name");
        namesET.setText(data);
        data = intent.getStringExtra("city");
        cityET.setText(data);
        data = intent.getStringExtra("sport");
        spinner.setSelection(Integer.parseInt(data));
        data = intent.getStringExtra("mvp");
        mvpET.setText(data);
        byte[] image = intent.getByteArrayExtra("image");
        bitmap = getImage(image);
        imageView.setImageBitmap(bitmap);

        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Load Image"), RESULT_LOAD_IMAGE);
            }
        });

        exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, com.example.dps924_a4.MainActivity.class);
                startActivity(intent);
            }
        });
        updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
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
                    dbHandler.updateTeam(idExtra, name, city, sport, mvp, image);
                    Intent intent = new Intent(DetailsActivity.this, com.example.dps924_a4.MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"City and Name required", Toast.LENGTH_SHORT).show();
                }
            }
        });
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
                int deleteCheck = dbHandler.deleteTeam(idExtra);
                if(deleteCheck > 0){
                    Intent intent = new Intent(DetailsActivity.this, com.example.dps924_a4.MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Not Deleted", Toast.LENGTH_SHORT).show();
                }
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
