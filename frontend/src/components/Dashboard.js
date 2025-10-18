import React, { useState, useEffect } from 'react';
import { Row, Col, Card, Table, Badge } from 'react-bootstrap';
import { companyAPI, positionAPI, candidateAPI, interviewAPI } from '../services/api';

const Dashboard = () => {
  const [stats, setStats] = useState({
    companies: 0,
    positions: 0,
    openPositions: 0,
    candidates: 0,
    interviews: 0,
    scheduledInterviews: 0
  });

  const [recentInterviews, setRecentInterviews] = useState([]);

  useEffect(() => {
    loadDashboardData();
  }, []);

  const loadDashboardData = async () => {
    try {
      const [companiesRes, positionsRes, openPositionsRes, candidatesRes, interviewsRes, scheduledInterviewsRes] = await Promise.all([
        companyAPI.getAll(),
        positionAPI.getAll(),
        positionAPI.getOpen(),
        candidateAPI.getAll(),
        interviewAPI.getAll(),
        interviewAPI.getByStatus('Scheduled')
      ]);

      setStats({
        companies: companiesRes.data.length,
        positions: positionsRes.data.length,
        openPositions: openPositionsRes.data.length,
        candidates: candidatesRes.data.length,
        interviews: interviewsRes.data.length,
        scheduledInterviews: scheduledInterviewsRes.data.length
      });

      // Get recent interviews (last 5)
      const recentData = interviewsRes.data
        .sort((a, b) => new Date(b.date) - new Date(a.date))
        .slice(0, 5);
      setRecentInterviews(recentData);
    } catch (error) {
      console.error('Error loading dashboard data:', error);
    }
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

  return (
    <div>
      <h2 className="mb-4">Dashboard</h2>
      
      <Row className="mb-4">
        <Col md={2}>
          <Card className="text-center">
            <Card.Body>
              <Card.Title>{stats.companies}</Card.Title>
              <Card.Text>Companies</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col md={2}>
          <Card className="text-center">
            <Card.Body>
              <Card.Title>{stats.positions}</Card.Title>
              <Card.Text>Total Positions</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col md={2}>
          <Card className="text-center">
            <Card.Body>
              <Card.Title>{stats.openPositions}</Card.Title>
              <Card.Text>Open Positions</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col md={2}>
          <Card className="text-center">
            <Card.Body>
              <Card.Title>{stats.candidates}</Card.Title>
              <Card.Text>Candidates</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col md={2}>
          <Card className="text-center">
            <Card.Body>
              <Card.Title>{stats.interviews}</Card.Title>
              <Card.Text>Total Interviews</Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col md={2}>
          <Card className="text-center">
            <Card.Body>
              <Card.Title>{stats.scheduledInterviews}</Card.Title>
              <Card.Text>Scheduled</Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Row>
        <Col>
          <Card>
            <Card.Header>
              <h5>Recent Interviews</h5>
            </Card.Header>
            <Card.Body>
              <Table striped hover>
                <thead>
                  <tr>
                    <th>Date</th>
                    <th>Candidate</th>
                    <th>Position</th>
                    <th>Company</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  {recentInterviews.map((interview) => (
                    <tr key={interview.idInterview}>
                      <td>{new Date(interview.date).toLocaleDateString()}</td>
                      <td>{interview.candidate?.name} {interview.candidate?.surname}</td>
                      <td>{interview.position?.name}</td>
                      <td>{interview.position?.company?.name}</td>
                      <td>{getStatusBadge(interview.status)}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default Dashboard;
