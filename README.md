## 🏫 School Management System (Java Swing Mini Project)

The School Management System is a beginner-friendly Java Swing GUI-based mini project developed to understand the fundamentals of:

* Java Swing (GUI development)
* Object-Oriented Programming (OOP)
* Basic service-layer architecture
* Event handling and user interaction

This project simulates a simple school/college management system where users can manage students, teachers, and fee payments through a graphical interface.

---

## 🎯 Objectives

* Learn how to build desktop applications using Java Swing
* Understand package-based project structure (GUI, Model, Service)
* Practice OOP concepts like classes, objects, inheritance, polymorphism and encapsulation
* Create a real-world inspired academic management system
* Role-based access (Admin / Teacher / Student)
* To understand the Database format work integration using Postgres + JDBC.

---

## 🧩 Features

**🔐 Login & Registration**

* Login window for users
* Registration option for new students or teachers

**🧑‍🎓 Student Management**

* Add new students
* View the list of students
* Display student details (LC Number, RegNo, Name, Email, Phone, Username, Semester, Course)

**👩‍🏫 Teacher Management**

* Add teacher details
* View teacher list
* Display teacher details ( Employee No, Name, Email, Phone, Username, Password, Subject Assigned, Course)

**📊 Dashboard**

* Central dashboard to navigate between modules
* Buttons to access Student, Teacher, and Admin. 

---

## 🗂️ Project Structure

```
School_Management_System/
│
├── src/
│   └── School_Management_System/
│       ├── Main/
|       |   ├── MainApp.java
│       │   └── TestDB.java
│       │
│       ├── GUI/
│       │   └── SchoolGUI.java
│       │
│       ├── Window/
│       │   ├── LoginWindow.java
|       |   ├── RegisterWindow.java
│       │   ├── DashboardWindow.java
|       |   ├── AdminDashboard.java
|       |   ├──ForgetPasswordWindow.java
│       │   └── ResetPasswordWindow.java
│       │
│       ├── Model/
│       │   ├── AuditLog.java
│       │   ├── Person.java
│       │   ├── Student.java 
│       │   ├── Teacher.java
│       │   ├── User.java
│       │   └──Course.java
│       │
│       ├── Service/
│       │   ├── SchoolService.java
│       │   ├── StudentService.java
|       |   ├── TeacherService.java
|       |   ├── UserService.java
|       |   ├── RegistrationService.java
│       │   └── CourseService.java
│       │
│       ├── Exception/
│       │   ├── DataNotFoundException.java
│       │   ├── DuplicateRecordException.java
│       │   └── InvalidInputException.java
│       │
│       ├── Panels/
│       │   ├── StudentPanel.java
│       │   ├── TeacherPanel.java
│       │   ├── AdminPanel.java
│       │   └── CoursePanel.java
│       │
│       ├── Forms/
│       │   ├── AddStudentForm.java
│       │   ├── AddTeacherForm.java
│       │   ├── AdminApprovalForm.java
│       │   ├── RegistrationRequestForm.java
│       │   ├── LoginForm.java
│       │   └── ForgetPasswordForm.java
│       │
│       ├── Data/
│       │   ├──  SQLDataStore.java   
│       │   └──  DatabaseUtil.java
│       │
│       ├── DataBase_Connection/
│       │   ├── TestDB.java
│       │   └── DBConnection.java
│       │
│       ├── DAO/
│       │   ├── AuditLogDAO.java
│       │   ├── UserDAO.java
│       │   └── impl/
│       │       ├── AuditLogDAOImpl.java
│       │       └── UserDAOImpl.java
│       │
│       ├── Widgets/
│       │   └── EntityTableModel.java
│       │
│       └── Util/
│           ├── Constants.java
│           ├── Theme.java
│           ├── PasswordUtil.java
│           └── ValidationUtil.java
│
│
├── External Libraries/
|       ├── jdk-25
│       └── postgresql-42.7.10 
├── pom.xml
└── .gitignore
```

## 🛠️ Technologies Used

* Java (JDK 8 or above)
* Java Swing for GUI
* AWT for layouts and events
* IDE: IntelliJ IDEA / Vs Code
* Postgres: pgAdmin 4 for maintenance and query, and postgresql-42.7.10 for connection. 

---

## ▶️ How to Run the Project

* Clone or download the project
* Open it in your Java IDE
* Ensure JDK is properly configured
* Run the main class (e.g., SchoolGUI or LoginWindow)
* The GUI window will appear

---

## 📚 Learning Outcomes

* Hands-on experience with Java Swing
* Clear understanding of GUI + Service separation
* Improved debugging skills (type mismatch, getters/setters, packages)
* Foundation for advanced projects (Database, JDBC)

---

## 🚀 Future Enhancements
* Attendance management
* Report generation
* Result management
* Bill Management
* Improved UI design

---

## 🎓 Author

Anup Bista
BCS.IT (Cyber Security & Network Technology)

---

## 🎓 Programmed by 
Aaisha Jha
Anup Bista
Dipesh Devkota
Famous Lama

---

## 📄 License

This project is for educational purposes only.

---

## Credit 

The credit for the design work and the README project primarily goes to us, the members, with additional assistance from ChatGPT and YouTube videos.

---

⭐ Thank you for reviewing my project.
