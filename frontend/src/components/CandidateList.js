import React, { useState, useEffect } from 'react';
import { Table, Button, Modal, Form, Row, Col, Card, Badge, InputGroup } from 'react-bootstrap';
import { toast } from 'react-toastify';
import { candidateAPI } from '../services/api';

const CandidateList = () => {
  const [candidates, setCandidates] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [editingCandidate, setEditingCandidate] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [statusFilter, setStatusFilter] = useState('All');
  const [formData, setFormData] = useState({
    name: '',
    surname: '',
    status: 'Active',
    email: '',
    phone: ''
  });

  const statusOptions = ['All', 'Active', 'Hired', 'Rejected', 'Inactive'];

  useEffect(() => {
    loadCandidates();
  }, []);

  const loadCandidates = async () => {
    try {
      const response = await candidateAPI.getAll();
      setCandidates(response.data);
    } catch (error) {
      toast.error('Error loading candidates');
      console.error('Error loading candidates:', error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editingCandidate) {
        await candidateAPI.update(editingCandidate.idCandidate, formData);
        toast.success('Candidate updated successfully');
      } else {
        await candidateAPI.create(formData);
        toast.success('Candidate created successfully');
      }
      setShowModal(false);
      setEditingCandidate(null);
      setFormData({ name: '', surname: '', status: 'Active' });
      loadCandidates();
    } catch (error) {
      toast.error('Error saving candidate');
      console.error('Error saving candidate:', error);
    }
  };

  const handleEdit = (candidate) => {
    setEditingCandidate(candidate);
    setFormData({
      name: candidate.name,
      surname: candidate.surname,
      status: candidate.status,
      email: candidate.email || '', // Koristi prazan string ako je null
      phone: candidate.phone || ''
    });
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this candidate?')) {
      try {
        await candidateAPI.delete(id);
        toast.success('Candidate deleted successfully');
        loadCandidates();
      } catch (error) {
        toast.error('Error deleting candidate');
        console.error('Error deleting candidate:', error);
      }
    }
  };

  const handleStatusChange = async (candidate, newStatus) => {
    try {
      await candidateAPI.updateStatus(candidate.idCandidate, newStatus);
      toast.success('Candidate status updated successfully');
      loadCandidates();
    } catch (error) {
      toast.error('Error updating candidate status');
      console.error('Error updating candidate status:', error);
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setEditingCandidate(null);
    setFormData({ name: '', surname: '', status: 'Active', email: '', phone: '' });
  };

  const getStatusBadge = (status) => {
    const statusClasses = {
      'Active': 'status-active',
      'Hired': 'status-hired',
      'Rejected': 'status-rejected',
      'Inactive': 'status-inactive'
    };
    return (
      <Badge className={statusClasses[status] || 'bg-secondary'}>
        {status}
      </Badge>
    );
  };

  const filteredCandidates = candidates.filter(candidate => {
    const matchesSearch = candidate.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         candidate.surname.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesStatus = statusFilter === 'All' || candidate.status === statusFilter;
    return matchesSearch && matchesStatus;
  });

  return (
    <div>
      <Row className="mb-4">
        <Col>
          <h2>Candidates</h2>
        </Col>
        <Col md={3}>
          <InputGroup>
            <Form.Control
              type="text"
              placeholder="Search candidates..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </InputGroup>
        </Col>
        <Col md={2}>
          <Form.Select
            value={statusFilter}
            onChange={(e) => setStatusFilter(e.target.value)}
          >
            {statusOptions.map(status => (
              <option key={status} value={status}>{status}</option>
            ))}
          </Form.Select>
        </Col>
        <Col md="auto">
          <Button variant="primary" onClick={() => setShowModal(true)}>
            Add Candidate
          </Button>
        </Col>
      </Row>

      <Card>
        <Card.Body>
          <Table striped hover>
            <thead>
              <tr>
                <th>Name</th>
                <th>Surname</th>
                <th>Email</th> {/* DODATO */}
                <th>Phone</th> {/* DODATO */}
                <th>Status</th>
                <th>Interviews</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredCandidates.map((candidate) => (
                <tr key={candidate.idCandidate}>
                  <td>{candidate.name}</td>
                  <td>{candidate.surname}</td>
                  <td>{candidate.email}</td> {/* DODATO */}
                  <td>{candidate.phone}</td> {/* DODATO */}
                  <td>{getStatusBadge(candidate.status)}</td>
                  <td>{candidate.interviews?.length || 0}</td>
                  <td>
                    <Button
                      variant="outline-primary"
                      size="sm"
                      className="me-2"
                      onClick={() => handleEdit(candidate)}
                    >
                      Edit
                    </Button>
                    <div className="btn-group" role="group">
                      <Button
                        variant="outline-success"
                        size="sm"
                        className="me-1"
                        onClick={() => handleStatusChange(candidate, 'Hired')}
                        disabled={candidate.status === 'Hired'}
                      >
                        Hire
                      </Button>
                      <Button
                        variant="outline-danger"
                        size="sm"
                        className="me-1"
                        onClick={() => handleStatusChange(candidate, 'Rejected')}
                        disabled={candidate.status === 'Rejected'}
                      >
                        Reject
                      </Button>
                      <Button
                        variant="outline-secondary"
                        size="sm"
                        onClick={() => handleStatusChange(candidate, 'Inactive')}
                        disabled={candidate.status === 'Inactive'}
                      >
                        Deactivate
                      </Button>
                    </div>
                    <Button
                      variant="outline-danger"
                      size="sm"
                      className="ms-2"
                      onClick={() => handleDelete(candidate.idCandidate)}
                    >
                      Delete
                    </Button>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Card.Body>
      </Card>

      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>
            {editingCandidate ? 'Edit Candidate' : 'Add New Candidate'}
          </Modal.Title>
        </Modal.Header>
        <Form onSubmit={handleSubmit}>
          <Modal.Body>
            <Row>
              <Col md={6}>
                <Form.Group className="mb-3">
                  <Form.Label>Name</Form.Label>
                  <Form.Control
                    type="text"
                    name="name"
                    value={formData.name}
                    onChange={handleInputChange}
                    required
                  />
                </Form.Group>
              </Col>
              <Col md={6}>
                <Form.Group className="mb-3">
                  <Form.Label>Surname</Form.Label>
                  <Form.Control
                    type="text"
                    name="surname"
                    value={formData.surname}
                    onChange={handleInputChange}
                    required
                    />
                  </Form.Group>
                </Col>
              </Row>
              <Row>
                <Col md={6}>
                  <Form.Group className="mb-3">
                    <Form.Label>Email</Form.Label>
                    <Form.Control
                      type="email"
                      name="email"
                      value={formData.email}
                      onChange={handleInputChange}
                    />
                  </Form.Group>
                </Col>
                <Col md={6}>
                  <Form.Group className="mb-3">
                    <Form.Label>Phone</Form.Label>
                    <Form.Control
                      type="text"
                      name="phone"
                      value={formData.phone}
                      onChange={handleInputChange}
                  />
                </Form.Group>
              </Col>
            </Row>
            
            <Form.Group className="mb-3">
              <Form.Label>Status</Form.Label>
              <Form.Select
                name="status"
                value={formData.status}
                onChange={handleInputChange}
              >
                <option value="Active">Active</option>
                <option value="Hired">Hired</option>
                <option value="Rejected">Rejected</option>
                <option value="Inactive">Inactive</option>
              </Form.Select>
            </Form.Group>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseModal}>
              Cancel
            </Button>
            <Button variant="primary" type="submit">
              {editingCandidate ? 'Update' : 'Create'}
            </Button>
          </Modal.Footer>
        </Form>
      </Modal>
    </div>
  );
};

export default CandidateList;
