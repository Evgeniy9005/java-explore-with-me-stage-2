DELETE FROM USERS;
ALTER TABLE USERS ALTER COLUMN ID RESTART WITH 1;
DELETE FROM CATEGORY;
ALTER TABLE CATEGORY ALTER COLUMN ID RESTART WITH 1;
DELETE FROM EVENTS;
ALTER TABLE EVENTS ALTER COLUMN ID RESTART WITH 1;
DELETE FROM PARTICIPATION_REQUEST;
ALTER TABLE PARTICIPATION_REQUEST ALTER COLUMN ID RESTART WITH 1;
DELETE FROM COMPILATION;
ALTER TABLE COMPILATION ALTER COLUMN ID RESTART WITH 1;