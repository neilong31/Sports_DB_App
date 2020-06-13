package com.example.dps924_a4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView list;
    Button exitButton, addButton;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.listLL);
        DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
        final List<Integer> id = dbHandler.getAllIDs();
        final Integer[] idArr = id.toArray(new Integer[0]);
        final List<String> names = dbHandler.getAllNames();
        final String[] namesArr = names.toArray(new String[0]);
        final List<String> cities = dbHandler.getAllCities();
        final String[] citiesArr = cities.toArray(new String[0]);
        final List<String> sports = dbHandler.getAllSports();
        final String[] sportsArr = sports.toArray(new String[0]);
        final List<String> mvps = dbHandler.getAllMVPs();
        final String[] mvpArr = mvps.toArray(new String[0]);
        final List<byte[]> images = dbHandler.getAllImages();
        final CustomListAdapter adapter = new CustomListAdapter(this, namesArr, citiesArr, sportsArr, mvpArr, images);
        list = findViewById(R.id.listLV);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("id", idArr[position]);
                intent.putExtra("name", namesArr[position]);
                intent.putExtra("city", citiesArr[position]);
                intent.putExtra("sport", sportsArr[position]);
                intent.putExtra("mvp", mvpArr[position]);
                intent.putExtra("image", images.get(position));
                startActivity(intent);
            }
        });
        exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });

        boolean temp = isStoragePermissionGranted();


    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
        }
    }
}

class CustomListAdapter extends ArrayAdapter<String> {
    Activity context;
    String[] names;
    String[] cities;
    String[] sports;
    String[] mvps;
    List<byte[]> images;

    public CustomListAdapter(Activity pContext, String[] pNames, String[] pCities, String[] pSports, String[] pMVPs, List<byte[]> pImages){
        super(pContext, R.layout.one_item, pNames);
        context = pContext;
        names = pNames;
        cities = pCities;
        sports = pSports;
        mvps = pMVPs;
        images = pImages;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.one_item, null, true);
        TextView listRow = rowView.findViewById(R.id.listRowTV);
        listRow.setText(cities[position] + " " + names[position]);
        return rowView;
    }
}