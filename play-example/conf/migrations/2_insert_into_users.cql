-- description: insert into users
-- authoredAt: 1451848295245
-- up:

INSERT INTO users(user_id, fname, lname) VALUES(123, 'Hans', 'Wurst');

-- down:

TRUNCATE TABLE users;
