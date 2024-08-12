package com.integrosys.cms.app.insurancecoveragedtls.bus;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.insurancecoveragedtls.IInsuranceCoverageDtls;

/**
 * Purpose : This InsuranceCoverageDtlsDAOImpl implements the methods that will be available to the
 * operating on a Insurance Covearge Details 
 *  
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 */
public class InsuranceCoverageDtlsDAOImpl extends HibernateDaoSupport implements IInsuranceCoverageDtlsDAO {
	/**
	 * @return String entity name
	 */
	public String getEntityName(){
		return IInsuranceCoverageDtlsDAO.ACTUAL_ENTITY_NAME; 
	}
	
	/**
	 * returns SearchResult List of Insurance Coverage Details
	 */
	public SearchResult getInsuranceCoverageDtls() throws InsuranceCoverageDtlsException{

		try{
			ArrayList resultList = (ArrayList) getHibernateTemplate().loadAll(OBInsuranceCoverageDtls.class);
			return new SearchResult(0, resultList.size(), resultList.size(), resultList);
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getInsuranceCoverageDtls",obe);
			obe.printStackTrace();
			throw new InsuranceCoverageDtlsException("Unable to get Insurance Coverage");
		}
	}

	/**
	 * Returns Insurance Coverage Details present for the  input id
	 */
	public IInsuranceCoverageDtls getInsuranceCoverageDtlsById(long id) throws InsuranceCoverageDtlsException {
		IInsuranceCoverageDtls InsuranceCoverageDtls = new OBInsuranceCoverageDtls();
		try{
			InsuranceCoverageDtls = (IInsuranceCoverageDtls)getHibernateTemplate().load(getEntityName(), new Long(id));
			return InsuranceCoverageDtls;
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getInsuranceCoverageDtlsById ",obe);
			obe.printStackTrace();
			throw new InsuranceCoverageDtlsException("Unable to find Insurance Coverage with id ["+id+"]");
		}
	
	}

	/**
	 * Return List of Insurance Coverage Details .
	 */
	public SearchResult getInsuranceCoverageDtlsList() throws InsuranceCoverageDtlsException{

		try{
			String query = "SELECT FROM "+getEntityName()+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y'";
			ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			return new SearchResult(0, resultList.size(), resultList.size(), resultList);
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getInsuranceCoverageDtlsList ",obe);
			obe.printStackTrace();
			throw new InsuranceCoverageDtlsException("Unable to get Insurance Coverage List");
		}
		
	}

	/**
	 * Updates the Insurance Coverage Details 
	 */
	public IInsuranceCoverageDtls updateInsuranceCoverageDtls(IInsuranceCoverageDtls InsuranceCoverageDtls) throws InsuranceCoverageDtlsException {

		try{
			getHibernateTemplate().saveOrUpdate(getEntityName(), InsuranceCoverageDtls);
			IInsuranceCoverageDtls returnObj = (IInsuranceCoverageDtls) getHibernateTemplate().load(getEntityName(),new Long(InsuranceCoverageDtls.getId()));
			return returnObj;
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in updateInsuranceCoverageDtls ",obe);
			obe.printStackTrace();
			throw new InsuranceCoverageDtlsException("Unable to update Insurance Coverage with id ["+InsuranceCoverageDtls.getId()+"]");
		}
		
	}
	
	/**
	 * soft delete the Insurance Coverage Details 
	 */
	
	public IInsuranceCoverageDtls deleteInsuranceCoverageDtls(IInsuranceCoverageDtls InsuranceCoverageDtls) throws InsuranceCoverageDtlsException {
		IInsuranceCoverageDtls returnObj = new OBInsuranceCoverageDtls();
		try{
			InsuranceCoverageDtls.setStatus("INACTIVE");
			InsuranceCoverageDtls.setDeprecated("Y");
			getHibernateTemplate().saveOrUpdate(getEntityName(), InsuranceCoverageDtls);
			returnObj = (IInsuranceCoverageDtls) getHibernateTemplate().load(getEntityName(),new Long(InsuranceCoverageDtls.getId()));
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in deleteInsuranceCoverageDtls ",obe);
			obe.printStackTrace();
			throw new InsuranceCoverageDtlsException("Unable to delete Insurance Coverage with id ["+InsuranceCoverageDtls.getId()+"]");
		}
		return returnObj;
	}
	
	/**
	 * Create the Insurance Coverage Details 
	 */
	public IInsuranceCoverageDtls createInsuranceCoverageDtls(IInsuranceCoverageDtls InsuranceCoverageDtls) throws InsuranceCoverageDtlsException {
		IInsuranceCoverageDtls returnObj = new OBInsuranceCoverageDtls();
		try{
			getHibernateTemplate().save(getEntityName(), InsuranceCoverageDtls);
			returnObj = (IInsuranceCoverageDtls) getHibernateTemplate().load(getEntityName(),new Long(InsuranceCoverageDtls.getId()));
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in createInsuranceCoverageDtls ",obe);
			obe.printStackTrace();
			throw new InsuranceCoverageDtlsException("Unable to create Insurance Coverage ");
		}
		return returnObj;
	}

	/**
	 * @return InsuranceCoverageDtls Object
	 * @param Entity Name
	 * @param Key  
	 * This method returns Insurance Coverage Details Object
	 */
	public IInsuranceCoverageDtls getInsuranceCoverageDtls(String entityName, Serializable key)throws InsuranceCoverageDtlsException {
		if(!(entityName==null|| key==null)){
		return (IInsuranceCoverageDtls) getHibernateTemplate().get(entityName, key);
		}else{
			throw new InsuranceCoverageDtlsException("ERROR- Entity name or key is null ");
		}
	}

	/**
	 * @return InsuranceCoverageDtls Object
	 * @param Entity Name
	 * @param InsuranceCoverageDtls Object  
	 * This method Updates Insurance Coverage Details Object
	 */
	public IInsuranceCoverageDtls updateInsuranceCoverageDtls(String entityName, IInsuranceCoverageDtls item)throws InsuranceCoverageDtlsException {
		if(!(entityName==null|| item==null)){
		getHibernateTemplate().update(entityName, item);

		return (IInsuranceCoverageDtls) getHibernateTemplate().load(entityName,
				new Long(item.getId()));
		}else{
			throw new InsuranceCoverageDtlsException("ERROR- Entity name or key is null ");
		}

	}
	/**
	 * @return InsuranceCoverageDtls Object
	 * @param Entity Name
	 * @param InsuranceCoverageDtls Object  
	 * This method Creates Insurance Coverage Details Object
	 */

	public IInsuranceCoverageDtls createInsuranceCoverageDtls(String entityName,
			IInsuranceCoverageDtls insuranceCoverage)throws InsuranceCoverageDtlsException {
		if(!(entityName==null|| insuranceCoverage==null)){
		
		Long key = (Long) getHibernateTemplate().save(entityName, insuranceCoverage);
		insuranceCoverage.setId(key.longValue());
		return insuranceCoverage;
		}else{
			throw new InsuranceCoverageDtlsException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return boolean flag
	 * @param Insurance Coverage Code
	 * This method check weather entered IC Code does exists or not.
	 */
	public boolean isICCodeUnique(String rmCode){
		String query = "SELECT FROM "+IInsuranceCoverageDtlsDAO.STAGING_ENTITY_NAME+" WHERE INSURANCE_COVERAGE_CODE="+rmCode;
		ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
		if(resultList.size()>0){
			String query1 = "SELECT FROM "+getEntityName()+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND INSURANCE_COVERAGE_CODE="+rmCode;
			ArrayList resultList1 = (ArrayList) getHibernateTemplate().find(query1);
			if(resultList1.size()>0)
				return true;
			else
				return false;
		}	
		else 
			return false;
		 
	}
	
}
