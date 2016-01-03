-- description: create table users
-- authoredAt: 1451848295245
-- up:

CREATE TABLE users (
  user_id int,
  fname text,
  lname text,
  PRIMARY KEY (user_id)
);

-- down:

DROP TABLE users;
