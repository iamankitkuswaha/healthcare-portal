import React, { useState } from "react";
import { Button, Form, ProgressBar, Alert } from "react-bootstrap";
import { uploadFile } from "../api/api";
import { readableFileSize } from "../utils/format";

const MAX_BYTES = 10 * 1024 * 1024;

export default function UploadForm({ onUploaded }) {
  const [file, setFile] = useState(null);
  const [progress, setProgress] = useState(0);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  function onFileChange(e) {
    setError(null);
    const f = e.target.files?.[0] ?? null;
    if (!f) { setFile(null); return; }
    if (!f.name.toLowerCase().endsWith(".pdf")) {
      setError("Only PDF files are allowed.");
      setFile(null);
      return;
    }
    if (f.size > MAX_BYTES) {
      setError(`File too large. Max allowed: ${readableFileSize(MAX_BYTES)}`);
      setFile(null);
      return;
    }
    setFile(f);
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setError(null);
    if (!file) { setError("Choose a file first"); return; }
    setLoading(true);
    try {
      await uploadFile(file, (p) => setProgress(p));
      setProgress(0);
      setFile(null);
      onUploaded();
    } catch (err) {
      setError(err?.response?.data?.message || err.message || "Upload failed");
    } finally {
      setLoading(false);
      setProgress(0);
      // clear input value
      const input = document.getElementById("file-input");
      if (input) input.value = "";
    }
  }

  return (
    <form onSubmit={handleSubmit}>
      {error && <Alert variant="danger">{error}</Alert>}
      <Form.Group>
        <Form.Label>Upload Document (PDF only, max {readableFileSize(MAX_BYTES)})</Form.Label>
        <Form.Control id="file-input" type="file" accept="application/pdf" onChange={onFileChange} />
      </Form.Group>

      {progress > 0 && <ProgressBar now={progress} label={`${progress}%`} className="mt-2" />}
      
       <div className="mt-3 d-grid gap-2">
      <Button variant="primary" size="lg" type="submit" disabled={!file || loading}>
        {loading ? "Uploading..." : "Upload Document"}
      </Button>
    </div>
    </form>
  );
}
