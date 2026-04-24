# 🎵 Scalable Playlist & Recommendation Service

> A **production-inspired backend system** demonstrating real-world distributed system concepts used by platforms like **Netflix** and **Spotify**.

Implements **playlist management**, **dynamic recommendations**, **Redis caching**, and **API rate limiting** with a strong focus on **scalability, performance, and clean architecture**.

---

## 🚀 Features

| Feature | Description |
|---------|-------------|
| ✅ Playlist CRUD | Create, Read, Update, Delete via REST APIs |
| 🎯 Recommendations | Genre-based & Popularity-based strategies |
| ⚡ Redis Caching | Cache-Aside strategy — 10x faster reads |
| 🚦 Rate Limiting | 5 requests/min per user (429 on exceed) |
| 🔄 Cache Invalidation | Observer pattern auto-clears cache on update |
| 📊 Metrics Logging | Request time, cache hit/miss, error tracking |

---

## 🏗️ System Architecture

```
CLIENT (Browser / Postman / PowerShell)
        ↓
MIDDLEWARE (Rate Limiter + Metrics Logger)
        ↓
ROUTES → CONTROLLERS → SERVICES
        ↓                ↓
     MongoDB          Redis Cache
   (persistent)      (in-memory)
```

---

## 📂 Project Structure

```
exp-7/
├── server.js                   ← Entry point
├── .env                        ← Environment config
├── api-tester.html             ← Browser-based API tester UI
├── src/
│   ├── config/
│   │   ├── db.js               ← MongoDB connection
│   │   └── redis.js            ← Redis connection
│   ├── models/
│   │   └── Playlist.js         ← Song & Playlist schemas
│   ├── middleware/
│   │   ├── rateLimiter.js      ← 5 req/min enforcer
│   │   └── metrics.js          ← Response time & cache logging
│   ├── services/
│   │   ├── PlaylistService.js  ← Cache-Aside logic
│   │   └── RecommendationService.js
│   ├── strategies/
│   │   ├── GenreStrategy.js    ← Filter by genre
│   │   ├── PopularityStrategy.js ← Sort by popularity
│   │   └── RecommendationContext.js ← Strategy switcher
│   ├── observers/
│   │   ├── PlaylistSubject.js  ← Event publisher
│   │   └── CacheInvalidator.js ← Auto-deletes Redis key
│   ├── controllers/
│   │   ├── PlaylistController.js
│   │   └── RecommendationController.js
│   ├── routes/
│   │   ├── playlistRoutes.js
│   │   └── recommendationRoutes.js
│   └── utils/
│       ├── logger.js
│       └── seed.js             ← Sample data seeder
```

---

## 🧠 Design Patterns

### 1. Strategy Pattern — Dynamic Recommendations

Switch algorithms at runtime without changing the controller:

```
GET /recommendations?type=genre&genres=Pop,Rock  → GenreStrategy
GET /recommendations?type=popularity             → PopularityStrategy
```

### 2. Observer Pattern — Automatic Cache Invalidation

```
PUT /playlist/:id
  → MongoDB updated
  → PlaylistSubject.notify('PLAYLIST_UPDATED')
  → CacheInvalidator.update()     ← fires automatically
  → redis.del('playlist:<id>')    ← stale cache deleted
```

### 3. SOLID Principles

| Principle | Applied |
|-----------|---------|
| **Single Responsibility** | Controller handles HTTP; Service handles logic |
| **Open/Closed** | Add new strategies without touching existing code |
| **Dependency Inversion** | Services use abstractions, not concrete classes |

---

## ⚡ Performance — Cache-Aside Strategy

```
GET /playlist/:id

  ┌─ Check Redis ──────────────────────────────┐
  │   HIT  → return data in ~2ms  ⚡           │
  │   MISS → query MongoDB (~20ms)             │
  │          → store in Redis (TTL: 1 hour)    │
  │          → return data                     │
  └────────────────────────────────────────────┘
```

**Result: 10x faster on repeated reads**

---

## 🔌 API Endpoints

| Method | Endpoint | Description | Status |
|--------|----------|-------------|--------|
| GET | `/health` | Server health check | 200 |
| POST | `/playlist` | Create a playlist | 201 |
| GET | `/playlist/:id` | Get playlist (cached) | 200 |
| PUT | `/playlist/:id` | Update + invalidate cache | 200 |
| DELETE | `/playlist/:id` | Delete + clear cache | 204 |
| GET | `/recommendations?type=popularity` | Top songs | 200 |
| GET | `/recommendations?type=genre&genres=Pop` | Genre filter | 200 |

---

## 🧪 Live Demo Results

All endpoints tested and verified:

| # | Request | Status | Result |
|---|---------|--------|--------|
| 1 | `GET /health` | `200 OK` | `{"status":"OK"}` |
| 2 | `POST /playlist` | `201 Created` | Playlist saved to MongoDB with `_id` |
| 3 | `GET /playlist/:id` *(1st)* | `200 OK` | 🔄 Cache MISS — MongoDB fetch (~15ms) |
| 4 | `GET /playlist/:id` *(2nd)* | `200 OK` | ⚡ Cache HIT — Redis served (~2ms) |
| 5 | `PUT /playlist/:id` | `200 OK` | Updated + Observer cleared Redis cache |
| 6 | `GET /recommendations?popularity` | `200 OK` | Songs sorted by score |
| 7 | `GET /recommendations?genre` | `200 OK` | Pop & Rock songs filtered |
| 8 | Rate limit (6 rapid requests) | `429` | `"You have exceeded the 5 requests per minute limit."` |
| 9 | `DELETE /playlist/:id` | `204` | Deleted + cache cleared |

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Runtime | Node.js v24 |
| Framework | Express.js |
| Database | MongoDB + Mongoose |
| Cache | Redis + ioredis |
| Rate Limiting | express-rate-limit |

---

## ⚙️ Setup & Installation

### Prerequisites
Make sure these are running:
```
MongoDB  → port 27017
Redis    → port 6379
```

### Quick Start

```powershell
# 1. Install packages
npm install

# 2. Seed the database (once only)
npm run seed

# 3. Start server
npm start
```

**Server live at → `http://localhost:3000`**
**API Tester UI → `http://localhost:3000/tester`**

### Environment Variables (`.env`)

```env
PORT=3000
MONGODB_URI=mongodb://localhost:27017/playlist_service
REDIS_URL=redis://localhost:6379
NODE_ENV=development
```

---

## 🧪 Test with PowerShell

```powershell
# Health Check
Invoke-WebRequest -Uri "http://localhost:3000/health" -UseBasicParsing | Select-Object -ExpandProperty Content

# Create Playlist
$body = '{"name":"My Playlist","ownerId":"user1","genres":["Pop","Rock"]}'
Invoke-WebRequest -Uri "http://localhost:3000/playlist" -Method POST -Body $body -ContentType "application/json" -UseBasicParsing | Select-Object -ExpandProperty Content

# Get Recommendations
Invoke-WebRequest -Uri "http://localhost:3000/recommendations?type=popularity" -UseBasicParsing | Select-Object -ExpandProperty Content
```

Or import **`Playlist-Service.postman_collection.json`** into Postman for the full collection with auto-test scripts.

---

## 🌟 Key Highlights

- 🔥 Real-world backend architecture with layered design
- ⚡ Redis cache gives **10x faster** reads on repeated requests
- 🧠 **Strategy + Observer** design patterns in production style
- 🚀 Stateless, scalable REST API — load balancer ready

---

## 🔮 Future Improvements

- 🔐 JWT Authentication & Authorization
- 📦 Docker + Kubernetes deployment
- 📡 Kafka for async event processing
- 🤖 ML-based personalized recommendations
- 📄 Pagination for large datasets

---

## 👨‍💻 Author

**Jashan Kumar**
B.Tech CSE | Backend & System Design Enthusiast

---

## 📜 License

MIT License
