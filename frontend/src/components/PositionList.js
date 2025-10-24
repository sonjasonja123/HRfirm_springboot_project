import React, { useState, useEffect } from 'react';
import { Table, Button, Modal, Form, Row, Col, Card, Badge, InputGroup } from 'react-bootstrap';
import { toast } from 'react-toastify';
import { positionAPI, companyAPI } from '../services/api';

const PositionList = () => {
  const [positions, setPositions] = useState([]);
  const [companies, setCompanies] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [editingPosition, setEditingPosition] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [showOpenOnly, setShowOpenOnly] = useState(false);
  const [formData, setFormData] = useState({
    name: '',
    details: '',
    open: true,
    company: null,
    dateFrom: '',
    dateTo: ''
  });

  useEffect(() => {
    loadPositions();
    loadCompanies();
  }, []);

  const loadPositions = async () => {
    try {
      const response = await positionAPI.getAll();
      setPositions(response.data);
    } catch (error) {
      toast.error('Error loading positions');
      console.error('Error loading positions:', error);
    }
  };

  const loadCompanies = async () => {
    try {
      const response = await companyAPI.getAll();
      setCompanies(response.data);
    } catch (error) {
      toast.error('Error loading companies');
      console.error('Error loading companies:', error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handleCompanyChange = (e) => {
    const companyId = e.target.value;
    const company = companies.find(c => c.idCompany == companyId);
    setFormData(prev => ({
      ...prev,
      company: company
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editingPosition) {
        await positionAPI.update(editingPosition.idPosition, formData);
        toast.success('Position updated successfully');
      } else {
        await positionAPI.create(formData);
        toast.success('Position created successfully');
      }
      setShowModal(false);
      setEditingPosition(null);
      setFormData({ name: '', details: '', open: true, company: null, dateFrom: '', dateTo: '' });
      loadPositions();
    } catch (error) {
      toast.error('Error saving position');
      console.error('Error saving position:', error);
    }
  };

  const handleEdit = (position) => {
    setEditingPosition(position);
    setFormData({
      name: position.name,
      details: position.details,
      open: position.open,
      company: position.company,
      dateFrom: position.dateFrom || '',
      dateTo: position.dateTo || ''
    });
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this position?')) {
      try {
        await positionAPI.delete(id);
        toast.success('Position deleted successfully');
        loadPositions();
      } catch (error) {
        toast.error('Error deleting position');
        console.error('Error deleting position:', error);
      }
    }
  };

  const handleToggleStatus = async (position) => {
    try {
      if (position.open) {
        await positionAPI.close(position.idPosition);
        toast.success('Position closed successfully');
      } else {
        await positionAPI.open(position.idPosition);
        toast.success('Position opened successfully');
      }
      loadPositions();
    } catch (error) {
      toast.error('Error updating position status');
      console.error('Error updating position status:', error);
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setEditingPosition(null);
    setFormData({ name: '', details: '', open: true, company: null, dateFrom: '', dateTo: '' });
  };

  const filteredPositions = positions.filter(position => {
    const matchesSearch = position.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         position.details.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         position.company?.name.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesStatus = !showOpenOnly || position.open;
    return matchesSearch && matchesStatus;
  });

  return (
    <div>
      <Row className="mb-4">
        <Col>
          <h2>Positions</h2>
        </Col>
        <Col md={4}>
          <InputGroup>
            <Form.Control
              type="text"
              placeholder="Search positions..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </InputGroup>
        </Col>
        <Col md="auto">
          <Form.Check
            type="checkbox"
            label="Open positions only"
            checked={showOpenOnly}
            onChange={(e) => setShowOpenOnly(e.target.checked)}
            className="me-3"
          />
          <Button variant="primary" onClick={() => setShowModal(true)}>
            Add Position
          </Button>
        </Col>
      </Row>

      <Card>
        <Card.Body>
          <Table striped hover>
            <thead>
              <tr>
                <th>Name</th>
                <th>Company</th>
                <th>Details</th>
                <th>Datum Od</th>
                <th>Datum Do</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredPositions.map((position) => (
                <tr key={position.idPosition}>
                  <td>{position.name}</td>
                  <td>{position.company?.name}</td>
                  <td>{position.details.length > 50 ? position.details.substring(0, 50) + '...' : position.details}</td>
                  <td>{position.dateFrom || 'N/A'}</td>
                  <td>{position.dateTo || 'N/A'}</td>
                  <td>
                    <Badge bg={position.open ? 'success' : 'secondary'}>
                      {position.open ? 'Open' : 'Closed'}
                    </Badge>
                  </td>
                  <td>
                    <Button
                      variant="outline-primary"
                      size="sm"
                      className="me-2"
                      onClick={() => handleEdit(position)}
                    >
                      Edit
                    </Button>
                    <Button
                      variant={position.open ? 'outline-warning' : 'outline-success'}
                      size="sm"
                      className="me-2"
                      onClick={() => handleToggleStatus(position)}
                    >
                      {position.open ? 'Close' : 'Open'}
                    </Button>
                    <Button
                      variant="outline-danger"
                      size="sm"
                      onClick={() => handleDelete(position.idPosition)}
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

      <Modal show={showModal} onHide={handleCloseModal} size="lg">
        <Modal.Header closeButton>
          <Modal.Title>
            {editingPosition ? 'Edit Position' : 'Add New Position'}
          </Modal.Title>
        </Modal.Header>
        <Form onSubmit={handleSubmit}>
          <Modal.Body>
            <Form.Group className="mb-3">
              <Form.Label>Position Name</Form.Label>
              <Form.Control
                type="text"
                name="name"
                value={formData.name}
                onChange={handleInputChange}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Company</Form.Label>
              <Form.Select
                name="company"
                value={formData.company?.idCompany || ''}
                onChange={handleCompanyChange}
                required
              >
                <option value="">Select a company</option>
                {companies.map((company) => (
                  <option key={company.idCompany} value={company.idCompany}>
                    {company.name}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Details</Form.Label>
              <Form.Control
                as="textarea"
                rows={3}
                name="details"
                value={formData.details}
                onChange={handleInputChange}
                required
              />
            </Form.Group>
            <Row>
              <Col md={6}>
                <Form.Group className="mb-3">
                  <Form.Label>Datum Od (Date From)</Form.Label>
                  <Form.Control
                    type="date"
                    name="dateFrom"
                    value={formData.dateFrom}
                    onChange={handleInputChange}
                  />
                  <Form.Text className="text-muted">
                    Pozicija će biti zatvorena pre ovog datuma
                  </Form.Text>
                </Form.Group>
              </Col>
              <Col md={6}>
                <Form.Group className="mb-3">
                  <Form.Label>Datum Do (Date To)</Form.Label>
                  <Form.Control
                    type="date"
                    name="dateTo"
                    value={formData.dateTo}
                    onChange={handleInputChange}
                  />
                  <Form.Text className="text-muted">
                    Pozicija će biti zatvorena posle ovog datuma
                  </Form.Text>
                </Form.Group>
              </Col>
            </Row>
            <Form.Group className="mb-3">
              <Form.Check
                type="checkbox"
                name="open"
                label="Position is open (automatski se ažurira na osnovu datuma)"
                checked={formData.open}
                onChange={handleInputChange}
              />
            </Form.Group>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseModal}>
              Cancel
            </Button>
            <Button variant="primary" type="submit">
              {editingPosition ? 'Update' : 'Create'}
            </Button>
          </Modal.Footer>
        </Form>
      </Modal>
    </div>
  );
};

export default PositionList;
