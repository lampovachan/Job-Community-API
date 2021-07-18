# CV-Generator

Generates employee's CV (first name, last name, age, goal, experience) from HTML to PDF using XHTMLRenderer Flying Saucer. Keeps PDF files in LocalStack (AWS S3). 

To run the application, start Docker by this command:
```
docker compose up -d
```
You can access next endpoits:

POST endpoint for creating employee's CV by body request you're sending:
```
http://localhost:8081/cv/create
```
GET endpoint for downloanding employee's CV. You need to enter id as path variable of GET request.
```
http://localhost:8081/cv/download/:id
```
PUT endpoint for updating employee's CV. You need to enter id as path variable of PUT request.
```
http://localhost:8081/cv/update/:id
```
DELETE endpoint for deleting employee's CV. LocalStack method for deleting object is not working right, that's why you can't delete CV for real.
```
http://localhost:8081/cv/:id
