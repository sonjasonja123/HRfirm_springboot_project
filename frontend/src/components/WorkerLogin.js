import React, { useState, useEffect } from 'react';
import { Form, Button, Card, Container } from 'react-bootstrap';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import authService from '../services/authService';
import backgroundImage from '../assets/images/pozadina.jpg';
import { FaUser, FaArrowLeft } from 'react-icons/fa';

const WorkerLogin = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        if (authService.isAuthenticated() && authService.getUserType() === 'worker') {
            navigate('/');
        }
    }, [navigate]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await authService.loginWorker(username, password);
            toast.success('Uspešna prijava!');
            navigate('/');
        } catch (error) {
            toast.error('Neuspešna prijava. Proverite kredencijale.');
            console.error('Login error:', error);
        }
    };

    return (
        <div 
            className="login-page-wrapper" 
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
            <Container style={{ maxWidth: '450px' }}>
                <Button 
                    variant="link" 
                    className="text-white mb-3"
                    onClick={() => navigate('/login')}
                    style={{ textDecoration: 'none', textShadow: '1px 1px 2px rgba(0,0,0,0.5)' }}
                >
                    <FaArrowLeft /> Nazad na izbor
                </Button>
                <Card className="shadow-lg" style={{ backgroundColor: 'rgba(255, 255, 255, 0.95)' }}>
                    <Card.Header as="h5" className="bg-success text-white text-center py-3">
                        <FaUser className="me-2" />
                        Login za Radnika
                    </Card.Header>
                    <Card.Body className="p-4">
                        <Form onSubmit={handleSubmit}>
                            <Form.Group className="mb-3" controlId="formBasicUsername">
                                <Form.Label>Korisničko ime</Form.Label>
                                <Form.Control 
                                    type="text" 
                                    placeholder="Unesite korisničko ime" 
                                    value={username}
                                    onChange={(e) => setUsername(e.target.value)}
                                    required
                                />
                            </Form.Group>
    
                            <Form.Group className="mb-3" controlId="formBasicPassword">
                                <Form.Label>Lozinka</Form.Label>
                                <Form.Control 
                                    type="password" 
                                    placeholder="Unesite lozinku" 
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    required
                                />
                            </Form.Group>
    
                            <Button variant="success" type="submit" className="w-100 py-2">
                                Prijavi se
                            </Button>
                        </Form>
                        <div className="mt-3 text-muted text-center">
                            <small>Test nalog: worker1 / worker123</small>
                        </div>
                    </Card.Body>
                </Card>
            </Container>
        </div>
    );
};

export default WorkerLogin;

