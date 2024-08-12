package com.integrosys.cms.app.manualinput.party.bus;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.manualinput.party.IPANValidationLog;
import com.integrosys.cms.app.manualinput.party.PANValidationLog;

/**
 * @author $Author: Ankit.Limbadia $<br>
 * 
 *         Dao Implication declares the methods used by Bus manager Implication
 */

public class PanValidationLogDaoImpl extends HibernateDaoSupport implements
	IPanValidationLogDao {

	public String getEntityName() {
		return IPanValidationLogDao.ACTUAL_INTERFACE_LOG_NAME;
	}
	
	//@Transactional(propagation = Propagation.REQUIRES_NEW)
	public IPANValidationLog createInterfaceLog(IPANValidationLog interfaceLog){
//		DefaultLogger.info(this,"interfaceLog.getPanNo():::"+interfaceLog.getPanNo());
		IPANValidationLog returnObj = new PANValidationLog();
		try {
			getHibernateTemplate().save(getEntityName(), interfaceLog);
			
			returnObj = (IPANValidationLog) getHibernateTemplate().load(
					getEntityName(), new Long(interfaceLog.getId()));
			DefaultLogger.info(this,"PANValidationLog created successfully!!");
		} catch (Exception obe) {
			DefaultLogger.error(this,"############# error in PanValidationLogDaoImpl:::createInterfaceLog ", obe);
			obe.printStackTrace();
		}
		return returnObj;
	}

	@Override
	public IPANValidationLog updateInterfaceLog(String entityName,
			IPANValidationLog item) {
		return null;
	}

	public Boolean checkPANAlreadyValidated (String entityName, String panNo,String partyId, String partyNameAsPerPan, String dateOfIncorporation) {
		
		Boolean flag = false;
		try{
			Map parameters = new HashMap();
			parameters.put("panNo", panNo);
			parameters.put("status", "Success");
			parameters.put("isPANNoValidated", "Y");
			parameters.put("partyNameAsPerPan", partyNameAsPerPan);
			parameters.put("dateOfIncorporation", dateOfIncorporation);
//			parameters.put("responseCode", "01");
			if(partyId!=null && !partyId.isEmpty()){
				parameters.put("partyID", partyId);
			}
			
			DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parameters))
			.addOrder(Order.desc("id"));
			
			List list = getHibernateTemplate().findByCriteria(criteria);
			
			if (list != null && !list.isEmpty() && list.size()>0) {
				flag = true;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public Date fetchLastValidatedDate (String entityName, String panNo,String partyId) {
		
		Date lastValidatedDate = null;
		try{
			Map parameters = new HashMap();
			parameters.put("panNo", panNo);
			parameters.put("status", "Success");
			parameters.put("isPANNoValidated", "Y");
			if(partyId!=null && !partyId.isEmpty()){
				parameters.put("partyID", partyId);
			}
			
			DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parameters))
			.addOrder(Order.desc("id"));
			
			List list = getHibernateTemplate().findByCriteria(criteria);
			
			if (list != null && !list.isEmpty() && list.size()>0) {
				lastValidatedDate = ((PANValidationLog)list.get(0)).getLastValidatedDate();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return lastValidatedDate;
	}
	
	/*public String getSequenceNoForRequestNo() {
		Query query = getSession().createSQLQuery("SELECT PAN_VALIDATION_REQNO_SEQ.NEXTVAL FROM dual");
		String sequenceNumber = query.uniqueResult().toString();
		NumberFormat numberFormat = new DecimalFormat("000000000");
		String requestNo = numberFormat.format(Long.parseLong(sequenceNumber));
		return requestNo;
	}*/
}
