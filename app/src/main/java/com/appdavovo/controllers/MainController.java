package com.appdavovo.controllers;

import android.content.Context;

import com.appdavovo.models.business.MainService;
import com.appdavovo.models.models.EmailTarget;
import com.appdavovo.models.models.Record;
import com.appdavovo.models.viewmodels.RecordViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    private MainService mainService;

    public MainController() {
        mainService = new MainService();
    }

    public boolean createNewRecord(RecordViewModel viewModel, Context context) {
        try {
            return mainService.createNewRecord(viewModel, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Record> getAllRecords(Context context) {
        try {
            return mainService.getAllRecords(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Record>();
    }

    public List<EmailTarget> getAllEmailTargets(Context context) {
        try {
            return mainService.getAllEmailTargets(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<EmailTarget>();
    }
}
