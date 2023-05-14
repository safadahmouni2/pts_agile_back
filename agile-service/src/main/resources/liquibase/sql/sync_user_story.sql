DROP PROCEDURE IF EXISTS SYNC_USER_STORY
$$
CREATE  PROCEDURE `SYNC_USER_STORY`()
BEGIN
		DECLARE KUNDE_REF_PREFIX VARCHAR(250) DEFAULT 'PTS-AGILE_USER_STORY_';       	

		DECLARE  v_user_story_id,
				 v_responsible_id,
				 v_ticket_id,
				 v_state_id,
				 v_product_id,
				 v_sprint_id,
				 v_urgency_id,
				 v_story_points,
				 v_progress,
				 v_feature_id,
				 v_test_id,
				 v_test_run_id,
				 v_test_step_id  BIGINT(20);  
		DECLARE  v_order_by_ds,
				 v_order_by_topic DECIMAL(31,15);
		DECLARE  v_project,
				 v_role  VARCHAR(100) CHARACTER SET UTF8;
		DECLARE  v_goal, 
				 v_benefit,
				 v_short_description,
				 v_topic  VARCHAR(250) CHARACTER SET UTF8;
		DECLARE  v_long_description,
				 v_acceptance_criteria  TEXT CHARACTER SET UTF8;	 
		DECLARE  v_creator VARCHAR(250) CHARACTER SET UTF8;
		DECLARE  v_created TIMESTAMP ;
		DECLARE  v_modifier VARCHAR(250) CHARACTER SET UTF8;
		DECLARE  v_modified TIMESTAMP ;
		

		DECLARE  done_p INT ;  
		DECLARE  v_count INT ;      


        DECLARE  cursor_data CURSOR FOR 
		  SELECT 
				US.id,      
				US.state_id,
				SP.ticket_id as sprint_ticket_id,
				US.responsible_id,
				US.product_id,
				US.urgency_id,
				US.story_points,
				US.progress,
				US.feature_id,
				US.test_id,
				US.test_run_id,
				US.test_step_id,
				US.order_by_topic,
				US.order_by_ds,
				US.project,
				US.role,
				US.goal,
				US.benefit,
				US.short_description,
				US.long_description,
				US.acceptance_criteria,
				US.creator,
				US.created,
				US.modifier,
				US.modified,
				TS.ticket_id as topic_ticket_id,
				US.ticket_id
				
			 FROM user_story AS US
			 LEFT JOIN sprint AS SP ON US.sprint_id = SP.id
			 LEFT JOIN topic AS TS ON US.topic_id = TS.id
			 WHERE ( US.ticket_id IS NOT NULL AND US.modified > US.exported_at)
			 ORDER BY US.id DESC
             limit 10;
			 
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_p = 1;
         
        OPEN cursor_data;
		
        read_loop: LOOP
         
			FETCH cursor_data INTO 
			v_user_story_id,
			v_state_id,
			v_sprint_id,
			v_responsible_id,
			v_product_id,
			v_urgency_id,
			v_story_points,
			v_progress,
			v_feature_id,
			v_test_id,
			v_test_run_id,
			v_test_step_id,
			v_order_by_topic,
			v_order_by_ds,
			v_project,
			v_role,
			v_goal,
			v_benefit,
			v_short_description,
			v_long_description,
			v_acceptance_criteria,
			v_creator,
			v_created,
			v_modifier,
			v_modified,
			v_topic,
			v_ticket_id;
			
			IF done_p = 1 THEN
				LEAVE read_loop;
			END IF;
			
			-- insert us into pts_ticket_import			    
			INSERT INTO pts_ticket_import 
			   (
				kunde_ref,
				state,
				matrix_id,
				responsible_id,
				type,
				product_id,
				urgency,
				fl1,
				progress,
				sent_back_by,
				cust_user_id,
				ap_id,
				fl3,
				amount_1,
				amount_2,
				project,
				text_m_1,
				text_m_2,
				text_m_3,
				long_description,
				short_description,
				solution_description,
				creator,
				ts_insert,
				editor,
				ts_update,
				assignment
				)
				VALUES(
					CONCAT(KUNDE_REF_PREFIX, v_user_story_id),
					v_state_id,
					v_sprint_id,
					v_responsible_id,
					1017760,
					v_product_id,
					v_urgency_id,
					v_story_points,
					v_progress,
					v_feature_id,
					v_test_id,
					v_test_run_id,
					v_test_step_id,
					v_order_by_topic,
					v_order_by_ds,
					v_project,
					v_role,
					v_goal,
					v_benefit,
					v_long_description,
					v_short_description,
					v_acceptance_criteria,
					v_creator,
					v_created,
					v_modifier,
					v_modified,
					v_topic
					);	
			UPDATE user_story SET exported_at = GREATEST(now(), modified) WHERE id=v_user_story_id ;		
		
        END LOOP read_loop;
        CLOSE cursor_data;

      END;
$$
