package com.appdavovo.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.appdavovo.R;
import com.appdavovo.models.models.EMeasurePeriod;

public class RecordPeriodView extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_period_view);

        toolbar = (Toolbar)(findViewById(R.id.recordPeriodToolbar));
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_all_records) {
            startActivity(new Intent(RecordPeriodView.this, AllRecordsView.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToGlucoseAmountActivity(EMeasurePeriod eMeasurePeriod) {
        Intent intent = new Intent(RecordPeriodView.this, RecordAmountView.class);
        intent.putExtra("enumId", eMeasurePeriod.getEnumId());
        startActivity(intent);
    }

    public void onMorningButtonClick(View v) {
        goToGlucoseAmountActivity(EMeasurePeriod.FASTING);
    }

    public void onAfternoonButtonClick(View v) {
        goToGlucoseAmountActivity(EMeasurePeriod.LUNCH_TIME);
    }

    public void onNightButtonClick(View v) {
        goToGlucoseAmountActivity(EMeasurePeriod.DINNER_TIME);
    }
}