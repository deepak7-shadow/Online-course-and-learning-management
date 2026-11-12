# Team Setup & Local Development Guide

## Prerequisites
- Java 21+ (OpenJDK)
- Node.js 18+ & npm
- Git
- Access to Supabase Project (URL & Anon Key)

## 1. Clone the Repository
```bash
git clone https://github.com/deepak7-shadow/Online-course-and-learning-management.git
cd Online-course-and-learning-management
```

## 2. Environment Setup
Copy the environment template files and fill in the required credentials:
```bash
cp .env.example .env
cp frontend/.env.example frontend/.env
cp backend/.env.example backend/.env
```

## 3. Branching Workflow for Team Members
- Member 1: `git switch -c feature/student-public-frontend`
- Member 2: `git switch -c feature/instructor-admin-frontend`
- Member 3: `git switch -c feature/supabase-jwt-admin-backend`
- Member 4: `git switch -c feature/course-learning-backend`

## 4. Frontend Setup (React + Vite)
```bash
cd frontend
npm install
npm run dev
```

## 5. Backend Setup (Spring Boot)
```bash
cd backend
./mvnw clean install
./mvnw spring-boot:run
```

