package com.integrosys.cms.app.systemBank.bus;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
/**
 * @author $Author: Abhijit R $<br>
 * 
 * Dao Implication declares the methods used by Bus manager Implication
 */
public class SystemBankDaoImpl extends HibernateDaoSupport implements
		ISystemBankDao {
	
	
	
	private static final String SELECT_SYSTEM_BANK_TRX = "SELECT id,system_bank_code,system_bank_name,city_town,address,state,country,region,contact_number,contact_mail,last_update_date from CMS_SYSTEM_BANK where master_id is not null ";

	/**
	 * @return SystemBank Object
	 * @param Entity Name
	 * @param Key  
	 * This method returns System Bank Object
	 * 
	 * 
	 */
	
	
	public String getEntityName(){
		return ISystemBankDao.ACTUAL_SYSTEM_BANK_NAME; 
	}
	
	
	public ISystemBank getSystemBank(String entityName, Serializable key)throws SystemBankException {
		if(!(entityName==null|| key==null)){
		return (ISystemBank) getHibernateTemplate().get(entityName, key);
		}else{
			throw new SystemBankException("ERROR- Entity name or key is null ");
		}
	}

	/**
	 * @return SystemBank Object
	 * @param Entity Name
	 * @param SystemBank Object  
	 * This method Updates System Bank Object
	 */
	public ISystemBank updateSystemBank(String entityName, ISystemBank item)throws SystemBankException {
		if(!(entityName==null|| item==null)){
		getHibernateTemplate().update(entityName, item);

		return (ISystemBank) getHibernateTemplate().load(entityName,
				new Long(item.getId()));
		}else{
			throw new SystemBankException("ERROR- Entity name or key is null ");
		}

	}
	/**
	 * @return SystemBank Object
	 * @param Entity Name
	 * @param SystemBank Object  
	 * This method Creates System Bank Object
	 */

	public ISystemBank createSystemBank(String entityName,
			ISystemBank systemBank)throws SystemBankException {
		if(!(entityName==null|| systemBank==null)){
		
		Long key = (Long) getHibernateTemplate().save(entityName, systemBank);
		systemBank.setId(key.longValue());
		return systemBank;
		}else{
			throw new SystemBankException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return List of all authorized System Bank
	 */
	
	public List getAllSystemBank() {
		//List resultList = null;
		String entityName = "actualOBSystemBank";
		String query = "FROM "+entityName +" where STATUS='ACTIVE'";
		ArrayList resultList;
		
		if( query != "" ){
			return resultList = (ArrayList) getHibernateTemplate().find(query);
			
		}else{
					throw new SystemBankException(
					"ERROR-- Unable to get System Bank List");
		}

		
	}
	
	
	
	/**
	 * @return List of all  System Bank according to the query passed.
     */
	
	public List listSystemBank(long bankCode) throws SearchDAOException {
		
		String entityName = "actualOBSystemBank";
		try{
		String query = "";
		if( bankCode != 0 )
			query = "SELECT FROM "+ entityName +" systemBank WHERE systemBank.id = "+new Long(bankCode);
		else
			query = "SELECT FROM "+ entityName;
		
		 ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			return resultList;
		}catch (Exception e) {
			throw new SystemBankException(
			"ERROR-- Unable to get System Bank List");
		}	
		
	}
	
	
	public ISystemBank getSystemBankById(long id) throws SystemBankException {
		ISystemBank systemBank = new OBSystemBank();
		try{
			systemBank = (ISystemBank)getHibernateTemplate().load(getEntityName(), new Long(id));
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getOtherBankById ",obe);
			obe.printStackTrace();
			throw new SystemBankException("Unable to find other bank with id ["+id+"]");
		}
		return systemBank;
	}

	public ISystemBank getSystemBankByCode(String bankCode) throws SystemBankException {
		//ISystemBank systemBank = new OBSystemBank();
		try{
			String query = "";
			
				query = "SELECT FROM "+ getEntityName() +" systemBank WHERE systemBank.systemBankCode = '"+bankCode+"'";
				
			 ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			 
			 if(resultList!=null && resultList.size() >0){
				 ISystemBank systemBank=(ISystemBank)resultList.get(0);
				 return systemBank;
			 }
			 
			return null;
			
			
			
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getSystemBankByCode ",obe);
			obe.printStackTrace();
			throw new SystemBankException("Unable to find other bank with code ["+bankCode+"]");
		}
		
				
	}
}
