package com.integrosys.cms.app.facilityNewMaster.bus;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import org.hibernate.Query;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMasterDao;
/**
 * @author  Abhijit R. 
 * Dao implication of interface FacilityNewMaster
 */

public class FacilityNewMasterDaoImpl extends HibernateDaoSupport implements IFacilityNewMasterDao{
	
	/**
	  * @return Particular FacilityNewMaster according 
	  * to the id passed as parameter.  
	  * 
	  */

	public IFacilityNewMaster getFacilityNewMaster(String entityName, Serializable key)throws FacilityNewMasterException {
		
		if(!(entityName==null|| key==null)){
		
		return (IFacilityNewMaster) getHibernateTemplate().get(entityName, key);
		}else{
			throw new FacilityNewMasterException("ERROR-- Entity Name Or Key is null");
		}
	}

	
	public IFacilityNewMaster getFacilityNewMasterRiskType(String facilityCat,String facilityName) throws FacilityNewMasterException {

		System.out.println("Inside  IFacilityNewMaster getFacilityNewMasterRiskType");
			String query = "SELECT FROM "+IFacilityNewMasterDao.ACTUAL_FACILITY_NEW_MASTER_NAME+" WHERE NEW_FACILITY_CATEGORY='"+facilityCat+"' AND NEW_FACILITY_NAME ='"+facilityName+"'";
			if(facilityCat!=null || facilityName!=null) {
				ArrayList resultList = (ArrayList)getHibernateTemplate().find(query);
				if(resultList.size()>0){
					IFacilityNewMaster iFacilityNewMaster = new OBFacilityNewMaster();
					iFacilityNewMaster = (OBFacilityNewMaster)resultList.get(0);
					return iFacilityNewMaster;
				}else {
					return null;
				}
			}else {
				
			throw new FacilityNewMasterException("Unable to find Risk Type List with Facility category ["+facilityCat+"]");
		}
	
	}

	public IFacilityNewMaster getFacilityNewMasterRiskTypeWithFacCode(String facCode) throws FacilityNewMasterException {

		System.out.println("Inside  IFacilityNewMaster getFacilityNewMasterRiskTypeWithFacCode=>facCode=>"+facCode);
			String query = "SELECT FROM "+IFacilityNewMasterDao.ACTUAL_FACILITY_NEW_MASTER_NAME+" WHERE NEW_FACILITY_CODE='"+facCode+"'";
			if(facCode!=null) {
				ArrayList resultList = (ArrayList)getHibernateTemplate().find(query);
				if(resultList.size()>0){
					IFacilityNewMaster iFacilityNewMaster = new OBFacilityNewMaster();
					iFacilityNewMaster = (OBFacilityNewMaster)resultList.get(0);
					return iFacilityNewMaster;
				}else {
					return null;
				}
			}else {
				
			throw new FacilityNewMasterException("Exception in getFacilityNewMasterRiskTypeWithFacCode Unable to find Risk Type List with Facility Code ["+facCode+"]");
		}
	
	}
	

	/**
	 * @return FacilityNewMaster Object
	 * @param Entity Name
	 * @param FacilityNewMaster Object  
	 * This method Updates FacilityNewMaster Object
	 */
	
	public IFacilityNewMaster updateFacilityNewMaster(String entityName, IFacilityNewMaster item)throws FacilityNewMasterException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (IFacilityNewMaster) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new FacilityNewMasterException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	
	/**
	  * Validate Line Number
	  */

	public boolean isUniqueCode(String lineNumber,String system)throws FacilityNewMasterException
	{
		
		if(!(lineNumber==null && lineNumber.trim()=="")){
			String query = "SELECT FROM "+IFacilityNewMasterDao.ACTUAL_FACILITY_NEW_MASTER_NAME+" WHERE DEPRECATED='N' and  LINE_NUMBER='"+lineNumber+"' and NEW_FACILITY_SYSTEM='"+system+"'";
			ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			if(resultList.size()>0){
					return true;
			}else 
				return false;
		
		}else{
			return false;
		}
	}
	public boolean isUniqueFacilityCode(String facilityCode)throws FacilityNewMasterException
	{
		
		if(!(facilityCode==null && facilityCode.trim()=="")){
			String query = "SELECT FROM "+IFacilityNewMasterDao.ACTUAL_FACILITY_NEW_MASTER_NAME+" WHERE DEPRECATED='N' and  NEW_FACILITY_CODE ='"+facilityCode+"'";
			ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			if(resultList.size()>0){
					return true;
			}else 
				return false;
		
		}else{
			throw new FacilityNewMasterException("ERROR- Entity name or key is null ");
		}
	}
	
	
	
	
	/**
	 * @return FacilityNewMaster Object
	 * @param Entity Name
	 * @param FacilityNewMaster Object  
	 * This method delete FacilityNewMaster Object
	 */
	
	public IFacilityNewMaster deleteFacilityNewMaster(String entityName, IFacilityNewMaster item)throws FacilityNewMasterException{

		if(!(entityName==null|| item==null)){
			item.setDeprecated("Y");
			item.setStatus("INACTIVE");
			getHibernateTemplate().update(entityName, item);
			return (IFacilityNewMaster) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new FacilityNewMasterException("ERROR-- Entity Name Or Key is null");
		}
	}
	/**
	 * @return FacilityNewMaster Object
	 * @param Entity Name
	 * @param FacilityNewMaster Object  
	 * This method Creates FacilityNewMaster Object
	 */
	public IFacilityNewMaster createFacilityNewMaster(String entityName,
			IFacilityNewMaster facilityNewMaster)
			throws FacilityNewMasterException {
		if(!(entityName==null|| facilityNewMaster==null)){
			if( facilityNewMaster.getNewFacilityCode() == null || facilityNewMaster.getNewFacilityCode().equals("")){
				String facilityCode=getFacilityCode();
				facilityNewMaster.setNewFacilityCode(facilityCode);
			}
			Long key = (Long) getHibernateTemplate().save(entityName, facilityNewMaster);
			facilityNewMaster.setId(key.longValue());
			return facilityNewMaster;
			}else{
				throw new FacilityNewMasterException("ERROR- Entity name or key is null ");
			}
	}


	private String getFacilityCode() {
		Query query = currentSession().createSQLQuery("SELECT FACILITY_SEQ.NEXTVAL FROM dual");
		String sequenceNumber = query.uniqueResult().toString();
		NumberFormat numberFormat = new DecimalFormat("0000000");
		String facilityCode = numberFormat.format(Long.parseLong(sequenceNumber));
		facilityCode = "FAC" + facilityCode;		
		return facilityCode;
	}
	
	
	/**
	  * @return Particular FacilityNewMaster according 
	  * to the id passed as parameter.  
	  * 
	  */

	public IFacilityNewMaster load(String entityName, long id)throws FacilityNewMasterException
	{
		if(!(entityName==null|| id==0)){
		return (IFacilityNewMaster)getHibernateTemplate().get(entityName , new Long(id));	
		}else{
			throw new FacilityNewMasterException("ERROR- Entity name or key is null ");
		}
	}

	public boolean isFacilityNameUnique(String facilityName) {
		String stagingQuery = "SELECT FROM "+IFacilityNewMasterDao.STAGE_FACILITY_NEW_MASTER_NAME+" WHERE NEW_FACILITY_NAME like '"+facilityName+"' ";
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if(stagingResultList.size()>0){
			String actualQuery = "SELECT FROM "+IFacilityNewMasterDao.ACTUAL_FACILITY_NEW_MASTER_NAME+" WHERE NEW_FACILITY_NAME like '"+facilityName+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if(actualResultList.size()>0)
				return true;
			else
				return false;
		}	
		else 
			return false;		 
	}
	
	public boolean isFacilityCpsIdUnique(String cpsId) {
			String actualQuery = "SELECT FROM "+IFacilityNewMasterDao.ACTUAL_FACILITY_NEW_MASTER_NAME+" WHERE CPS_ID = '"+cpsId+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if(actualResultList.size()>0)
				return true;
			else
				return false;
		 
	}

}
