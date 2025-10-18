import React from 'react';
import { Navbar as BootstrapNavbar, Nav, Container, Button } from 'react-bootstrap';
import { LinkContainer } from 'react-router-bootstrap';
import authService from '../services/authService';

const Navbar = () => {
  const handleLogout = () => {
    authService.logout();
  };

  return (
    <BootstrapNavbar bg="dark" variant="dark" expand="lg">
      <Container>
        <LinkContainer to="/">
          <BootstrapNavbar.Brand>HR Management System</BootstrapNavbar.Brand>
        </LinkContainer>
        <BootstrapNavbar.Toggle aria-controls="basic-navbar-nav" />
        <BootstrapNavbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <LinkContainer to="/">
              <Nav.Link>Dashboard</Nav.Link>
            </LinkContainer>
            <LinkContainer to="/companies">
              <Nav.Link>Companies</Nav.Link>
            </LinkContainer>
            <LinkContainer to="/positions">
              <Nav.Link>Positions</Nav.Link>
            </LinkContainer>
            <LinkContainer to="/candidates">
              <Nav.Link>Candidates</Nav.Link>
            </LinkContainer>
            <LinkContainer to="/interviews">
              <Nav.Link>Interviews</Nav.Link>
            </LinkContainer>
          </Nav>
          <Nav>
            <Button variant="outline-light" onClick={handleLogout}>
              Logout
            </Button>
          </Nav>
        </BootstrapNavbar.Collapse>
      </Container>
    </BootstrapNavbar>
  );
};

export default Navbar;
