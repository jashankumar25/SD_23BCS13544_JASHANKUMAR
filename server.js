// ============================================================
// server.js — ENTRY POINT
// This is the main file that boots the entire application.
// It connects to MongoDB, initializes Redis, registers all
// middleware, sets up routes, and starts the HTTP server.
// ============================================================

// Load environment variables from .env file into process.env
// Must be called FIRST before any other module reads process.env
require('dotenv').config();

const express = require('express'); // Web framework for Node.js
const cors    = require('cors');    // Allows cross-origin requests (e.g., from browser)

// Internal modules
const connectDB         = require('./src/config/db');          // MongoDB connection
const metricsMiddleware = require('./src/middleware/metrics'); // Request logger
const logger            = require('./src/utils/logger');       // Console logger utility

// ── Observer Pattern Setup ──────────────────────────────────
// PlaylistSubject is the "publisher" — it notifies all registered observers
// when a playlist event occurs (created, updated, deleted).
const playlistSubject  = require('./src/observers/PlaylistSubject');
const CacheInvalidator = require('./src/observers/CacheInvalidator');

// Register CacheInvalidator as an observer.
// Now whenever playlistSubject.notify() is called, CacheInvalidator.update() fires.
playlistSubject.addObserver(new CacheInvalidator());
// ────────────────────────────────────────────────────────────

// Create the Express application instance
const app = express();

// Connect to MongoDB (async — runs in background)
connectDB();

// ── Middleware Stack ─────────────────────────────────────────
// Middleware runs on EVERY request before it reaches the route handler.
// The order matters — they execute top to bottom.

app.use(cors());            // Allow all cross-origin requests
app.use(express.json());    // Parse JSON request bodies (req.body)
app.use(metricsMiddleware); // Log: method, URL, status, response time
// ────────────────────────────────────────────────────────────

// ── API Routes ───────────────────────────────────────────────
// Mount the playlist router at /playlist
// All requests to /playlist/* are handled by playlistRoutes.js
app.use('/playlist',        require('./src/routes/playlistRoutes'));

// Mount the recommendation router at /recommendations
app.use('/recommendations', require('./src/routes/recommendationRoutes'));
// ────────────────────────────────────────────────────────────

// Health check endpoint — used by load balancers and monitoring tools
// Returns 200 OK if the server is alive
app.get('/health', (req, res) => res.status(200).json({ status: 'OK' }));

// Serve the built-in API Tester UI (api-tester.html) as static files
// This allows accessing the UI at http://localhost:3000
app.use(express.static(__dirname));

// Root route → open API Tester
app.get('/',       (req, res) => res.sendFile(__dirname + '/api-tester.html'));
app.get('/tester', (req, res) => res.sendFile(__dirname + '/api-tester.html'));

// Read PORT from .env (defaults to 3000 if not set)
const PORT = process.env.PORT || 3000;

// Start listening for HTTP requests on the specified port
app.listen(PORT, () => {
    logger.info(`Server running on port ${PORT}`);
});
