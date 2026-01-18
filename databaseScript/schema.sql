-- CREATE DATABASE cinema_db;
-- \c cinema_db;

CREATE TABLE IF NOT EXISTS movies (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100) NOT NULL,
    duration INTEGER NOT NULL, -- in minutes
    rating DECIMAL(3, 1) CHECK (rating >= 0 AND rating <= 10),
    release_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS halls (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    capacity INTEGER NOT NULL CHECK (capacity > 0)
);

CREATE TABLE IF NOT EXISTS showtimes (
    id SERIAL PRIMARY KEY,
    movie_id INTEGER NOT NULL REFERENCES movies(id) ON DELETE CASCADE,
    hall_id INTEGER NOT NULL REFERENCES halls(id) ON DELETE CASCADE,
    show_date DATE NOT NULL,
    show_time TIME NOT NULL,
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    UNIQUE(hall_id, show_date, show_time)
);

CREATE TABLE IF NOT EXISTS tickets (
    id SERIAL PRIMARY KEY,
    showtime_id INTEGER NOT NULL REFERENCES showtimes(id) ON DELETE CASCADE,
    seat_number VARCHAR(10) NOT NULL,
    customer_name VARCHAR(255),
    is_booked BOOLEAN DEFAULT FALSE,
    UNIQUE(showtime_id, seat_number)
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_showtimes_movie_id ON showtimes(movie_id);
CREATE INDEX idx_showtimes_date ON showtimes(show_date);
CREATE INDEX idx_tickets_showtime_id ON tickets(showtime_id);
CREATE INDEX idx_tickets_is_booked ON tickets(is_booked);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);