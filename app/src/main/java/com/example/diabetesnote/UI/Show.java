package com.example.diabetesnote.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.diabetesnote.Adapters.ShowRecyclerAdapter;
import com.example.diabetesnote.DataModel.Information;
import com.example.diabetesnote.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class Show extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Information> informationList;
    ShowRecyclerAdapter adapter;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ListCreation();
    }

    private void Init() {
        recyclerView = findViewById(R.id.show_recycler);
        realm = Realm.getDefaultInstance();
    }

    private void ListCreation() {
        informationList = realm.where(Information.class).findAll();
        adapter = new ShowRecyclerAdapter(informationList , this);
        recyclerView.setAdapter(adapter);
    }
}