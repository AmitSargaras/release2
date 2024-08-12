package com.integrosys.cms.app.user.bus;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.hibernate.Query;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
/**
 * @author $Author: Abhijit R $<br>
 * 
 * Dao Implication declares the methods used by Bus manager Implication
 */
public class SMSUserLogDaoImpl extends HibernateDaoSupport implements
		ISMSUserLogDao {
	
	
	

	public ISMSUserLog createSMSUserLog(ISMSUserLog SMSUserLog)throws Exception {
		if(!(SMSUserLog==null)){
		SMSUserLog.setUploadId(genrateUserSegmentSeq());
		Long key = (Long) getHibernateTemplate().save(ISMSUserLogDao.ACTUAL_SMS_USER_LOG, SMSUserLog);
		SMSUserLog.setId(key.longValue());
		return SMSUserLog;
		}else{
			throw new Exception("ERROR- Entity name or key is null ");
		}
	}
	private String getSMSLogCode() {
		Query query = currentSession().createSQLQuery("SELECT SMS_LOG_SEQ.NEXTVAL FROM dual");
		String sequenceNumber = query.uniqueResult().toString();
		NumberFormat numberFormat = new DecimalFormat("0000000");
		String smsLogCode = numberFormat.format(Long.parseLong(sequenceNumber));
		smsLogCode = "SMSLOG" + smsLogCode;		
//		System.out.println(":::::::::::::::::::smsLogCode:::::::::::::::::"+smsLogCode);
		return smsLogCode;
	}
	
	private String genrateUserSegmentSeq() throws RemoteException {
		SequenceManager seqmgr = new SequenceManager();
		String seq = null;
		try {
			seq = seqmgr.getSeqNum("SMS_LOG_SEQ", true);
			DefaultLogger.debug(this, "Generated sequence " + seq);
			NumberFormat numberFormat = new DecimalFormat("0000000");
			String docCode = numberFormat.format(Long.parseLong(seq));
			docCode = "LOG" + docCode;	
			return docCode;
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception occured while generating sequence   " + e.getMessage());
			throw new RemoteException("Exception in creating the user id", e);
		}
	}
}
