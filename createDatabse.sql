CREATE DATABASE close;

USE close;
        
CREATE TABLE user(      
  ducks INT DEFAULT 0,     
  name VARCHAR(255), 
  phone VARCHAR(255), 
  password VARCHAR(255), 
  
  PRIMARY KEY(phone)
);

CREATE TABLE photo(
  link VARCHAR(255),
  userPhone VARCHAR(255),
  PRIMARY KEY(link),
  CONSTRAINT
  FOREIGN KEY(userPhone) REFERENCES user(phone)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);

CREATE TABLE interest(
  name VARCHAR(255),
  PRIMARY KEY(name)
);

CREATE TABLE userIsInterested(
  userPhone VARCHAR(255),
  interest VARCHAR(255),
  
  CONSTRAINT
  FOREIGN KEY(userPhone) REFERENCES user(phone),
  FOREIGN KEY(interest) REFERENCES interest(name)
  ON DELETE CASCADE
  ON UPDATE CASCADE

);


