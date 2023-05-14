DROP PROCEDURE IF EXISTS MIGRATION_SPRINT
$$
CREATE  PROCEDURE MIGRATION_SPRINT()
BEGIN -- Declare variables
DECLARE done INT DEFAULT FALSE;

DECLARE MY_PROC_NAME VARCHAR(250) DEFAULT 'MIGRATION_SPRINT';

DECLARE t_ticket_id,
        t_product_id,
        t_state_id,
		t_ds_duration BIGINT(20);
         

DECLARE t_name,
        t_short_name,
		t_project,
		t_parent VARCHAR(100);

DECLARE t_creator,
        t_modifier VARCHAR(250);

DECLARE t_ds_meeting_url VARCHAR(500);

DECLARE t_start_date,
        t_end_date,
		t_ds_start_time,
        t_created,
		t_modified TIMESTAMP ;

DECLARE ticket_exists INTEGER ;

DECLARE cur
CURSOR
FOR
SELECT ticket_id,
       state,
       product_id,
       project,
       short_description,
       file_name_1,
	   lock_date,
       mail_date,
       target_date,
       fl1,
       company,
       parent_ticket_id,
	   creator,
	   ts_insert,
	   editor,
	   ts_update

FROM pts.pts_ticket
WHERE TYPE = 1017761;

DECLARE CONTINUE
HANDLER FOR NOT FOUND
SET done = TRUE;

-- Open the cursor
 OPEN cur;

-- Loop through the result set
 read_loop: LOOP -- Fetch the next row
 FETCH cur INTO t_ticket_id,
                t_state_id,
                t_product_id,
                t_project,
                t_name,
                t_short_name,
                t_start_date,
                t_end_date,
                t_ds_start_time,
                t_ds_duration,
                t_ds_meeting_url,
                t_parent,
                t_creator,
                t_created,
                t_modifier,
                t_modified;

-- Exit the loop if no more rows
 IF done THEN LEAVE read_loop;

END IF;



SELECT count(*) INTO ticket_exists
FROM sprint
WHERE ticket_id = t_ticket_id ;
CALL pts_log.i0(MY_PROC_NAME, concat('Try to Export Ticket: ', t_ticket_id, ', count entries in  sprint ', ticket_exists));



IF ticket_exists = 0 THEN -- Migrate the ticket to sprint if it does not already exist

INSERT INTO sprint (ticket_id, product_id, state_id, ds_duration,name,short_name,project,parent,creator,modifier,
ds_meeting_url,start_date,end_date,ds_start_time,created,modified)
VALUES( t_ticket_id,
       t_product_id,
       t_state_id,
       t_ds_duration,
       t_name,
       t_short_name,
       t_project,
	   t_parent,
       t_creator,
       t_modifier,
	   t_ds_meeting_url,
       t_start_date,
       t_end_date,
	   t_ds_start_time,
       t_created,
	   t_modified
);

CALL pts_log.i0(MY_PROC_NAME, concat('ticket id : ', t_ticket_id, ', ticket migrated to sprint '));

END IF;

END LOOP;

-- Close the cursor
 CLOSE cur;

END;
$$