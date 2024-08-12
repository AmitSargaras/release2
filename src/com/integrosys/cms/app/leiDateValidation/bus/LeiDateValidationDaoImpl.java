package com.integrosys.cms.app.leiDateValidation.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.fileUpload.bus.OBLeiDetailsFile;
import com.integrosys.cms.ui.leiDateValidation.LeiDateValidationForm;

public class LeiDateValidationDaoImpl extends HibernateDaoSupport implements ILeiDateValidationDao{

	
	/**
	  * @return Particular LeiDateValidation according 
	  * to the id passed as parameter.  
	  * 
	  */

	public ILeiDateValidation getLeiDateValidation(String entityName, Serializable key)throws LeiDateValidationException {
		
		if(!(entityName==null|| key==null)){
		
		return (ILeiDateValidation) getHibernateTemplate().get(entityName, key);
		}else{
			throw new LeiDateValidationException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	/**
	 * @return LeiDateValidation Object
	 * @param Entity Name
	 * @param LeiDateValidation Object  
	 * This method Creates LeiDateValidation Object
	 */
	public ILeiDateValidation createLeiDateValidation(String entityName,
			ILeiDateValidation leiDateValidation)
			throws LeiDateValidationException {
		if(!(entityName==null|| leiDateValidation==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, leiDateValidation);
			leiDateValidation.setId(key.longValue());
			return leiDateValidation;
			}else{
				throw new LeiDateValidationException("ERROR- Entity name or key is null ");
			}
	}
	
	/**
	 * @return LeiDateValidation Object
	 * @param Entity Name
	 * @param LeiDateValidation Object  
	 * This method Updates LeiDateValidation Object
	 */
	
	public ILeiDateValidation updateLeiDateValidation(String entityName, ILeiDateValidation item)throws LeiDateValidationException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (ILeiDateValidation) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new LeiDateValidationException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	public boolean isPartyIDUnique(String partyID) {
			String actualQuery = "SELECT FROM "+ILeiDateValidationDao.ACTUAL_LEI_DATE_VALIDATION+" WHERE PARTY_ID like '"+partyID+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if(actualResultList.size()>0)
				return false;
			else
				return true;
	}
	
	
	public boolean isValidPartyID(String partyID) {
		String actualQuery = "SELECT FROM mainProfile WHERE LMP_LE_ID = '"+partyID+"'";
		ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
		if(actualResultList.size()>0)
			return true;
		else
			return false;
}
	
	public List getLeiDateValidationList(){
		List leiDateValidationList = new ArrayList();
		try{
			
			String query = "SELECT FROM "+ILeiDateValidationDao.ACTUAL_LEI_DATE_VALIDATION+" WHERE STATUS!='INACTIVE'";
			leiDateValidationList = (List) getHibernateTemplate().find(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return leiDateValidationList;
	}

	
	public Integer getLeiDateValidationPeriod(String partyID) {
		Integer actualResult = 0;
		try {
		String actualQuery = "SELECT FROM "+ILeiDateValidationDao.ACTUAL_LEI_DATE_VALIDATION+" WHERE PARTY_ID = '"+partyID+"' ";
		List<OBLeiDateValidation> actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if(actualResultList.size()>0) {
				actualResult = Integer.valueOf((String)actualResultList.get(0).getLeiDateValidationPeriod());
				String status = (String)actualResultList.get(0).getStatus();
				if("INACTIVE".equals(status)) {
					actualResult = 0;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return actualResult;
}
	
	public void updatePublicInputLEI(OBLeiDetailsFile obLeiDetailsFile) {
			String sqlQuery = " update mainProfile set LEI_CODE = '"+obLeiDetailsFile.getLeiCode() +"', LEI_EXPIRY_DATE = '"+obLeiDetailsFile.getLeiExpDate() +"'"
					+ " where LMP_LE_ID = '"+obLeiDetailsFile.getPartyId()+"';";
			
			getHibernateTemplate().update(sqlQuery);
	}
	
}
