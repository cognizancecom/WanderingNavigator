package com.example.wanderingnavigator.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.wanderingnavigator.dao.ExcursionDAO;
import com.example.wanderingnavigator.dao.VacationDAO;
import com.example.wanderingnavigator.entities.Excursion;
import com.example.wanderingnavigator.entities.Vacation;


@Database(entities = {Vacation.class, Excursion.class}, version = 12, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class VacationDatabaseBuilder extends RoomDatabase {
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();

    private static volatile VacationDatabaseBuilder INSTANCE;

    static VacationDatabaseBuilder getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (VacationDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    VacationDatabaseBuilder.class, "vacation_database.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
