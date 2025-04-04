package com.example.wanderingnavigator.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wanderingnavigator.entities.Excursion;

import java.util.List;

@Dao
public interface ExcursionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

    @Query("SELECT * FROM excursion_table WHERE vacationId = :vacationId ORDER BY date")
    LiveData<List<Excursion>> getExcursionsForVacation(int vacationId);

    @Query("SELECT * FROM excursion_table ORDER BY date")
    List<Excursion> getAllExcursions();

    @Query("SELECT * FROM excursion_table WHERE vacationId = :vacationId ORDER BY date")
    List<Excursion> getAssociatedExcursions(int vacationId);

    @Query("SELECT * FROM excursion_table WHERE vacationId = :excursionId LIMIT 1")
    Excursion getExcursionById(int excursionId);
}
