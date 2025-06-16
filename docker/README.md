
# Docker Compose Setup: PostgreSQL, Redis, Elasticsearch, Kibana

This project provides a Docker-based development environment with the following services:

- **PostgreSQL 16.2** – Relational database
- **Redis 8.0.2** – In-memory data store
- **Elasticsearch 8.13.4** – Full-text search engine
- **Kibana 8.13.4** – Visualization tool for Elasticsearch

---

## 🔧 Services & Ports

| Service        | Port on Host | Description                      |
|----------------|--------------|----------------------------------|
| PostgreSQL     | 5432         | SQL database                     |
| Redis          | 6379         | Caching and in-memory store      |
| Elasticsearch  | 9200 / 9300  | REST API / Cluster communication |
| Kibana         | 5601         | Web UI for Elasticsearch         |

---

## 🚀 Getting Started

### 1. Clone this repository

```bash
git clone https://github.com/ShrinithaMonica/dochub.git
cd dochub
```

### 2. To Start Containers
```bash
docker-compose up -d
```

### 3. To Stop Containers
```bash
docker-compose down
```

### 4. To Login To Postgres
```bash
docker exec -it docker-postgres-1 psql -U postgres -d dochub
```
