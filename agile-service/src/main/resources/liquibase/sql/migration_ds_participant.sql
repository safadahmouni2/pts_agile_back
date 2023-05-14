DROP PROCEDURE IF EXISTS MIGRATION_DS_PARTICIPANT
$$
CREATE  PROCEDURE MIGRATION_DS_PARTICIPANT()
BEGIN -- Declare variables
DECLARE done INT DEFAULT FALSE;

DECLARE MY_PROC_NAME VARCHAR(250) DEFAULT 'MIGRATION_DS_PARTICIPANT';

DECLARE t_ticket_id,
        t_state_id,
		t_daily_scrum_id,
		t_user_id	BIGINT(20);

DECLARE t_creator, t_user_code VARCHAR(250);

DECLARE t_created TIMESTAMP ;

DECLARE t_modifier VARCHAR(250);

DECLARE t_modified TIMESTAMP ;

DECLARE ticket_exists INTEGER ;

DECLARE cur
CURSOR
FOR
SELECT PT.ticket_id,
       DS.id,
	   PT.responsible_id,
	   PT.state,
	   PT.creator,
	   PT.ts_insert,
	   PT.editor,
	   PT.ts_update,
	   CU.user_code 
FROM pts.pts_ticket PT 
INNER JOIN pts.core_user AS CU ON CU.user_id = PT.responsible_id 
INNER JOIN pts_agile.daily_scrum AS DS ON DS.ticket_id = PT.parent_ticket_id
WHERE TYPE = 1017907;

DECLARE CONTINUE
HANDLER FOR NOT FOUND
SET done = TRUE;

-- Open the cursor
 OPEN cur;

-- Loop through the result set
 read_loop: LOOP -- Fetch the next row
 FETCH cur INTO t_ticket_id,
				t_daily_scrum_id,
				t_user_id,
				t_state_id,
                t_creator,
                t_created,
                t_modifier,
                t_modified,
				t_user_code;

-- Exit the loop if no more rows
 IF done THEN LEAVE read_loop;

 END IF;

IF t_ticket_id IS NOT NULL THEN -- log here --

SELECT count(*) INTO ticket_exists
FROM ds_participant
WHERE ticket_id = t_ticket_id ;

CALL pts_log.i0(MY_PROC_NAME, concat('Try to Export Ticket: ', t_ticket_id, ', count entries in  ds_participant ', ticket_exists));

IF ticket_exists = 0 THEN -- Migrate the ticket to ds_participant if it does not already exist

INSERT INTO ds_participant (ticket_id, daily_scrum_id, user_id, user_code, state_id , creator, created, modifier, modified)
VALUES(
	   t_ticket_id,
	   t_daily_scrum_id,
	   t_user_id,
	   t_user_code,
	   t_state_id,
       t_creator,
       t_created,
       t_modifier,
       t_modified
);

CALL pts_log.i0(MY_PROC_NAME, concat('ticket id : ', t_ticket_id, ', ticket migrated to ds_participant '));

END IF;

END IF;

END LOOP;

-- Close the cursor
 CLOSE cur;

END;
$$
