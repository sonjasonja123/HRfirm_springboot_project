import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Button, Table, Modal, Form } from 'react-bootstrap';
import { toast } from 'react-toastify';
import { FaPlus, FaEdit, FaTrash, FaBriefcase } from 'react-icons/fa';
import authService from '../services/authService';
import backgroundImage from '../assets/images/pozadinadash.jpg';

const CompanyDashboard = () => {
    const [positions, setPositions] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [editingPosition, setEditingPosition] = useState(null);
    const [formData, setFormData] = useState({
        title: '',
        description: ''
    });
    const [companyInfo, setCompanyInfo] = useState(null);

    useEffect(() => {
        loadCompanyInfo();
        loadPositions();
    }, []);

    const loadCompanyInfo = () => {
        const user = authService.getCurrentUser();
        setCompanyInfo(user);
    };

    const loadPositions = async () => {
        try {
            const user = authService.getCurrentUser();
            if (!user) return;
            
            const response = await authService.get(`/company/positions?username=${user.username}&password=${user.password}`);
            setPositions(response.data);
        } catch (error) {
            console.error('Error loading positions:', error);
            toast.error('Greška pri učitavanju pozicija');
        }
    };

    const handleShowModal = (position = null) => {
        if (position) {
            setEditingPosition(position);
            setFormData({
                title: position.name,
                description: position.details
            });
        } else {
            setEditingPosition(null);
            setFormData({ title: '', description: '' });
        }
        setShowModal(true);
    };

    const handleCloseModal = () => {
        setShowModal(false);
        setEditingPosition(null);
        setFormData({ title: '', description: '' });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (editingPosition) {
                await authService.put(`/company/positions/${editingPosition.idPosition}`, formData);
                toast.success('Pozicija uspešno ažurirana!');
            } else {
                const user = authService.getCurrentUser();
                await authService.post('/company/postPosition', {
                    username: user.username,
                    password: user.password,
                    title: formData.title,
                    description: formData.description
                });
                toast.success('Pozicija uspešno kreirana!');
            }
            handleCloseModal();
            loadPositions();
        } catch (error) {
            console.error('Error saving position:', error);
            toast.error('Greška pri čuvanju pozicije');
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Da li ste sigurni da želite da obrišete ovu poziciju?')) {
            try {
                await authService.delete(`/positions/${id}`);
                toast.success('Pozicija uspešno obrisana!');
                loadPositions();
            } catch (error) {
                console.error('Error deleting position:', error);
                toast.error('Greška pri brisanju pozicije');
            }
        }
    };

    return (
        <div 
            style={{ 
                backgroundImage: `url(${backgroundImage})`,
                backgroundSize: 'cover',
                backgroundPosition: 'center',
                minHeight: '100vh',
                paddingTop: '20px',
                paddingBottom: '20px'
            }}
        >
            <Container>
                <Row className="mb-4">
                    <Col>
                        <Card className="shadow" style={{ backgroundColor: 'rgba(255, 255, 255, 0.95)' }}>
                            <Card.Body>
                                <h2 className="text-primary">
                                    <FaBriefcase className="me-2" />
                                    Dobrodošli, {companyInfo?.name || 'Kompanija'}
                                </h2>
                                <p className="text-muted mb-0">
                                    Upravljajte vašim pozicijama i pregledajte kandidate
                                </p>
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>

                <Row className="mb-3">
                    <Col>
                        <Button 
                            variant="primary" 
                            size="lg"
                            onClick={() => handleShowModal()}
                        >
                            <FaPlus className="me-2" />
                            Dodaj novu poziciju
                        </Button>
                    </Col>
                </Row>

                <Row>
                    <Col>
                        <Card className="shadow" style={{ backgroundColor: 'rgba(255, 255, 255, 0.95)' }}>
                            <Card.Header as="h5" className="bg-primary text-white">
                                Vaše pozicije
                            </Card.Header>
                            <Card.Body>
                                {positions.length === 0 ? (
                                    <div className="text-center py-5">
                                        <p className="text-muted">Trenutno nemate postavljenih pozicija.</p>
                                        <Button variant="primary" onClick={() => handleShowModal()}>
                                            Dodaj prvu poziciju
                                        </Button>
                                    </div>
                                ) : (
                                    <Table striped bordered hover responsive>
                                        <thead>
                                            <tr>
                                                <th>Naziv</th>
                                                <th>Detalji</th>
                                                <th>Status</th>
                                                <th>Datum od</th>
                                                <th>Datum do</th>
                                                <th>Akcije</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {positions.map((position) => (
                                                <tr key={position.idPosition}>
                                                    <td><strong>{position.name}</strong></td>
                                                    <td>{position.details}</td>
                                                    <td>
                                                        <span className={`badge ${position.open ? 'bg-success' : 'bg-secondary'}`}>
                                                            {position.open ? 'Otvorena' : 'Zatvorena'}
                                                        </span>
                                                    </td>
                                                    <td>{position.dateFrom || 'N/A'}</td>
                                                    <td>{position.dateTo || 'N/A'}</td>
                                                    <td>
                                                        <Button 
                                                            variant="warning" 
                                                            size="sm" 
                                                            className="me-2"
                                                            onClick={() => handleShowModal(position)}
                                                        >
                                                            <FaEdit />
                                                        </Button>
                                                        <Button 
                                                            variant="danger" 
                                                            size="sm"
                                                            onClick={() => handleDelete(position.idPosition)}
                                                        >
                                                            <FaTrash />
                                                        </Button>
                                                    </td>
                                                </tr>
                                            ))}
                                        </tbody>
                                    </Table>
                                )}
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>

                {/* Modal for Add/Edit Position */}
                <Modal show={showModal} onHide={handleCloseModal} size="lg">
                    <Modal.Header closeButton>
                        <Modal.Title>
                            {editingPosition ? 'Izmeni poziciju' : 'Dodaj novu poziciju'}
                        </Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form onSubmit={handleSubmit}>
                            <Form.Group className="mb-3">
                                <Form.Label>Naziv pozicije</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="npr. Senior Java Developer"
                                    value={formData.title}
                                    onChange={(e) => setFormData({ ...formData, title: e.target.value })}
                                    required
                                />
                            </Form.Group>

                            <Form.Group className="mb-3">
                                <Form.Label>Opis pozicije</Form.Label>
                                <Form.Control
                                    as="textarea"
                                    rows={5}
                                    placeholder="Opišite zahteve i odgovornosti za ovu poziciju..."
                                    value={formData.description}
                                    onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                                    required
                                />
                            </Form.Group>

                            <div className="d-flex justify-content-end gap-2">
                                <Button variant="secondary" onClick={handleCloseModal}>
                                    Otkaži
                                </Button>
                                <Button variant="primary" type="submit">
                                    {editingPosition ? 'Sačuvaj izmene' : 'Kreiraj poziciju'}
                                </Button>
                            </div>
                        </Form>
                    </Modal.Body>
                </Modal>
            </Container>
        </div>
    );
};

export default CompanyDashboard;

