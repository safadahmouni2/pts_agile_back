DROP PROCEDURE IF EXISTS MIGRATION_SPRINT_MEMBER
$$
CREATE  PROCEDURE MIGRATION_SPRINT_MEMBER()
BEGIN -- Declare variables
DECLARE done INT DEFAULT FALSE;

DECLARE MY_PROC_NAME VARCHAR(250) DEFAULT 'MIGRATION_SPRINT_MEMBER';

DECLARE t_ticket_id,
        t_state_id,
		t_sprint_id	 BIGINT(20);

DECLARE t_user_code VARCHAR(10);

DECLARE t_role VARCHAR(100);

DECLARE t_creator VARCHAR(250);

DECLARE t_created TIMESTAMP ;

DECLARE t_modifier VARCHAR(250);

DECLARE t_modified TIMESTAMP ;

DECLARE ticket_exists INTEGER ;

DECLARE cur
CURSOR
FOR
SELECT PT.ticket_id,
       S.id,
	   PT.state,
	   PT.file_name_1,
	   PT.creator,
	   PT.ts_insert,
	   PT.editor,
	   PT.ts_update,
	   CU.user_code 
FROM pts.pts_ticket PT 
INNER JOIN pts.core_user AS CU ON CU.user_id = PT.responsible_id 
INNER JOIN pts_agile.sprint AS S ON S.ticket_id = PT.parent_ticket_id
WHERE TYPE = 1030050;

DECLARE CONTINUE
HANDLER FOR NOT FOUND
SET done = TRUE;

-- Open the cursor
 OPEN cur;

-- Loop through the result set
 read_loop: LOOP -- Fetch the next row
 FETCH cur INTO t_ticket_id,
				t_sprint_id,
				t_state_id,
				t_role,
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
FROM sprint_member
WHERE ticket_id = t_ticket_id ;

CALL pts_log.i0(MY_PROC_NAME, concat('Try to Export Ticket: ', t_ticket_id, ', count entries in  sprint_member ', ticket_exists));

IF ticket_exists = 0 THEN -- Migrate the ticket to sprint_member if it does not already exist

INSERT INTO sprint_member (ticket_id, sprint_id, state_id,  role , creator, created, modifier, modified, user_code)
VALUES(
	   t_ticket_id,
	   t_sprint_id,
	   t_state_id,
	   t_role,
       t_creator,
       t_created,
       t_modifier,
       t_modified,
	   t_user_code
);

CALL pts_log.i0(MY_PROC_NAME, concat('ticket id : ', t_ticket_id, ', ticket migrated to sprint_member '));

END IF;

END IF;

END LOOP;

-- Close the cursor
 CLOSE cur;

END;
$$