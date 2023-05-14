DROP PROCEDURE IF EXISTS MIGRATION_USER_STORY
$$
CREATE  PROCEDURE MIGRATION_USER_STORY()
BEGIN -- Declare variables
DECLARE done INT DEFAULT FALSE;

DECLARE MY_PROC_NAME VARCHAR(250) DEFAULT 'MIGRATION_USER_STORY';

DECLARE t_ticket_id,
        t_state_id,
		t_responsible_id,
		t_product_id,
		t_sprint_id,
		t_urgency,
		t_story_points,
		t_progress,
		t_feature_id,
		t_test_id,
		t_test_run_id,
		t_test_step_id  BIGINT(20);

DECLARE t_order_by_ds,
		t_order_by_topic DECIMAL(31,15);
		
DECLARE t_project,
	    t_role  VARCHAR(100);
		
DECLARE t_goal, 
		t_benefit,
		t_short_description VARCHAR(250);
        
DECLARE	t_topic  VARCHAR(250) CHARACTER SET UTF8;
		
DECLARE t_long_description,
		t_acceptance_criteria  TEXT;

DECLARE t_creator VARCHAR(250);

DECLARE t_created TIMESTAMP ;

DECLARE t_modifier VARCHAR(250);

DECLARE t_modified TIMESTAMP ;

DECLARE ticket_exists INTEGER ;

DECLARE cur
CURSOR
FOR
SELECT          PT.ticket_id,
				PT.state,
				S.id,
				PT.responsible_id,
				PT.product_id,
				PT.urgency,
				PT.fl1,
				PT.progress,
				PT.sent_back_by,
				PT.cust_user_id,
				PT.ap_id,
				PT.fl3,
				PT.amount_1,
				PT.amount_2,
				PT.project,
				PT.text_m_1,
				PT.text_m_2,
				PT.text_m_3,
				PT.long_description,
				PT.short_description,
				PT.solution_description,
				PT.creator,
				PT.ts_insert,
				PT.editor,
				PT.ts_update,
                TS.id
FROM pts.pts_ticket PT
INNER JOIN pts_agile.sprint AS S ON S.ticket_id = PT.matrix_id
LEFT JOIN pts_agile.topic AS TS ON TS.ticket_id = PT.assignment
WHERE TYPE = 1017760;

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
				t_responsible_id,
				t_product_id,
				t_urgency,
				t_story_points,
				t_progress,
				t_feature_id,
				t_test_id,
				t_test_run_id,
				t_test_step_id,
				t_order_by_ds,
				t_order_by_topic,
				t_project,
				t_role,
				t_goal,
				t_benefit,
				t_short_description,
				t_long_description,
				t_acceptance_criteria,
                t_creator,
                t_created,
                t_modifier,
                t_modified,
                t_topic;

-- Exit the loop if no more rows
 IF done THEN LEAVE read_loop;

 END IF;

IF t_ticket_id IS NOT NULL THEN -- log here --

SELECT count(*) INTO ticket_exists
FROM user_story
WHERE ticket_id = t_ticket_id ;

CALL pts_log.i0(MY_PROC_NAME, concat('Try to Export Ticket: ', t_ticket_id, ', count entries in  user_story ', ticket_exists));

IF ticket_exists = 0 THEN -- Migrate the ticket to user_story if it does not already exist

INSERT INTO user_story (ticket_id,  state_id, responsible_id, product_id,  sprint_id , urgency_id , story_points, progress, feature_id, test_id, test_run_id,
 test_step_id, order_by_topic, order_by_ds, project, role, goal, benefit, short_description, long_description, acceptance_criteria,  creator, created, modifier, modified,topic_id)
VALUES(
			t_ticket_id,
			t_state_id,
			t_responsible_id,
			t_product_id,
			t_sprint_id,
			t_urgency,
			t_story_points,
			t_progress,
			t_feature_id,
			t_test_id,
			t_test_run_id,
			t_test_step_id,
			t_order_by_ds,
			t_order_by_topic,
			t_project,
			t_role,
			t_goal,
			t_benefit,
			t_short_description,
			t_long_description,
			t_acceptance_criteria,
            t_creator,
            t_created,
            t_modifier,
            t_modified, 
            t_topic
);

CALL pts_log.i0(MY_PROC_NAME, concat('ticket id : ', t_ticket_id, ', ticket migrated to user_story '));

END IF;

END IF;

END LOOP;

-- Close the cursor
 CLOSE cur;

END;
$$