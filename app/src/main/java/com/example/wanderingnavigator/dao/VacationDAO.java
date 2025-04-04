package com.example.wanderingnavigator.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wanderingnavigator.entities.Excursion;
import com.example.wanderingnavigator.entities.Vacation;

import java.util.List;

@Dao
public interface VacationDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Vacation vacation);

    @Update
    void update(Vacation vacation);

    @Delete
    void delete(Vacation vacation);

    @Query("SELECT * FROM vacations ORDER BY startDate")
    List<Vacation> getAllVacations();

    @Query("SELECT * FROM vacations WHERE vacationTitle LIKE :searchTerm")
    List<Vacation> searchVacations(String searchTerm);

    @Query("SELECT * FROM excursion_table WHERE title LIKE :searchTerm")
    List<Excursion> searchExcursions(String searchTerm);

}
