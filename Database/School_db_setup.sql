SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'School_Management_System_db'
  AND pid <> pg_backend_pid();

DROP DATABASE IF EXISTS "School_Management_System_db";

CREATE DATABASE "School_Management_System_db";
