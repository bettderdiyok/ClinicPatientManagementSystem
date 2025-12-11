# Sample Data for Clinic Appointment Management System
This file contains a small example dataset that can be used to quickly test the system without manually entering all records.
## Doctors
```json
{
"id": 1,
"name": "Dr. Ayşe Yılmaz",
"branch": "CARDIOLOGY"
}
``` 
## Patients
```json
{
"id": 1,
"name": "Elif Demir",
"nationalId": "12345678901",
"age": 29
}
```
### Example Appointment – First Visit
```json
{
"id": 1,
"doctorId": 1,
"patientId": 1,
"time": "2025-01-15T10:00",
"type": "FIRST_VISIT",
"status": "BOOKED"
}
```

