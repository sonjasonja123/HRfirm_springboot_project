import React from 'react';
import { Container, Row, Col, Card, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { FaBuilding, FaUser } from 'react-icons/fa';
import backgroundImage from '../assets/images/pozadina.jpg';

const LoginSelection = () => {
    const navigate = useNavigate();

    return (
        <div 
            className="login-selection-page" 
            style={{ 
                backgroundImage: `url(${backgroundImage})`,
                backgroundSize: 'cover',
                backgroundPosition: 'center',
                minHeight: '100vh',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center'
            }}
        >
            <Container>
                <Row className="justify-content-center">
                    <Col md={10} lg={8}>
                        <div className="text-center mb-5">
                            <h1 className="text-white mb-3" style={{ textShadow: '2px 2px 4px rgba(0,0,0,0.5)' }}>
                                Dobrodošli u HR Management System
                            </h1>
                            <p className="text-white" style={{ textShadow: '1px 1px 2px rgba(0,0,0,0.5)', fontSize: '1.2rem' }}>
                                Molimo izaberite tip naloga
                            </p>
                        </div>
                        <Row>
                            <Col md={6} className="mb-4">
                                <Card 
                                    className="text-center h-100 shadow-lg hover-card"
                                    style={{ 
                                        cursor: 'pointer',
                                        transition: 'transform 0.3s ease',
                                        backgroundColor: 'rgba(255, 255, 255, 0.95)'
                                    }}
                                    onClick={() => navigate('/login/company')}
                                    onMouseEnter={(e) => e.currentTarget.style.transform = 'scale(1.05)'}
                                    onMouseLeave={(e) => e.currentTarget.style.transform = 'scale(1)'}
                                >
                                    <Card.Body className="d-flex flex-column align-items-center justify-content-center p-5">
                                        <FaBuilding size={80} className="text-primary mb-4" />
                                        <Card.Title as="h3" className="mb-3">Kompanija</Card.Title>
                                        <Card.Text className="mb-4">
                                            Prijavite se kao kompanija da biste upravljali pozicijama i pregledali kandidate
                                        </Card.Text>
                                        <Button variant="primary" size="lg">
                                            Login za Klijenta
                                        </Button>
                                    </Card.Body>
                                </Card>
                            </Col>
                            <Col md={6} className="mb-4">
                                <Card 
                                    className="text-center h-100 shadow-lg hover-card"
                                    style={{ 
                                        cursor: 'pointer',
                                        transition: 'transform 0.3s ease',
                                        backgroundColor: 'rgba(255, 255, 255, 0.95)'
                                    }}
                                    onClick={() => navigate('/login/worker')}
                                    onMouseEnter={(e) => e.currentTarget.style.transform = 'scale(1.05)'}
                                    onMouseLeave={(e) => e.currentTarget.style.transform = 'scale(1)'}
                                >
                                    <Card.Body className="d-flex flex-column align-items-center justify-content-center p-5">
                                        <FaUser size={80} className="text-success mb-4" />
                                        <Card.Title as="h3" className="mb-3">Radnik</Card.Title>
                                        <Card.Text className="mb-4">
                                            Prijavite se kao radnik da biste upravljali kandidatima, intervjuima i pozicijama
                                        </Card.Text>
                                        <Button variant="success" size="lg">
                                            Login za Radnika
                                        </Button>
                                    </Card.Body>
                                </Card>
                            </Col>
                        </Row>
                    </Col>
                </Row>
            </Container>
        </div>
    );
};

export default LoginSelection;

