import React, { useState, useEffect } from 'react';
import { Table, Button, Modal, Form, Row, Col, Card, Badge, InputGroup } from 'react-bootstrap';
import { toast } from 'react-toastify';
import { companyAPI } from '../services/api';

const CompanyList = () => {
  const [companies, setCompanies] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [editingCompany, setEditingCompany] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [formData, setFormData] = useState({
    name: '',
    industry: '',
    pib: '',
    contact: ''
  });

  useEffect(() => {
    loadCompanies();
  }, []);

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
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editingCompany) {
        await companyAPI.update(editingCompany.idCompany, formData);
        toast.success('Company updated successfully');
      } else {
        await companyAPI.create(formData);
        toast.success('Company created successfully');
      }
      setShowModal(false);
      setEditingCompany(null);
      setFormData({ name: '', industry: '', pib: '', contact: '' });
      loadCompanies();
    } catch (error) {
      toast.error('Error saving company');
      console.error('Error saving company:', error);
    }
  };

  const handleEdit = (company) => {
    setEditingCompany(company);
    setFormData({
      name: company.name,
      industry: company.industry,
      pib: company.pib || '',
      contact: company.contact || ''
    });
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this company?')) {
      try {
        await companyAPI.delete(id);
        toast.success('Company deleted successfully');
        loadCompanies();
      } catch (error) {
        toast.error('Error deleting company');
        console.error('Error deleting company:', error);
      }
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setEditingCompany(null);
    setFormData({ name: '', industry: '', pib: '', contact: '' });
  };

  // ISPRAVKA JE OVDE: Dodat je `?` operator da spreči grešku ako je neko polje `null` ili `undefined`
  const filteredCompanies = companies.filter(company =>
    company.name?.toLowerCase().includes(searchTerm.toLowerCase()) ||
    company.industry?.toLowerCase().includes(searchTerm.toLowerCase()) ||
    company.pib?.toLowerCase().includes(searchTerm.toLowerCase()) ||
    company.contact?.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div>
      <Row className="mb-4">
        <Col>
          <h2>Companies</h2>
        </Col>
        <Col md={4}>
          <InputGroup>
            <Form.Control
              type="text"
              placeholder="Search companies (Name, Industry, PIB, Contact)..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </InputGroup>
        </Col>
        <Col md="auto">
          <Button variant="primary" onClick={() => setShowModal(true)}>
            Add Company
          </Button>
        </Col>
      </Row>

      <Card>
        <Card.Body>
          <Table striped hover>
            <thead>
              <tr>
                <th>Name</th>
                <th>Industry</th>
                <th className="col-pib">PIB</th>
                <th>Contact</th>
                <th className="col-positions">Positions</th>
                <th className="col-actions">Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredCompanies.map((company) => (
                <tr key={company.idCompany}>
                  <td>{company.name}</td>
                  <td>
                    <Badge bg="info">{company.industry}</Badge>
                  </td>
                  <td className="col-pib">{company.pib}</td>
                  <td>{company.contact}</td>
                  <td className="col-positions">{company.positions?.length || 0}</td>
                  <td className="col-actions">
                    <Button
                      variant="outline-primary"
                      size="sm"
                      className="me-2"
                      onClick={() => handleEdit(company)}
                    >
                      Edit
                    </Button>
                    <Button
                      variant="outline-danger"
                      size="sm"
                      onClick={() => handleDelete(company.idCompany)}
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
            {editingCompany ? 'Edit Company' : 'Add New Company'}
          </Modal.Title>
        </Modal.Header>
        <Form onSubmit={handleSubmit}>
          <Modal.Body>
            <Form.Group className="mb-3">
              <Form.Label>Company Name</Form.Label>
              <Form.Control
                type="text"
                name="name"
                value={formData.name}
                onChange={handleInputChange}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Industry</Form.Label>
              <Form.Control
                type="text"
                name="industry"
                value={formData.industry}
                onChange={handleInputChange}
                required
              />
            </Form.Group>
            <Row>
              <Col md={6}>
                <Form.Group className="mb-3">
                  <Form.Label>PIB</Form.Label>
                  <Form.Control
                    type="text"
                    name="pib"
                    value={formData.pib}
                    onChange={handleInputChange}
                  />
                </Form.Group>
              </Col>
              <Col md={6}>
                <Form.Group className="mb-3">
                  <Form.Label>Contact Person/Phone</Form.Label>
                  <Form.Control
                    type="text"
                    name="contact"
                    value={formData.contact}
                    onChange={handleInputChange}
                  />
                </Form.Group>
              </Col>
            </Row>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseModal}>
              Cancel
            </Button>
            <Button variant="primary" type="submit">
              {editingCompany ? 'Update' : 'Create'}
            </Button>
          </Modal.Footer>
        </Form>
      </Modal>
    </div>
  );
};

export default CompanyList;