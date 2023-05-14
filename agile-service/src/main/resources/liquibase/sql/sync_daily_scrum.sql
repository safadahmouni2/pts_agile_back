DROP PROCEDURE IF EXISTS SYNC_DAILY_SCRUM 
$$
CREATE  PROCEDURE `SYNC_DAILY_SCRUM`()
BEGIN

		DECLARE KUNDE_REF_PREFIX VARCHAR(250) DEFAULT 'PTS-AGILE_DAILY_SCRUM';       	

		DECLARE  v_daily_scrum_id BIGINT(20);  
		DECLARE  v_ticket_id BIGINT(20);  
		DECLARE  v_state_id BIGINT(20);
		DECLARE  v_sprint_progress DECIMAL(31,15);
		DECLARE  v_sprint_id BIGINT(20);
		DECLARE  v_finished_at TIMESTAMP;
		DECLARE  v_started_at TIMESTAMP;
		DECLARE  v_creator VARCHAR(250);
		DECLARE  v_created TIMESTAMP ;
		DECLARE  v_modifier VARCHAR(250);
		DECLARE  v_modified TIMESTAMP ;
		

		DECLARE  done_p INT ;  
		DECLARE  v_count INT ;      


        DECLARE  cursor_data CURSOR FOR 
		  SELECT 
				DS.id,      
				DS.state_id,
				DS.sprint_progress,
				SP.ticket_id,
				DS.finished_at,
				DS.started_at,
				DS.creator,
				DS.created,
				DS.modifier,
				DS.modified,
				DS.ticket_id
				
			 FROM daily_scrum AS DS
			 INNER JOIN sprint AS SP ON DS.sprint_id = SP.id
			 WHERE ( DS.ticket_id IS NOT NULL AND SP.ticket_id IS NOT NULL AND DS.modified > DS.exported_at)  
			 ORDER BY DS.id DESC
             limit 10;
			 
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_p = 1;
         
        OPEN cursor_data;
		
        read_loop: LOOP
         
			FETCH cursor_data INTO 
			v_daily_scrum_id,
			v_state_id,
			v_sprint_progress,
			v_sprint_id,
			v_finished_at,
			v_started_at,
			v_creator,
			v_created,
			v_modifier,
			v_modified,
			v_ticket_id;
			
			IF done_p = 1 THEN
				LEAVE read_loop;
			END IF;
				
			-- insert daily_scrum into pts_ticket_import			    
			INSERT INTO pts_ticket_import 
				(
				kunde_ref,
				parent_ticket_id,
				mail_date,
				state,
				lock_date,
				type,
				target_date,
				amount_1,
				creator,
				ts_insert,
				editor,
				ts_update
				)
				VALUES(
					CONCAT(KUNDE_REF_PREFIX, v_daily_scrum_id),	  
					v_sprint_id,
					v_started_at,
					v_state_id,
					v_started_at,
					1017896,
					v_finished_at,
					v_sprint_progress,
					v_creator,
					v_created,
					v_modifier,
					v_modified
					);	
			UPDATE user_story SET exported_at = GREATEST(now(), modified) WHERE id=v_user_story_id ;


        END LOOP read_loop;
        CLOSE cursor_data;

      END;
$$

