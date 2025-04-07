# Wandering Navigator

## Title and Purpose of the Application
The Wandering Navigator is a user-friendly Android mobile application designed to help users efficiently manage and organize vacations and associated excursions. The app allows users to:

- Add, update, delete, and view vacations and excursions
- Set alerts for important dates
- Share vacation details seamlessly
- Generate reports for faster decision-making

---

## Directions for Operating the Application

### 1. Home Screen
Launch the app to view the home screen, where you can navigate to vacations.

### 2. Adding a Vacation
1. Click the "+" button on the vacations screen.
2. Fill out the details:
   - **Title** (e.g., "Spring Break 2025")
   - **Hotel or place of stay**
   - **Start Date** (MM/DD/YY format)
   - **End Date** (MM/DD/YY format)
3. Press **"Save Vacation"** from the top-right dropdown to store the vacation details.

#### Validation:
- Ensure that dates are in the correct format.
- The end date must be after the start date.

---

### 3. Updating or Deleting a Vacation
1. Tap on a vacation from the list to view detailed vacation information.
2. To update:
   - Edit the fields and click **"Save Vacation"** from the top-right dropdown to save changes.
3. To delete:
   - Tap **"Delete Vacation"** from the top-right dropdown.

---

### 4. Viewing Vacation Details
Click on a vacation to display a detailed view, including its excursions.

---

### 5. Adding an Excursion
1. Navigate to a vacation and tap "+" to add an excursion.
2. Fill out the details:
   - **Title** (e.g., "Hiking")
   - **Date** (MM/DD/YY format)
3. Press **"Save Excursion"** from the top-right dropdown to add the excursion.

#### Validation:
- Ensure that the excursion date is within the associated vacation's start and end dates.

---

### 6. Updating or Deleting an Excursion
1. Tap on an excursion to view its details.
2. To update:
   - Edit fields and press **"Update"** to save changes.
3. To delete:
   - Tap **"Delete Excursion"** from the top-right dropdown.

---

### 7. Setting Alerts
#### For Vacations:
- Set alerts for the start and end dates.
- Notifications will display the vacation title and indicate if it is starting or ending.

#### For Excursions:
- Set alerts for the excursion date.
- Notifications will display the excursion title on the day of the event.

---

### 8. Sharing Vacation Details
1. Tap **"Share Vacation"** from the top-right dropdown button on the detailed vacation view.
2. Choose to share via email, clipboard, or SMS. The message will be pre-filled with vacation details.

---

### 9. List Views
- **Vacation List:** Displays all vacations with their titles.
- **Excursion List:** Displays all excursions associated with a selected vacation.

---

### 10. Generating Reports
1. Access the new reporting feature from the main menu.
2. Select the type of report you want to generate (e.g., vacation summary, excursion details).
3. Choose the date range for the report.
4. The app will quickly generate the report, typically within 15–30 seconds for large datasets.

---

## Technical Specifications

### Database Architecture
- Room Persistence Library as an abstraction layer over SQLite.
- Entity relationships between Vacations and Excursions.
- Data validation at the database level.

### User Interface
- Home screen with navigation options.
- List views for vacations and excursions.
- Detailed views for editing and viewing information.
- Material Design components for a modern look and feel.

### Deployment Details
- Minimum Android Version: Android 8.0 (API Level 26).
- Target SDK Version: Latest stable Android version.

---

## Features Implementation

### Vacation Management
- CRUD operations (Create, Read, Update, Delete).
- Date validation.
- Alert settings for start and end dates.
- Sharing functionality via multiple channels.

### Excursion Management
- CRUD operations within the context of a vacation.
- Date validation to ensure excursions occur during vacations.
- Alert settings for excursion dates.

### Reporting
- Fast report generation (15–30 seconds for large datasets).
- Various report types available (vacation summary, excursion details, etc.).
- Date range selection for customized reports.

---

## Development Notes
- The app follows MVVM architecture pattern.
- Comprehensive validation throughout the application.
- Notifications system for alerts on specified dates.
- Enhanced in-app reporting with architectural improvements for faster report generation.

---

## Version Control
This project is version controlled using Git and hosted on GitLab