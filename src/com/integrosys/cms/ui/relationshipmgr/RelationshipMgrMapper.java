package com.integrosys.cms.ui.relationshipmgr;

import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrDAOImpl;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Describe this class Purpose Description
 * 
 * Used for setting values from OBObject to form & form to OBObject.
 * 
 * @author $Author: Dattatay Thorat $
 * @version $Revision:1.0 $
 * @since $Date: 2011-03-31 16:07:56 +0800  $ Tag : $Name$
 */

public class RelationshipMgrMapper extends AbstractCommonMapper{

	public RelationshipMgrMapper() {
		DefaultLogger.debug(this, "constructor");
	}

	public IRelationshipMgrDAO relationshipMgrDAO;
	
	
	public IRelationshipMgrDAO getRelationshipMgrDAO() {
		return relationshipMgrDAO;
	}

	public void setRelationshipMgrDAO(IRelationshipMgrDAO relationshipMgrDAO) {
		this.relationshipMgrDAO = relationshipMgrDAO;
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm
	 * @param inputs
	 * @return
	 * @throws MapperException
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap inputs)
			throws MapperException {
		
		RelationshipMgrForm form = (RelationshipMgrForm) cForm;
		IRelationshipMgr relationshipMgr = new OBRelationshipMgr();
		ICommonUser user = (ICommonUser) inputs.get(IGlobalConstant.USER);
		try{
			if(form.getId()== null){
				relationshipMgr.setId(1l);
			}else{		
				relationshipMgr.setId(Long.parseLong(form.getId()));
			}	
			relationshipMgr.setRelationshipMgrCode(form.getRelationshipMgrCode());
			relationshipMgr.setRelationshipMgrName(form.getRelationshipMgrName());
			relationshipMgr.setRelationshipMgrMailId(form.getRelationshipMgrMailId());
			relationshipMgr.setReportingHeadName(form.getReportingHeadName());
			relationshipMgr.setReportingHeadMailId(form.getReportingHeadMailId());
			relationshipMgr.setRelationshipMgrMobileNo(form.getRelationshipMgrMobileNo());
			
			if(null!= form.getEmployeeId() && !"".equalsIgnoreCase(form.getEmployeeId()))
			relationshipMgr.setEmployeeId(form.getEmployeeId().toUpperCase());
			
			if(form.getRegionId()!=null && !form.getRegionId().trim().equals("")){
				relationshipMgr.setRegion(new OBRegion());
				try {
					relationshipMgr.getRegion().setIdRegion(Long.parseLong(form.getRegionId()));
				}catch(Exception e) {
					IRelationshipMgrDAO relationshipMgrDAOImpl = (IRelationshipMgrDAO)BeanHouse.get("relationshipMgrDAO");
					IRegion region = relationshipMgrDAOImpl.getRegionByRegionName(form.getRegionId());
					relationshipMgr.getRegion().setIdRegion(region.getIdRegion());
				}
			}
			
			relationshipMgr.setLocalCADs(form.getLocalCADs());
			
			relationshipMgr.setRelationshipMgrCity(form.getRelationshipMgrCity());
			relationshipMgr.setRelationshipMgrState(form.getRelationshipMgrState());
			
			IRegionDAO iRegion =  (IRegionDAO) BeanHouse.get("regionDAO");
			String wboRegion = form.getWboRegion();
			String rhRegion = form.getReportingHeadRegion();
			
			if("maker_submit_create_relationship_mgr".equals(form.getEvent()) || "maker_save_create_relationship_mgr".equals(form.getEvent())) {
			if(null!=form.getWboRegion() && !"".equals(form.getWboRegion())) {
				wboRegion = (iRegion.getRegionById(Long.parseLong(form.getWboRegion())).getRegionName());
			}
			if(null!=form.getReportingHeadRegion() && !"".equals(form.getReportingHeadRegion())) {
				rhRegion = (iRegion.getRegionById(Long.parseLong(form.getReportingHeadRegion())).getRegionName());
			}
			}
			relationshipMgr.setWboRegion(wboRegion);
			relationshipMgr.setReportingHeadName(form.getReportingHeadName());
			relationshipMgr.setReportingHeadEmployeeCode(form.getReportingHeadEmployeeCode());
			relationshipMgr.setReportingHeadMailId(form.getReportingHeadMailId());
			relationshipMgr.setReportingHeadMobileNo(form.getReportingHeadMobileNo());
			
			
//			relationshipMgr.setReportingHeadRegion(form.getReportingHeadRegion());
			relationshipMgr.setReportingHeadRegion(rhRegion);
			
			if(form.getDeprecated()!=null && !form.getDeprecated().trim().equals("")){
				relationshipMgr.setDeprecated(form.getDeprecated());
			}else{
				relationshipMgr.setDeprecated("N");
			}	
			if(form.getStatus()!=null && !form.getStatus().trim().equals("")){
				relationshipMgr.setStatus(form.getStatus());
			}else{
				relationshipMgr.setStatus("ACTIVE");
			}	
			relationshipMgr.setVersionTime(0l);
			if(form.getCreatedBy()!=null && !form.getCreatedBy().trim().equals("")){
				relationshipMgr.setCreatedBy(form.getCreatedBy());
			}else{
				relationshipMgr.setCreatedBy(user.getLoginID());
			}	
			relationshipMgr.setCreationDate(new Date());
			if(form.getLastUpdateBy()!=null && !form.getLastUpdateBy().trim().equals("")){
				relationshipMgr.setLastUpdateBy(user.getLoginID());
			}else{
				relationshipMgr.setLastUpdateBy(user.getLoginID());
			}	
			relationshipMgr.setLastUpdateDate(new Date());
			
			relationshipMgr.setFileUpload(form.getFileUpload());
			
		}catch (Exception e) {
			DefaultLogger.error(this, "Exception in RelationshipMgrMapper",e);
			e.printStackTrace();
			throw new MapperException("Exception in RelationshipMgrMapper");
		}	
		return relationshipMgr;
	}

	/**
	 * 
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm
	 * @param obj
	 * @param map
	 * @return
	 * @throws MapperException
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map)
			throws MapperException {

		IRelationshipMgr relationshipMgr = (OBRelationshipMgr) obj;
		RelationshipMgrForm relationshipMgrForm =  new RelationshipMgrForm();

		try {

			relationshipMgrForm.setId(Long.toString(relationshipMgr.getId()));
			relationshipMgrForm.setRelationshipMgrCode(relationshipMgr.getRelationshipMgrCode());
			if(StringUtils.isNotBlank(relationshipMgr.getRelationshipMgrName()))
				relationshipMgrForm.setRelationshipMgrName(relationshipMgr.getRelationshipMgrName());
			if(StringUtils.isNotBlank(relationshipMgr.getRelationshipMgrMailId()))
				relationshipMgrForm.setRelationshipMgrMailId(relationshipMgr.getRelationshipMgrMailId());
			if(StringUtils.isNotBlank(relationshipMgr.getEmployeeId()))
				relationshipMgrForm.setEmployeeId(relationshipMgr.getEmployeeId().toUpperCase());
			if(StringUtils.isNotBlank(relationshipMgr.getReportingHeadName()))
				relationshipMgrForm.setReportingHeadName(relationshipMgr.getReportingHeadName());
			if(StringUtils.isNotBlank(relationshipMgr.getReportingHeadMailId()))
				relationshipMgrForm.setReportingHeadMailId(relationshipMgr.getReportingHeadMailId());
			if(relationshipMgr.getRegion()!=null){
				relationshipMgrForm.setRegionId(Long.toString(relationshipMgr.getRegion().getIdRegion()));
			//if(StringUtils.isNotBlank(relationshipMgr.getRegion().getRegionName()))
				relationshipMgrForm.setRegion(relationshipMgr.getRegion().getRegionName());
			}
			if(relationshipMgr.getLocalCADs()!=null){
				relationshipMgrForm.setLocalCADs(relationshipMgr.getLocalCADs());
			}
			
			relationshipMgrForm.setRelationshipMgrMobileNo(relationshipMgr.getRelationshipMgrMobileNo());
			relationshipMgrForm.setRelationshipMgrCity(relationshipMgr.getRelationshipMgrCity());
			relationshipMgrForm.setRelationshipMgrState(relationshipMgr.getRelationshipMgrState());
			relationshipMgrForm.setWboRegion(relationshipMgr.getWboRegion());
			relationshipMgrForm.setReportingHeadName(relationshipMgr.getReportingHeadName());
			relationshipMgrForm.setReportingHeadEmployeeCode(relationshipMgr.getReportingHeadEmployeeCode());
			relationshipMgrForm.setReportingHeadMailId(relationshipMgr.getReportingHeadMailId());
			relationshipMgrForm.setReportingHeadMobileNo(relationshipMgr.getReportingHeadMobileNo());
			relationshipMgrForm.setReportingHeadRegion(relationshipMgr.getReportingHeadRegion());  
			
			relationshipMgrForm.setDeprecated(relationshipMgr.getDeprecated());
			relationshipMgrForm.setStatus(relationshipMgr.getStatus());
			relationshipMgrForm.setCreatedBy(relationshipMgr.getCreatedBy());
			relationshipMgrForm.setCreationDate(relationshipMgr.getCreationDate());
			relationshipMgrForm.setLastUpdateBy(relationshipMgr.getLastUpdateBy());
			relationshipMgrForm.setLastUpdateDate(relationshipMgr.getLastUpdateDate().toString());
			relationshipMgrForm.setVersionTime(relationshipMgr.getVersionTime());
		} catch (Exception e) {
			DefaultLogger.error(this, "Exception In RelationshipMgrMapper", e);
			e.printStackTrace();
			throw new MapperException("Exception in RelationshipMgrMapper");
		}

		return relationshipMgrForm;

	}
	
	/**
	 * declares the key-value pair upfront for objects that needs to be accessed
	 * in scope
	 * @return 2D-array key value pair
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE }
				});
	}

}
