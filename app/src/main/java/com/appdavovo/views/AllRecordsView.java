package com.appdavovo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.appdavovo.R;
import com.appdavovo.controllers.MainController;
import com.appdavovo.models.models.Record;

import java.util.ArrayList;
import java.util.List;

public class AllRecordsView extends AppCompatActivity {

    private ListView listView;
    private MainController mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_records_view);
        mainController = new MainController();

        listView = (ListView)(findViewById(R.id.recordListView));

        setUpListView();
    }

    private void setUpListView() {

        List<Record> records = mainController.getAllRecords(this);
        List<String> recordsFormatted = new ArrayList<>();
        for (Record record : records) {
            recordsFormatted.add(record.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recordsFormatted);
        listView.setAdapter(adapter);
    }
}