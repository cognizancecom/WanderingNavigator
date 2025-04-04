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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wanderingnavigator.R;
import com.example.wanderingnavigator.database.Repository;
import com.example.wanderingnavigator.entities.Excursion;
import com.example.wanderingnavigator.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {

    int excursionId;
    int vacationId;
    int newVacationId;
    String title;
    String date;
    String excursionTitle;
    Excursion currentExcursion;

    EditText editName;
    EditText editNote;
    TextView editDate;
    int newVacationID;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursions_details);
        repository = new Repository(getApplication());
        editName = findViewById(R.id.excursionName);
        excursionId = getIntent().getIntExtra("id", -1);
        excursionTitle = getIntent().getStringExtra("title");
        date = getIntent().getStringExtra("startDate");
        vacationId = getIntent().getIntExtra("vacationId",-1);
        editDate = findViewById(R.id.excursionDate);
        editNote = findViewById(R.id.note);
        newVacationId = getIntent().getIntExtra("newVacationId",-1);
        editName.setText(excursionTitle);
        editDate.setText(date);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editDate.getText().toString();
                if (info.equals("")) info = "04/01/25";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(ExcursionDetails.this, startDate, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();

            }
        };
        Spinner spinner = findViewById(R.id.spinner);
        ArrayList<Vacation> vacationArrayList = new ArrayList<>();
        vacationArrayList.addAll(repository.getAllVacations());


        ArrayAdapter<Vacation> vacationAdapter = new ArrayAdapter<Vacation>(this,
                android.R.layout.simple_spinner_item, vacationArrayList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                Vacation vacation = getItem(position);
                textView.setText(vacation.getVacationTitle());
                return textView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                Vacation vacation = getItem(position);
                textView.setText(vacation.getVacationTitle());
                return textView;
            }
        };

        spinner.setAdapter(vacationAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Vacation selectedVacation = (Vacation) parent.getItemAtPosition(position);
                newVacationId = selectedVacation.getVacationId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    private boolean validateNonBlank(String title, String date) {
        if (title.isBlank() || date.isBlank()) {
            return false;
        } else {
            return true;
        }
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

    public boolean isInDateRange(String date, int vacationId) throws ParseException {
        ArrayList<Vacation> vacationArrayList = new ArrayList<>(repository.getAllVacations());
        Vacation vacation = null;

        for (Vacation v : vacationArrayList) {
            if (v.getVacationId() == vacationId) {
                vacation = v;
                break;
            }
        }

        if (vacation == null) {
            Toast.makeText(this, "Vacation not found for ID: " + vacationId, Toast.LENGTH_SHORT).show();
            return false;
        }


        Date startVacation = sdf.parse(vacation.getStartDate());
        Date endVacation = sdf.parse(vacation.getEndDate());
        Date dateDate = sdf.parse(date);

        if (dateDate.compareTo(startVacation) >= 0 && dateDate.compareTo(endVacation) <= 0) {
            return true;
        } else if (dateDate.compareTo(startVacation) < 0) {
            Toast.makeText(this, "Excursion is before vacation starts.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(this, "Excursion is after vacation ends.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void showRangeError() {
        Toast.makeText(this, "Please choose a new date for your excursion", Toast.LENGTH_SHORT).show();
    }

    private void showEmptyError(String action) {
        Toast.makeText(this, "Please complete all fields before " + action, Toast.LENGTH_LONG).show();
    }

    private void showFormatError() {
        Toast.makeText(this, "Please use the correct date format of MM/dd/yyyy", Toast.LENGTH_LONG).show();
    }

    private void showSuccess() {
        Toast.makeText(this, "All fields entered correctly", Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.excursionsave) {
            boolean validBlank = validateNonBlank(editName.getText().toString(), editDate.getText().toString());
            boolean validDate = isValidDate(editDate.getText().toString());
            boolean validRange = false;
            try {
                validRange = isInDateRange(editDate.getText().toString(), newVacationId);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if (validBlank && validDate) {
                if (validRange) {
                    showSuccess();
                    Excursion excursion;

                    int finalVacationId = (newVacationId != -1) ? newVacationId : vacationId;

                    if (excursionId == -1) {
                        if (repository.getAllExcursions().isEmpty()) {
                            excursionId = 1;
                        } else {
                            excursionId = repository.getAllExcursions()
                                    .get(repository.getAllExcursions().size() - 1)
                                    .getExcursionId() + 1;
                        }
                        excursion = new Excursion(excursionId, finalVacationId, editName.getText().toString(), editDate.getText().toString());
                        repository.insert(excursion);

                        Toast.makeText(ExcursionDetails.this, "Excursion saved", Toast.LENGTH_LONG).show();
                        this.finish();
                    } else {
                        excursion = new Excursion(excursionId, finalVacationId, editName.getText().toString(), editDate.getText().toString());
                        repository.update(excursion);

                        Toast.makeText(this, "Excursion updated", Toast.LENGTH_LONG).show();
                        this.finish();
                    }
                } else if (!validRange) {
                    showRangeError();
                }
            } else if (!validBlank) {
                showEmptyError("saving.");
            } else if (!validDate) {
                showFormatError();
            }
            return true;
        }



        if (item.getItemId() == R.id.excursiondelete) {
            if (excursionId == -1) {
                Toast.makeText(this, "Can't delete an empty excursion. Please choose a saved excursion", Toast.LENGTH_LONG).show();
            } else {
                for (Excursion excursion : repository.getAllExcursions()) {
                    if (excursion.getExcursionId() == excursionId) {
                        currentExcursion = excursion;
                    }
                }

                try {
                    repository.delete(currentExcursion);
                    Toast.makeText(this, currentExcursion.getTitle() + " was deleted", Toast.LENGTH_LONG).show();
                    this.finish();
                } catch (Exception e) {
                    Toast.makeText(this, "Couldn't delete excursion", Toast.LENGTH_LONG).show();
                }
            }
            return true;
        }

        if (item.getItemId() == R.id.excursionshare) {
            Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TITLE, "Would you like to share your excursion details?");
            sentIntent.putExtra(Intent.EXTRA_TEXT, "Here's my excursion details!" +
                    "\n\nExcursion Title: " + editName.getText().toString() +
                    "\nDate: " + editDate.getText().toString());
            sentIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);
            return true;
        }

        if (item.getItemId() == R.id.excursionnotify) {
            boolean validBlank = validateNonBlank(editName.getText().toString(), editDate.getText().toString());
            boolean validDate = isValidDate(editDate.getText().toString());

            if (validBlank && validDate) {
                String excursionTitle = editName.getText().toString();
                String dateFromScreen = editDate.getText().toString();
                Date myDate = null;

                try {
                    myDate = sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = myDate.getTime();
                Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
                intent.putExtra("key", "Your excursion \"" + excursionTitle + "\" is today!");
                PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

                Toast.makeText(this, "Excursion notification has been set", Toast.LENGTH_LONG).show();
                this.finish();
            } else if (!validBlank) {
                showEmptyError("setting notification.");
            } else if (!validDate) {
                showFormatError();
            }
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
