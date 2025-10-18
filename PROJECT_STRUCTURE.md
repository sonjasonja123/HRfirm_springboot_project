# Project Structure

```
hr-management-system/
├── src/main/java/com/hr/
│   ├── HrManagementSystemApplication.java
│   ├── config/
│   │   ├── CorsConfig.java
│   │   └── SecurityConfig.java
│   ├── controller/
│   │   ├── CompanyController.java
│   │   ├── PositionController.java
│   │   ├── CandidateController.java
│   │   └── InterviewController.java
│   ├── model/
│   │   ├── Company.java
│   │   ├── Position.java
│   │   ├── Candidate.java
│   │   ├── Interview.java
│   │   ├── InterviewRound.java
│   │   └── InterviewType.java
│   ├── repository/
│   │   ├── CompanyRepository.java
│   │   ├── PositionRepository.java
│   │   ├── CandidateRepository.java
│   │   ├── InterviewRepository.java
│   │   └── InterviewRoundRepository.java
│   └── service/
│       ├── CompanyService.java
│       ├── PositionService.java
│       ├── CandidateService.java
│       └── InterviewService.java
├── src/main/resources/
│   └── application.yml
├── frontend/
│   ├── public/
│   │   ├── index.html
│   │   └── manifest.json
│   ├── src/
│   │   ├── components/
│   │   │   ├── Navbar.js
│   │   │   ├── Dashboard.js
│   │   │   ├── CompanyList.js
│   │   │   ├── PositionList.js
│   │   │   ├── CandidateList.js
│   │   │   └── InterviewList.js
│   │   ├── services/
│   │   │   └── api.js
│   │   ├── App.js
│   │   ├── App.css
│   │   ├── index.js
│   │   └── index.css
│   └── package.json
├── pom.xml
├── README.md
├── QUICK_START.md
├── PROJECT_STRUCTURE.md
├── start-backend.bat
├── start-frontend.bat
├── start-all.bat
├── start-backend.sh
├── start-frontend.sh
├── start-all.sh
└── .gitignore
```

## Backend Structure

### Models (Entities)
- **Company**: Represents companies that post job positions
- **Position**: Represents job positions within companies
- **Candidate**: Represents individuals applying for positions
- **Interview**: Represents scheduled interviews between candidates and positions
- **InterviewRound**: Represents specific rounds within an interview
- **InterviewType**: Enum for different types of interview rounds

### Repository Layer
- Extends JpaRepository for basic CRUD operations
- Custom query methods for specific business needs
- Follows Spring Data JPA conventions

### Service Layer
- Contains business logic
- Uses repositories for data access
- Handles transactions
- Provides validation and error handling

### Controller Layer
- REST API endpoints
- Request/response handling
- Input validation
- CORS configuration

### Configuration
- **SecurityConfig**: Spring Security configuration
- **CorsConfig**: CORS configuration for frontend communication
- **application.yml**: Application properties and database configuration

## Frontend Structure

### Components
- **Navbar**: Navigation bar with menu items
- **Dashboard**: Overview page with statistics and recent activities
- **CompanyList**: CRUD operations for companies
- **PositionList**: CRUD operations for positions
- **CandidateList**: CRUD operations for candidates
- **InterviewList**: CRUD operations for interviews and interview rounds

### Services
- **api.js**: Axios-based API client for backend communication
- Centralized API endpoint management
- Request/response interceptors

### Styling
- Bootstrap 5 for responsive design
- Custom CSS for specific styling needs
- React Bootstrap components for UI elements

## Database
- H2 in-memory database for development
- JPA/Hibernate for ORM
- Automatic schema generation
- H2 console for database inspection

## Key Features
- Full CRUD operations for all entities
- Search and filtering capabilities
- Status management for candidates and interviews
- Interview round management
- Responsive design
- Real-time updates
- Error handling and user feedback
