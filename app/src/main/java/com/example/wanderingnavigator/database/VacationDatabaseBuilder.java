package com.example.wanderingnavigator.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import net.sqlcipher.database.SupportFactory;
import net.sqlcipher.database.SQLiteDatabase;


import com.example.wanderingnavigator.dao.ExcursionDAO;
import com.example.wanderingnavigator.dao.VacationDAO;
import com.example.wanderingnavigator.entities.Excursion;
import com.example.wanderingnavigator.entities.Vacation;

import java.io.File;


@Database(entities = {Vacation.class, Excursion.class}, version = 13, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class VacationDatabaseBuilder extends RoomDatabase {
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();

    private static volatile VacationDatabaseBuilder INSTANCE;

    static VacationDatabaseBuilder getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (VacationDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    // For existing databases, you need to handle migration
                    File dbFile = context.getDatabasePath("vacation_database.db");
                    boolean dbExists = dbFile.exists();

                    if (dbExists) {
                        // If database exists, we need to use it without encryption first
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                        VacationDatabaseBuilder.class, "vacation_database.db")
                                .fallbackToDestructiveMigration()
                                .build();
                    } else {
                        // For new installations, use encryption
                        char[] passphrase = "secure_database_key".toCharArray();
                        byte[] passphraseBytes = SQLiteDatabase.getBytes(passphrase);
                        SupportFactory factory = new SupportFactory(passphraseBytes);

                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                        VacationDatabaseBuilder.class, "vacation_database.db")
                                .fallbackToDestructiveMigration()
                                .openHelperFactory(factory)
                                .build();
                    }
                }
            }
        }
        return INSTANCE;
    }
}
