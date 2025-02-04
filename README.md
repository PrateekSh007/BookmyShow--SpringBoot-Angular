# BookmyShow--SpringBoot-Angular

A fully functional BookMyShow clone built using **Spring Boot**, **Angular**, and **MySQL**. This project demonstrates a wide range of features and a robust data mapping structure, making it a great example of a modern full-stack web application.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Installation & Setup](#installation--setup)
  - [Prerequisites](#prerequisites)
  - [Backend Setup](#backend-setup)
  - [Frontend Setup](#frontend-setup)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgments](#acknowledgments)

---

## Features

- **JWT Authentication:**  
  Secure login and registration using JSON Web Tokens.

- **Movie Filtering:**  
  Filter movies based on the selected city.

- **Admin Functionality:**  
  Admin users can add and manage movies, theatres, and shows.

- **User Booking:**  
  End users can select movies, book tickets, and manage their bookings.

- **Email Notifications:**  
  Automatic email notifications are sent with booking details.

- **Downloadable Booking Details:**  
  Users can download their booking information for offline reference.

- **Analytics Dashboard:**  
  View and analyze data through pie charts and other visualizations.

- **Robust Data Mapping:**  
  The data is stored in MySQL with a comprehensive relational mapping hierarchy:  
  **Cities → Theatres → Shows → Seats → Movies**

---

## Tech Stack

- **Backend:** Spring Boot
- **Frontend:** Angular
- **Database:** MySQL

---

## Project Structure

```plaintext
BookmyShow--SpringBoot-Angular/
├── backend/          # Spring Boot project files
├── frontend/         # Angular project files
├── docs/             # Documentation (if any)
├── README.md         # This file
└── .gitignore        # Git ignore configuration
