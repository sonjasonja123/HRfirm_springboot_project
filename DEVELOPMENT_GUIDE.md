# Development Guide

## Development Environment Setup

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- Node.js 16 or higher
- Git
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### Backend Development

#### Project Structure
```
src/main/java/com/hr/
├── HrManagementSystemApplication.java    # Main application class
├── config/                               # Configuration classes
│   ├── CorsConfig.java                  # CORS configuration
│   └── SecurityConfig.java              # Security configuration
├── controller/                           # REST controllers
│   ├── CompanyController.java           # Company API endpoints
│   ├── PositionController.java          # Position API endpoints
│   ├── CandidateController.java         # Candidate API endpoints
│   └── InterviewController.java         # Interview API endpoints
├── model/                                # Entity classes
│   ├── Company.java                     # Company entity
│   ├── Position.java                    # Position entity
│   ├── Candidate.java                   # Candidate entity
│   ├── Interview.java                   # Interview entity
│   ├── InterviewRound.java              # Interview round entity
│   └── InterviewType.java               # Interview type enum
├── repository/                           # Data access layer
│   ├── CompanyRepository.java           # Company repository
│   ├── PositionRepository.java          # Position repository
│   ├── CandidateRepository.java         # Candidate repository
│   ├── InterviewRepository.java         # Interview repository
│   └── InterviewRoundRepository.java    # Interview round repository
└── service/                              # Business logic layer
    ├── CompanyService.java              # Company business logic
    ├── PositionService.java             # Position business logic
    ├── CandidateService.java            # Candidate business logic
    └── InterviewService.java            # Interview business logic
```

#### Coding Standards

##### Java Code Style
- Use camelCase for variables and methods
- Use PascalCase for classes and interfaces
- Use UPPER_CASE for constants
- Use meaningful names
- Add JavaDoc comments for public methods
- Follow Spring Boot conventions

##### Example:
```java
/**
 * Service class for managing companies
 */
@Service
@Transactional
public class CompanyService {
    
    private final CompanyRepository companyRepository;
    
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
    
    /**
     * Retrieves all companies
     * @return List of all companies
     */
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}
```

##### Entity Design
- Use JPA annotations properly
- Implement proper relationships
- Add validation annotations
- Use appropriate data types
- Follow naming conventions

##### Example:
```java
@Entity
@Table(name = "companies")
public class Company {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_company")
    private Long idCompany;
    
    @NotBlank(message = "Company name is required")
    @Size(max = 100, message = "Company name must not exceed 100 characters")
    @Column(name = "name", nullable = false)
    private String name;
    
    // ... other fields and methods
}
```

### Frontend Development

#### Project Structure
```
frontend/src/
├── components/                           # React components
│   ├── Navbar.js                        # Navigation component
│   ├── Dashboard.js                     # Dashboard component
│   ├── CompanyList.js                   # Company management
│   ├── PositionList.js                  # Position management
│   ├── CandidateList.js                 # Candidate management
│   └── InterviewList.js                 # Interview management
├── services/                            # API services
│   └── api.js                          # API client
├── App.js                              # Main app component
├── App.css                             # App styles
├── index.js                            # Entry point
└── index.css                           # Global styles
```

#### React Coding Standards

##### Component Structure
```javascript
import React, { useState, useEffect } from 'react';
import { ComponentName } from 'react-bootstrap';
import { apiService } from '../services/api';

const MyComponent = () => {
  // State declarations
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  
  // Effect hooks
  useEffect(() => {
    loadData();
  }, []);
  
  // Event handlers
  const handleSubmit = async (e) => {
    e.preventDefault();
    // Handle form submission
  };
  
  // Render
  return (
    <div>
      {/* JSX content */}
    </div>
  );
};

export default MyComponent;
```

##### Naming Conventions
- Use PascalCase for component names
- Use camelCase for variables and functions
- Use descriptive names
- Use meaningful prop names

##### State Management
- Use useState for local state
- Use useEffect for side effects
- Keep state as close to where it's used as possible
- Use custom hooks for shared logic

### API Development

#### REST API Design
- Use HTTP methods correctly (GET, POST, PUT, DELETE)
- Use meaningful URLs
- Return appropriate HTTP status codes
- Use consistent response formats
- Implement proper error handling

#### Example API Endpoint:
```java
@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {
    
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }
    
    @PostMapping
    public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company) {
        Company createdCompany = companyService.createCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
    }
}
```

### Database Development

#### Entity Relationships
- Use proper JPA annotations
- Implement bidirectional relationships
- Use appropriate fetch strategies
- Handle cascade operations carefully

#### Example Relationship:
```java
@Entity
public class Company {
    
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Position> positions = new ArrayList<>();
    
    public void addPosition(Position position) {
        positions.add(position);
        position.setCompany(this);
    }
}
```

### Testing

#### Unit Testing
- Test business logic in services
- Mock dependencies
- Test edge cases
- Use meaningful test names

#### Example Unit Test:
```java
@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {
    
    @Mock
    private CompanyRepository companyRepository;
    
    @InjectMocks
    private CompanyService companyService;
    
    @Test
    void shouldReturnAllCompanies() {
        // Given
        List<Company> companies = Arrays.asList(new Company(), new Company());
        when(companyRepository.findAll()).thenReturn(companies);
        
        // When
        List<Company> result = companyService.getAllCompanies();
        
        // Then
        assertEquals(2, result.size());
        verify(companyRepository).findAll();
    }
}
```

#### Integration Testing
- Test API endpoints
- Test database operations
- Test complete workflows
- Use @SpringBootTest

### Code Quality

#### Code Review Checklist
- [ ] Code follows naming conventions
- [ ] Methods are properly documented
- [ ] Error handling is implemented
- [ ] Tests are written and passing
- [ ] No hardcoded values
- [ ] Security considerations are addressed
- [ ] Performance implications are considered

#### Static Analysis
- Use SonarQube for code quality
- Use SpotBugs for bug detection
- Use Checkstyle for code style
- Use PMD for code analysis

### Performance Considerations

#### Backend Performance
- Use appropriate fetch strategies
- Implement pagination for large datasets
- Use database indexes
- Optimize queries
- Use connection pooling

#### Frontend Performance
- Use React.memo for expensive components
- Implement lazy loading
- Optimize bundle size
- Use proper state management
- Minimize re-renders

### Security Best Practices

#### Backend Security
- Validate all inputs
- Use parameterized queries
- Implement proper authentication
- Use HTTPS in production
- Sanitize user inputs

#### Frontend Security
- Sanitize user inputs
- Use HTTPS
- Implement proper error handling
- Don't expose sensitive data
- Use secure storage

### Documentation

#### Code Documentation
- Write clear comments
- Use JavaDoc for public APIs
- Document complex logic
- Keep documentation up to date
- Use meaningful commit messages

#### API Documentation
- Use Swagger/OpenAPI
- Document all endpoints
- Provide examples
- Document error responses
- Keep documentation current

### Version Control

#### Git Workflow
- Use feature branches
- Write meaningful commit messages
- Use pull requests for code review
- Keep commits atomic
- Use conventional commits

#### Example Commit Messages:
```
feat: add company management functionality
fix: resolve position update issue
docs: update API documentation
refactor: improve candidate service structure
test: add unit tests for interview service
```

### Continuous Integration

#### Build Pipeline
- Automated testing
- Code quality checks
- Security scanning
- Performance testing
- Deployment automation

#### Example GitHub Actions:
```yaml
name: CI/CD Pipeline

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 17
      uses: actions/setup-java@v2
      with:
        java-version: '17'
    - name: Run tests
      run: mvn test
    - name: Run integration tests
      run: mvn verify
```

### Troubleshooting

#### Common Issues
1. **Database Connection**: Check connection strings and credentials
2. **CORS Issues**: Verify allowed origins
3. **Memory Issues**: Monitor JVM heap size
4. **Performance**: Use profiling tools
5. **Security**: Regular security audits

#### Debugging Tips
- Use IDE debugger
- Add logging statements
- Use browser developer tools
- Monitor network requests
- Check application logs
