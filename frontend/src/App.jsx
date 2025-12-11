// src/App.js
import React, { useEffect, useState, useCallback } from "react";
import { Container, Row, Col, Card, Toast } from "react-bootstrap";
import UploadForm from "./components/UploadForm";
import DocumentList from "./components/DocumentList";
import { listDocuments } from "./api/api";
import "./index.css";

function App() {
  const [docs, setDocs] = useState([]);
  const [toast, setToast] = useState(null); // { message, variant }

  const fetchDocs = useCallback(async () => {
    try {
      const data = await listDocuments();
      setDocs(data);
    } catch (err) {
      console.error(err);
      showToast("Failed to load documents", "danger");
    }
  }, []);

  useEffect(() => { fetchDocs(); }, [fetchDocs]);

  function showToast(message, variant = "success") {
    setToast({ message, variant });
  }
  function hideToast() { setToast(null); }

  return (
    <div className="page-container">
      <Container>
        <Row className="justify-content-center">
          <Col xs={12} md={10} lg={8}>
            {/* center-col is relative so toast moves with scroll */}
            <div className="center-col">
              <Card className="p-4 mb-4 app-card">
                <h3>Healthcare Document Portal</h3>
                <div className="mt-3">
                  <UploadForm
                    onUploaded={() => {
                      fetchDocs();
                      showToast("Uploaded", "success");
                    }}
                  />
                </div>
              </Card>

              <Card className="p-4 app-card">
                <h4>My Documents</h4>
                {/* only this area scrolls */}
                <div className="docs-scroll mt-3">
                  <DocumentList
                    docs={docs}
                    onDeleted={() => {
                      fetchDocs();
                      showToast("Deleted", "success");
                    }}
                  />
                </div>
              </Card>

              {/* Toast placed INSIDE center-col so it scrolls with the page */}
              {toast && (
                <div className="center-toast">
                  <Toast onClose={hideToast} show autohide={false} bg={toast.variant === "success" ? "success" : "danger"}>
                    <Toast.Header closeButton>
                      <strong className="me-auto" style={{ color: toast.variant === "success" ? "#fff" : "#fff" }}>
                        {toast.variant === "success" ? "Success" : "Error"}
                      </strong>
                    </Toast.Header>
                    <Toast.Body className="text-white" autohide delay = {1000}>{toast.message}</Toast.Body>
                  </Toast>
                </div>
              )}
            </div>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default App;
