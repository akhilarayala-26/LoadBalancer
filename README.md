# LoadBalancer
# Load Balancer Simulation

This project implements a load balancer in Java that distributes incoming requests across multiple backend servers using Round-Robin and Least-Connections algorithms. The project includes a web-based frontend for user interaction and visualization of server statuses.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [License](#license)

## Features

- Simulates a load balancer that can distribute requests using:
  - **Round-Robin Algorithm**
  - **Least-Connections Algorithm**
- Web-based frontend to visualize backend server status.
- RESTful API for communication between the frontend and backend.
- Dynamic updates of active connections on backend servers.
- User-friendly interface to simulate requests.

## Technologies Used

- **Java**: For backend implementation (Load Balancer and Backend Servers).
- **HTML/JavaScript**: For the frontend.
- **JSON**: For data interchange between frontend and backend.
- **Java's HttpServer**: For creating a simple REST API.

## Project Structure

- **`frontend/`**: This directory contains the files related to the user interface of the application.
  - **`index.html`**: The main HTML file that provides the structure and content of the frontend.
  - **`script.js`**: A JavaScript file that handles the interactivity and communication with the backend API.

- **`backend/`**: This directory contains the Java classes responsible for the load balancing logic and server management.
  - **`/lib/`**: Contains external libraries required for the project.
    - **`json-simple-1.1.jar`**: A JSON processing library used for handling JSON data.
  - **`BackendServer.java`**: Implements the logic for individual backend servers, including tracking active connections.
  - **`LoadBalancer.java`**: Contains the implementation of the load balancing algorithms (Round-Robin and Least Connections).
  - **`LoadBalancerAPI.java`**: Sets up a simple REST API for the frontend to interact with the load balancer and backend servers.

## API Endpoints

This project provides a simple REST API to facilitate communication between the frontend and backend. Below are the available API endpoints:

### 1. **Get Server Status**
- **Endpoint**: `/api/status`
- **Method**: `GET`
- **Description**: Retrieves the current status of the backend servers, including their names and the number of active connections.'

## License

This project is licensed under the Apache License 2.0.
