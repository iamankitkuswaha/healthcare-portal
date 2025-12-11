#  Healthcare Document Portal — Frontend (React)

This is the frontend part for the **Healthcare Document Portal**, made using **React CRA**, **Bootstrap**, and **Axios**.  User can upload medical documents (PDF only), see list, download and delete documents. UI is kept simple and responsive.

---

## Overview

This frontend talks to the backend API (Spring Boot + SQLite).  
It includes:

- PDF upload form  
- File validation  
- Document list table  
- Download and Delete actions  
- Toast messages for success/error  
- Scrollbar only inside the document list area  
- Long file names adjust automatically  

Design is clean and easy to follow.

---

## How It Works

1. User selects a PDF  
2. Frontend validates size and type  
3. Sends the file to backend using Axios multipart upload  
4. Backend stores the file and metadata  
5. Frontend refreshes the document list  
6. Toast shows success or error  
7. User can download or delete files  

---

## Assumptions

- Backend runs locally on:  
  **http://localhost:8081/documents**
- Only **PDF** files are allowed  
- File size limit defined in UploadForm.jsx(ex: `MAX_BYTES=10,485,760`)  
- No authentication for this assignment  
- Scroll area required, list can be big  

---

## Updating Backend URI

If your backend is running on another URL, update it inside:

### `src/api/api.js`

Find:

```js
const baseURL = "http://localhost:8081";
```

## Setup and run locally

Make sure **Node.js** and **npm** are installed.
Clone or download the frontend folder to your local machine.
### 1️⃣ Go to project folder
```bash
cd frontend
```

### 2️⃣ Install dependencies
```bash
npm install
```

### 3️⃣ Start the development server
```bash
npm start
```

Frontend will open at:

👉 **http://localhost:3000**

Make sure [backend](backend/README.md#setup-and-run-locally) is running before testing upload, download, or delete.
---

## Improvements (Future Scope)

- Drag & drop file upload  
- Pagination for big lists  
- PDF preview in modal  
- Dark mode  
- User login  
- Global error handler  
- Animated toast messages  

These features can be added later to improve the application.
