DROP PROCEDURE IF EXISTS EXPORT_SPRINT
$$
CREATE PROCEDURE `EXPORT_SPRINT`()
BEGIN
	    DECLARE MY_PROC_NAME VARCHAR(250) DEFAULT 'EXPORT_SPRINT';
		DECLARE KUNDE_REF_PREFIX VARCHAR(250) DEFAULT 'PTS-AGILE_SPRINT_';       	

		DECLARE v_sprint_id,
		        v_ticket_id,
		        v_product_id,
		        v_state_id,
				v_ds_duration,
				v_pts_ticket_id BIGINT(20);
		DECLARE v_name,
		        v_short_name VARCHAR(100) CHARACTER SET UTF8;
 
		DECLARE v_project,
				v_parent VARCHAR(100);

		DECLARE v_creator,
		        v_modifier VARCHAR(250);

		DECLARE v_ds_meeting_url VARCHAR(500);

		DECLARE v_start_date,
		        v_end_date,
				v_ds_start_time,
		        v_created,
				v_modified TIMESTAMP ;
		
		DECLARE done_p, 
		        v_count	INT;

        DECLARE  cursor_data CURSOR FOR 
		  SELECT
		  SP.id,
          SP.ticket_id,
          SP.product_id,
          SP.state_id,
          SP.ds_duration,
          SP.name,
          SP.short_name,
          SP.project,
          SP.parent,
          SP.creator,
          SP.modifier,
          SP.ds_meeting_url,
          SP.start_date,
          SP.end_date,
          SP.ds_start_time,
          SP.created,
          SP.modified
        FROM sprint AS SP
         WHERE  SP.ticket_id IS NULL
		 ORDER BY SP.id DESC
        limit 10;
			 
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_p = 1;
         
        OPEN cursor_data;
	
        read_loop: LOOP
         
			FETCH cursor_data INTO
			v_sprint_id,
            v_ticket_id,
            v_product_id,
            v_state_id,
		    v_ds_duration,
			v_name,
            v_short_name,
		    v_project,
		    v_parent,
            v_creator,
            v_modifier,
			v_ds_meeting_url,
			v_start_date,
            v_end_date,
		    v_ds_start_time,
            v_created,
			v_modified;
			
			IF done_p = 1 THEN
				LEAVE read_loop;
			END IF;

			SELECT COUNT(*) INTO v_count
			FROM
			    pts_ticket_import
			WHERE
			    kunde_ref = CONCAT(KUNDE_REF_PREFIX, v_sprint_id);
			-- log text 
			-- CALL pts_log.i0(MY_PROC_NAME ,concat('Try to Export Sprint: ', v_sprint_id, ', count entries in  pts.pts_ticket ',v_count )); 
			
			IF (v_count =0)THEN
			 
				-- insert sprint into pts_ticket			    
				INSERT INTO pts_ticket_import
					(
					kunde_ref,
                    state,
                    product_id,
                    project,
                    short_description,
                    file_name_1,
                    lock_date,
                    mail_date,
                    target_date,
                    fl1,
                    type,
                    company,
                    parent_ticket_id,
					creator,
					ts_insert,
					editor,
					ts_update
					)
					VALUES(
						CONCAT(KUNDE_REF_PREFIX, v_sprint_id),	  
                         v_state_id,
                         v_product_id,
                         v_project,
                         v_name,
                         v_short_name,
                         v_start_date,
                         v_end_date,
                         v_ds_start_time,
                         v_ds_duration,
                         1017761,
                         v_ds_meeting_url,
                         v_parent,
						 v_creator,
						 v_created,
						 v_modifier,
						 v_modified
						);
				UPDATE sprint SET exported_at = GREATEST(now(), modified) WHERE id=v_sprint_id ;		

				-- log text 
				-- CALL pts_log.i0(MY_PROC_NAME ,concat( 'sprint id : ', v_sprint_id, ', sprint inserted in pts_ticket'));	
			   
			-- ELSE KUNDE_REF EXIST IN pts_ticket
			ELSE
				SELECT ticket_id INTO v_pts_ticket_id FROM pts_ticket_import 
				WHERE kunde_ref=CONCAT(KUNDE_REF_PREFIX, v_sprint_id) limit 1;
				-- log text
				UPDATE sprint 
				SET 
				    ticket_id = v_pts_ticket_id
				WHERE
				    id = v_sprint_id and ticket_id is null;
		    END IF;
        END LOOP read_loop;
        CLOSE cursor_data;

      END;
$$