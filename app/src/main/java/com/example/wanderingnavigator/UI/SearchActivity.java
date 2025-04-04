package com.example.wanderingnavigator.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanderingnavigator.R;
import com.example.wanderingnavigator.database.Repository;
import com.example.wanderingnavigator.entities.Excursion;
import com.example.wanderingnavigator.entities.Vacation;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText searchTermEditText;
    private RadioGroup searchTypeRadioGroup;
    private Button searchButton;
    private RecyclerView resultsRecyclerView;
    private TextView noResultsTextView;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository = new Repository(getApplication());

        searchTermEditText = findViewById(R.id.searchTermEditText);
        searchTypeRadioGroup = findViewById(R.id.searchTypeRadioGroup);
        searchButton = findViewById(R.id.searchButton);
        resultsRecyclerView = findViewById(R.id.resultsRecyclerView);
        noResultsTextView = findViewById(R.id.noResultsTextView);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });
    }

    private void performSearch() {
        String searchTerm = searchTermEditText.getText().toString().trim();

        if (searchTerm.isEmpty()) {
            Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedRadioButtonId = searchTypeRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

        if (selectedRadioButton == null) {
            Toast.makeText(this, "Please select a search type", Toast.LENGTH_SHORT).show();
            return;
        }

        String searchType = selectedRadioButton.getText().toString();

        if (searchType.equals("Vacations")) {
            List<Vacation> results = repository.searchVacations(searchTerm);
            displayVacationResults(results);
        } else {
            List<Excursion> results = repository.searchExcursions(searchTerm);
            displayExcursionResults(results);
        }
    }

    private void displayVacationResults(List<Vacation> results) {
        if (results == null || results.isEmpty()) {
            resultsRecyclerView.setVisibility(View.GONE);
            noResultsTextView.setVisibility(View.VISIBLE);
        } else {
            resultsRecyclerView.setVisibility(View.VISIBLE);
            noResultsTextView.setVisibility(View.GONE);

            VacationAdapter adapter = new VacationAdapter(this);
            adapter.setVacations(results);
            resultsRecyclerView.setAdapter(adapter);
            resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private void displayExcursionResults(List<Excursion> results) {
        if (results == null || results.isEmpty()) {
            resultsRecyclerView.setVisibility(View.GONE);
            noResultsTextView.setVisibility(View.VISIBLE);
        } else {
            resultsRecyclerView.setVisibility(View.VISIBLE);
            noResultsTextView.setVisibility(View.GONE);

            ExcursionAdapter adapter = new ExcursionAdapter(this);
            adapter.setFilteredExcursions(results);
            resultsRecyclerView.setAdapter(adapter);
            resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}