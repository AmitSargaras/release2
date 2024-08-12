package com.integrosys.cms.app.user.bus;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.state.bus.OBState;
import com.integrosys.cms.app.systemBank.bus.SystemBankJdbcImpl.SystemBankRowMapper;
/**
 * @author $Author: Abhijit R $<br>
 * 
 * Dao Implication declares the methods used by Bus manager Implication
 */
public class SMSUserErrorLogDaoImpl extends HibernateDaoSupport implements
		ISMSUserErrorLogDao {
	
	
	public ISMSUserErrorLog createSMSUserErrorLog(ISMSUserErrorLog SMSUserErrorLog)throws Exception {
		if(!(SMSUserErrorLog==null)){
		
		Long key = (Long) getHibernateTemplate().save(ISMSUserErrorLogDao.ACTUAL_SMS_USER_ERROR_LOG, SMSUserErrorLog);
		SMSUserErrorLog.setId(key.longValue());
		return SMSUserErrorLog;
		}else{
			throw new Exception("ERROR- Entity name or key is null ");
		}
	}

}
