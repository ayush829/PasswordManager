\# RevPassword Manager



\## 📌 Project Overview

RevPassword Manager is a \*\*secure, console-based password management application\*\* developed using \*\*Java and JDBC\*\* with \*\*Oracle Database\*\* as the backend.  

The application allows users to securely store, manage, and retrieve passwords for multiple online accounts using a \*\*master password–protected vault\*\*.



The project follows a \*\*layered architecture\*\* and focuses on \*\*security, modularity, and clean design\*\*.



---



\## 🎯 Objectives

\- Secure storage of user credentials

\- Master password–based authentication

\- Encryption and hashing for sensitive data

\- Proper separation of concerns using layered architecture

\- Practical implementation of JDBC with Oracle Database



---



\## 🛠️ Technologies Used

\- \*\*Programming Language:\*\* Java

\- \*\*Database:\*\* Oracle Database

\- \*\*Database Connectivity:\*\* JDBC

\- \*\*IDE:\*\* IntelliJ IDEA / VS Code

\- \*\*Logging:\*\* Log4j

\- \*\*Testing:\*\* JUnit

\- \*\*Modeling Tools:\*\* StarUML (ER \& Architecture diagrams)



---



\## 🧱 Application Architecture

The project follows a \*\*Layered Architecture\*\*:



\- \*\*UI Layer\*\*

&nbsp; - Console-based interaction with users

&nbsp; - Handles user input and output



\- \*\*Service Layer\*\*

&nbsp; - Contains business logic

&nbsp; - Handles validation, encryption, and flow control



\- \*\*DAO Layer\*\*

&nbsp; - Handles all database operations using JDBC

&nbsp; - Isolates SQL logic from business logic



\- \*\*Database Layer\*\*

&nbsp; - Oracle database storing users, passwords, and verification data



---



\## 🗂️ Project Structure

src/

│

├── ui/

│ └── ConsoleMenu.java

│

├── service/

│ ├── UserService.java

│ └── PasswordService.java

│

├── dao/

│ ├── UserDAO.java

│ └── PasswordDAO.java

│

├── model/

│ ├── User.java

│ └── PasswordEntry.java

│

├── util/

│ └── DBConnection.java

│

├── security/

│ ├── EncryptionUtil.java

│ ├── PasswordUtil.java

│ └── PasswordGenerator.java

│

├── test/

│ └── PasswordUtilTest.java

│

└── Main.java






---

## 🔐 Security Features
- **Master password hashing** (no plain text storage)
- **Encryption of stored account passwords**
- **Security questions for account recovery**
- **Verification codes (OTP-style) for sensitive operations**
- **Re-verification before viewing stored passwords**

---

## ✨ Core Features
- User registration and login
- Add, update, delete stored account passwords
- Search passwords by account name (partial match)
- View password with re-verification
- Generate strong random passwords
- Update user profile details (name & email)
- Forgot password support using security question
- Logging of important operations using Log4j

---

## 🗄️ Database Design
### Tables Used:
- **USERS**
- **PASSWORD_ENTRIES**
- **VERIFICATION_CODES**

Relationships:
- One user can have multiple password entries
- One user can generate multiple verification codes

(ER Diagram created using StarUML)

---

## ▶️ How to Run the Project
1. Clone or download the project
2. Open in IntelliJ IDEA or VS Code
3. Add Oracle JDBC driver (`ojdbc8.jar`) to classpath
4. Configure database credentials in `DBConnection.java`
5. Ensure required tables exist in Oracle schema
6. Run `Main.java`
7. Use console menu to interact with the application

---

## 🧪 Testing
- Basic unit testing implemented using **JUnit**
- Password hashing functionality tested
- Application behavior verified manually via console

---

## 📊 Diagrams Included
- **ER Diagram** – Database design
- **Architecture Diagram** – Layered system design

(Both created using StarUML)

---

## 🚀 Future Enhancements
- Email-based OTP delivery
- Password strength analysis
- GUI-based interface
- Role-based access control
- Cloud database integration

---

## 👤 Author
**Ayush Kumar Singh**

---

## ✅ Conclusion
RevPassword Manager demonstrates a complete end-to-end Java application using JDBC and Oracle Database with a strong emphasis on security, clean architecture, and real-world design principles.

