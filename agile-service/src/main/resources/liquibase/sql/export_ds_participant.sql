DROP PROCEDURE IF EXISTS EXPORT_DS_PARTICIPANT 
$$
CREATE  PROCEDURE `EXPORT_DS_PARTICIPANT`()
BEGIN
	    DECLARE MY_PROC_NAME VARCHAR(250) DEFAULT 'EXPORT_DS_PARTICIPANT';
		DECLARE KUNDE_REF_PREFIX VARCHAR(250) DEFAULT 'PTS-AGILE_DS_PARTICIPANT_';       	

		DECLARE  v_ds_participant_id,
				 v_daily_scrum_id,
				 v_user_id,
				 v_ticket_id,
				 v_state_id   BIGINT(20);  
		DECLARE  v_user_code VARCHAR(250);
		DECLARE  v_creator VARCHAR(250);
		DECLARE  v_created TIMESTAMP ;
		DECLARE  v_modifier VARCHAR(250);
		DECLARE  v_modified TIMESTAMP ;
		

		DECLARE  done_p INT ;  
		DECLARE  v_count INT ;      


        DECLARE  cursor_data CURSOR FOR 
		  SELECT 
				DSP.id,      
				DSP.state_id,
				DS.ticket_id,
				DSP.user_id,
				DSP.creator,
				DSP.created,
				DSP.modifier,
				DSP.modified
				
			 FROM ds_participant AS DSP
			 INNER JOIN daily_scrum AS DS ON DSP.daily_scrum_id = DS.id
			 WHERE  DSP.ticket_id IS NULL AND DS.ticket_id IS NOT NULL   
			 ORDER BY DSP.id DESC
             limit 10;
			 
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_p = 1;
         
        OPEN cursor_data;
		
        read_loop: LOOP
         
			FETCH cursor_data INTO 
			v_ds_participant_id,
			v_state_id,
			v_daily_scrum_id,
			v_user_id,
			v_creator,
			v_created,
			v_modifier,
			v_modified;
			
			IF done_p = 1 THEN
				LEAVE read_loop;
			END IF;
				
			
			SELECT count(*) INTO v_count FROM pts_ticket_import 
				WHERE kunde_ref=CONCAT(KUNDE_REF_PREFIX, v_ds_participant_id);
			
			IF (v_count =0)THEN
			 
				-- insert ds_participant into pts_ticket_import			    
				INSERT INTO pts_ticket_import 
					(
					kunde_ref,
					parent_ticket_id,
					responsible_id,
					state,
					type,
					creator,
					ts_insert,
					editor,
					ts_update
					)
					VALUES(
						CONCAT(KUNDE_REF_PREFIX, v_ds_participant_id),	  
						v_daily_scrum_id,
						v_user_id,
						v_state_id,
						1017907,
						v_creator,
						v_created,
						v_modifier,
						v_modified
						);

			UPDATE ds_participant SET exported_at = GREATEST(now(), modified)  WHERE id=v_ds_participant_id ;							
			   
			-- ELSE KUNDE_REF EXIST IN pts_ticket_import
			ELSE
				SELECT ticket_id INTO v_ticket_id FROM pts_ticket_import 
				WHERE kunde_ref=CONCAT(KUNDE_REF_PREFIX, v_ds_participant_id) and ticket_id is not null limit 1;
				
		        UPDATE ds_participant SET ticket_id = v_ticket_id WHERE id=v_ds_participant_id;

			 
		    END IF;
        END LOOP read_loop;
        CLOSE cursor_data;

      END;
$$
