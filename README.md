# Clinic Appointment Management System (Console App)
This project is a **console-based clinic management system** written in Java.  
It simulates a small clinic’s appointment workflow, including doctors, patients, appointment scheduling, conflict rules, and emergency handling.  
The primary goal is to practice **OOP, layered architecture, validation, and JSON-based persistence**.

## Features

- Add, update, delete and list doctors & patients
- Create normal or emergency appointments
- Appointment types:
    - FIRST_VISIT
    - CONTROL
    - EMERGENCY
- Validation rules:
    - Working hours (09:00–17:00)
    - 15-minute time slots
    - No weekend appointments
    - Doctor day-off check
    - Conflict checks (doctor/patient collisions)
- Data stored in JSON files (Gson + Java NIO)

## Technologies Used
- Java 17
- Object-Oriented Programming (OOP)
- Layered Architecture (CLI → Service → Repository → Domain)
- Gson (JSON serialization/deserialization)
- Java NIO (Files, Path, BufferedReader/Writer)
- Custom Exceptions
- Collections API (List, Map)