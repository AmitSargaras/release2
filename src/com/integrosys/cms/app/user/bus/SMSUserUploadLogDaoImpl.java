package com.integrosys.cms.app.user.bus;

import java.util.Collection;
import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
/**
 * @author $Author: Abhijit R $<br>
 * 
 * Dao Implication declares the methods used by Bus manager Implication
 */
public class SMSUserUploadLogDaoImpl extends HibernateDaoSupport implements
		ISMSUserUploadLogDao {
	
	
	public ISMSUserUploadLog createSMSUserUploadLog(ISMSUserUploadLog SMSUserUploadLog)throws Exception {
		if(!(SMSUserUploadLog==null)){
			//Collection collection = (List)getHibernateTemplate().find("FROM "+ISMSUserUploadLogDao.ACTUAL_SMS_USER_UPLOAD_LOG);
			//getHibernateTemplate().deleteAll(collection);
		Long key = (Long) getHibernateTemplate().save(ISMSUserUploadLogDao.ACTUAL_SMS_USER_UPLOAD_LOG, SMSUserUploadLog);
		SMSUserUploadLog.setId(key.longValue());
		return SMSUserUploadLog;
		}else{
			throw new Exception("ERROR- Entity name or key is null ");
		}
	}

}
