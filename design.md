# 🏥 Healthcare Document Portal — Design Document

This document describes the technical design, architecture, API specification, and and assumptions for the Healthcare Document Portal application.

The project is a simple but functional document management system where a user can upload PDF files, view uploaded documents, download them, and delete them. It demonstrates full-stack development skills including React UI, REST APIs, backend logic, file handling, and SQLite integration.

---

# 1. Tech Stack Choices

### **Frontend framework and reasons to choose**
I used **React (Create React App)** because:

- Easy setup and beginner friendly  
- Component-based UI  
- Large ecosystem  
- Works well with Axios and REST APIs  
- Bootstrap integration for responsive UI  

---

### **Backend framework and reasons to choose**
I used **Spring Boot** because:

- Reliable REST API development  
- Easy file upload support (`MultipartFile`)  
- Smooth JPA integration  
- Automatic configuration  
- Strong exception handling and validation  

---

### **Database and reasons to choose**
I chose **SQLite** because:

- Lightweight and serverless  
- Very easy to use for assignments  
- File-based and portable  
- Easy inspection using sqlite3 CLI  
- Good enough for single-user scenario  
---

### **The changes would I consider to support 1,000 users.**

- Migrate SQLite → PostgreSQL/MySQL  
- Store files in AWS S3 instead of local folder  
- Add authentication (JWT/OAuth)  
- Add pagination and caching  
- Use Docker & Kubernetes  
- Use centralized logging and monitoring  
---

# 2. Architecture Overview

### **Components**

- **Frontend (React)** – Handles UI, file upload form, list, download/delete buttons  
- **Backend (Spring Boot)** – Validates and stores documents, exposes REST APIs  
- **uploads/ folder** – Stores the actual PDF files  
- **SQLite Database** – Stores metadata (`id`, `filename`, `filepath`, `filesize`, `created_at`)  

---
### **System Flow Overview**

**Frontend (React)** → **Backend (Spring Boot)** → **File Storage (uploads/)** → **Database (SQLite)** → **Frontend (React)**

---

### **Frontend (React)**

- User selects PDF  
- React validates file (type + size)  
- Sends upload request via Axios  
- Displays document list  
- Handles download & delete actions  

---

### **Backend (Spring Boot)**

- Receives upload, download, and delete API calls  
- Validates file type and size  
- Generates unique filename using UUID prefix  
- Saves file and metadata  
- Communicates with SQLite and file storage  
- Returns JSON responses to frontend  

---

### **File Storage (uploads/)**

- Stores physical PDF files  
- Files are saved using a UUID-based unique name  
- Provides file path back to backend for metadata storage  

---

### **Database (SQLite)**

Stores metadata such as:

- `id`  
- `filename`  
- `filepath`  
- `filesize`  
- `created_at`  

Supports operations:

- Insert metadata  
- List all documents  
- Find by ID  
- Delete by ID  

---

### **Return Flow**

- Backend sends success or error JSON  
- Frontend refreshes the document list  
- User sees updated UI immediately  

---

# 4. API Specification
For detailed API behavior and Postman testing steps, refer to:  
➡️ [API Endpoints, Functionality & Testing](backend/README.md#api-endpoints-functionality--testing-using-postman)


# 5. Data Flow Description

### **Upload Flow**
1. User selects PDF file in the browser.
2. React validates file type & size.
3. Sends file to backend via Axios (multipart/form-data).
4. Backend validates:

- must be PDF
- must be ≤10 MB

5. A short UUID is generated and added to filename.
6. Backend creates /uploads folder if not present.
7. File is stored physically inside the folder.
8. Metadata saved into SQLite:

- filename
- filepath
- filesize
- created_at

9. Backend returns JSON response.
10. Frontend refreshes the document list.

### **Download Flow**
1. User clicks Download button.
2. Frontend makes GET request to /documents/{id}
3. Backend verifies file exists.
4. Backend streams PDF file as application/pdf.
5. Browser downloads the file.  
---

# 6. Assumptions

- Only PDF files allowed  
- Max file size: **10 MB**  
- No authentication required  
- Backend runs on port **8081**  
- SQLite database auto-created  
- Uploads directory auto-created  
- File names kept unique using UUID  

# 7. Future Improvements

- Add login + role-based access.
- Pagination for large document lists.
- Cloud file storage (AWS S3).
- Drag-and-drop upload UI.
- Preview PDF inside browser.
- Versioning of documents.
- Background virus scanning.
- Multi-user document separation.
- Monitoring + logging dashboards. 
---
