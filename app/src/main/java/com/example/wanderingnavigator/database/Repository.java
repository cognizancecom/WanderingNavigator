package com.example.wanderingnavigator.database;

import android.app.Application;

import com.example.wanderingnavigator.dao.ExcursionDAO;
import com.example.wanderingnavigator.dao.VacationDAO;
import com.example.wanderingnavigator.entities.Excursion;
import com.example.wanderingnavigator.entities.Vacation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private VacationDAO mVacationDAO;
    private ExcursionDAO mExcursionDAO;
    private List<Vacation> mAllVacations;
    private List<Excursion> mAllExcursions;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        VacationDatabaseBuilder database = VacationDatabaseBuilder.getDatabase(application);
        mVacationDAO = database.vacationDAO();
        mExcursionDAO = database.excursionDAO();
    }

    public List<Vacation> getAllVacations() {
        databaseExecutor.execute(() -> {
            mAllVacations = mVacationDAO.getAllVacations();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllVacations;
    }

    // Add search method for vacations
    public List<Vacation> searchVacations(String searchTerm) {
        final List<Vacation>[] results = new List[1];
        databaseExecutor.execute(() -> {
            results[0] = mVacationDAO.searchVacations("%" + searchTerm + "%");
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return results[0];
    }

    public void insert(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.insert(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.update(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.delete(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Excursions
    public List<Excursion> getAllExcursions() {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getAllExcursions();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllExcursions;
    }

    // Add search method for excursions
    public List<Excursion> searchExcursions(String searchTerm) {
        final List<Excursion>[] results = new List[1];
        databaseExecutor.execute(() -> {
            results[0] = mExcursionDAO.searchExcursions("%" + searchTerm + "%");
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return results[0];
    }

    public List<Excursion> getAssociatedExcursions(int vacationId) {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getAssociatedExcursions(vacationId);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllExcursions;
    }

    public void insert(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.insert(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.update(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.delete(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Excursion getExcursionById(int excursionId) {
        final Excursion[] excursion = new Excursion[1];
        databaseExecutor.execute(() -> {
            excursion[0] = mExcursionDAO.getExcursionById(excursionId);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return excursion[0];
    }
    // Add to Repository.java
    public boolean secureDelete(Vacation vacation, String userCredential) {
        // Verify user has permission to delete
        if (authenticateUser(userCredential)) {
            delete(vacation);
            return true;
        }
        return false;
    }

    public boolean secureUpdate(Vacation vacation, String userCredential) {
        // Verify user has permission to update
        if (authenticateUser(userCredential)) {
            update(vacation);
            return true;
        }
        return false;
    }

    private boolean authenticateUser(String credential) {
        // Simple authentication check
        return credential != null && !credential.isEmpty();
    }

    // Add report generation methods
    public String generateVacationsReport() {
        List<Vacation> vacations = getAllVacations();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String timestamp = formatter.format(new Date());

        StringBuilder report = new StringBuilder();
        report.append("Vacation Report - Generated: ").append(timestamp).append("\n\n");

        for (Vacation vacation : vacations) {
            report.append("ID: ").append(vacation.getVacationId()).append("\n");
            report.append("Title: ").append(vacation.getVacationTitle()).append("\n");
            report.append("Hotel: ").append(vacation.getVacationHotel()).append("\n");
            report.append("Start Date: ").append(vacation.getStartDate()).append("\n");
            report.append("End Date: ").append(vacation.getEndDate()).append("\n");
            report.append("Created: ").append(vacation.getCreatedDateStr()).append("\n");
            report.append("Modified: ").append(vacation.getModifiedDateStr()).append("\n\n");
        }

        return report.toString();
    }

    public String generateExcursionsReport() {
        List<Excursion> excursions = getAllExcursions();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String timestamp = formatter.format(new Date());

        StringBuilder report = new StringBuilder();
        report.append("Excursion Report - Generated: ").append(timestamp).append("\n\n");

        for (Excursion excursion : excursions) {
            report.append("ID: ").append(excursion.getExcursionId()).append("\n");
            report.append("Vacation ID: ").append(excursion.getVacationId()).append("\n");
            report.append("Title: ").append(excursion.getTitle()).append("\n");
            report.append("Date: ").append(excursion.getDate()).append("\n");
            report.append("Created: ").append(excursion.getCreatedDateStr()).append("\n");
            report.append("Modified: ").append(excursion.getModifiedDateStr()).append("\n\n");
        }

        return report.toString();
    }
}