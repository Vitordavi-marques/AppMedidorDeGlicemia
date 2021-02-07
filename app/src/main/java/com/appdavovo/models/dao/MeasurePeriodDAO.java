package com.appdavovo.models.dao;

import android.content.Context;
import android.database.Cursor;

import com.appdavovo.models.models.EMeasurePeriod;
import com.appdavovo.models.models.MeasurePeriod;

public class MeasurePeriodDAO extends DataAccessObject {

    public static final String TABLE_NAME = "MeasurePeriod";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ENUM_ID = "enumId";
    public static final String COLUMN_NAME = "name";

    public MeasurePeriodDAO(Context context) {
        super(context);
    }

    public MeasurePeriod getByEnumId(int enumId) {
        String sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE enumId = ?";

        Cursor c = null;
        try {
            c = db.rawQuery(sql, new String[]{ Integer.toString(enumId) });
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.moveToFirst();

        MeasurePeriod measurePeriod = new MeasurePeriod();
        measurePeriod.id = c.getInt(c.getColumnIndex(COLUMN_ID));
        measurePeriod.enumId = c.getInt(c.getColumnIndex(COLUMN_ENUM_ID));
        measurePeriod.name = c.getString(c.getColumnIndex(COLUMN_NAME));
        measurePeriod.period = EMeasurePeriod.getByEnumId(enumId);

        c.close();
        db.close();
        return measurePeriod;
    }
}
