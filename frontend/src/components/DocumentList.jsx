// src/components/DocumentList.jsx
import React, { useState } from "react";
import { Table, Button, Spinner } from "react-bootstrap";
import { readableFileSize } from "../utils/format";
import { downloadDocument, deleteDocument } from "../api/api";
import ConfirmModal from "./ConfirmModal";
import { FiFileText, FiDownload, FiTrash2 } from "react-icons/fi";

export default function DocumentList({ docs, onDeleted }) {
  const [loadingId, setLoadingId] = useState(null);
  const [deleteId, setDeleteId] = useState(null);
  const [busy, setBusy] = useState(false);

  async function handleDownload(d) {
    setLoadingId(d.id);
    try {
      const blob = await downloadDocument(d.id);
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = d.name || "document.pdf";
      document.body.appendChild(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(url);
    } catch (err) {
      alert("Download failed");
    } finally {
      setLoadingId(null);
    }
  }

  async function confirmDelete() {
    if (deleteId == null) return;
    setBusy(true);
    try {
      await deleteDocument(deleteId);
      onDeleted && onDeleted();
    } catch (err) {
      alert(err?.response?.data?.message || "Delete failed");
    } finally {
      setBusy(false);
      setDeleteId(null);
    }
  }

  if (!docs || docs.length === 0) {
    return (
      <div className="p-4 text-center text-muted">
        <div style={{ fontSize: 88, opacity: 0.18 }}><FiFileText /></div>
        <div style={{ marginTop: 12, fontSize: 18 }}>No documents uploaded yet</div>
      </div>
    );
  }

  return (
    <>
      <Table responsive hover className="mb-0">
        <thead>
        <tr>
            <th>#</th>
            <th className="col-filename">File name</th>
            <th>Size</th>
            <th>Uploaded At</th>
            <th className="actions-col">Actions</th>
        </tr>
        </thead>

        <tbody>
          {docs.map((d, idx) => (
            <tr key={d.id}>
              <td>{idx + 1}</td>
              <td className="col-filename" title={d.filename}>{d.filename}</td>
              <td>{readableFileSize(d.filesize)}</td>
              <td>{d.created_at ? new Date(d.created_at).toLocaleString() : ""}</td>
              <td style={{ whiteSpace: "nowrap" }}>
                <Button
                  size="sm"
                  variant="outline-primary"
                  onClick={() => handleDownload(d)}
                  disabled={loadingId === d.id}
                  className="action-btn me-2"
                >
                  {loadingId === d.id ? <Spinner as="span" animation="border" size="sm" /> : <><FiDownload /> </>}
                </Button>

                <Button
                  size="sm"
                  variant="outline-danger"
                  onClick={() => setDeleteId(d.id)}
                  disabled={busy}
                  className="action-btn"
                >
                  <FiTrash2 /> 
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>

      <ConfirmModal
        show={deleteId != null}
        title="Delete document"
        message="Are you sure you want to delete this document? This action cannot be undone."
        onCancel={() => setDeleteId(null)}
        onConfirm={confirmDelete}
        confirmLabel="Delete"
      />
    </>
  );
}
