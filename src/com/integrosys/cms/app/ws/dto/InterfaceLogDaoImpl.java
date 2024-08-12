package com.integrosys.cms.app.ws.dto;

import java.io.Serializable;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * @author $Author: Bharat Waghela $<br>
 * 
 *         Dao Implication declares the methods used by Bus manager Implication
 */

public class InterfaceLogDaoImpl extends HibernateDaoSupport implements
		IInterfaceLogDao {

	private static final String DATASOURCE_JNDI_KEY = "dbconfig.weblogic.datasource.jndiname";
	
    private IInterfaceLogDao interfaceLogDao;
	
	public IInterfaceLogDao getHolidayDao() {
		return interfaceLogDao;
	}
	public void setInterfaceLogDao(IInterfaceLogDao interfaceLogDao) {
		this.interfaceLogDao = interfaceLogDao;
	}
	
	
	public String getEntityName() {
		return IInterfaceLogDao.ACTUAL_INTERFACE_LOG_NAME;
	}

	/**
	 * @return PartyGroup Object
	 * @param Entity
	 *            Name
	 * @param Key
	 *            This method returns party group Object
	 */
	public IInterfaceLogDao getInterfaceLog(String entityName, Serializable key)
			throws InterfaceLogException {
		if (!(entityName == null || key == null)) {
			return (IInterfaceLogDao) getHibernateTemplate().get(entityName, key);
		} else {
			throw new InterfaceLogException("ERROR- Entity name or key is null ");
		}
	}

	public IInterfaceLogDao getInterfaceLogById( Serializable key)
	throws InterfaceLogException {
if (!(key == null)) {
	return (IInterfaceLogDao) getHibernateTemplate().get(getEntityName(), key);
} else {
	throw new InterfaceLogException("ERROR- Entity name or key is null ");
}
}
	public IInterfaceLogDao deleteInterfaceLog(String entityName, IInterfaceLog item)
			throws InterfaceLogException {

		if (!(entityName == null || item == null)) {
			getHibernateTemplate().update(entityName, item);
			return (IInterfaceLogDao) getHibernateTemplate().load(entityName,
					new Long(item.getId()));
		} else {
			throw new InterfaceLogException("ERROR-- Entity Name Or Key is null");
		}
	}

	public IInterfaceLogDao enableInterfaceLog(String entityName, IInterfaceLog item)
			throws InterfaceLogException {

		if (!(entityName == null || item == null)) {
			getHibernateTemplate().update(entityName, item);
			return (IInterfaceLogDao) getHibernateTemplate().load(entityName,
					new Long(item.getId()));
		} else {
			throw new InterfaceLogException("ERROR-- Entity Name Or Key is null");
		}
	}

	/**
	 * @return PartyGroup Object
	 * @param Entity
	 *            Name
	 * @param PartyGroup
	 *            Object This method Updates Party group Object
	 */
	public IInterfaceLogDao updateInterfaceLog(String entityName, IInterfaceLog item)
			throws InterfaceLogException {
		if (!(entityName == null || item == null)) {
			getHibernateTemplate().update(entityName, item);

			return (IInterfaceLogDao) getHibernateTemplate().load(entityName,
					new Long(item.getId()));
		} else {
			throw new InterfaceLogException("ERROR- Entity name or key is null ");
		}

	}

	//@Transactional(propagation = Propagation.REQUIRES_NEW)
	public IInterfaceLog createInterfaceLog(IInterfaceLog interfaceLog)
			throws InterfaceLogException {
		IInterfaceLog returnObj = new OBInterfaceLog();
		try {
			getHibernateTemplate().save(getEntityName(), interfaceLog);
			
			returnObj = (IInterfaceLog) getHibernateTemplate().load(
					getEntityName(), new Long(interfaceLog.getId()));
		} catch (Exception obe) {
			DefaultLogger.error(this,
					"############# error in createInterfaceLog ", obe);
			obe.printStackTrace();
			throw new InterfaceLogException("Unable to generate InterfaceLog ");
		}
		return returnObj;
	}

public OBInterfaceLog  getActualFromDTO( InterfaceLoggingDTO dto ) throws InterfaceLogException {
		
	    OBInterfaceLog interfaceLog = new OBInterfaceLog();
		
		interfaceLog.setInterfaceName(dto.getInterfaceName()) ;
		interfaceLog.setRequestMessage(dto.getRequestMessage()) ;
		interfaceLog.setResponseMessage(dto.getResponseMessage());
		interfaceLog.setStatus(dto.getStatus()) ;
		interfaceLog.setRequestDateTime(dto.getRequestDate()) ;
		interfaceLog.setResponseDateTime(dto.getResponseDate()) ;
		interfaceLog.setErrorMessage(dto.getErrorMessage());
		interfaceLog.setErrorCode(dto.getErrorCode());
		interfaceLog.setTransactionId(dto.getTransactionId()); 
		interfaceLog.setWsClient(dto.getWsClient()) ;

		interfaceLog.setPartyId(dto.getPartyId()) ;
		interfaceLog.setPartyName(dto.getPartyName()) ;
		
		interfaceLog.setCamId(dto.getCamId()) ;
		interfaceLog.setCamNo(dto.getCamNo()) ;
		
		interfaceLog.setFacilityId(dto.getFacilityId());
		interfaceLog.setSecurityId(dto.getSecurityId());
		interfaceLog.setCpsSecId(dto.getCpsSecId());
		
		interfaceLog.setCpsDocId(dto.getCpsDocId());
		interfaceLog.setClimsDocId(dto.getClimsDocId());
		
		interfaceLog.setCpsDiscrId(dto.getCpsDiscrId()) ;
		interfaceLog.setClimsDiscrId(dto.getClimsDiscrId()) ;

		interfaceLog.setI_name(dto.getI_name()) ;
		interfaceLog.setParticulars(dto.getParticulars()) ;
		interfaceLog.setOperation(dto.getOperation()) ;
		
		interfaceLog.setSecSubType(dto.getSecSubType()) ;
		
		interfaceLog.setCamExtDate(dto.getCamExtDate()) ;
		
		interfaceLog.setIsacReferenceNo(dto.getIsacReferenceNo()) ;
		interfaceLog.setIsacErrorCode(dto.getIsacErrorCode()) ;
		interfaceLog.setIsacErrorMessage(dto.getIsacErrorMessage()) ;
		interfaceLog.setIsacmakerid(dto.getIsacmakerid());
		interfaceLog.setIsaccheckerid(dto.getIsaccheckerid());
		
		return interfaceLog;
	}
}
