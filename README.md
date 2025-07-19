# StayEase 🏨 | Hotel Booking REST API

A Spring Boot-based RESTful API for managing hotel bookings in a hotel aggregator system. The project supports user registration/login with role-based access control using **JWT Authentication**, and enables operations for customers, hotel managers, and admins.

---

## 🚀 Tech Stack

- Java 17+
- Spring Boot 3+
- Spring Security + JWT
- MySQL
- Hibernate + JPA
- Lombok
- Maven

---

## 🎯 Features

- ✅ **User Authentication & Authorization** using JWT
- ✅ Role-based Access (`CUSTOMER`, `HOTEL_MANAGER`, `ADMIN`)
- ✅ Secure **password hashing** using BCrypt
- ✅ **Hotel Management**: Add, Update, Delete, View
- ✅ **Room Booking**: Book, View, Cancel
- ✅ Validations & Error Handling with proper HTTP status codes
- ✅ Clean layered architecture: Controller, Service, Repository, Entity

---

## 🔐 Roles & Permissions

| Role          | Permissions                                                                 |
|---------------|------------------------------------------------------------------------------|
| `ADMIN`       | Register, Login, Create/Delete Hotels                                        |
| `HOTEL_MANAGER` | Update Hotel Details, Cancel Bookings                                     |
| `CUSTOMER`    | Register, Login, Book Rooms, View Bookings                                  |

---

## 🌐 API Endpoints

### 🔓 Public Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/users/register` | `POST` | Register a new user |
| `/api/users/login`    | `POST` | Authenticate user and return JWT |
| `/api/hotels`         | `GET`  | Get all available hotels |

### 🔒 Secured Endpoints

> ✅ Require `Authorization: Bearer <JWT>` header

#### Admin Only
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/hotels` | `POST` | Create a hotel |
| `/api/hotels/{id}` | `DELETE` | Delete a hotel |

#### Hotel Manager Only
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/hotels/{id}` | `PUT` | Update hotel details |
| `/api/bookings/{id}` | `DELETE` | Cancel a booking |

#### Customer Only
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/bookings/{hotelId}` | `POST` | Book a room |
| `/api/bookings/{id}` | `GET` | View a booking |

---

## 🛠 Setup & Run Locally

1. **Clone the repo**
```bash
git clone https://github.com/supriyaskharat/StayEase.git
cd StayEase
