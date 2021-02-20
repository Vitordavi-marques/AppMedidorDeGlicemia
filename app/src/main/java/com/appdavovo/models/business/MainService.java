package com.appdavovo.models.business;

import android.content.Context;

import com.appdavovo.models.dao.EmailTargetDAO;
import com.appdavovo.models.models.EmailTarget;
import com.appdavovo.models.models.MeasurePeriod;
import com.appdavovo.models.models.Record;
import com.appdavovo.models.dao.MeasurePeriodDAO;
import com.appdavovo.models.dao.RecordDAO;
import com.appdavovo.models.viewmodels.RecordViewModel;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainService {

    private Date getActualDateWithoutTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public boolean saveRecord(RecordViewModel viewModel, Context context) {

        // Get MeasurePeriod according to view model
        MeasurePeriodDAO measurePeriodDAO = new MeasurePeriodDAO(context);
        MeasurePeriod measurePeriod = measurePeriodDAO.getByEnumId(viewModel.eMeasurePeriod.getEnumId());

        // Get actual date and create RecordDAO
        Date actualDate = Calendar.getInstance().getTime();
        RecordDAO recordDAO = new RecordDAO(context);

        // Get supposed existing record
        Record recordWithExistingDate = recordDAO.selectByDayAndPeriod(
                getActualDateWithoutTime(), measurePeriod);

        // Another record exists with same period
        recordDAO = new RecordDAO(context);
        boolean result = false;
        if (recordWithExistingDate != null) {
            recordWithExistingDate.date = actualDate;
            recordWithExistingDate.glucoseAmount = viewModel.glucoseAmount;
            recordWithExistingDate.synced = false;
            recordWithExistingDate.setHashcode();
            result = recordDAO.update(recordWithExistingDate, measurePeriod);

        // No record exists - create a new one
        } else {
            Record newRecord = new Record(viewModel);
            newRecord.date = actualDate;
            newRecord.synced = false;
            recordWithExistingDate.setHashcode();
            result = recordDAO.insert(newRecord, measurePeriod);
        }

        return result;
    }

    public List<Record> getAllRecords(Context context) {
        RecordDAO recordDAO = new RecordDAO(context);
        return recordDAO.selectAll();
    }

    public List<EmailTarget> getAllEmailTargets(Context context) {
        EmailTargetDAO emailTargetDAO = new EmailTargetDAO(context);
        return emailTargetDAO.selectAll();
    }
}
