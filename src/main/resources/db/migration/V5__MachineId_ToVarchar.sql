ALTER TABLE usr
    DROP FOREIGN KEY usr_ibfk_1;


ALTER TABLE statemachine
    MODIFY machineId varchar(255) NOT NULL;

ALTER TABLE usr
    ADD COLUMN machineId varchar(255) NOT NULL,
    ADD FOREIGN KEY (machineId) REFERENCES statemachine(machineId);