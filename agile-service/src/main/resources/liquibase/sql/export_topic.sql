DROP PROCEDURE IF EXISTS EXPORT_TOPIC
$$
CREATE PROCEDURE EXPORT_TOPIC()
	BEGIN
	    DECLARE MY_PROC_NAME VARCHAR(250) DEFAULT 'EXPORT_TOPIC';
		DECLARE KUNDE_REF_PREFIX VARCHAR(250) DEFAULT 'PTS-AGILE_TOPIC_';       	
		DECLARE v_topic_id BIGINT(20); 
		DECLARE v_state_id BIGINT(20); 
		DECLARE v_product_id BIGINT(20); 
		DECLARE v_project VARCHAR(100) CHARACTER SET UTF8; 
		DECLARE v_name VARCHAR(250) CHARACTER SET UTF8;
		DECLARE v_description TEXT CHARACTER SET UTF8;
		DECLARE v_display_order BIGINT(20);
		DECLARE v_is_default BIGINT(20);
		DECLARE v_pts_ticket_id BIGINT(20);
		DECLARE v_creator VARCHAR(250) CHARACTER SET UTF8;
		DECLARE v_created DATETIME ;
		DECLARE v_modifier VARCHAR(250) CHARACTER SET UTF8;
		DECLARE v_modified DATETIME ;
		DECLARE done_p INT ;
		DECLARE v_count INT ;

        DECLARE  cursor_data CURSOR FOR 
		    SELECT 
				topic.id,      
				topic.state_id,
				topic.product_id,
				topic.project,
				topic.name,
				topic.description,
				topic.display_order,
				IF(dt.id is null, 0, 1) is_default,
				topic.creator,
				topic.created,
				topic.modifier,
				topic.modified
			FROM topic
			LEFT JOIN default_topic dt ON dt.topic_id = topic.id
			WHERE topic.ticket_id IS NULL 
			ORDER BY topic.id DESC
            LIMIT 10;
			 
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_p = 1;
         
        OPEN cursor_data;
		
        read_loop: LOOP
         
			FETCH cursor_data INTO 
			v_topic_id,
			v_state_id,
			v_product_id,
			v_project,
			v_name,
			v_description,
			v_display_order,
			v_is_default,
			v_creator,
			v_created,
			v_modifier,
			v_modified;
			
			IF done_p = 1 THEN
				LEAVE read_loop;
			END IF;
				
			SELECT count(*) INTO v_count FROM pts_ticket_import 
				WHERE kunde_ref=CONCAT(KUNDE_REF_PREFIX, v_topic_id);
			
			-- log text 
			-- CALL pts_log.i0(MY_PROC_NAME ,concat('Try to Export Topic: ', v_topic_id, ', count entries in  pts_ticket_import ',v_count ));
			
			IF (v_count = 0)THEN
				-- insert topic into pts_ticket_import			    
				INSERT INTO pts_ticket_import 
					(
					kunde_ref,
					state,
					product_id,
					project,
					short_description,
					long_description,
					amount_1,
					fl1,
					type,
					creator,
					ts_insert,
					editor,
					ts_update
					)
					VALUES(
						CONCAT(KUNDE_REF_PREFIX, v_topic_id),	  
						v_state_id,
						v_product_id,
						v_project,
						v_name,
						v_description,
						v_display_order,
						v_is_default,
						1018012,
						v_creator,
						v_created,
						v_modifier,
						v_modified
						);

			UPDATE topic SET exported_at = GREATEST(now(), modified)  WHERE id=v_topic_id ;	
			   
			-- ELSE KUNDE_REF EXIST IN pts_ticket_import
			ELSE
				SELECT ticket_id INTO v_pts_ticket_id FROM pts_ticket_import 
				WHERE kunde_ref=CONCAT(KUNDE_REF_PREFIX, v_topic_id) and ticket_id is not null limit 1;
				
				-- CALL pts_log.i0(MY_PROC_NAME ,concat( 'topic id : ', v_topic_id, ', upadted with topic_id : ', v_topic_id));
		        UPDATE topic SET ticket_id = v_pts_ticket_id WHERE id=v_topic_id and ticket_id is null;

		    END IF;
        END LOOP read_loop;
        CLOSE cursor_data;
	END;
	$$