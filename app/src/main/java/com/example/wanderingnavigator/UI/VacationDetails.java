package com.example.wanderingnavigator.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanderingnavigator.R;
import com.example.wanderingnavigator.database.Repository;
import com.example.wanderingnavigator.entities.Excursion;
import com.example.wanderingnavigator.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {

    int vacationId;
    int numExcursions;
    Vacation currentVacation;
    String vacationTitle;
    String vacationHotel;
    String start;
    String end;
    EditText editTitle;
    EditText editHotel;
    EditText editStart;
    EditText editEnd;
    Repository repository;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);

        repository = new Repository(getApplication());
        editTitle = findViewById(R.id.vacationName1);
        editHotel = findViewById(R.id.hotel);
        editStart = findViewById(R.id.start);
        editEnd = findViewById(R.id.end);
        vacationId = getIntent().getIntExtra("id", -1);
        vacationTitle = getIntent().getStringExtra("title");
        vacationHotel = getIntent().getStringExtra("accommodations");
        start = getIntent().getStringExtra("startDate");
        end = getIntent().getStringExtra("endDate");
        editTitle.setText(vacationTitle);
        editHotel.setText(vacationHotel);
        editStart.setText(start);
        editEnd.setText(end);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        DatePickerDialog.OnDateSetListener startDateListener = (view, year, month, dayOfMonth) -> {
            myCalendarStart.set(year, month, dayOfMonth);
            editStart.setText(sdf.format(myCalendarStart.getTime()));
        };

        editStart.setOnClickListener(v -> {
            try {
                if (editStart.getText() != null && !editStart.getText().toString().isEmpty()) {
                    myCalendarStart.setTime(sdf.parse(editStart.getText().toString()));
                }
                new DatePickerDialog(VacationDetails.this, startDateListener,
                        myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            } catch (ParseException e) {
                e.printStackTrace();
                new DatePickerDialog(VacationDetails.this, startDateListener,
                        myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        DatePickerDialog.OnDateSetListener endDateListener = (view, year, month, dayOfMonth) -> {
            myCalendarEnd.set(year, month, dayOfMonth);
            editEnd.setText(sdf.format(myCalendarEnd.getTime()));
        };

        editEnd.setOnClickListener(v -> {
            try {
                if (editEnd.getText() != null && !editEnd.getText().toString().isEmpty()) {
                    myCalendarEnd.setTime(sdf.parse(editEnd.getText().toString()));
                }

                new DatePickerDialog(VacationDetails.this, endDateListener,
                        myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            } catch (ParseException e) {
                e.printStackTrace();
                new DatePickerDialog(VacationDetails.this, endDateListener,
                        myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationId", vacationId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupExcursionRecyclerView();
    }


    private void setupExcursionRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e : repository.getAllExcursions()) {
            if (e.getVacationId() == vacationId) filteredExcursions.add(e);
        }
        excursionAdapter.setFilteredExcursions(filteredExcursions);
    }

    private boolean validateNonBlank(String title, String hotel, String startDate, String endDate) {
        return !(title.isBlank() || hotel.isBlank() || startDate.isBlank() || endDate.isBlank());
    }

    public boolean isValidDate(String date) {
        try {
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean isValidDateRange(String startDate, String endDate) {
        try {
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            return start.compareTo(end) <= 0;
        } catch (ParseException e) {
            return false;
        }
    }

    private void showEmptyError(String settingNotification) {
        Toast.makeText(this, "Please complete all fields before saving.", Toast.LENGTH_LONG).show();
    }

    private void showDateFormatError() {
        Toast.makeText(this, "Please use the correct date format of MM/dd/yy", Toast.LENGTH_LONG).show();
    }

    private void showDateRangeError() {
        Toast.makeText(this, "End date must be after start date", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.vacationsave) {
            String title = editTitle.getText().toString().trim();
            String hotel = editHotel.getText().toString().trim();
            String start = editStart.getText().toString().trim();
            String end = editEnd.getText().toString().trim();

            boolean validBlank = validateNonBlank(title, hotel, start, end);
            boolean validStartDate = isValidDate(start);
            boolean validEndDate = isValidDate(end);
            boolean validDateRange = isValidDateRange(start, end);

            if (!validBlank) {
                showEmptyError("setting notification");
                return true;
            }

            if (!validStartDate || !validEndDate) {
                showDateFormatError();
                return true;
            }

            if (!validDateRange) {
                showDateRangeError();
                return true;
            }

            Vacation vacation;
            if (vacationId == -1) {
                if (repository.getAllVacations().isEmpty()) {
                    vacationId = 1;
                } else {
                    vacationId = repository.getAllVacations().get(repository.getAllVacations().size() - 1).getVacationId() + 1;
                }
                vacation = new Vacation(vacationId, editTitle.getText().toString(), hotel, start, end);
                repository.insert(vacation);
                Toast.makeText(this, "Vacation saved", Toast.LENGTH_LONG).show();
            } else {
                vacation = new Vacation(vacationId, title, hotel, start, end);
                repository.update(vacation);
                Toast.makeText(this, "Vacation updated", Toast.LENGTH_LONG).show();
            }

            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.vacationdelete) {
            if (vacationId == -1) {
                Toast.makeText(this, "Cannot delete unsaved vacation", Toast.LENGTH_LONG).show();
                return true;
            }

            for (Vacation vacation : repository.getAllVacations()) {
                if (vacation.getVacationId() == vacationId) {
                    currentVacation = vacation;
                }
            }

            if (currentVacation != null) {
                List<Excursion> associatedExcursions = new ArrayList<>();
                for (Excursion e : repository.getAllExcursions()) {
                    if (e.getVacationId() == vacationId) {
                        associatedExcursions.add(e);
                    }
                }

                for (Excursion e : associatedExcursions) {
                    repository.delete(e);
                }

                repository.delete(currentVacation);
                Toast.makeText(this, currentVacation.getVacationTitle() + " was deleted", Toast.LENGTH_LONG).show();
                this.finish();
            }
            return true;
        }

        if (item.getItemId() == R.id.vacationshare) {
            Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TITLE, "Share your vacation details?");
            sentIntent.putExtra(Intent.EXTRA_TEXT, "My vacation details!" +
                    "\n\nVacation Title: " + editTitle.getText().toString() +
                    "\nHotel: " + editHotel.getText().toString() +
                    "\nStart Date: " + editStart.getText().toString() +
                    "\nEnd Date: " + editEnd.getText().toString());
            sentIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);
            return true;
        }

        if (item.getItemId() == R.id.vacationnotify) {
            boolean validBlank = validateNonBlank(editTitle.getText().toString(),
                    editHotel.getText().toString(),
                    editStart.getText().toString(),
                    editEnd.getText().toString());
            boolean validStartDate = isValidDate(editStart.getText().toString());
            boolean validEndDate = isValidDate(editEnd.getText().toString());

            if (validBlank && validStartDate && validEndDate) {
                String vacationTitle = editTitle.getText().toString();

                try {

                    Date startDate = sdf.parse(editStart.getText().toString());
                    Long triggerStart = startDate.getTime();


                    Intent intentStart = new Intent(VacationDetails.this, MyReceiver.class);
                    intentStart.putExtra("key", "Your vacation \"" + vacationTitle + "\" starts today!");


                    PendingIntent senderStart = PendingIntent.getBroadcast(VacationDetails.this,
                            MainActivity.numAlert++, intentStart, PendingIntent.FLAG_IMMUTABLE);

                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, triggerStart, senderStart);


                    Date endDate = sdf.parse(editEnd.getText().toString());
                    Long triggerEnd = endDate.getTime();


                    Intent intentEnd = new Intent(VacationDetails.this, MyReceiver.class);
                    intentEnd.putExtra("key", "Your vacation \"" + vacationTitle + "\" ends today!");


                    PendingIntent senderEnd = PendingIntent.getBroadcast(VacationDetails.this,
                            MainActivity.numAlert++, intentEnd, PendingIntent.FLAG_IMMUTABLE);

                    alarmManager.set(AlarmManager.RTC_WAKEUP, triggerEnd, senderEnd);

                    Toast.makeText(this, "Vacation start and end notifications have been set", Toast.LENGTH_LONG).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error setting notifications: Invalid date format", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (!validBlank) {
                    showEmptyError("setting notification");
                } else {
                    showFormatError();
                }
            }
        }


        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showFormatError() {
    }
}


