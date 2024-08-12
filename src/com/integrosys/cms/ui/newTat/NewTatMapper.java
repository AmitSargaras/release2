
package com.integrosys.cms.ui.newTat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.holiday.bus.OBHoliday;
import com.integrosys.cms.app.newTat.bus.INewTat;
import com.integrosys.cms.app.newTat.bus.OBNewTat;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.imageTag.ImageTagAction;

/**
 *@author $Author: Abhijit R$
 *Mapper for New Tat
 */

public class NewTatMapper extends AbstractCommonMapper {
	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		NewTatForm newTatForm =(NewTatForm) cForm;
		String event = newTatForm.getEvent();
		if (NewTatAction.LIST_CUSTOMER.equals(event)) {

			CustomerSearchCriteria cSearch = new CustomerSearchCriteria();
			DefaultLogger.debug(this, "getGobutton value---->");
			cSearch.setCustomerName(newTatForm.getLspShortNameListSearch());
			cSearch.setLegalID(newTatForm.getLspLeIdListSearch());
			String nItemsStr = PropertyManager
			.getValue("customer.pagination.nitems");
			int nItems = 20;
			if (null != nItemsStr) {
				try {
					nItems = Integer.parseInt(nItemsStr);
				} catch (NumberFormatException e) {
					nItems = 20;
				}
			}
			if (newTatForm.getNumItems() > 10) {
				cSearch.setNItems(newTatForm.getNumItems());
			} else {
				cSearch.setNItems(nItems);
			}

			cSearch.setStartIndex(newTatForm.getStartIndex());

			return cSearch;
		}
		INewTat iNewTat= new OBNewTat();
		DefaultLogger.debug(this, "Entering method mapFormToOB");
		//iNewTat.setId(                  newTatForm.getId());                          
		if(newTatForm.getVersionTime()!=null && !"".equals(newTatForm.getVersionTime())){
			iNewTat.setVersionTime( Long.parseLong(newTatForm.getVersionTime())) ;  
		}
		iNewTat.setCreateBy(           	newTatForm.getCreateBy()) ;  
		if(newTatForm.getCreationDate()!=null && !"".equals(newTatForm.getCreationDate())){
			iNewTat.setCreationDate(   DateUtil.convertDate(newTatForm.getCreationDate())) ;  
		}
		iNewTat.setLastUpdateBy(      	newTatForm.getLastUpdateBy()) ;  
		if(newTatForm.getLastUpdateDate()!=null && !"".equals(newTatForm.getLastUpdateDate())){
			iNewTat.setLastUpdateDate(DateUtil.convertDate(newTatForm.getLastUpdateDate()) );  
		}
		iNewTat.setDeprecated(          newTatForm.getDeprecated()) ;           
		iNewTat.setStatus(              newTatForm.getStatus()) ;  
		if(newTatForm.getCmsLeMainProfileId()!=null && !"".equals(newTatForm.getCmsLeMainProfileId())){
			iNewTat.setCmsLeMainProfileId( Long.parseLong(newTatForm.getCmsLeMainProfileId())) ;  
		}
		iNewTat.setLspLeId(            	newTatForm.getLspLeId()) ;              
		iNewTat.setLspShortName(        newTatForm.getLspShortName()) ;   
		iNewTat.setLspLeIdListSearch(            	newTatForm.getLspLeIdListSearch()) ;              
		iNewTat.setLspShortNameListSearch(        newTatForm.getLspShortNameListSearch()) ;   
		if(newTatForm.getCaseId()!=null && !"".equals(newTatForm.getCaseId())){
			iNewTat.setCaseId(       Long.parseLong(       newTatForm.getCaseId())); 
		}
		if(newTatForm.getId()!=null && !"".equals(newTatForm.getId())){
			iNewTat.setId(       Long.parseLong(       newTatForm.getId())); 
		}
		iNewTat.setModule(              newTatForm.getModule()) ;               
		iNewTat.setCaseInitiator(       newTatForm.getCaseInitiator() );        
		iNewTat.setRelationshipManager(	newTatForm.getRelationshipManager() );  
		iNewTat.setDocStatus   (		newTatForm.getDocStatus()) ;            
		iNewTat.setRemarks(             newTatForm.getRemarks());                
		iNewTat.setFacilityCategory(   	newTatForm.getFacilityCategory()) ;     
		iNewTat.setFacilityName(       	newTatForm.getFacilityName()) ;         
		iNewTat.setCamType(            	newTatForm.getCamType() );              
		iNewTat.setDeferralType(       	newTatForm.getDeferralType()) ;         
		iNewTat.setLssCoordinatorBranch(newTatForm.getLssCoordinatorBranch()) ; 
		iNewTat.setType(                newTatForm.getType());  
		if(newTatForm.getActivityTime()!=null && !"".equals(newTatForm.getActivityTime())){
			iNewTat.setActivityTime(   DateUtil.convertDate(newTatForm.getActivityTime())) ;  
		}
		if(newTatForm.getActualActivityTime()!=null && !"".equals(newTatForm.getActualActivityTime())){
			iNewTat.setActualActivityTime( 	DateUtil.convertDate(newTatForm.getActualActivityTime()));  
		}
		iNewTat.setFacilitySystem(            newTatForm.getFacilitySystem()); 
		iNewTat.setFacilityManual(            newTatForm.getFacilityManual());
		iNewTat.setAmount(             	newTatForm.getAmount()) ;               
		iNewTat.setCurrency(          	newTatForm.getCurrency()) ;             
		iNewTat.setLineNumber(         	newTatForm.getLineNumber() );
		
		if( newTatForm.getFacilitySection() != null && "SYSTEM".equals(newTatForm.getFacilitySection())){
			iNewTat.setSerialNumber(       	newTatForm.getSerialNumberSystem()) ; 
		}
		else if( newTatForm.getFacilitySection() != null && "MANUAL".equals(newTatForm.getFacilitySection())){
			iNewTat.setSerialNumber(       	newTatForm.getSerialNumberManual()) ; 
		}
		iNewTat.setRegion(newTatForm.getRegion());
		iNewTat.setRmRegion(newTatForm.getRmRegion());
		iNewTat.setSegment(newTatForm.getSegment());
		iNewTat.setIsTatBurst(newTatForm.getIsTatBurst());
		iNewTat.setDelayReason(newTatForm.getDelayReason());
		iNewTat.setDelayReasonText(newTatForm.getDelayReasonText());
		iNewTat.setFacilitySection(newTatForm.getFacilitySection());
		
		
		
		
		
		
		return iNewTat;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		NewTatForm newTatForm = new NewTatForm();
		NewTatForm form = (NewTatForm) cForm;
		String event =(String) inputs.get("event");
		if (NewTatAction.LIST_CUSTOMER.equals(event)) {
			if (obj != null) {
				SearchResult sr = (SearchResult) obj;
				newTatForm.setCurrentIndex(sr.getCurrentIndex());
				newTatForm.setNumItems(sr.getNItems());
				newTatForm.setLspLeIdListSearch(form.getLspLeIdListSearch());
				newTatForm.setLspShortNameListSearch(form.getLspShortNameListSearch());
				newTatForm.setSearchResult(sr.getResultList());
			} else {
				newTatForm.setSearchResult(null);
				newTatForm.setCurrentIndex(0);
				newTatForm.setStartIndex(0);
				newTatForm.setNumItems(0);
			}
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return newTatForm;


		}

		INewTat iNewTat= (INewTat)obj;

		newTatForm.setCreateBy(           	iNewTat.getCreateBy()) ;  
		if(iNewTat.getCreationDate()!=null && !"".equals(iNewTat.getCreationDate())){
			newTatForm.setCreationDate( iNewTat.getCreationDate().toString()) ;  
		}
		newTatForm.setLastUpdateBy(      	iNewTat.getLastUpdateBy()) ;  
		if(iNewTat.getLastUpdateDate()!=null && !"".equals(iNewTat.getLastUpdateDate())){
			newTatForm.setLastUpdateDate(iNewTat.getLastUpdateDate().toString() );  
		}
		newTatForm.setDeprecated(          iNewTat.getDeprecated()) ;           
		newTatForm.setStatus(              iNewTat.getStatus()) ;  
		if(iNewTat.getCmsLeMainProfileId()!=0 ){
			newTatForm.setCmsLeMainProfileId(  String.valueOf(iNewTat.getCmsLeMainProfileId())) ;  
		}
		if(iNewTat.getVersionTime()!=0 ){
			newTatForm.setVersionTime(  String.valueOf(iNewTat.getVersionTime())) ;  
		}
		newTatForm.setLspLeId(            	iNewTat.getLspLeId()) ;              
		newTatForm.setLspShortName(        iNewTat.getLspShortName()) ;   
		newTatForm.setLspLeIdListSearch(            	iNewTat.getLspLeIdListSearch()) ;              
		newTatForm.setLspShortNameListSearch(        iNewTat.getLspShortNameListSearch()) ;   
		if(iNewTat.getCaseId() !=0 ){
			newTatForm.setCaseId( String.valueOf(iNewTat.getCaseId())); 
		}
		if(iNewTat.getId() !=0 ){
			newTatForm.setId( String.valueOf(iNewTat.getId())); 
		}
		newTatForm.setModule(              iNewTat.getModule()) ;               
		newTatForm.setCaseInitiator(       iNewTat.getCaseInitiator() );        
		newTatForm.setRelationshipManager(	iNewTat.getRelationshipManager() );  
		newTatForm.setDocStatus   (		iNewTat.getDocStatus()) ;            
		newTatForm.setRemarks(             iNewTat.getRemarks());                
		newTatForm.setFacilityCategory(   	iNewTat.getFacilityCategory()) ;     
		newTatForm.setFacilityName(       	iNewTat.getFacilityName()) ;         
		newTatForm.setCamType(            	iNewTat.getCamType() );              
		newTatForm.setDeferralType(       	iNewTat.getDeferralType()) ;         
		newTatForm.setLssCoordinatorBranch(iNewTat.getLssCoordinatorBranch()) ; 
		newTatForm.setType(                iNewTat.getType());  
		if(iNewTat.getActivityTime()!=null && !"".equals(iNewTat.getActivityTime())){
			newTatForm.setActivityTime(iNewTat.getActivityTime().toString()) ;  
		}
		if(iNewTat.getActualActivityTime()!=null && !"".equals(iNewTat.getActualActivityTime())){
			newTatForm.setActualActivityTime((iNewTat.getActualActivityTime().toString()));  
		}
		newTatForm.setFacilityManual(            iNewTat.getFacilityManual());               
		newTatForm.setFacilitySystem(            iNewTat.getFacilitySystem()); 
		newTatForm.setAmount(             	iNewTat.getAmount()) ;               
		newTatForm.setCurrency(          	iNewTat.getCurrency()) ;             
		newTatForm.setLineNumber(         	iNewTat.getLineNumber() );           
		//newTatForm.setSerialNumber(       	iNewTat.getSerialNumber()) ;   
		
		if( iNewTat.getFacilityManual() != null && !"".equals(iNewTat.getFacilityManual())){
			newTatForm.setSerialNumberManual(       	iNewTat.getSerialNumber()) ; 
		}
		else if( iNewTat.getFacilitySystem() != null && !"".equals(iNewTat.getFacilitySystem())){
			newTatForm.setSerialNumberSystem(       	iNewTat.getSerialNumber()) ; 
		}
		
		
		
		newTatForm.setRegion(iNewTat.getRegion());
		newTatForm.setRmRegion(iNewTat.getRmRegion());
		newTatForm.setSegment(iNewTat.getSegment());
		newTatForm.setIsTatBurst(iNewTat.getIsTatBurst());
		newTatForm.setDelayReason(iNewTat.getDelayReason());
		newTatForm.setDelayReasonText(iNewTat.getDelayReasonText());
		newTatForm.setFacilitySection(iNewTat.getFacilitySection());

		return newTatForm;
	}

	/**
	 * declares the key-value pair upfront for objects that needs to be accessed
	 * 
	 * in scope
	 * @return 2D-array key value pair
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "newTatObj", "com.integrosys.cms.app.newTat.bus.OBNewTat", SERVICE_SCOPE }, });
	}
}
