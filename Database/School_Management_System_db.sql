-- =========================================
-- RESET DATABASE (SAFE VERSION)
-- =========================================
DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

-- =========================================
-- EXTENSIONS (OPTIONAL BUT USEFUL)
-- =========================================
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- =========================================
-- ENUM: USER ROLE
-- =========================================
CREATE TYPE user_role AS ENUM ('ADMIN','STUDENT','TEACHER');

-- =========================================
-- COURSES TABLE
-- =========================================
CREATE TABLE courses (
    course_id SERIAL PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    credits INT NOT NULL CHECK (credits > 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO courses (code, name, credits)
VALUES
('CS123', 'Cyber Security', 3),
('AI101', 'Artificial Intelligence', 3);

-- =========================================
-- STUDENTS TABLE (NORMALIZED ✅)
-- =========================================
CREATE TABLE students (
    lc_number VARCHAR(20) PRIMARY KEY,
    reg_no INT NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    semester INT NOT NULL CHECK (semester > 0),
    course_id INT REFERENCES courses(course_id) ON DELETE SET NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO students
(lc_number, reg_no, full_name, email, phone, semester, course_id, username, password)
VALUES
('LC11103', 1001, 'Alice Smith', 'alice@example.com', '9815609454', 2, 2, 'alice123', 'AliceS@123');

-- =========================================
-- TEACHERS TABLE (NORMALIZED ✅)
-- =========================================
CREATE TABLE teachers (
    employee_no VARCHAR(20) PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO teachers
(employee_no, full_name, phone, username, password)
VALUES
('55001', 'John Doe', '9815001234', 'johnd', 'JohnD@123');

-- =========================================
-- TEACHER-COURSE RELATION (MANY-TO-MANY ✅)
-- =========================================
CREATE TABLE teacher_courses (
    id SERIAL PRIMARY KEY,
    teacher_id VARCHAR(20) REFERENCES teachers(employee_no) ON DELETE CASCADE,
    course_id INT REFERENCES courses(course_id) ON DELETE CASCADE
);

-- =========================================
-- ADMIN TABLE
-- =========================================
CREATE TABLE admin (
    employee_no VARCHAR(20) PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO admin
(employee_no, full_name, email, phone, username, password)
VALUES
('66001', 'Nolan Shrestha', 'nolan@kfaltd.com', '9815609454', 'nolan678', 'NolanS&678');

-- =========================================
-- USERS TABLE (AUTH SYSTEM 🔐)
-- =========================================
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(50) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    salt TEXT NOT NULL,
    role user_role NOT NULL,
    linked_person_id VARCHAR(20),
    is_active BOOLEAN DEFAULT TRUE,
    failed_attempts INT DEFAULT 0,
    last_login TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (full_name, username, password_hash, salt, role, linked_person_id)
VALUES
('Nolan Shrestha','nolan678', 'HASHED_ADMIN_PASS', 'SALT1', 'ADMIN', '66001'),
('Alice Smith','alice123', 'HASHED_PASS', 'SALT2', 'STUDENT', 'LC11103'),
('John Doe','johnd', 'HASHED_PASS', 'SALT3', 'TEACHER', '55001');

-- =========================================
-- REGISTRATION REQUESTS
-- =========================================
CREATE TABLE registration_requests (
    request_id SERIAL PRIMARY KEY,
    lc_number VARCHAR(20),
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    role user_role NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING'
        CHECK (status IN ('PENDING','APPROVED','REJECTED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO registration_requests (lc_number, full_name, email, phone, role, username, password)
VALUES
('LC11004', 'Bob Johnson', 'bob@example.com', '9815005678', 'STUDENT', 'bobj815', 'BobJ%815'),
('LC11005', 'Clara Smith', 'clara@example.com', '9815009876', 'TEACHER', 'claras453', 'ClaraS#453');

-- =========================================
-- LOGIN AUDIT (SECURITY 🔐)
-- =========================================
CREATE TABLE login_audit (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50),
    success BOOLEAN,
    attempt_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(50)
);

-- =========================================
-- INDEXES (PERFORMANCE 🚀)
-- =========================================
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_students_course ON students(course_id);
CREATE INDEX idx_registration_status ON registration_requests(status);