# Job-Community-API

This repository contains two REST services. The first one provides Spring Security JWT based registration and authentication, and contains employees and companies data. 
The second one generates employee's CV based on employee's data from the first service.

The general idea of this project is job network for keeping employees and companies data in one place.

User can register, authenticate and store information in a profile (such as photo, name, surname and experience). 

There is also places of work with its profiles which contains office photos.

User can add a company to the profile and indicate the dates of work.

Based on users profile data, user can generate CV and download it.

For testing API, access 
"""
localhost:8076/swagger-ui/
"""
