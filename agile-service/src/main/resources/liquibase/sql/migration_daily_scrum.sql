DROP PROCEDURE IF EXISTS MIGRATION_DAILY_SCRUM
$$
CREATE  PROCEDURE MIGRATION_DAILY_SCRUM()
BEGIN -- Declare variables
DECLARE done INT DEFAULT FALSE;

DECLARE MY_PROC_NAME VARCHAR(250) DEFAULT 'MIGRATION_DAILY_SCRUM';

DECLARE t_ticket_id,
        t_state_id BIGINT(20);

DECLARE  t_sprint_progress DECIMAL(31,15);

DECLARE  t_sprint_id BIGINT(20);

DECLARE  t_finished_at TIMESTAMP;

DECLARE  t_started_at TIMESTAMP;

DECLARE t_creator VARCHAR(250);

DECLARE t_created TIMESTAMP ;

DECLARE t_modifier VARCHAR(250);

DECLARE t_modified TIMESTAMP ;

DECLARE ticket_exists INTEGER ;

DECLARE cur
CURSOR
FOR
SELECT PT.ticket_id,
       PT.state,
	   S.id,
       COALESCE(PT.mail_date, PT.lock_date) ,
       PT.target_date,
       PT.amount_1,
       PT.creator,
       PT.ts_insert,
       PT.editor,
       PT.ts_update
FROM pts.pts_ticket PT
INNER JOIN pts_agile.sprint AS S ON S.ticket_id = PT.parent_ticket_id
WHERE TYPE = 1017896;

DECLARE CONTINUE
HANDLER FOR NOT FOUND
SET done = TRUE;

-- Open the cursor
 OPEN cur;

-- Loop through the result set
 read_loop: LOOP -- Fetch the next row
 FETCH cur INTO t_ticket_id,
                t_state_id,
				t_sprint_id,
                t_started_at,
                t_finished_at,
                t_sprint_progress,
                t_creator,
                t_created,
                t_modifier,
                t_modified;

-- Exit the loop if no more rows
 IF done THEN LEAVE read_loop;

 END IF;

IF t_ticket_id IS NOT NULL THEN -- log here --

SELECT count(*) INTO ticket_exists
FROM daily_scrum
WHERE ticket_id = t_ticket_id ;

CALL pts_log.i0(MY_PROC_NAME, concat('Try to Export Ticket: ', t_ticket_id, ', count entries in  daily_scrum ', ticket_exists));

IF ticket_exists = 0 THEN -- Migrate the ticket to daily_scrum if it does not already exist

INSERT INTO daily_scrum (ticket_id,  state_id, sprint_id, started_at, finished_at , sprint_progress, creator, created, modifier, modified)
VALUES(
	   t_ticket_id,
       t_state_id,
	   t_sprint_id,
	   t_started_at,
       t_finished_at,
       t_sprint_progress,
       t_creator,
       t_created,
       t_modifier,
       t_modified
);

CALL pts_log.i0(MY_PROC_NAME, concat('ticket id : ', t_ticket_id, ', ticket migrated to daily_scrum '));

END IF;

END IF;

END LOOP;

-- Close the cursor
 CLOSE cur;

END;
$$