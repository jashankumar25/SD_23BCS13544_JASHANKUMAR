# 🎵 Playlist REST API & Nginx Load Balancing

This project demonstrates a fully functional, container-less **Java REST API** paired with an **Nginx Load Balancer** and a **Real-Time Interactive Dashboard**. It serves as a comprehensive system design experiment outlining core principles such as reverse proxying, rate-limiting, CORS handling, and round-robin traffic distribution.

## ✨ Features

- **Custom Java HTTP Backend**: Built from the ground up using Java's built-in `com.sun.net.httpserver` (No Spring Boot or heavy frameworks).
- **Nginx Reverse Proxy**: Uses `nginx.conf` to dynamically route inbound traffic to multiple isolated instances of the Java backend on different ports (3000 & 3001) using a Round-Robin strategy.
- **Built-in Rate Limiting**: Ensures fair usage per client IP. Prevents DDoS style bursts across testing thresholds. 
- **CORS Handling**: Securely manages cross-origin preflight requests directly from the Java backend, enabling secure external web-client `fetch()` operations.
- **Live Visual Dashboard**: A beautiful, single-page `index.html` UI that calculates absolute throughput (`Requests Per Second`), completion time, and visually maps Nginx's round-robin traffic assignments dynamically in a stunning dark-mode layout.
- **One-Click Automation**: A `run_experiment6.bat` script that manages killing lingering processes, compiling Java files, deploying Nginx config, starting the HTTP servers, and launching the dashboard!

## 🚀 Quick Start

1. Ensure **Java JDK** and **Nginx** are installed on your Windows environment. Make sure the Nginx path inside `run_experiment6.bat` accurately defines your local Nginx installation context.
2. Open a Command Prompt (cmd) and navigate to the project directory:
   ```cmd
   cd <your-path>\Experiment6_PlaylistREST_API
   ```
3. Execute the automation script:
   ```cmd
   run_experiment6.bat
   ```
4. **Interactive Load Testing**: The script will automatically open `index.html` in your default browser. Click the **"🚀 Fire 200 Requests Mode"** button to start pumping network traffic through the Nginx endpoints and watch the nodes balance the load in real time!

## 🛠️ Architecture Stack

- **Backend**: Core Java 11+
- **Reverse Proxy / Load Balancer**: Nginx
- **Frontend / Dashboard**: Vanilla HTML5, CSS3 (Glassmorphism design), Vanilla JS (Fetch API with Promises)
- **Scripting**: Windows Batch `.bat` Scripts

## 📸 Dashboard Preview

The unified Glassmorphic dashboard dynamically captures the throughput (`req/sec`), total operational time, and visually populates progress bars mapping the requests delivered directly to `Port 3000` vs `Port 3001`.

## 👤 Author

Developed by **Jashan Kumar**

## 📜 License

This project is licensed under the MIT License.
