# 🏭 Multi-Warehouse Stock Management System

A full-stack web application for managing inventory across multiple warehouses,
built with **Spring Boot** (REST API) and **Angular** (Frontend UI).

---

## 📋 Table of Contents

- [Description](#description)
- [Technologies](#technologies)
- [Features](#features)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Running](#installation--running)
- [Default Credentials](#default-credentials)
- [API Documentation](#api-documentation)
- [Security & Roles](#security--roles)

---

## 📖 Description

This system allows businesses to efficiently manage their stock across
multiple warehouses. It supports real-time stock tracking, automatic
low-stock alerts, and role-based access control for different user types.

---

## 🛠️ Technologies

### Backend
| Technology | Version |
|---|---|
| Java | 17 |
| Spring Boot | 4.0.6 |
| Spring Security | Basic Auth |
| Spring Data JPA | Hibernate 7 |
| Spring Validator | Bean Validation |
| MySQL | 5.7 |
| Swagger / OpenAPI | 3 (springdoc 2.8.6) |
| Maven | 3.9.6 |

### Frontend
| Technology | Version |
|---|---|
| Angular | 17+ |
| TypeScript | 5+ |
| Bootstrap | 5 |
| Nginx | Alpine |

### DevOps
| Technology | Usage |
|---|---|
| Docker | Containerization |
| Docker Compose | Multi-container orchestration |

---

## ✅ Features

- 📦 **Product Management** — Full CRUD with search by name, category, supplier
- 🏢 **Warehouse Management** — Full CRUD with capacity tracking
- 📋 **Stock Management** — Track stock per product per warehouse
- ⚠️ **Automatic Alerts** — Detect and display low-stock items in real time
- 🔄 **Stock Movements** — Record stock entries and exits with automatic quantity updates
- 📊 **Dashboard** — Overview of total products, warehouses, stocks and movements
- 🔐 **Authentication** — HTTP Basic Auth with role-based access control
- 📝 **API Documentation** — Interactive Swagger UI

---

## 📁 Project Structure
multi-warehouse-stock/
├── MiniProjet/                    # Spring Boot Backend
│   ├── src/
│   │   └── main/java/tn/itbs/projet/
│   │       ├── controllers/       # REST Controllers
│   │       ├── entities/          # JPA Entities
│   │       ├── repositories/      # Spring Data Repositories
│   │       ├── services/          # Business Logic
│   │       └── security/          # Spring Security Config
│   ├── Dockerfile
│   └── pom.xml
├── src/                           # Angular Frontend
│   └── app/
│       ├── components/            # UI Components
│       ├── services/              # HTTP Services
│       ├── models/                # TypeScript Interfaces
│       ├── guards/                # Route Guards
│       └── interceptors/          # HTTP Interceptors
├── Dockerfile                     # Angular Dockerfile
├── nginx.conf                     # Nginx Configuration
├── docker-compose.yml             # Docker Compose
└── README.md

---

## 📦 Prerequisites

- [Docker Desktop](https://www.docker.com/products/docker-desktop) installed and running
- Git

---

## 🚀 Installation & Running

### Option 1 — Docker (Recommended)

```bash
# 1. Clone the repository
git clone https://github.com/MohamedAzizKouch/multi-warehouse-stock.git
cd multi-warehouse-stock

# 2. Build and start all services
docker compose build --no-cache
docker compose up
```

Wait for all 3 containers to start:
- ✅ `mysql_stock` — Database
- ✅ `spring_backend` — REST API
- ✅ `angular_frontend` — Web UI

### Access the Application

| Service | URL |
|---|---|
| 🌐 Frontend (Angular) | http://localhost |
| ⚙️ Backend API | http://localhost:9090 |
| 📝 Swagger UI | http://localhost:9090/swagger-ui/index.html |

### Stop the Application

```bash
docker compose down
```

---

### Option 2 — Manual (Development)

**Backend (Eclipse or IntelliJ):**
```bash
# Import MiniProjet as Maven project
# Configure src/main/resources/application.properties
# Run MiniProjetApplication.java
# API available at http://localhost:9090
```

**Frontend (VS Code):**
```bash
cd stock-frontend
npm install
ng serve
# UI available at http://localhost:4200
```

---

## 🔑 Default Credentials

| Email | Password | Role |
|---|---|---|
| admin@stock.tn | admin123 | ADMIN |

---

## 📝 API Documentation

Full interactive API documentation available at:
http://localhost:9090/swagger-ui/index.html

### Main Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/produit/add` | Add a product |
| `GET` | `/produit/all` | Get all products |
| `PUT` | `/produit/update/{id}` | Update a product |
| `DELETE` | `/produit/delete/{id}` | Delete a product |
| `POST` | `/entrepot/add` | Add a warehouse |
| `GET` | `/entrepot/all` | Get all warehouses |
| `POST` | `/stock/add` | Add a stock entry |
| `GET` | `/stock/alertes` | Get low-stock alerts |
| `POST` | `/mouvement/entree` | Record stock entry |
| `POST` | `/mouvement/sortie` | Record stock exit |
| `GET` | `/dashboard/stats` | Get dashboard statistics |

---

## 🔐 Security & Roles

| Role | Permissions |
|---|---|
| `ADMIN` | Full access — manage users, products, warehouses, stocks, movements |
| `GESTIONNAIRE` | Manage products, warehouses, stocks and movements (no user management) |
| `OBSERVATEUR` | Read-only access to all resources |

Authentication uses **HTTP Basic Auth**.
All endpoints except Swagger UI require authentication.

---

## 👤 Author

**Mohamed Aziz Kouch**
- GitHub: [@MohamedAzizKouch](https://github.com/MohamedAzizKouch)

---

## 🏫 Academic Context

Project developed as part of the **ITBS** curriculum.
Subject: Multi-Warehouse Stock Management System.