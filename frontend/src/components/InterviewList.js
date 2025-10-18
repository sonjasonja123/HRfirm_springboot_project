import React, { useState, useEffect } from 'react';
import { Table, Button, Modal, Form, Row, Col, Card, Badge, InputGroup } from 'react-bootstrap';
import { toast } from 'react-toastify';
import { interviewAPI, candidateAPI, positionAPI } from '../services/api';

const InterviewList = () => {
  const [interviews, setInterviews] = useState([]);
  const [candidates, setCandidates] = useState([]);
  const [positions, setPositions] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [showRoundModal, setShowRoundModal] = useState(false);
  const [editingInterview, setEditingInterview] = useState(null);
  const [selectedInterview, setSelectedInterview] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [statusFilter, setStatusFilter] = useState('All');
  const [formData, setFormData] = useState({
    candidate: null,
    position: null,
    date: '',
    status: 'Scheduled'
  });
  const [roundFormData, setRoundFormData] = useState({
    type: 'TECHNICAL',
    number: 1
  });

  const statusOptions = ['All', 'Scheduled', 'Completed', 'Canceled'];
  const interviewTypes = ['GROUP', 'TECHNICAL', 'PSYCHOLOGICAL', 'STRUCTURAL'];

  useEffect(() => {
    loadInterviews();
    loadCandidates();
    loadPositions();
  }, []);

  const loadInterviews = async () => {
    try {
      const response = await interviewAPI.getAll();
      setInterviews(response.data);
    } catch (error) {
      toast.error('Error loading interviews');
      console.error('Error loading interviews:', error);
    }
  };

  const loadCandidates = async () => {
    try {
      const response = await candidateAPI.getAll();
      setCandidates(response.data);
    } catch (error) {
      toast.error('Error loading candidates');
      console.error('Error loading candidates:', error);
    }
  };

  const loadPositions = async () => {
    try {
      const response = await positionAPI.getAll();
      setPositions(response.data);
    } catch (error) {
      toast.error('Error loading positions');
      console.error('Error loading positions:', error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleRoundInputChange = (e) => {
    const { name, value } = e.target;
    setRoundFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleCandidateChange = (e) => {
    const candidateId = e.target.value;
    const candidate = candidates.find(c => c.idCandidate == candidateId);
    setFormData(prev => ({
      ...prev,
      candidate: candidate
    }));
  };

  const handlePositionChange = (e) => {
    const positionId = e.target.value;
    const position = positions.find(p => p.idPosition == positionId);
    setFormData(prev => ({
      ...prev,
      position: position
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editingInterview) {
        await interviewAPI.update(editingInterview.idInterview, formData);
        toast.success('Interview updated successfully');
      } else {
        await interviewAPI.create(formData);
        toast.success('Interview created successfully');
      }
      setShowModal(false);
      setEditingInterview(null);
      setFormData({ candidate: null, position: null, date: '', status: 'Scheduled' });
      loadInterviews();
    } catch (error) {
      toast.error('Error saving interview');
      console.error('Error saving interview:', error);
    }
  };

  const handleRoundSubmit = async (e) => {
    e.preventDefault();
    try {
      await interviewAPI.addRound(selectedInterview.idInterview, roundFormData.type, parseInt(roundFormData.number));
      toast.success('Interview round added successfully');
      setShowRoundModal(false);
      setRoundFormData({ type: 'TECHNICAL', number: 1 });
      loadInterviews();
    } catch (error) {
      toast.error('Error adding interview round');
      console.error('Error adding interview round:', error);
    }
  };

  const handleEdit = (interview) => {
    setEditingInterview(interview);
    setFormData({
      candidate: interview.candidate,
      position: interview.position,
      date: interview.date ? new Date(interview.date).toISOString().slice(0, 16) : '',
      status: interview.status
    });
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this interview?')) {
      try {
        await interviewAPI.delete(id);
        toast.success('Interview deleted successfully');
        loadInterviews();
      } catch (error) {
        toast.error('Error deleting interview');
        console.error('Error deleting interview:', error);
      }
    }
  };

  const handleStatusChange = async (interview, newStatus) => {
    try {
      await interviewAPI.updateStatus(interview.idInterview, newStatus);
      toast.success('Interview status updated successfully');
      loadInterviews();
    } catch (error) {
      toast.error('Error updating interview status');
      console.error('Error updating interview status:', error);
    }
  };

  const handleAddRound = (interview) => {
    setSelectedInterview(interview);
    setShowRoundModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setEditingInterview(null);
    setFormData({ candidate: null, position: null, date: '', status: 'Scheduled' });
  };

  const handleCloseRoundModal = () => {
    setShowRoundModal(false);
    setSelectedInterview(null);
    setRoundFormData({ type: 'TECHNICAL', number: 1 });
  };

  const getStatusBadge = (status) => {
    const statusClasses = {
      'Scheduled': 'interview-scheduled',
      'Completed': 'interview-completed',
      'Canceled': 'interview-canceled'
    };
    return (
      <Badge className={statusClasses[status] || 'bg-secondary'}>
        {status}
      </Badge>
    );
  };

  const filteredInterviews = interviews.filter(interview => {
    const matchesSearch = interview.candidate?.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         interview.candidate?.surname.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         interview.position?.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         interview.position?.company?.name.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesStatus = statusFilter === 'All' || interview.status === statusFilter;
    return matchesSearch && matchesStatus;
  });

  return (
    <div>
      <Row className="mb-4">
        <Col>
          <h2>Interviews</h2>
        </Col>
        <Col md={3}>
          <InputGroup>
            <Form.Control
              type="text"
              placeholder="Search interviews..."
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
            Add Interview
          </Button>
        </Col>
      </Row>

      <Card>
        <Card.Body>
          <Table striped hover>
            <thead>
              <tr>
                <th>Date</th>
                <th>Candidate</th>
                <th>Position</th>
                <th>Company</th>
                <th>Status</th>
                <th>Rounds</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredInterviews.map((interview) => (
                <tr key={interview.idInterview}>
                  <td>{new Date(interview.date).toLocaleDateString()}</td>
                  <td>{interview.candidate?.name} {interview.candidate?.surname}</td>
                  <td>{interview.position?.name}</td>
                  <td>{interview.position?.company?.name}</td>
                  <td>{getStatusBadge(interview.status)}</td>
                  <td>
                    {interview.interviewRounds?.length || 0}
                    {interview.interviewRounds?.length > 0 && (
                      <Button
                        variant="outline-info"
                        size="sm"
                        className="ms-2"
                        onClick={() => handleAddRound(interview)}
                      >
                        View
                      </Button>
                    )}
                  </td>
                  <td>
                    <Button
                      variant="outline-primary"
                      size="sm"
                      className="me-2"
                      onClick={() => handleEdit(interview)}
                    >
                      Edit
                    </Button>
                    <div className="btn-group" role="group">
                      <Button
                        variant="outline-success"
                        size="sm"
                        className="me-1"
                        onClick={() => handleStatusChange(interview, 'Completed')}
                        disabled={interview.status === 'Completed'}
                      >
                        Complete
                      </Button>
                      <Button
                        variant="outline-danger"
                        size="sm"
                        className="me-1"
                        onClick={() => handleStatusChange(interview, 'Canceled')}
                        disabled={interview.status === 'Canceled'}
                      >
                        Cancel
                      </Button>
                    </div>
                    <Button
                      variant="outline-info"
                      size="sm"
                      className="ms-2"
                      onClick={() => handleAddRound(interview)}
                    >
                      Add Round
                    </Button>
                    <Button
                      variant="outline-danger"
                      size="sm"
                      className="ms-2"
                      onClick={() => handleDelete(interview.idInterview)}
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
            {editingInterview ? 'Edit Interview' : 'Add New Interview'}
          </Modal.Title>
        </Modal.Header>
        <Form onSubmit={handleSubmit}>
          <Modal.Body>
            <Form.Group className="mb-3">
              <Form.Label>Candidate</Form.Label>
              <Form.Select
                name="candidate"
                value={formData.candidate?.idCandidate || ''}
                onChange={handleCandidateChange}
                required
              >
                <option value="">Select a candidate</option>
                {candidates.map((candidate) => (
                  <option key={candidate.idCandidate} value={candidate.idCandidate}>
                    {candidate.name} {candidate.surname}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Position</Form.Label>
              <Form.Select
                name="position"
                value={formData.position?.idPosition || ''}
                onChange={handlePositionChange}
                required
              >
                <option value="">Select a position</option>
                {positions.map((position) => (
                  <option key={position.idPosition} value={position.idPosition}>
                    {position.name} - {position.company?.name}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Date & Time</Form.Label>
              <Form.Control
                type="datetime-local"
                name="date"
                value={formData.date}
                onChange={handleInputChange}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Status</Form.Label>
              <Form.Select
                name="status"
                value={formData.status}
                onChange={handleInputChange}
              >
                <option value="Scheduled">Scheduled</option>
                <option value="Completed">Completed</option>
                <option value="Canceled">Canceled</option>
              </Form.Select>
            </Form.Group>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseModal}>
              Cancel
            </Button>
            <Button variant="primary" type="submit">
              {editingInterview ? 'Update' : 'Create'}
            </Button>
          </Modal.Footer>
        </Form>
      </Modal>

      <Modal show={showRoundModal} onHide={handleCloseRoundModal}>
        <Modal.Header closeButton>
          <Modal.Title>Add Interview Round</Modal.Title>
        </Modal.Header>
        <Form onSubmit={handleRoundSubmit}>
          <Modal.Body>
            <Form.Group className="mb-3">
              <Form.Label>Interview Type</Form.Label>
              <Form.Select
                name="type"
                value={roundFormData.type}
                onChange={handleRoundInputChange}
                required
              >
                {interviewTypes.map(type => (
                  <option key={type} value={type}>{type}</option>
                ))}
              </Form.Select>
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Round Number</Form.Label>
              <Form.Control
                type="number"
                name="number"
                value={roundFormData.number}
                onChange={handleRoundInputChange}
                min="1"
                required
              />
            </Form.Group>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseRoundModal}>
              Cancel
            </Button>
            <Button variant="primary" type="submit">
              Add Round
            </Button>
          </Modal.Footer>
        </Form>
      </Modal>
    </div>
  );
};

export default InterviewList;
