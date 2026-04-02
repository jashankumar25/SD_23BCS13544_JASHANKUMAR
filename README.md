# 🏗️ System Design Experiments Collection

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white) 
![Nginx](https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white)
![System Design](https://img.shields.io/badge/System_Design-00599c?style=for-the-badge)
![Load Balancing](https://img.shields.io/badge/Load_Balancing-8B5CF6?style=for-the-badge)
![Concurrency](https://img.shields.io/badge/Concurrency-10B981?style=for-the-badge)

Welcome to the **System Design Experiments Collection**! This repository is a curated suite of projects built from the ground up to explore, implement, and test core software engineering principles, system architecture patterns, and DevOps networking concepts.

Everything in this repository is built natively with core Java (no heavy abstractions like Spring Boot) to provide a deep, foundational understanding of how these systems work under the hood.

---

## 📂 Experiments Overview

### 🎬 [Experiment 3: Movie Ticket Booking](./Experiment3_MovieTicketBooking)
**Focus:** *Concurrency, Domain Modeling, and Object-Oriented Design.*
- Simulates a theater booking platform.
- Focuses on concurrent execution patterns to prevent race conditions during ticket reservation.
- Employs SOLID principles and appropriate Design Patterns to ensure highly cohesive and decoupled modules.

### 🎵 [Experiment 4: Music Streaming Service](./Experiment4_MusicStreaming)
**Focus:** *State Management, Strategy Patterns, and Core Logic Execution.*
- A backend simulation of a complex music streaming service.
- Handles playlist queues, continuous playback functionalities, and different streaming strategies.
- Implements the Strategy Pattern dynamically for tasks like song recommendations.

### 🌐 [Experiment 5: Network Request Flow Simulation](./Experiment5_NetworkSimulation)
**Focus:** *Network Lifecycle, Packet Simulation, and Request Parsing.*
- Simulates how a network request travels through the OSI layers to a server and back.
- Focuses on packet creation, routing simulation, and tracking network lifecycle states.
- Great for understanding the absolute fundamentals of network IO.

### 🚀 [Experiment 6: Playlist REST API & Nginx Load Balancing](./Experiment6_PlaylistREST_API)
**Focus:** *RESTful Architecture, Load Balancing (Round-Robin), Rate-Limiting, CORS, and UI Dashboards.*
- Contains a fully functional, container-less Java HTTP REST API.
- Implements custom **Rate Limiting** logic protecting the API against DDoS bursts.
- Utilizes an **Nginx Reverse Proxy** (`/NginxExperiment/`) to dynamically balance incoming traffic across multiple instances of the backend servers running on different ports (3000 & 3001).
- Includes a stunning Glassmorphism **HTML5 Dashboard** that calculates true network throughput dynamically and visually maps Nginx traffic distribution in real-time.
- Features a one-click automated `.bat` script that compiles Java, controls Nginx nodes, handles API lifecycle, and boots the analytics dashboard.

---

## 🛠️ Technology Stack

- **Backend Logic:** Core Java 11+
- **Reverse Proxy / Server Load-Balancing:** Nginx
- **Frontend / Telemetry Analysis:** Vanilla HTML5, CSS3, JavaScript (Fetch API/Promises)
- **Deployment & Automation:** Windows Command Scripts (`.bat`)

## 🚀 How to Run

Each experiment folder contains its own source code and execution scripts. 
For example, to launch the full-stack load balancing visualizer in **Experiment 6**:

1. Open your terminal.
2. Navigate into the respective experiment directory:
   ```cmd
   cd Experiment6_PlaylistREST_API
   ```
3. Run the automation batch file:
   ```cmd
   run_experiment6.bat
   ```
*(Make sure Nginx is configured in your local environment prior to executing proxy-based experiments).*

## 👤 Author

Developed by **Jashan Kumar** ([@jashankumar25](https://github.com/jashankumar25))

## 📜 License

This system design repository and its internal experiment modules are licensed under the MIT License.
"# SD_23BCS13544_JASHANKUMAR" 
