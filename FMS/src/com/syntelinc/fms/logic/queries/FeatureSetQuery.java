package com.syntelinc.fms.logic.queries;

import java.util.List;
import org.springframework.context.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.syntelinc.fms.logic.FeatureSet;
import com.syntelinc.fms.logic.mappers.FeatureSetMapper;

public class FeatureSetQuery {
	ApplicationContext context ;
    JdbcTemplate temp;
    
    public FeatureSetQuery()
    {
    	context=new ClassPathXmlApplicationContext("spring-config.xml");
    	temp = (JdbcTemplate)context.getBean("jtemp");
    }
//	public List<FeatureSet> getRoomsSetByFeature(int featureID) 
//	{
//		//SELECT * FROM FM_FEATURE_SETS WHERE FID=?;
//		List<FeatureSet> f  = temp.query("SELECT * FROM FM_FEATURE_SETS WHERE FID=?",new FeatureSetMapper(),featureID);
//		return f;
//	}
	public List<FeatureSet> getFeaturesByRoom(int roomID) 
	{
		//SELECT * FROM FM_FEATURE_SETS WHERE ROOM_ID
		List<FeatureSet> f=temp.query("SELECT * FROM FM_FEATURE_SETS WHERE room_id=?",new FeatureSetMapper(),roomID);
		return f;
	}
	
	public int updateFeatureSet(FeatureSet fs) 
	{
		int qty = fs.getFeatureSetQuantity();
		int roomID  =fs.getFeatureSetRoom().getRoomID();
		int fid = fs.getFeatureSetFeature().getFeatureID();
		int f  = temp.update("UPDATE FM_FEATURE_SETS SET QTY=? WHERE ROOM_ID=? AND FID=?",qty,roomID,fid);
		//UPDATE FM_FEATURE_SETS SET QTY=? WHERE ROOM_ID=roomID AND FID =fid;
		return f;
	}
	public int insertFeatureSet(FeatureSet fs) 
	{
		int qty = fs.getFeatureSetQuantity();
		int roomID  =fs.getFeatureSetRoom().getRoomID();
		int fid = fs.getFeatureSetFeature().getFeatureID();
		//INSERT INTO FM_FEATURE_SETS VALUES(fid,roomID,qty);
		int f  = temp.update("INSERT INTO FM_FEATURE_SETS VALUES(?,?,?)",fid,roomID,qty);
		return f;
	}
	public int deleteFeatureSet(FeatureSet fs) 
	{
	
		int roomID  =fs.getFeatureSetRoom().getRoomID();
		int fid = fs.getFeatureSetFeature().getFeatureID();
		//DELETE FROM FM_FEATURE_SETS WHERE FID=FID AND ROOM_ID=roomID;
		int f  = temp.update("DELETE FROM FM_FEATURE_SETS WHERE FID=FID AND ROOM_ID=roomID",fid,roomID);
		return f;
	}
	public int chrisUpdateFeatureSet(int roomID, int chairs, int projectors, int whiteboards, int desktops, int televisions) 
	{
		//int f  = temp.update("UPDATE FM_FEATURE_SETS SET QTY=? WHERE ROOM_ID=? AND FID=?",qty,roomID,fid);
		//UPDATE FM_FEATURE_SETS SET QTY=? WHERE ROOM_ID=roomID AND FID =fid;
		temp.update("MERGE INTO fm_feature_sets dest USING( SELECT ? fid, ? room_id, ? qty FROM dual) src ON( dest.fid = src.fid AND dest.room_id = src.room_id ) WHEN MATCHED THEN UPDATE SET qty = src.qty WHEN NOT MATCHED THEN INSERT( fid, room_id, qty ) VALUES( src.fid, src.room_id, src.qty )", 1, roomID, chairs);
		temp.update("MERGE INTO fm_feature_sets dest USING( SELECT ? fid, ? room_id, ? qty FROM dual) src ON( dest.fid = src.fid AND dest.room_id = src.room_id ) WHEN MATCHED THEN UPDATE SET qty = src.qty WHEN NOT MATCHED THEN INSERT( fid, room_id, qty ) VALUES( src.fid, src.room_id, src.qty )", 2, roomID, projectors);
		temp.update("MERGE INTO fm_feature_sets dest USING( SELECT ? fid, ? room_id, ? qty FROM dual) src ON( dest.fid = src.fid AND dest.room_id = src.room_id ) WHEN MATCHED THEN UPDATE SET qty = src.qty WHEN NOT MATCHED THEN INSERT( fid, room_id, qty ) VALUES( src.fid, src.room_id, src.qty )", 3, roomID, whiteboards);
		temp.update("MERGE INTO fm_feature_sets dest USING( SELECT ? fid, ? room_id, ? qty FROM dual) src ON( dest.fid = src.fid AND dest.room_id = src.room_id ) WHEN MATCHED THEN UPDATE SET qty = src.qty WHEN NOT MATCHED THEN INSERT( fid, room_id, qty ) VALUES( src.fid, src.room_id, src.qty )", 4, roomID, desktops);
		temp.update("MERGE INTO fm_feature_sets dest USING( SELECT ? fid, ? room_id, ? qty FROM dual) src ON( dest.fid = src.fid AND dest.room_id = src.room_id ) WHEN MATCHED THEN UPDATE SET qty = src.qty WHEN NOT MATCHED THEN INSERT( fid, room_id, qty ) VALUES( src.fid, src.room_id, src.qty )", 5, roomID, televisions);
		/*temp.update("INSERT INTO FM_FEATURE_SETS (fid, room_id, qty) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE qty = VALUES(qty)", 1, roomID, chairs);
		temp.update("INSERT INTO FM_FEATURE_SETS (fid, room_id, qty) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE qty = VALUES(qty)", 2, roomID, projectors);
		temp.update("INSERT INTO FM_FEATURE_SETS (fid, room_id, qty) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE qty = VALUES(qty)", 3, roomID, whiteboards);
		temp.update("INSERT INTO FM_FEATURE_SETS (fid, room_id, qty) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE qty = VALUES(qty)", 4, roomID, desktops);
		temp.update("INSERT INTO FM_FEATURE_SETS (fid, room_id, qty) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE qty = VALUES(qty)", 5, roomID, televisions);*/
		return 1;
	}
}
