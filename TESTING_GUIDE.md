# Testing Guide

## Backend Testing

### Unit Tests
Run unit tests for the backend:
```bash
mvn test
```

### Integration Tests
Run integration tests:
```bash
mvn verify
```

### API Testing with Postman/curl

#### Companies API
```bash
# Get all companies
curl -X GET http://localhost:8080/api/companies

# Create a company
curl -X POST http://localhost:8080/api/companies \
  -H "Content-Type: application/json" \
  -d '{"name": "Tech Corp", "industry": "Technology"}'

# Get company by ID
curl -X GET http://localhost:8080/api/companies/1

# Update company
curl -X PUT http://localhost:8080/api/companies/1 \
  -H "Content-Type: application/json" \
  -d '{"name": "Tech Corp Updated", "industry": "Software"}'

# Delete company
curl -X DELETE http://localhost:8080/api/companies/1
```

#### Positions API
```bash
# Get all positions
curl -X GET http://localhost:8080/api/positions

# Create a position
curl -X POST http://localhost:8080/api/positions \
  -H "Content-Type: application/json" \
  -d '{"name": "Software Engineer", "details": "Full-stack development", "open": true, "company": {"idCompany": 1}}'

# Get open positions
curl -X GET http://localhost:8080/api/positions/open

# Close a position
curl -X PUT http://localhost:8080/api/positions/1/close
```

#### Candidates API
```bash
# Get all candidates
curl -X GET http://localhost:8080/api/candidates

# Create a candidate
curl -X POST http://localhost:8080/api/candidates \
  -H "Content-Type: application/json" \
  -d '{"name": "John", "surname": "Doe", "status": "Active"}'

# Update candidate status
curl -X PUT http://localhost:8080/api/candidates/1/status?status=Hired
```

#### Interviews API
```bash
# Get all interviews
curl -X GET http://localhost:8080/api/interviews

# Create an interview
curl -X POST http://localhost:8080/api/interviews \
  -H "Content-Type: application/json" \
  -d '{"date": "2024-01-15T10:00:00", "status": "Scheduled", "candidate": {"idCandidate": 1}, "position": {"idPosition": 1}}'

# Add interview round
curl -X POST http://localhost:8080/api/interviews/1/rounds?type=TECHNICAL&number=1
```

## Frontend Testing

### Manual Testing
1. **Navigation**: Test all navigation links
2. **CRUD Operations**: Test create, read, update, delete for all entities
3. **Search/Filter**: Test search and filter functionality
4. **Status Updates**: Test status change operations
5. **Responsive Design**: Test on different screen sizes

### Test Scenarios

#### Company Management
1. Add a new company
2. Edit company details
3. Search companies by name/industry
4. Delete a company
5. Verify company appears in positions

#### Position Management
1. Create positions for different companies
2. Open/close positions
3. Search positions by name/company
4. Edit position details
5. Delete positions

#### Candidate Management
1. Add candidates with different statuses
2. Update candidate status (Hire, Reject, Deactivate)
3. Search candidates by name
4. Edit candidate information
5. Delete candidates

#### Interview Management
1. Schedule interviews between candidates and positions
2. Add different types of interview rounds
3. Update interview status
4. Search interviews by candidate/position
5. View interview rounds

## Database Testing

### H2 Console
1. Go to http://localhost:8080/h2-console
2. Use credentials: sa/password
3. JDBC URL: jdbc:h2:mem:testdb
4. Verify data integrity
5. Test relationships between entities

### Data Validation
1. Test required field validation
2. Test data type validation
3. Test business rule validation
4. Test foreign key constraints

## Performance Testing

### Load Testing
1. Create multiple companies, positions, candidates
2. Schedule many interviews
3. Test search performance with large datasets
4. Monitor response times

### Memory Testing
1. Monitor memory usage during operations
2. Test with large datasets
3. Verify no memory leaks

## Error Handling Testing

### Backend Errors
1. Test invalid input data
2. Test missing required fields
3. Test invalid IDs
4. Test constraint violations

### Frontend Errors
1. Test network errors
2. Test validation errors
3. Test user feedback
4. Test error recovery

## Security Testing

### CORS Testing
1. Test cross-origin requests
2. Verify CORS headers
3. Test preflight requests

### Input Validation
1. Test SQL injection attempts
2. Test XSS attempts
3. Test input sanitization

## Browser Compatibility
- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## Mobile Testing
- Test responsive design
- Test touch interactions
- Test mobile navigation
- Test form inputs on mobile

## Accessibility Testing
- Test keyboard navigation
- Test screen reader compatibility
- Test color contrast
- Test focus indicators
