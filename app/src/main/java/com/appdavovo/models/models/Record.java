package com.appdavovo.models.models;

import android.os.Message;

import com.appdavovo.models.utils.HashHelper;
import com.appdavovo.models.viewmodels.RecordViewModel;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Record extends Entity {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Date date;
    public double glucoseAmount;
    public EMeasurePeriod eMeasurePeriod;
    public boolean synced;
    public String hashcode;

    public Record(RecordViewModel viewModel) {
        glucoseAmount = viewModel.glucoseAmount;
        eMeasurePeriod = viewModel.eMeasurePeriod;
    }

    public Record() {
    }

    public void setHashcode() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dateOnStartOfDay = calendar.getTime();

        String checksumInput =
                dateFormat.format(dateOnStartOfDay) +
                Integer.toString(eMeasurePeriod.getEnumId()) +
                Double.toString(glucoseAmount);

        hashcode = HashHelper.checksum(checksumInput);
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return MessageFormat.format(
                "{0} | {1} | {2} | {3} | {4}",
                dateFormat.format(date),
                eMeasurePeriod.getName(),
                Integer.toString(((int)glucoseAmount)),
                Boolean.toString(synced),
                hashcode);
    }
}
