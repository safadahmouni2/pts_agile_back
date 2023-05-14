DROP PROCEDURE IF EXISTS MIGRATION_TOPIC
$$
CREATE  PROCEDURE MIGRATION_TOPIC()
BEGIN -- Declare variables
DECLARE done INT DEFAULT FALSE;

DECLARE MY_PROC_NAME VARCHAR(250) DEFAULT 'MIGRATION_TOPIC';

DECLARE t_ticket_id,
        t_state_id,
        t_product_id,
        t_project_id,
        t_topic_id BIGINT(20);

DECLARE t_name,
        t_description VARCHAR(250);

DECLARE t_display_order DECIMAL(31, 15);

DECLARE t_is_default TINYINT(4);

DECLARE t_creator VARCHAR(250);

DECLARE t_created TIMESTAMP ;

DECLARE t_modifier VARCHAR(250);

DECLARE t_modified TIMESTAMP ;

DECLARE ticket_exists INTEGER ;

DECLARE cur
CURSOR
FOR
SELECT ticket_id,
       state,
       product_id,
       project,
       short_description,
       long_description,
       amount_1,
       creator,
       ts_insert,
       editor,
       ts_update,
       fl1
FROM pts.pts_ticket
WHERE TYPE = 1018012;

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
                t_project_id,
                t_name,
                t_description,
                t_display_order,
                t_creator,
                t_created,
                t_modifier,
                t_modified,
                t_is_default;

-- Exit the loop if no more rows
 IF done THEN LEAVE read_loop;

END IF;

IF t_ticket_id IS NOT NULL THEN -- log here --

SELECT count(*) INTO ticket_exists
FROM topic
WHERE ticket_id = t_ticket_id ;

CALL pts_log.i0(MY_PROC_NAME, concat('Try to Export Ticket: ', t_ticket_id, ', count entries in  topic ', ticket_exists));

IF ticket_exists = 0 THEN -- Migrate the ticket to topic if it does not already exist

INSERT INTO topic (ticket_id, state_id, product_id, project, name, description, display_order, creator, created, modifier, modified)
VALUES( t_ticket_id,
       t_state_id,
       t_product_id,
       t_project_id,
       t_name,
       t_description,
       t_display_order,
       t_creator,
       t_created,
       t_modifier,
       t_modified
);

 if t_is_default = 1 THEN

    SELECT T.id INTO t_topic_id
    FROM topic T
    WHERE T.ticket_id = t_ticket_id ;

    INSERT INTO default_topic ( product_id, topic_id, creator, created, modifier, modified)
    VALUES( 
       t_product_id,
       t_topic_id,
       t_creator,
       t_created,
       t_modifier,
       t_modified
   ); 
  END IF;
CALL pts_log.i0(MY_PROC_NAME, concat('ticket id : ', t_ticket_id, ', ticket migrated to topic '));

END IF;

END IF;

END LOOP;

-- Close the cursor
 CLOSE cur;

END;
$$