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
import java.util.List;

public class MainService {

    public boolean createNewRecord(RecordViewModel viewModel, Context context) {
        MeasurePeriodDAO measurePeriodDAO = new MeasurePeriodDAO(context);
        MeasurePeriod measurePeriod = measurePeriodDAO.getByEnumId(viewModel.eMeasurePeriod.getEnumId());

        Record record = new Record(viewModel);
        record.date = Calendar.getInstance().getTime();

        RecordDAO recordDAO = new RecordDAO(context);

        boolean result = recordDAO.insert(record, measurePeriod);
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
