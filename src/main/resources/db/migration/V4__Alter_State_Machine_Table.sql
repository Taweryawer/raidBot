ALTER TABLE usr
    DROP FOREIGN KEY usr_ibfk_1;
drop table state_machine;

CREATE TABLE StateMachine (
  machineId int NOT NULL AUTO_INCREMENT,
  state varchar(255) NOT NULL,
  stateMachineContext blob NOT NULL,
  PRIMARY KEY (machineId)
);

ALTER TABLE usr
    ADD FOREIGN KEY (id) REFERENCES StateMachine(machineId);