package com.integrosys.cms.ui.discrepency;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.discrepency.bus.IDiscrepencyFacilityList;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepency;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepencyFacilityList;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class DiscrepencyMapper extends AbstractCommonMapper {
	DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
					GLOBAL_SCOPE }
				 });
	}

	public Object mapFormToOB(CommonForm cForm, HashMap inputs)
			throws MapperException {
		HashMap facMap= new HashMap();
		DefaultLogger.debug(this,
				" -- DiscrepencyMapper-- Entering method mapFormToOB");
		DiscrepencyForm form = (DiscrepencyForm) cForm;
		IDiscrepency discrepency = new OBDiscrepency();
		
		if("maker_next_create_temp_list_discrepency".equals(form.getEvent())){
			return discrepency;
		}
		ILimitProfile limit = (ILimitProfile) inputs.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);		
		ILimit[] limits=null;
		if(limit!=null){
			limits=limit.getLimits();	
		}
		
		if(limits!=null){
		for(int k=0;k<limits.length;k++){
		facMap.put(String.valueOf(limits[k].getLimitID()), limits[k].getFacilityName());
		}
		}
//		List discrepencylist = new ArrayList();
		if(form.getCreditApprover()!=null)
		discrepency.setCreditApprover(form.getCreditApprover());
		if(form.getRecDate()!=null)
		discrepency.setRecDate(DateUtil.convertDate(form.getRecDate()));
		if(form.getWaiveDate()!=null)
		discrepency.setWaiveDate(DateUtil.convertDate(form.getWaiveDate()));
		if(form.getDeferDate()!=null)
			discrepency.setDeferDate(DateUtil.convertDate(form.getDeferDate()));
		if(form.getDocRemarks()!=null)
		form.getDocRemarks();
		discrepency.setDocRemarks(form.getDocRemarks());
		if(form.getCreditApprover()!=null)
		discrepency.setCreditApprover(form.getCreditApprover());
		if(form.getStatus()!=null)
		discrepency.setStatus(form.getStatus());
		
		discrepency.setDiscrepency(form.getDiscrepency());		
		discrepency.setApprovedBy(form.getApprovedBy());
		discrepency.setCritical(form.getCritical());
		discrepency.setDiscrepencyType(form.getDiscrepencyType());
		discrepency.setDiscrepencyRemark(form.getDiscrepencyRemark());
		discrepency.setAcceptedDate(DateUtil.convertDate(form.getAcceptedDate()));
		discrepency.setCreationDate(DateUtil.convertDate(form.getCreationDate()));
		discrepency.setOriginalTargetDate(DateUtil.convertDate(form.getOriginalTargetDate()));
		discrepency.setNextDueDate(DateUtil.convertDate(form.getNextDueDate()));
		discrepency.setSelectedArray(form.getSelectedArray());
		discrepency.setUnCheckArray(form.getUnCheckArray());

		if( form.getId() != null && !(form.getId().equals("")) )
			discrepency.setId(Long.parseLong(form.getId()));
		
		if( form.getCustomerId() != null && !(form.getCustomerId().equals("")) )
		discrepency.setCustomerId(Long.parseLong(form.getCustomerId()));
		
		if( form.getCounter() != null && !(form.getCounter().equals("")) )
			discrepency.setCounter(Long.parseLong(form.getCounter()));
				
		String facilityName = "";
		facilityName = form.getHiddenList();
		if( facilityName != null && !(facilityName.equals(""))){
			String[] splitList = facilityName.split("-");
//			IDiscrepencyFacilityList[] discrepencylist =new OBDiscrepencyFacilityList[splitList.length];
			IDiscrepencyFacilityList[] discrepencylist =new IDiscrepencyFacilityList[splitList.length];
			for(int i=0;i<splitList.length;i++ ){
				 discrepencylist[i] = new OBDiscrepencyFacilityList();
				 discrepencylist[i].setFacilityName(facMap.get(splitList[i]).toString());
				 discrepencylist[i].setFacilityId(Long.parseLong(splitList[i]));
				
			}
			discrepency.setFacilityList(discrepencylist);	
		}		
			
		if( form.getDeferedCounter() != null && !(form.getDeferedCounter().equals("")) ){
			discrepency.setDeferedCounter(Long.parseLong(form.getDeferedCounter()));
		}
		discrepency.setTotalDeferedDays(form.getTotalDeferedDays());
		discrepency.setOriginalDeferedDays(form.getOriginalDeferedDays());
		discrepency.setVersionTime(0L);
		
		return discrepency;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)
			throws MapperException {
		DefaultLogger.debug(this," -- DiscrepencyMapper-- Entering method mapOBToForm");
		DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
		
		DiscrepencyForm form = (DiscrepencyForm) cForm;
		IDiscrepency discrepency = (OBDiscrepency) obj;
		
		if("maker_create_discrepency_temp".equals(form.getEvent())
				||"maker_conform_update_temp_discrepency".equals(form.getEvent())
				||"prepare_create_discrepency_return".equals(form.getEvent())){
			
			DiscrepencyForm form2= new DiscrepencyForm();
			return form2;
		}else if("prepare_create_discrepency".equals(form.getEvent())){
			return form;
		}else if("prepare_maker_edit_discrepency".equals(form.getEvent())
				||"prepare_maker_edit_reject_discrepency".equals(form.getEvent())){
			if(form.getCreditApprover()==null)
				form.setCreditApprover(discrepency.getCreditApprover());
			if(form.getDiscrepencyRemark()==null)
				form.setDiscrepencyRemark(discrepency.getDiscrepencyRemark());
			if(form.getDocRemarks()==null)
				form.setDocRemarks(discrepency.getDocRemarks());
			if( form.getCreationDate() == null )
				form.setCreationDate(df.format(discrepency.getCreationDate()));
			
			if( form.getAcceptedDate() == null ){
				if(discrepency.getAcceptedDate()!=null){
				form.setAcceptedDate(df.format(discrepency.getAcceptedDate()));
				}
			}
			if( form.getOriginalTargetDate() == null ){
				if(discrepency.getOriginalTargetDate()!=null){
					form.setOriginalTargetDate(df.format(discrepency.getOriginalTargetDate()));	
				}
				
			}
				
			form.setDiscrepencyType(discrepency.getDiscrepencyType());
			if( form.getNextDueDate() == null ){
				if(discrepency.getNextDueDate()!=null){
				form.setNextDueDate(df.format(discrepency.getNextDueDate()));
				}
			}
			form.setId(String.valueOf(discrepency.getId()));
			form.setCustomerId(String.valueOf(discrepency.getCustomerId()));
			form.setCounter(String.valueOf(discrepency.getCounter()));
			form.setDeferedCounter(String.valueOf(discrepency.getDeferedCounter()));
			 if(discrepency.getOriginalDeferedDays()!=null){
		    	form.setOriginalDeferedDays(discrepency.getOriginalDeferedDays());}
		    if(discrepency.getTotalDeferedDays()!=null){
		    	form.setTotalDeferedDays(discrepency.getTotalDeferedDays());}
		    if(discrepency.getRecDate()!=null)
				form.setRecDate(df.format(discrepency.getRecDate()));
			if(discrepency.getWaiveDate()!=null)
				form.setWaiveDate(df.format(discrepency.getWaiveDate()));
			if(discrepency.getDeferDate()!=null)
				form.setDeferDate(df.format(discrepency.getDeferDate()));
			
			if(discrepency.getStatus()!=null)
				form.setStatus(discrepency.getStatus());
			if(form.getApprovedBy()==null)
				form.setApprovedBy(discrepency.getApprovedBy());
			if(form.getDiscrepency()==null)
				form.setDiscrepency(discrepency.getDiscrepency());
			if(form.getCritical()==null)
				form.setCritical(discrepency.getCritical());
				return form;
			
		}
		
		else if("prepare_maker_edit_discrepency_waive".equals(form.getEvent())
				||"prepare_maker_edit_discrepency_defer".equals(form.getEvent())
				||"prepare_maker_edit_discrepency_close".equals(form.getEvent())
				||"prepare_maker_edit_process_discrepency_waive".equals(form.getEvent())
				||"prepare_maker_edit_process_discrepency_defer".equals(form.getEvent())
				||"prepare_maker_edit_process_discrepency_close".equals(form.getEvent())
				){
			if(form.getCreditApprover()==null)
				form.setCreditApprover(discrepency.getCreditApprover());
			if(form.getDiscrepencyRemark()==null)
				form.setDiscrepencyRemark(discrepency.getDiscrepencyRemark());
			if(form.getDocRemarks()==null)
				form.setDocRemarks(discrepency.getDocRemarks());
			if("prepare_maker_edit_discrepency_defer".equals(form.getEvent())
					||"prepare_maker_edit_process_discrepency_defer".equals(form.getEvent())){
				if( form.getNextDueDate() == null )
					if(discrepency.getNextDueDate()!=null){
					form.setNextDueDate(df.format(discrepency.getNextDueDate()));
					}
			}
		}else{
			if(discrepency.getCreditApprover()!=null)
				form.setCreditApprover(discrepency.getCreditApprover());
			if(discrepency.getDiscrepencyRemark()!=null)
				form.setDiscrepencyRemark(discrepency.getDiscrepencyRemark());
			if(discrepency.getDocRemarks()!=null)
				form.setDocRemarks(discrepency.getDocRemarks());
			if(!"prepare_maker_edit_discrepency_defer".equals(form.getEvent())
					||!"prepare_maker_edit_process_discrepency_defer".equals(form.getEvent())){
				if( discrepency.getNextDueDate() != null )
					form.setNextDueDate(df.format(discrepency.getNextDueDate()));
			}
		}

		form.setDiscrepency(discrepency.getDiscrepency());
		form.setApprovedBy(discrepency.getApprovedBy());
		form.setCritical(discrepency.getCritical());
			
			
		if(form.getRecDate()==null){
			if(discrepency.getRecDate()!=null)
					form.setRecDate(df.format(discrepency.getRecDate()));
		}		
		if(form.getWaiveDate()==null){	
			if(discrepency.getWaiveDate()!=null)
				form.setWaiveDate(df.format(discrepency.getWaiveDate()));
		}
		if(form.getDeferDate()==null){
			if(discrepency.getDeferDate()!=null)
				form.setDeferDate(df.format(discrepency.getDeferDate()));
		}
			
			if(discrepency.getStatus()!=null)
				form.setStatus(discrepency.getStatus());
				
/*		Set list = discrepency.getFacilityList();
		Iterator itor = list.iterator();
		String faciliy = "";
		String faciliyId = "";
		while( itor.hasNext() ){
			IDiscrepencyFacilityList discrepencyList = (OBDiscrepencyFacilityList)itor.next();
			faciliy =  discrepencyList.getFacilityName() + "-" + faciliy;
			faciliyId = discrepencyList.getFacilityId() + "-" + faciliyId;
		
		}
		form.setHiddenList(faciliy);
		form.setSelectedFacilityList(faciliy);*/
//		form.setDiscrepencyId(faciliyId);
		    	//form.setDiscrepencyId(discrepency.getd);
		form.setDiscrepencyType(discrepency.getDiscrepencyType());
		
		if( discrepency.getCreationDate() != null )
			form.setCreationDate(df.format(discrepency.getCreationDate()));
		
		if( discrepency.getAcceptedDate() != null )
			form.setAcceptedDate(df.format(discrepency.getAcceptedDate()));
		
		if( discrepency.getOriginalTargetDate() != null )
			form.setOriginalTargetDate(df.format(discrepency.getOriginalTargetDate()));
		
		
		
		form.setId(String.valueOf(discrepency.getId()));
		form.setCustomerId(String.valueOf(discrepency.getCustomerId()));
		form.setCounter(String.valueOf(discrepency.getCounter()));
		form.setDeferedCounter(String.valueOf(discrepency.getDeferedCounter()));
		 if(discrepency.getOriginalDeferedDays()!=null)
	    	form.setOriginalDeferedDays(discrepency.getOriginalDeferedDays());
	    if(discrepency.getTotalDeferedDays()!=null)
	    	form.setTotalDeferedDays(discrepency.getTotalDeferedDays());
		
		return form;
	}
}
