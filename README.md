# HR Management System

A comprehensive web application for managing HR processes including companies, positions, candidates, and interviews. Built with Spring Boot backend and React frontend.

## Features

- **Company Management**: Create, read, update, and delete companies with industry information
- **Position Management**: Manage job positions with details and open/closed status
- **Candidate Management**: Track candidates with status updates (Active, Hired, Rejected, Inactive)
- **Interview Management**: Schedule and manage interviews with multiple rounds
- **Interview Rounds**: Support for different interview types (Group, Technical, Psychological, Structural)
- **Search and Filtering**: Advanced search capabilities across all entities
- **Dashboard**: Overview of key metrics and recent activities

## Technology Stack

### Backend
- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- Spring Security
- H2 Database (for development)
- Maven

### Frontend
- React 18
- React Router DOM
- React Bootstrap
- Axios
- React Toastify

## Prerequisites

- Java 17 or higher
- Node.js 16 or higher
- Maven 3.6 or higher

## Installation and Setup

### Backend Setup

1. Navigate to the project root directory
2. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
3. The backend will start on `http://localhost:8080`
4. H2 Database console is available at `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:testdb`
   - Username: `sa`
   - Password: `password`

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```

4. The frontend will start on `http://localhost:3000`

## API Endpoints

### Companies
- `GET /api/companies` - Get all companies
- `GET /api/companies/{id}` - Get company by ID
- `POST /api/companies` - Create new company
- `PUT /api/companies/{id}` - Update company
- `DELETE /api/companies/{id}` - Delete company
- `GET /api/companies/search/name?name={name}` - Search companies by name
- `GET /api/companies/search/industry?industry={industry}` - Search companies by industry

### Positions
- `GET /api/positions` - Get all positions
- `GET /api/positions/open` - Get open positions
- `GET /api/positions/closed` - Get closed positions
- `GET /api/positions/{id}` - Get position by ID
- `POST /api/positions` - Create new position
- `PUT /api/positions/{id}` - Update position
- `DELETE /api/positions/{id}` - Delete position
- `PUT /api/positions/{id}/close` - Close position
- `PUT /api/positions/{id}/open` - Open position

### Candidates
- `GET /api/candidates` - Get all candidates
- `GET /api/candidates/{id}` - Get candidate by ID
- `POST /api/candidates` - Create new candidate
- `PUT /api/candidates/{id}` - Update candidate
- `DELETE /api/candidates/{id}` - Delete candidate
- `PUT /api/candidates/{id}/status?status={status}` - Update candidate status

### Interviews
- `GET /api/interviews` - Get all interviews
- `GET /api/interviews/{id}` - Get interview by ID
- `POST /api/interviews` - Create new interview
- `PUT /api/interviews/{id}` - Update interview
- `DELETE /api/interviews/{id}` - Delete interview
- `POST /api/interviews/{interviewId}/rounds` - Add interview round
- `GET /api/interviews/{interviewId}/rounds` - Get interview rounds

## Database Schema

The application uses the following entities:
- **Company**: idCompany, name, industry
- **Position**: idPosition, name, details, open, company
- **Candidate**: idCandidate, name, surname, status
- **Interview**: idInterview, date, status, candidate, position
- **InterviewRound**: idInterviewRound, type, number, interview
- **InterviewType** (Enum): GROUP, TECHNICAL, PSYCHOLOGICAL, STRUCTURAL

## Usage

1. **Companies**: Add companies that will post job positions
2. **Positions**: Create job positions for companies
3. **Candidates**: Register candidates who can apply for positions
4. **Interviews**: Schedule interviews between candidates and positions
5. **Interview Rounds**: Add different types of interview rounds to each interview

## Development

### Backend Development
- The application follows the Repository-Service-Controller pattern
- All entities are properly validated using Bean Validation
- CORS is configured to allow frontend communication
- H2 database is used for development (can be easily switched to PostgreSQL/MySQL)

### Frontend Development
- React components are organized by feature
- Bootstrap is used for styling
- Axios is used for API communication
- Toast notifications provide user feedback

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License.
