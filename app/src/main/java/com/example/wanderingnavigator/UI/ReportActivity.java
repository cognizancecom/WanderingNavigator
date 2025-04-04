package com.example.wanderingnavigator.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;



import androidx.appcompat.app.AppCompatActivity;

import com.example.wanderingnavigator.R;
import com.example.wanderingnavigator.database.Repository;

import java.util.Locale;

public class ReportActivity extends AppCompatActivity {

    private RadioGroup reportTypeRadioGroup;
    private Button generateReportButton;
    private TextView reportTitleTextView;
    private TextView reportTimestampTextView;
    private TextView reportContentTextView;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository = new Repository(getApplication());

        reportTypeRadioGroup = findViewById(R.id.reportTypeRadioGroup);
        generateReportButton = findViewById(R.id.generateReportButton);
        reportTitleTextView = findViewById(R.id.reportTitleTextView);
        reportTimestampTextView = findViewById(R.id.reportTimestampTextView);
        reportContentTextView = findViewById(R.id.reportContentTextView);

        generateReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateReport();
            }
        });
    }

    private void generateReport() {
        int selectedRadioButtonId = reportTypeRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

        if (selectedRadioButton == null) {
            return;
        }

        String reportType = selectedRadioButton.getText().toString();

        // Get current timestamp for report
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String timestamp = formatter.format(new Date());

        reportTimestampTextView.setText("Generated: " + timestamp);

        if (reportType.equals("Vacations")) {
            reportTitleTextView.setText("Vacation Report");
            String reportContent = repository.generateVacationsReport();
            reportContentTextView.setText(reportContent);
        } else {
            reportTitleTextView.setText("Excursion Report");
            String reportContent = repository.generateExcursionsReport();
            reportContentTextView.setText(reportContent);
        }
    }
}