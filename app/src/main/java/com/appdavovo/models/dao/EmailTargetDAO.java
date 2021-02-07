package com.appdavovo.models.dao;

import android.content.Context;
import android.database.Cursor;

import com.appdavovo.models.models.EMeasurePeriod;
import com.appdavovo.models.models.EmailTarget;
import com.appdavovo.models.models.Record;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class EmailTargetDAO extends DataAccessObject {

    public static final String TABLE_NAME = "EmailTargets";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_OWNER_NAME = "ownerName";
    public static final String COLUMN_EMAIL = "email";

    public EmailTargetDAO(Context context) { super(context); }

    public List<EmailTarget> selectAll() {
        String sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_ID + " > ?";

        Cursor c = null;
        try {
            c = db.rawQuery(sql, new String[] { Integer.toString(0) });
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<EmailTarget> targets = new ArrayList<>();
        c.moveToFirst();
        while (!c.isAfterLast()) {
            EmailTarget et = new EmailTarget();
            et.id = c.getInt(c.getColumnIndex(COLUMN_ID));
            et.ownerName = c.getString(c.getColumnIndex(COLUMN_OWNER_NAME));
            et.email = c.getString(c.getColumnIndex(COLUMN_EMAIL));
            targets.add(et);
            c.moveToNext();
        }

        return targets;
    }
}
