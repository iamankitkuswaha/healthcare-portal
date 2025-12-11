# 📘 Healthcare Document Portal – README

This project is a simple Healthcare Document Portal.
User can upload medical documents (PDF), see list, download them, and delete them.
It is built using Spring Boot (backend) and React CRA (frontend).
The system stores files in a local folder and saves metadata inside SQLite database.

# Project Overview

The application has two main parts:
## **[Frontend (React CRA)](<frontend-github-url>)**  
- Upload PDF files
- Shows all uploaded documents
- Allows download & delete
- Uses Axios for API calls
- UI is responsive with Bootstrap

## **[Backend (Spring Boot + SQLite)](<backend-github-url>)**  
- Exposes REST API endpoints
- Handles PDF upload (multipart)
- Validates file type & size
- Saves file into uploads/ folder
- Saves metadata (filename, path, size, created_at) in SQLite

---

# Prerequisites

## **Frontend Requirements**
- **Node.js:** v19.1.0  
- **npm:** v9.2.0  
- **VS Code recommended**  

## **Backend Requirements**
- **Java 21**  
- **Maven**  
- **SQLite3 CLI** (optional, to inspect DB)  
- **IntelliJ IDEA recommended**  
- **Postman** for API testing  
---

# How to Run Locally

## **Backend Setup (Spring Boot)**  
Detailed instructions are inside backend documentation:  
➡️ **[Setup and Run Locally](backend/README.md#setup-and-run-locally)**

## **Frontend Setup (React CRA)**  
Detailed instructions are inside frontend documentation:  
➡️ **[Setup and Run Locally](frontend/README.md#setup-and-run-locally)**


# Example API Calls
Full API documentation and Postman examples are in backend README:
**[API Endpoints, Functionality & Testing](backend/README.md#api-endpoints-functionality--testing-using-postman)**

