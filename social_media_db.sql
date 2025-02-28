CREATE DATABASE social_media_db;

USE social_media_db;

CREATE TABLE account (
    user_name VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    user_role VARCHAR(20) NOT NULL
);

CREATE TABLE posts (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50),
    post_content VARCHAR(200) NOT NULL,
    post_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    post_order INT NOT NULL,
    FOREIGN KEY (user_name) REFERENCES account(user_name) ON DELETE CASCADE
);

CREATE TABLE follows (
    user_name VARCHAR(50),
    follow1 VARCHAR(50) NULL,
    follow2 VARCHAR(50) NULL,
    follow3 VARCHAR(50) NULL,
    PRIMARY KEY (user_name),
    FOREIGN KEY (user_name) REFERENCES account(user_name) ON DELETE CASCADE,
    FOREIGN KEY (follow1) REFERENCES account(user_name) ON DELETE SET NULL,
    FOREIGN KEY (follow2) REFERENCES account(user_name) ON DELETE SET NULL,
    FOREIGN KEY (follow3) REFERENCES account(user_name) ON DELETE SET NULL
);

CREATE TABLE help_messages (
    message_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50),
    message TEXT NOT NULL,
    submission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_name) REFERENCES account(user_name) ON DELETE CASCADE
);