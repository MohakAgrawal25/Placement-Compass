# Placement Compass 🧭

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-brightgreen)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18-blue)](https://reactjs.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-31648c)](https://www.postgresql.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A full‑stack web application that centralizes campus placement information, replacing scattered emails and PDFs with a single, searchable platform. Students can explore drives from multiple colleges, filter by eligibility, read anonymous reviews, and access interview resources – all without logging in. College admins get a dedicated dashboard to manage drives, branches, skills, and moderate student‑generated content.

---

## ✨ Features

### 👨‍🎓 For Students (no login required)
- 🔍 **Browse placement drives** from all colleges – past and present.
- 🎯 **Powerful filters**: college, branch, CGPA, skills, location, gender preference, minimum rounds.
- ⭐ **Anonymous company reviews** – read and contribute experiences.
- 📚 **Interview resources** – share tips and learn from peers across institutions.
- 📊 **Interactive visualisations** (Recharts) – average packages, top recruiters, branch‑wise stats.

### 👨‍💼 For Admins (college‑specific access)
- 🏢 **Full CRUD** for companies, locations, branches, skills, and placement drives.
- 🏛️ **Multi‑college support** – each admin manages only their college’s data; super admins can oversee all.
- 🛡️ **Moderate content** – delete reviews or resources posted by students of their college.
- 📈 **Complete history** – track past drives and hiring statistics.

---

## 🛠️ Tech Stack

| Area       | Technology                                  |
|------------|---------------------------------------------|
| **Backend**| Spring Boot 4.0.3, Spring Security, JWT, JPA, PostgreSQL |
| **Frontend**| React 18, React Router 6, Axios, Recharts  |
| **Styling**| Custom CSS with global design system        |
| **Build**  | Maven (backend), npm (frontend)             |

---

## 📋 Prerequisites

- Java 17 or later
- Node.js 18+ and npm
- PostgreSQL 14+
- Git

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/placement-compass.git
cd placement-compass
```

The project has two main folders: `backend` and `frontend`.

### 2. Backend setup

```bash
cd backend/filter-backend/filter-backend
```

#### Configure the database

Create a PostgreSQL database, e.g. `placement_db`.  
Edit `src/main/resources/application.properties` with your credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/placement_db
spring.datasource.username=your_username
spring.datasource.password=your_password

# Keep the rest as is
```

#### Build and run

```bash
./mvnw clean install
./mvnw spring-boot:run
```

The backend will start on `http://localhost:8080`.

### 3. Frontend setup

Open a new terminal:

```bash
cd frontend
npm install
```

#### Configure the API base URL

If needed, adjust `axiosConfig.js` – by default it points to `http://localhost:8080/api`.

#### Start the React development server

```bash
npm start
```

The app will open at `http://localhost:3000`.

---

## 🗄️ Database Initialisation

On the first run, the backend automatically creates the tables and populates them with sample data using `CommandLineRunner` components. You’ll see logs like:

```
✅ Inserted default engineering branches.
✅ Inserted 382 default companies.
✅ Inserted 94 default locations.
✅ Inserted 13 default colleges.
✅ Super admin created: username='superadmin', password='admin123'
✅ Created 13 college admins (default password: 'college@123')
✅ Inserted some sample placement drives.
✅ Inserted some sample reviews.
✅ Inserted some sample resources.
```

If you ever need to reset the data, you can truncate the tables (in the correct order) and restart the application.

---

## 🧪 Default Admin Credentials

- **Super admin**  
  Username: `superadmin`  
  Password: `admin123`

- **College admin** (one per college, e.g. `admin_iitbombay`)  
  Password: `college@123`


## 🤝 Contributing

Contributions are welcome! Feel free to open issues or submit pull requests.

1. Fork the project.
2. Create your feature branch (`git checkout -b feature/amazing-feature`).
3. Commit your changes (`git commit -m 'Add some amazing feature'`).
4. Push to the branch (`git push origin feature/amazing-feature`).
5. Open a pull request.

---

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

---

## 📧 Contact

Your Name – [mohakagrawal10@gmail.com](mailto:mohakagrawal10@gmail.com)

Project Link: [https://github.com/yourusername/placement-compass](https://github.com/yourusername/placement-compass)

---

*Happy placing!* 🎓
