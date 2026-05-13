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
- [Cloud Deployment](#cloud-deployment)

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
| Google Cloud Platform | Cloud Deployment |
| Google Compute Engine | VM Hosting |
| Google Cloud SQL | Managed MySQL Database |
| Google Artifact Registry | Docker Image Registry |

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
- ☁️ **Cloud Deployment** — Hosted on Google Cloud Platform

---

## 📁 Project Structure

```
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
├── docker-compose.yml             # Docker Compose (local)
├── docker-compose.prod.yml        # Docker Compose (production/GCP)
└── README.md
```

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

### Access the Application (Local)

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

## ☁️ Cloud Deployment

The application is deployed on **Google Cloud Platform** using the following services:

| GCP Service | Usage |
|---|---|
| Compute Engine (e2-small) | Hosts Docker containers (backend + frontend) |
| Cloud SQL (MySQL 5.7) | Managed database |
| Artifact Registry | Stores Docker images |

### Live Access

| Service | URL |
|---|---|
| 🌐 Frontend (Angular) | http://35.195.52.114 |
| 📝 Swagger UI | http://35.195.52.114:9090/swagger-ui/index.html |

### GCP Architecture

```
User
 │
 ▼
Compute Engine VM (e2-small — europe-west1)
 ├── angular_frontend (Nginx :80)
 │       │ proxy_pass
 │       ▼
 └── spring_backend (:9090)
         │
         ▼
  Cloud SQL (MySQL 5.7 — europe-west1)
```

### Re-deploy Steps

```bash
# 1. Build and push new images
gcloud builds submit --tag europe-west1-docker.pkg.dev/multi-warehouse-stock/warehouse-repo/backend:latest ./MiniProjet
gcloud builds submit --tag europe-west1-docker.pkg.dev/multi-warehouse-stock/warehouse-repo/frontend:latest .

# 2. SSH into the VM
gcloud compute ssh warehouse-vm --zone=europe-west1-b

# 3. Pull and restart containers
docker compose -f docker-compose.prod.yml pull
docker compose -f docker-compose.prod.yml up -d
```

---

## 🔑 Default Credentials

| Email | Password | Role |
|---|---|---|
| admin@stock.tn | admin123 | ADMIN |

---

## 📝 API Documentation

Full interactive API documentation available at:
- **Local:** http://localhost:9090/swagger-ui/index.html
- **Cloud:** http://35.195.52.114:9090/swagger-ui/index.html

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