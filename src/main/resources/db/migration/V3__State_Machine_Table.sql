CREATE TABLE state_machine (
  id int NOT NULL,
  statemachine blob NOT NULL,
  PRIMARY KEY (id)
);
ALTER TABLE usr
    ADD FOREIGN KEY (id) references state_machine(id);