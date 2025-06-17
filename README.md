# 📄 Document Management and Q&A System

This project is a **modular and scalable application** designed for managing documents, user authentication, and providing basic keyword-based Q&A functionality. It uses Spring Boot for the backend, with support systems like PostgreSQL, Redis, and Elasticsearch for storage, caching, and search.

---

## 🚀 Project Objectives

- Upload and ingest various document types (PDF, DOCX, text, CSV).
- Extract and store document content is s3 and elasticsearch asynchronously.
- Support Document retrieval by fuzzy match.
- Provide full user authentication and role-based access.
- Enable basic filtering, sorting, and pagination of documents.
- Ready for high-volume data processing and cloud deployment.

---

## ⚙️ Tech Stack

| Component        | Technology Used                         |
|------------------|-----------------------------------------|
| Backend          | Java 21, Spring Boot 3.5                |
| Authentication   | Spring Security + JWT                   |
| Database         | PostgreSQL 16.2 (with full-text search) |
| Cache Layer      | Redis 8.0.2                             |
| Search Engine    | Elasticsearch 8.13.4                    |
| API Docs         | Swagger / Springdoc OpenAPI             |
| Containerization | Docker & Docker Compose                 |
| Others           | Kibana for search visualization         |
| Maven            | Maven 3.9.6                             |

---

## 📂 Features

### ✅ Document Ingestion
- Accepts `.pdf`, `.docx`, `.csv`, and `.txt` files
- Extracts content
- Stores in Elasticsearch for searching, S3 for document storing, and postgres

### 🔍 Basic Q&A (Keyword-based)
- Accepts user questions via REST API
- Uses SQL full-text search or Elasticsearch
- Returns best-matching document snippets

### 🔐 Authentication
- Register, login, logout
- Role-based access: `admin`, `editor`, `viewer`
- JWT token-based authentication

### 📄 Document Filtering
- Filter documents by author, type, date
- Supports pagination and sorting

---

## 🧪 Test Coverage

- 70%+ test coverage with JUnit
- Unit test cases included for:
    - Controllers
    - Services
    - Repositories
    - Authentication

---

## 🚢 Dockerized Setup

All services are containerized using Docker Compose.

### ▶️ Start All Services

## 🐳 Docker Setup

**Note**: Follow [`docker/README.md`](docker/README.md) for complete instructions on setting up Redis, PostgreSQL, Elasticsearch, and Kibana using Docker Compose.

---

## 📦 Running the Backend Locally

> Make sure PostgreSQL, and Elasticsearch are running (via Docker).

