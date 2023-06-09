DROP EVENT IF EXISTS PTS_SYNC_SCHEDULE
$$
SET GLOBAL event_scheduler="ON"
$$
CREATE EVENT IF NOT EXISTS PTS_SYNC_SCHEDULE
ON SCHEDULE EVERY 30 SECOND
COMMENT 'SYNC DATA TABLES EVERY 30 SECOND'
 DO
   BEGIN
-- CALL PROCEDURE TO SYNC DATA AND PASSING TYPE IN PARAMETER --------
      CALL  SYNC_USER_STORY();
      CALL  SYNC_DAILY_SCRUM();
      CALL  SYNC_SPRINT();
      CALL  SYNC_TOPIC();
      CALL  SYNC_DS_PARTICIPANT();
   END;
$$