/**
 * 
 */
package com.integrosys.cms.app.ws.jax.common;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.hibernate.criterion.DetachedCriteria;

import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.holiday.bus.HolidayException;
/*updating the code to support hibernate 4 jars
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;*/
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import com.integrosys.cms.app.udf.bus.OBUdf;
import com.integrosys.cms.asst.validator.ASSTValidator;
/**
 * @author Bharat Waghela
 *
 */

public class MasterAccessUtility extends HibernateDaoSupport {
	
	public Object getMaster(String entityName, Serializable key)throws HolidayException {
		try {
		return  getHibernateTemplate().get(entityName, key);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Object getMasterData(String entityName, Serializable Id)throws HolidayException {
		try {
			String query = "FROM "+entityName+" WHERE ENTRY_ID = ? AND ACTIVE_STATUS = '1' ";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query,Id);
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public Object getObjectforMaster(String entityName,Serializable Id) {
		String query="";
		try {
			if("actualCountry".equalsIgnoreCase(entityName))
				query = "FROM "+entityName+" WHERE ID = ? AND STATUS = 'ACTIVE' ";
			else if("actualRegion".equalsIgnoreCase(entityName))
				query = "FROM "+entityName+" WHERE ID = ? AND STATUS = 'ACTIVE' ";
			else if("actualState".equalsIgnoreCase(entityName))
				query = "FROM "+entityName+" WHERE ID = ? AND STATUS = 'ACTIVE' ";
			else if("actualCity".equalsIgnoreCase(entityName))
				query = "FROM "+entityName+" WHERE ID = ? AND STATUS = 'ACTIVE' ";
			else if("actualOBPartyGroup".equalsIgnoreCase(entityName))
				query = "FROM "+entityName+" WHERE ID = ? AND STATUS = 'ACTIVE' ";
			
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query,Id);
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					return null;
				}
			}else{
				return null;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error getObjectforMaster during web Service called!!!");
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * This method retrieves the entity obj by CPS id and enityName
	 * 
	 * @param entityName
	 * @param cpsId
	 * @return
	 * @throws Exception
	 */
	public Object getObjByEntityNameAndCPSId(String entityName,String cpsId) throws Exception {
		try {
			String query = "FROM "+entityName+" WHERE CPS_ID = ? ";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query,cpsId);
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}
			}else{
				throw new Exception("Unable to get List for "+entityName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("ERROR-- While retriving getObjectByEntityNameAndCPSId() ");
		}
		return null;
	}
	
	public boolean isRecordDuplicate(String entityName,String cpsId){
		
		String query1 = "SELECT FROM "+entityName+" WHERE DEPRECATED!='Y' AND UPPER(cps_id) = '"+cpsId.toUpperCase()+"' ";
		ArrayList resultList1 = (ArrayList) getHibernateTemplate().find(query1);
		if(resultList1.size()>0)
			return true;
		else
			return false;
	
}
	
	public Object getObjectByEntityNameAndCode(String entityName,String code,String fieldName,ActionErrors errors) {
		try {
			String query = "FROM "+entityName+" WHERE RISK_TYPE_CODE = ? AND DEPRECATED='N' ";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query,code);
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}
	}
	
	public Object getObjectByEntityNameAndBranchCode(String entityName,String branchCode,String fieldName,ActionErrors errors) {
		try {
			String query = "FROM "+entityName+" WHERE SYSTEM_BANK_BRANCH_CODE = ? AND DEPRECATED = 'N'";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query,branchCode);
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}
	}
	
	public Object getObjectByEntityNameAndCountryISOCode(String entityName,String countryISOCode,String fieldName,ActionErrors errors) {
		try {
			String query = "FROM "+entityName+" WHERE CURRENCY_ISO_CODE = ? AND STATUS='ENABLE'";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query,countryISOCode);
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}
	}
	
	//============= Added By Anil For handling category code master ==================================	
	public Object getObjectByEntityNameAndCPSId(String entityName,String cpsId,String fieldName,ActionErrors errors,String categoryCode) {
		try {
			String query = "FROM "+entityName+" WHERE CPS_ID = ?  AND CATEGORY_CODE = ? AND ACTIVE_STATUS = '1' ";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query, new Object[]{cpsId,categoryCode});
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}
	}
	
	public Object getObjectByEntityName(String entityName,String entryCode,String fieldName,ActionErrors errors,String categoryCode) {
		try {
			String query = "FROM "+entityName+" WHERE ENTRY_CODE = ?  AND CATEGORY_CODE = ? AND ACTIVE_STATUS = '1' ";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query, new Object[]{entryCode,categoryCode});
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}
	}
	
	public Object getObjectByEntityNameColsubType(String entityName,String entryCode,String fieldName,ActionErrors errors,String categoryCode,String collType) {
		try {
			String query = "FROM "+entityName+" WHERE ENTRY_CODE = ?  AND CATEGORY_CODE = ? AND REF_ENTRY_CODE = ? AND ACTIVE_STATUS = '1' ";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query, new Object[]{entryCode,categoryCode,collType});
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}
	}
	
	//Added by smriti
	public Object getObjectByEntityName(String CMS_RELATIONSHIP_MGR,String RM_MGR_NAME,String fieldName,ActionErrors errors) {
		try {
			String query = "FROM "+CMS_RELATIONSHIP_MGR+" WHERE RM_MGR_NAME = ? AND STATUS = 'ACTIVE' ";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query, new Object[]{CMS_RELATIONSHIP_MGR});
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}
	}
	
	public Object getObjectByEntityNameColl(String entityName,String collCode,String fieldName,ActionErrors errors,String collType,String collSubType) {
		try {
			String query = "FROM "+entityName+" WHERE NEW_COLLATERAL_CODE = ? AND NEW_COLLATERAL_MAIN_TYPE = ? AND NEW_COLLATERAL_SUB_TYPE = ? AND STATUS = 'ACTIVE' ";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query, new Object[]{collCode,collType,collSubType});
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}
	}
	
	
	public Object getObjByEntityNameAndCPSId(String entityName,String cpsId,String categoryCode) throws Exception {
		try {
			String query = "FROM "+entityName+" WHERE CPS_ID = ? AND CATEGORY_CODE = ? ";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query,new Object[]{cpsId,categoryCode});
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}
			}else{
				throw new Exception("Unable to get List for "+entityName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("ERROR-- While retriving getObjectByEntityNameAndCPSId() ");
		}
		return null;
	}
	
	public Object getObjByEntityNameAndApprovalCode(String entityName,String approvalCode,String fieldName,ActionErrors errors) {
		try {
			String query = "FROM "+entityName+" WHERE STATUS = 'ACTIVE' AND APPROVAL_CODE = ? ";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query,new Object[]{approvalCode});
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}
	}
	
	public Object getObjByEntityNameAndFacilityCode(String entityName,String facilityCode,String fieldName,ActionErrors errors) {
		try {
			String query = "FROM "+entityName+" WHERE STATUS = 'ACTIVE' AND NEW_FACILITY_CODE = ? ";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query,new Object[]{facilityCode});
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}
	}
	
	public Object getObjByEntityNameAndCollateralCode(String entityName,String collateralCode,String fieldName,ActionErrors errors) {
		try {
			String query = "FROM "+entityName+" WHERE STATUS = 'ACTIVE' AND NEW_COLLATERAL_CODE = ? ";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query,new Object[]{collateralCode});
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}
	}
		
	
	public Object getObjByEntityNameAndfacilityName(String entityName,String facilityname,String fieldName,ActionErrors errors) {
		try {
			String query = "FROM "+entityName+" WHERE STATUS = 'ACTIVE' AND NEW_FACILITY_NAME = ? ";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query,new Object[]{facilityname});
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}
	}
	//============= Added By Anil For handling category code master ==================================
	
	@SuppressWarnings("unchecked")
	public List<Object> getObjectListBySpecification(DetachedCriteria criteria) {
		/*updating the code to support hibernate 4 jars
		return  getHibernateTemplate().findByCriteria(criteria);*/
		return  (List<Object>) getHibernateTemplate().findByCriteria(criteria);
	}
	
	public Object getObjectByEntityNameAndId(String entityName,String id,String fieldName,ActionErrors errors) {
		try {
			String query = "FROM "+entityName+" WHERE ID = ? AND DEPRECATED='N' AND STATUS='ACTIVE' ";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query,id);
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}
	}
	
	public Object getObjectByEntityNameAndCatEntry(String entityName,String entryCode,String fieldName,ActionErrors errors,String categoryCode) {
		try {
			String query = "FROM "+entityName+" WHERE ENTRY_CODE = ?  AND CATEGORY_CODE = ? AND ACTIVE_STATUS = '1' ";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query, new Object[]{entryCode,categoryCode});
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}
	}
	
		public Object getObjectByEntityNameAndCPSId(String entityName,String cpsId,String fieldName,ActionErrors errors) {
		try {
			String query = "FROM "+entityName+" WHERE CPS_ID = ? ";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query,new Object[]{cpsId});
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}
	}
	
	public Object getObjectByEntityNameAndEntryName(String entityName,String entryCode,String fieldName,ActionErrors errors,String categoryCode) {
		try {
			String query = "FROM "+entityName+" WHERE ENTRY_CODE = ?  AND CATEGORY_CODE = ? AND ACTIVE_STATUS = '1' ";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query, new Object[]{entryCode,categoryCode});
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}
	}
	
	public Object getObjectByEntityNameAndSequence(String entityName,int sequence,String fieldName,ActionErrors errors) {
		try {
			String query = "FROM "+entityName+" WHERE SEQUENCE = ? AND MODULEID = '1' AND STATUS = 'ACTIVE' ";
			List resultList = new ArrayList();
			if( query != "" ){
				resultList =(List) getHibernateTemplate().find(query,sequence);
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!!...");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}
	}

	public Object getObjectByEntityNameAndEntryCode(String entityName,String entryCode,String categoryCode) {
		ArrayList resultList = new ArrayList();
		try {
			String query = "FROM "+entityName+" WHERE ENTRY_CODE = ?  AND CATEGORY_CODE = ? AND ACTIVE_STATUS = '1' ";
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query, new Object[]{entryCode,categoryCode});
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!");
			e.printStackTrace();
		}
		return resultList;
	}

	public Object getObjectByEntityNameAndSequenceForRest(String entityName, String moduleId, String sequence, String fieldName,
			ActionErrors errors) {
		try {
			String query = "FROM "+entityName+" WHERE SEQUENCE = ? AND MODULEID = "+moduleId+" AND STATUS = 'ACTIVE' ";
			List resultList = new ArrayList();
			if( query != "" ){
				resultList =(List) getHibernateTemplate().find(query,sequence);
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.inactive.field.udf"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!!...");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}		
	}

	
	public Object getObjectforMasterRelatedCode(String entityName,String code,String fieldName,ActionErrors errors) {
		String query="";
		try {
			if("actualSystemBankBranch".equalsIgnoreCase(entityName))
			query = "FROM "+entityName+" WHERE SYSTEM_BANK_BRANCH_CODE = ? AND STATUS = 'ACTIVE' ";
			else if("actualForexFeedEntry".equalsIgnoreCase(entityName))
			query = "FROM "+entityName+" WHERE CURRENCY_ISO_CODE = ? AND STATUS = 'ENABLE' ";
			else if("actualCreditApproval".equalsIgnoreCase(entityName))
				query = "FROM "+entityName+" WHERE APPROVAL_CODE = ? AND DEFERRAL_POWERS='Y' AND STATUS = 'ACTIVE' ";
			else if("actualCountry".equalsIgnoreCase(entityName))
				query = "FROM "+entityName+" WHERE COUNTRY_CODE = ? AND STATUS = 'ACTIVE' ";
			else if("actualRegion".equalsIgnoreCase(entityName))
				query = "FROM "+entityName+" WHERE REGION_CODE = ? AND STATUS = 'ACTIVE' ";
			else if("actualState".equalsIgnoreCase(entityName))
				query = "FROM "+entityName+" WHERE STATE_CODE = ? AND STATUS = 'ACTIVE' ";
			else if("actualCity".equalsIgnoreCase(entityName))
				query = "FROM "+entityName+" WHERE CITY_CODE = ? AND STATUS = 'ACTIVE' ";
			else if("actualInsuranceCoverage".equalsIgnoreCase(entityName))
				query = "FROM "+entityName+" WHERE ID = ? AND STATUS = 'ACTIVE' ";
			else if("actualOBValuationAgency".equalsIgnoreCase(entityName))
				query = "FROM "+entityName+" WHERE VALUATION_AGENCY_CODE = ? AND STATUS = 'ACTIVE' ";
			else if("actualOtherBank".equalsIgnoreCase(entityName))
				query = "FROM "+entityName+" WHERE BANK_CODE = ? AND STATUS = 'ACTIVE' ";
			else if("actualOBSystemBank".equalsIgnoreCase(entityName))
				query = "FROM "+entityName+" WHERE SYSTEM_BANK_CODE = ? AND STATUS = 'ACTIVE' ";
			
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query, new Object[]{code});
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error getObjectforMasterRelatedCode during web Service called!!!");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}
	}
	
	public Object getObjectforMasterRelatedCodeWaiver(String entityName,String code,String fieldName,ActionErrors errors) {
		String query="";
		try {
			if("actualCreditApproval".equalsIgnoreCase(entityName))
				query = "FROM "+entityName+" WHERE APPROVAL_CODE = ? AND WAIVING_POWERS='Y' AND STATUS = 'ACTIVE' ";
			
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query, new Object[]{code});
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error getObjectforMasterRelatedCodeWaiver during web Service called!!!");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}
	}
	
	public Object getObjectforSysMasterBank(String entityName,String code,String fieldName,ActionErrors errors) {
		String query="";
		try {
			if("actualOtherBank".equalsIgnoreCase(entityName))
				query = "FROM "+entityName+" WHERE BANK_CODE = ? AND STATUS = 'ACTIVE' ";
			else if("actualOBSystemBank".equalsIgnoreCase(entityName))
				query = "FROM "+entityName+" WHERE SYSTEM_BANK_CODE = ? AND STATUS = 'ACTIVE' ";
			ArrayList resultList = new ArrayList();
			if( query != "" ){
				resultList =(ArrayList) getHibernateTemplate().find(query, new Object[]{code});
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					return errors;
				}
			}else{
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error getObjectforMasterRelatedCode during web Service called!!!");
			e.printStackTrace();
			return errors;
		}
	}
	
	//  Code for REST UDF ENQUIRY Service
	public List<OBUdf> getUdfListEnquiry(String entity,String moduleId,String sequence,String mandatory,String status) {
	    List<OBUdf> resultList = new ArrayList<OBUdf>();
	    String query = "";
	    try {
	    	if(sequence.equals("ALL")) {
	    		query = "SELECT FROM " + entity + " WHERE MODULEID = " + moduleId;
		        resultList = (List<OBUdf>) getHibernateTemplate().find(query);				
	    	}else if(!mandatory.isEmpty() && status.isEmpty() && sequence.isEmpty()) {
	    		query = "SELECT FROM " + entity + " WHERE MANDATORY = ? AND MODULEID = " + moduleId;
		        resultList = (List<OBUdf>) getHibernateTemplate().find(query, new Object[]{mandatory});
	    	}else if(!status.isEmpty() && sequence.isEmpty() && mandatory.isEmpty()){
	    		query = "SELECT FROM " + entity + " WHERE STATUS = ? AND MODULEID = "+ moduleId;
		        resultList = (List<OBUdf>) getHibernateTemplate().find(query, new Object[]{status});
	    	}else if(!sequence.isEmpty() && status.isEmpty() && mandatory.isEmpty()){
	    		query = "SELECT FROM " + entity + " WHERE SEQUENCE = ? AND MODULEID = "+ moduleId;
		        resultList = (List<OBUdf>) getHibernateTemplate().find(query, new Object[]{sequence});				
	    	}else if(!sequence.isEmpty() && !mandatory.isEmpty() && status.isEmpty()){
	    		query = "SELECT FROM " + entity + " WHERE SEQUENCE = ? AND MANDATORY = ? AND MODULEID = "+ moduleId;
		        resultList = (List<OBUdf>) getHibernateTemplate().find(query, new Object[]{sequence,mandatory});
	    	}else if(!sequence.isEmpty() && !status.isEmpty() && mandatory.isEmpty()){
	    		query = "SELECT FROM " + entity + " WHERE SEQUENCE = ? AND STATUS = ? AND MODULEID = "+ moduleId;
		        resultList = (List<OBUdf>) getHibernateTemplate().find(query, new Object[]{sequence,status});
	    	}else if(!mandatory.isEmpty() && !status.isEmpty() && sequence.isEmpty()){
	    		query = "SELECT FROM " + entity + " WHERE STATUS = ? AND MANDATORY = ? AND MODULEID = "+ moduleId;
		        resultList = (List<OBUdf>) getHibernateTemplate().find(query, new Object[]{status,mandatory});
	    	}else if(!sequence.isEmpty() && !mandatory.isEmpty() && !status.isEmpty()){
	    		query = "SELECT FROM " + entity + " WHERE SEQUENCE = ? AND STATUS = ? AND MANDATORY = ? AND MODULEID = "+ moduleId;
		        resultList = (List<OBUdf>) getHibernateTemplate().find(query, new Object[]{sequence,status,mandatory});				
	    	}
	    	
	    } catch (Exception e) {
			DefaultLogger.error(this, "Error getUdfListEnquiry method during web Service called!!!");
	    	e.printStackTrace();
	    }
	    return resultList;
	}

	
	public Object getObjectByEntityNameAndSequenceForRestUdf(String entityName, String moduleId, String sequence, String fieldName,
			ActionErrors errors) {
		try {
			String query = "FROM "+entityName+" WHERE SEQUENCE = ? AND MODULEID = "+moduleId;
			List resultList = new ArrayList();
			if( query != "" ){
				resultList =(List) getHibernateTemplate().find(query,sequence);
				if(resultList!=null && resultList.size()>0){
					return (Object) resultList.get(0); 
				}else{
					errors.add(fieldName,new ActionMessage("error.inactive.field.udf"));
					return errors;
				}
			}else{
				errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
				return errors;
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Error during web Service called!!!!...");
			e.printStackTrace();
			errors.add(fieldName,new ActionMessage("error.invalid.field.value"));
			return errors;
		}		
	}
	
	
	  public Object getObjectByEntityNameAndRMCode(String entityName, String RMCode, String fieldName, ActionErrors errors)
	  {
	    try
	    {
	      String query = "FROM " + entityName + " WHERE RM_MGR_CODE = ? ";
	      ArrayList resultList = new ArrayList();
	      if (query != "")
	      {
	        resultList = (ArrayList)getHibernateTemplate().find(query, new Object[] { RMCode });
	        if ((resultList != null) && (resultList.size() > 0)) {
	          return resultList.get(0);
	        }
	        errors.add(fieldName, new ActionMessage("error.invalid.field.value"));
	        return errors;
	      }
	      errors.add(fieldName, new ActionMessage("error.invalid.field.value"));
	      return errors;
	    }
	    catch (Exception e)
	    {
	      DefaultLogger.error(this, "Exception in getObjectByEntityNameAndRMCode=>Error during web Service called!!!");
	      e.printStackTrace();
	      errors.add(fieldName, new ActionMessage("error.invalid.field.value"));
	    }
	    return errors;
	  }

	// End --

}