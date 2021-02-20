package com.appdavovo.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.appdavovo.models.models.EMeasurePeriod;
import com.appdavovo.models.models.MeasurePeriod;
import com.appdavovo.models.models.Record;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordDAO extends DataAccessObject {

    public static final String TABLE_NAME = "Record";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_GLUCOSE_AMOUNT = "glucoseAmount";
    public static final String COLUMN_PERIOD_ID = "periodId";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public RecordDAO(Context context) {
        super(context);
    }

    public boolean insert(Record record, MeasurePeriod measurePeriod) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, dateFormat.format(record.date));
        values.put(COLUMN_GLUCOSE_AMOUNT, record.glucoseAmount);
        values.put(COLUMN_PERIOD_ID, measurePeriod.id);

        long newId = -1;
        try {
            newId = db.insert(TABLE_NAME, null, values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return false;
    }

    public boolean update(Record record, MeasurePeriod measurePeriod) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, dateFormat.format(record.date));
        values.put(COLUMN_GLUCOSE_AMOUNT, record.glucoseAmount);
        values.put(COLUMN_PERIOD_ID, measurePeriod.id);

        int rowsAffected = 0;
        try {
            rowsAffected = db.update(TABLE_NAME, values,
                    COLUMN_ID + " = ?",
                    new String[] { Integer.toString(record.id) });
            return (rowsAffected > 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return false;
    }

    public List<Record> selectAll() {
        String sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_ID + " > ?";

        Cursor c = null;
        try {
            c = db.rawQuery(sql, new String[] { Integer.toString(0) });
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Record> records = new ArrayList<>();
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Record r = new Record();
            r.id = c.getInt(c.getColumnIndex(COLUMN_ID));
            r.glucoseAmount = c.getDouble(c.getColumnIndex(COLUMN_GLUCOSE_AMOUNT));
            r.eMeasurePeriod = EMeasurePeriod.getByEnumId(c.getInt(c.getColumnIndex(COLUMN_PERIOD_ID)));
            try {
                r.date = dateFormat.parse(c.getString(c.getColumnIndex(COLUMN_DATE)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            records.add(r);
            c.moveToNext();
        }

        db.close();

        return records;
    }

    public Record selectByDayAndPeriod(Date date, MeasurePeriod measurePeriod) {
        String sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE " +
                COLUMN_DATE + " > ? AND " +
                COLUMN_PERIOD_ID + " = ?";

        Cursor c = null;
        try {
            c = db.rawQuery(sql, new String[] {
                    dateFormat.format(date),
                    Integer.toString(measurePeriod.id) });
        } catch (Exception e) {
            e.printStackTrace();
        }

        Record record = null;
        if (c.moveToFirst()) {
            record = new Record();
            record.id = c.getInt(c.getColumnIndex(COLUMN_ID));
            record.glucoseAmount = c.getDouble(c.getColumnIndex(COLUMN_GLUCOSE_AMOUNT));
            record.eMeasurePeriod = EMeasurePeriod.getByEnumId(c.getInt(c.getColumnIndex(COLUMN_PERIOD_ID)));
            try {
                record.date = dateFormat.parse(c.getString(c.getColumnIndex(COLUMN_DATE)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        c.close();
        db.close();

        return record;
    }
}
