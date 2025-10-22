import React from 'react';
import { Navbar as BootstrapNavbar, Nav, Container, Button } from 'react-bootstrap';
import { LinkContainer } from 'react-router-bootstrap';
import { useLocation } from 'react-router-dom';
import authService from '../services/authService';

const Navbar = () => {
  const location = useLocation();
  const isAuthenticated = authService.isAuthenticated();
  const userType = authService.getUserType();
  const currentUser = authService.getCurrentUser();
  
  const handleLogout = () => {
    authService.logout();
  };

  // Ne prikazuj navbar na login stranicama
  if (location.pathname.includes('/login')) {
    return null;
  }

  return (
    <BootstrapNavbar bg="dark" variant="dark" expand="lg">
      <Container>
        <LinkContainer to={userType === 'company' ? '/company/dashboard' : '/'}>
          <BootstrapNavbar.Brand>HR Management System</BootstrapNavbar.Brand>
        </LinkContainer>
        <BootstrapNavbar.Toggle aria-controls="basic-navbar-nav" />
        <BootstrapNavbar.Collapse id="basic-navbar-nav">
          {isAuthenticated && (
            <>
              <Nav className="me-auto">
                {userType === 'company' ? (
                  // Navbar za kompaniju
                  <LinkContainer to="/company/dashboard">
                    <Nav.Link>Moje Pozicije</Nav.Link>
                  </LinkContainer>
                ) : (
                  // Navbar za radnika
                  <>
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
                  </>
                )}
              </Nav>
              <Nav>
                <span className="navbar-text text-light me-3">
                  {userType === 'company' 
                    ? currentUser?.name 
                    : `${currentUser?.name} ${currentUser?.surname || ''}`}
                </span>
                <Button variant="outline-light" onClick={handleLogout}>
                  Logout
                </Button>
              </Nav>
            </>
          )}
        </BootstrapNavbar.Collapse>
      </Container>
    </BootstrapNavbar>
  );
};

export default Navbar;
