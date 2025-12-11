import axios from "axios";

const baseURL = "http://localhost:8081";

const client = axios.create({
  baseURL,
  timeout: 30000,
});

// file: FormData key must be "file"
export async function uploadFile(file, onProgress) {
  const form = new FormData();
  form.append("file", file);
  const res = await client.post("/documents/upload", form, {
    headers: { "Content-Type": "multipart/form-data" },
    onUploadProgress: (e) => {
      if (e.total) {
        const percent = Math.round((e.loaded * 100) / e.total);
        onProgress && onProgress(percent);
      }
    },
  });
  return res.data;
}

export async function listDocuments() {
  const res = await client.get("/documents");
  return res.data;
}

export async function downloadDocument(id) {
  const res = await client.get(`/documents/${id}`, { responseType: "blob" });
  return res.data;
}

export async function deleteDocument(id) {
  const res = await client.delete(`/documents/${id}`);
  return res.data;
}
