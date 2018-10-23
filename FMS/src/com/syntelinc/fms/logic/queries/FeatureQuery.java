package com.syntelinc.fms.logic.queries;
import org.springframework.context.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.syntelinc.fms.logic.Feature;
import com.syntelinc.fms.logic.mappers.FeatureMapper;
public class FeatureQuery {
	ApplicationContext context ;
    JdbcTemplate temp;
    
    public FeatureQuery()
    {
    	context=new ClassPathXmlApplicationContext("spring-config.xml");
    	temp = (JdbcTemplate)context.getBean("jtemp");
    }
	public Feature getFeatureByID(int id) 
	{
		//SELECT * FROM FM_FEATURES WHERE FID= ?;
		try {
			Feature f  = temp.queryForObject("SELECT * FROM FM_FEATURES WHERE FID=?",new FeatureMapper(),id);
			return f;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public Feature getFeatureByName(String name) {
		//SELECT * FROM FM_FEATURES WHERE FDESC=?;
		try {
			Feature f  = temp.queryForObject("SELECT * FROM FM_FEATURES WHERE FDESC=?",new FeatureMapper(),name);
			return f;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	public int updateFeature(Feature f) 
	{
		String fd=f.getFeatureDescription();
		int id=f.getFeatureID();
		//UPDATE FM_FEATURES SET FDESC=? WHERE FID=?;
		int f1  = temp.update("UPDATE FM_FEATURES SET FDESC=? WHERE FID=?",fd,id);
		return f1;
	}
	
	public int insertFeature(Feature f) {
		String fd=f.getFeatureDescription();
		int id=f.getFeatureID();
		//Insert into fm_features values(fid,fdesc);
		int f1  = temp.update("Insert into fm_features values(?,?)",id,fd);
		return f1;

	}
	
}
