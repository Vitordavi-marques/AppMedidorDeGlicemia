package com.appdavovo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.appdavovo.R;
import com.appdavovo.controllers.MainController;
import com.appdavovo.models.models.EMeasurePeriod;
import com.appdavovo.models.viewmodels.RecordViewModel;

import java.text.MessageFormat;

public class RecordAmountView extends AppCompatActivity {

    private TextView periodTextView;
    private EditText glucoseAmountEditField;
    private EMeasurePeriod eMeasurePeriod;
    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_amount_view);
        controller = new MainController();

        periodTextView = (TextView)(findViewById(R.id.periodTextView));
        glucoseAmountEditField = (EditText)(findViewById(R.id.glucoseAmountField));
        showKeyboard(glucoseAmountEditField, this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int enumId = extras.getInt("enumId");
            setEMeasurePeriod(enumId);
        }
    }

    // Buttons
    public void onSaveRecordButton(View view) {
        if (!validateInputs())
            return;
        hideKeyboard(this);

        RecordViewModel recordViewModel = new RecordViewModel();
        recordViewModel.glucoseAmount = Double.parseDouble(glucoseAmountEditField.getText().toString());
        recordViewModel.eMeasurePeriod = eMeasurePeriod;

        boolean result = controller.saveRecord(recordViewModel, this);

        if (result) {
            AlertDialog dialog = AlertDialogBuilder.buildCustomOk(
                    getResources().getString(R.string.success),
                    getResources().getString(R.string.record_created_success),
                    getResources().getString(R.string.ok_button),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(RecordAmountView.this, RecordPeriodView.class));
                        }
                    },
                    this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } else {
            AlertDialogBuilder.buildOkDialog(
                    getResources().getString(R.string.there_is_an_error),
                    getResources().getString(R.string.record_created_fail),
                    this).show();
        }
    }

    public void onCancelRecordButton(View view) {
        hideKeyboard(this);
        startActivity(new Intent(RecordAmountView.this, RecordPeriodView.class));
    }

    private void setEMeasurePeriod(int enumId) {
        eMeasurePeriod = EMeasurePeriod.getByEnumId(enumId);

        switch (eMeasurePeriod) {
            case FASTING:
                periodTextView.setText(R.string.period_view_fasting);
                periodTextView.setTextColor(getResources().getColor(R.color.morningColor));
                break;
            case LUNCH_TIME:
                periodTextView.setText(R.string.period_view_lunch_time);
                periodTextView.setTextColor(getResources().getColor(R.color.afternoonColor));
                break;
            case DINNER_TIME:
                periodTextView.setText(R.string.period_view_dinner_time);
                periodTextView.setTextColor(getResources().getColor(R.color.nightColor));
                break;
        }
    }

    private boolean validateInputs() {
        String glucoseAmount = glucoseAmountEditField.getText().toString();
        try {
            double value = Double.parseDouble(glucoseAmount);
        } catch (NumberFormatException e) {
            String pattern = getResources().getString(R.string.dialog_glucose_amount_invalid_format);
            String message = MessageFormat.format(pattern, glucoseAmount);

            String title = getResources().getString(R.string.there_is_an_error);

            AlertDialog dialog = AlertDialogBuilder.buildOkDialog(title, message, this);
            dialog.show();

            return false;
        }
        return true;
    }

    //Helper methods
    private void showKeyboard(EditText editText, Context context) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}