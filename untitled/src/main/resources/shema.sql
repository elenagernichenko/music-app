-- Создание таблицы Ролей
CREATE TABLE IF NOT EXISTS Roles (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(50) NOT NULL
    );

-- Создание таблицы Пользователей
CREATE TABLE IF NOT EXISTS Users (
                                     id SERIAL PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    bio TEXT,
    musicPreferences TEXT,
    avatarPath TEXT,
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES Roles(id) ON DELETE CASCADE
    );

-- Создание таблицы Треков
CREATE TABLE IF NOT EXISTS Tracks (
                                      id SERIAL PRIMARY KEY,
                                      name VARCHAR(100) NOT NULL,
    artist VARCHAR(100) NOT NULL,
    album VARCHAR(100),
    year VARCHAR(100),
    filename VARCHAR(100),
    popularity INT DEFAULT 0
    );

-- Создание таблицы Новостей
CREATE TABLE IF NOT EXISTS News (
                                    id SERIAL PRIMARY KEY,
                                    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Создание таблицы Плейлистов
CREATE TABLE IF NOT EXISTS Playlists (
                                         id SERIAL PRIMARY KEY,
                                         name VARCHAR(100) NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
    );

-- Создание таблицы Треков Плейлиста
CREATE TABLE IF NOT EXISTS PlaylistTracks (
                                              id SERIAL PRIMARY KEY,
                                              playlist_id INT NOT NULL,
                                              track_id INT NOT NULL,
                                              FOREIGN KEY (playlist_id) REFERENCES Playlists(id) ON DELETE CASCADE,
    FOREIGN KEY (track_id) REFERENCES Tracks(id) ON DELETE CASCADE
    );

-- Создание таблицы Треков Пользователя
CREATE TABLE IF NOT EXISTS UserTracks (
                                          id SERIAL PRIMARY KEY,
                                          user_id INT NOT NULL,
                                          track_id INT NOT NULL,
                                          FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (track_id) REFERENCES Tracks(id) ON DELETE CASCADE
    );

-- Вставка данных для таблицы Ролей
INSERT INTO Roles (id, name) VALUES (1, 'USER');
INSERT INTO Roles (id, name) VALUES (2, 'ADMIN');
