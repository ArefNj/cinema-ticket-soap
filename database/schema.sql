CREATE TABLE IF NOT EXISTS movies (
    movie_id TEXT PRIMARY KEY,
    title TEXT NOT NULL,
    genre TEXT NOT NULL,
    duration INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS show_times (
    showtime_id TEXT PRIMARY KEY,
    movie_id TEXT NOT NULL,
    time TEXT NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id)
);

CREATE TABLE IF NOT EXISTS reservations (
    reservation_id TEXT PRIMARY KEY,
    movie_id TEXT NOT NULL,
    showtime_id TEXT NOT NULL,
    seat INTEGER NOT NULL,
    customer_name TEXT NOT NULL,
    status TEXT NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (showtime_id) REFERENCES show_times(showtime_id)
);

INSERT OR IGNORE INTO movies(movie_id, title, genre, duration) VALUES ('M101', 'Interstellar', 'Sci-Fi', 169);
INSERT OR IGNORE INTO movies(movie_id, title, genre, duration) VALUES ('M102', 'The Godfather', 'Crime', 175);
INSERT OR IGNORE INTO movies(movie_id, title, genre, duration) VALUES ('M103', 'Inception', 'Sci-Fi', 148);

INSERT OR IGNORE INTO show_times(showtime_id, movie_id, time) VALUES ('ST101', 'M101', '2026-06-10 15:30');
INSERT OR IGNORE INTO show_times(showtime_id, movie_id, time) VALUES ('ST102', 'M102', '2026-06-11 15:30');
INSERT OR IGNORE INTO show_times(showtime_id, movie_id, time) VALUES ('ST103', 'M103', '2026-06-12 15:30');
INSERT OR IGNORE INTO show_times(showtime_id, movie_id, time) VALUES ('ST104', 'M101', '2026-06-13 20:00');

INSERT OR IGNORE INTO reservations(reservation_id, movie_id, showtime_id, seat, customer_name, status) VALUES ('R101', 'M101', 'ST101', 1, 'Bob', 'CONFIRMED');
INSERT OR IGNORE INTO reservations(reservation_id, movie_id, showtime_id, seat, customer_name, status) VALUES ('R102', 'M101', 'ST101', 2, 'Alice', 'CONFIRMED');
INSERT OR IGNORE INTO reservations(reservation_id, movie_id, showtime_id, seat, customer_name, status) VALUES ('R103', 'M102', 'ST102', 3, 'Michel', 'CONFIRMED');
