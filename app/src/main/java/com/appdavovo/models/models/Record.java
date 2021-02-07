package com.appdavovo.models.models;

import android.os.Message;

import com.appdavovo.models.viewmodels.RecordViewModel;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Record extends Entity {

    public Date date;
    public double glucoseAmount;
    public EMeasurePeriod eMeasurePeriod;

    public Record(RecordViewModel viewModel) {
        glucoseAmount = viewModel.glucoseAmount;
        eMeasurePeriod = viewModel.eMeasurePeriod;
    }

    public Record() {
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return MessageFormat.format(
                "{0} | {1} | {2}",
                dateFormat.format(date),
                eMeasurePeriod.getName(),
                Integer.toString(((int)glucoseAmount)));
    }
}
