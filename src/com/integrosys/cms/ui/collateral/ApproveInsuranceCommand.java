package com.integrosys.cms.ui.collateral;

import java.util.*;

import com.crystaldecisions.celib.properties.o;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGC;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGCJdbc;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC;

public class ApproveInsuranceCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, 
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
				 {"insuranceList", "java.util.ArrayList", SERVICE_SCOPE},
				});
	}
	
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		List editIns=new ArrayList();
		ICollateralTrxValue trxValue = (ICollateralTrxValue) map.get("serviceColObj");
		IInsuranceGCDao  insuranceGCDao = (IInsuranceGCDao) BeanHouse.get("insuranceGcDao");
		List insuranceList=(ArrayList)map.get("insuranceList");
		
		
		
		Date toDay=new Date();
		IInsuranceGCJdbc insuranceGCJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
		try{
			if(insuranceList!=null){
			Iterator it = insuranceList.iterator();
			while(it.hasNext()){
				OBInsuranceGC s = (OBInsuranceGC) it.next();
				//Condition for new created 
				if(s.getIsProcessed().equals("N")&& s.getDeprecated().equals("N")){
				s.setIsProcessed("Y");
				s.setLastApproveBy(trxValue.getLoginId());
				s.setLastApproveOn(toDay);
				insuranceGCDao.updateInsurance("stageInsurance", s);
				OBInsuranceGC newIns = new OBInsuranceGC();
				newIns=replicateInsurance(s,newIns);
				insuranceGCDao.createInsurance("actualInsurance", newIns);
				
				 //Uma Khot::Insurance Deferral maintainance
				if(null!=newIns && ICMSConstant.STATE_ITEM_RECEIVED.equals(newIns.getInsuranceStatus())){
				createNewPendingInsurance(insuranceGCDao,newIns);
				}
				
				updateStageInsurance(insuranceGCDao,s,"stageInsurance");
				}
				
				//Condition for delete
				
				if(s.getIsProcessed().equals("N")&& s.getDeprecated().equals("Y")){
					s.setIsProcessed("Y");
					s.setLastApproveBy(trxValue.getLoginId());
					s.setLastApproveOn(toDay);
					insuranceGCDao.updateInsurance("stageInsurance", s);
					
					String collateralID=s.getInsuranceCode();
					long parent=s.getParentId();
					
					OBInsuranceGC newIns =actualDeleteObject(collateralID,parent);
					//newIns=replicateInsurance(s,newIns);
					insuranceGCDao.updateInsurance("actualInsurance", newIns);
					
					updateStageInsurance(insuranceGCDao,s,"stageInsurance");
					}
				
				//condition for edit
				
					if(s.getIsProcessed().equals("NM")&& s.getDeprecated().equals("N")){
						s.setIsProcessed("Y");
						s.setLastApproveBy(trxValue.getLoginId());
						s.setLastApproveOn(toDay);
						insuranceGCDao.updateInsurance("stageInsurance", s);
						
						String collateralID=s.getInsuranceCode();
						long parent=s.getParentId();
						
						//OBInsuranceGC newIns =actualEditObject(collateralID,parent,s);
						//newIns=replicateInsurance(s,newIns);
						editIns=actualEditObject(collateralID,parent,s);
						if(editIns.size()>0){
						for(int j=0;j<editIns.size();j++){
							OBInsuranceGC replaceIns = new OBInsuranceGC();
							replaceIns=(OBInsuranceGC)editIns.get(j);
						insuranceGCDao.updateInsurance("actualInsurance",replaceIns);
						}
						}
						
						if("PENDING_RECEIVED".equals(s.getInsuranceStatus())){
							createNewPendingInsurance(insuranceGCDao,s);
						}
						
						updateStageInsurance(insuranceGCDao,s,"stageInsurance");
					}
					
					//conditioin for draft
					
						if(s.getIsProcessed().equals("DD")&& (s.getDeprecated().equals("N")||s.getDeprecated().equals("Y"))){
							s.setIsProcessed("Y");
							s.setLastApproveBy(trxValue.getLoginId());
							s.setLastApproveOn(toDay);
							insuranceGCDao.updateInsurance("stageInsurance", s);
							String collateralID=s.getInsuranceCode();
							long parent=s.getParentId();
							
							OBInsuranceGC newIns =actualDeleteObject(collateralID,parent);
							//newIns=replicateInsurance(s,newIns);
							insuranceGCDao.updateInsurance("actualInsurance", newIns);
							
							updateStageInsurance(insuranceGCDao,s,"stageInsurance");
							}
						
						if(s.getIsProcessed().equals("DA")&& (s.getDeprecated().equals("N")||s.getDeprecated().equals("Y"))){
							s.setIsProcessed("Y");
							s.setLastApproveBy(trxValue.getLoginId());
							s.setLastApproveOn(toDay);
							insuranceGCDao.updateInsurance("stageInsurance", s);
							OBInsuranceGC newIns = new OBInsuranceGC();
							newIns=replicateInsurance(s,newIns);
							insuranceGCDao.createInsurance("actualInsurance", newIns);
							
							 //Uma Khot::Insurance Deferral maintainance
							if(null!=newIns && ICMSConstant.STATE_ITEM_RECEIVED.equals(newIns.getInsuranceStatus())){
							createNewPendingInsurance(insuranceGCDao,newIns);
							}
							
							updateStageInsurance(insuranceGCDao,s,"stageInsurance");
							}
						
						if(s.getIsProcessed().equals("DE")&& (s.getDeprecated().equals("N")||s.getDeprecated().equals("Y"))){
							s.setIsProcessed("Y");
							s.setLastApproveBy(trxValue.getLoginId());
							s.setLastApproveOn(toDay);
							insuranceGCDao.updateInsurance("stageInsurance", s);
							String collateralID=s.getInsuranceCode();
							long parent=s.getParentId();
							
							//OBInsuranceGC newIns =actualEditObject(collateralID,parent,s);
							//newIns=replicateInsurance(s,newIns);
							editIns=actualEditObject(collateralID,parent,s);
							if(editIns.size()>0){
							for(int j=0;j<editIns.size();j++){
								OBInsuranceGC replaceIns = new OBInsuranceGC();
								replaceIns=(OBInsuranceGC)editIns.get(j);
							insuranceGCDao.updateInsurance("actualInsurance",replaceIns);
							}
					
				
				
				
			}
							if("UPDATE_RECEIVED".equals(s.getInsuranceStatus())){
								createNewPendingInsurance(insuranceGCDao,s);
							}
							
							updateStageInsurance(insuranceGCDao,s,"stageInsurance");
			}
		}
			}
		}
		
		catch(Exception ex){
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
			
		}
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return temp;
	}
	
	



public OBInsuranceGC draftInsurance(OBInsuranceGC s){
	OBInsuranceGC replaceIns = new OBInsuranceGC();
	replaceIns.setInsuranceCode(s.getInsuranceCode());
		replaceIns.setCreationDate(s.getCreationDate());
		replaceIns.setParentId(s.getParentId());
		replaceIns.setIsProcessed(s.getIsProcessed());
		replaceIns.setDeprecated(s.getDeprecated());
		replaceIns.setCoverNoteNo(s.getCoverNoteNo());
		replaceIns.setEffectiveDate(s.getEffectiveDate());
		replaceIns.setExpiryDate(s.getExpiryDate());
		replaceIns.setReceivedDate(s.getReceivedDate());
		replaceIns.setInsuranceCompany(s.getInsuranceCompany());
		replaceIns.setInsuranceCoverge(s.getInsuranceCoverge());
		replaceIns.setInsuranceCurrency(s.getInsuranceCurrency());
		replaceIns.setInsuranceDefaulted(s.getInsuranceDefaulted());
		replaceIns.setInsurancePolicyAmt(s.getInsurancePolicyAmt());
		replaceIns.setInsurancePolicyNo(s.getInsurancePolicyNo());
		replaceIns.setInsurancePremium(s.getInsurancePremium());
		replaceIns.setInsuranceRequired(s.getInsuranceRequired());
		replaceIns.setInsuredAmount(s.getInsuredAmount());
		replaceIns.setRemark(s.getRemark());
		if(s.getSelectComponent()!=null){
			replaceIns.setSelectComponent(s.getSelectComponent());
		}
		/*if(s.getAllComponent()!=null){
			replaceIns.setAllComponent(s.getAllComponent());
		}*/
		replaceIns.setLastApproveBy(s.getLastApproveBy());
		replaceIns.setLastApproveOn(s.getLastApproveOn());
		replaceIns.setLastUpdatedBy(s.getLastUpdatedBy());
		replaceIns.setLastUpdatedOn(s.getLastUpdatedOn());
		
		//Uma Khot::Insurance Deferral maintainance
		replaceIns.setInsuranceStatus(s.getInsuranceStatus());
		replaceIns.setInsuredAddress(s.getInsuredAddress());
		replaceIns.setRemark2(s.getRemark2());
		replaceIns.setInsuredAgainst(s.getInsuredAgainst());
		replaceIns.setOriginalTargetDate(s.getOriginalTargetDate());
		replaceIns.setOldPolicyNo(s.getOldPolicyNo());
		replaceIns.setNextDueDate(s.getNextDueDate());
		replaceIns.setDateDeferred(s.getDateDeferred());
		replaceIns.setWaivedDate(s.getWaivedDate());
		replaceIns.setCreditApprover(s.getCreditApprover());
		
		return replaceIns;
	}
	
	
	
	public OBInsuranceGC replicateInsurance(OBInsuranceGC s, OBInsuranceGC newIns){
		
		newIns.setInsuranceCode(s.getInsuranceCode());
		newIns.setCreationDate(s.getCreationDate());
		newIns.setParentId(s.getParentId());
		newIns.setIsProcessed(s.getIsProcessed());
		newIns.setDeprecated(s.getDeprecated());
		newIns.setCoverNoteNo(s.getCoverNoteNo());
		newIns.setEffectiveDate(s.getEffectiveDate());
		newIns.setExpiryDate(s.getExpiryDate());
		newIns.setReceivedDate(s.getReceivedDate());
		newIns.setInsuranceCompany(s.getInsuranceCompany());
		newIns.setInsuranceCoverge(s.getInsuranceCoverge());
		newIns.setInsuranceCurrency(s.getInsuranceCurrency());
		newIns.setInsuranceDefaulted(s.getInsuranceDefaulted());
		newIns.setInsurancePolicyAmt(s.getInsurancePolicyAmt());
		newIns.setInsurancePolicyNo(s.getInsurancePolicyNo());
		newIns.setInsurancePremium(s.getInsurancePremium());
		newIns.setInsuranceRequired(s.getInsuranceRequired());
		newIns.setInsuredAmount(s.getInsuredAmount());
		newIns.setRemark(s.getRemark());
		if(s.getSelectComponent()!=null){
			newIns.setSelectComponent(s.getSelectComponent());
		}
		/*if(s.getAllComponent()!=null){
			newIns.setAllComponent(s.getAllComponent());
		}*/
		newIns.setLastApproveBy(s.getLastApproveBy());
		newIns.setLastApproveOn(s.getLastApproveOn());
		newIns.setLastUpdatedBy(s.getLastUpdatedBy());
		newIns.setLastUpdatedOn(s.getLastUpdatedOn());
		
		//Uma Khot::Insurance Deferral maintainance
		
		if("PENDING_RECEIVED".equals(s.getInsuranceStatus()) || "UPDATE_RECEIVED".equals(s.getInsuranceStatus())){
			newIns.setInsuranceStatus(ICMSConstant.STATE_ITEM_RECEIVED);
		}
		else if(ICMSConstant.STATE_ITEM_PENDING_WAIVER.equals(s.getInsuranceStatus())){
			newIns.setInsuranceStatus(ICMSConstant.STATE_ITEM_WAIVED);
		}
		else if(ICMSConstant.STATE_ITEM_PENDING_DEFERRAL.equals(s.getInsuranceStatus())){
			newIns.setInsuranceStatus(ICMSConstant.STATE_ITEM_DEFERRED);
		}
		else if(ICMSConstant.STATE_ITEM_PENDING_UPDATE.equals(s.getInsuranceStatus())){
			newIns.setInsuranceStatus(ICMSConstant.STATE_ITEM_AWAITING);
		}else{
		newIns.setInsuranceStatus(s.getInsuranceStatus());
		}
		newIns.setInsuredAddress(s.getInsuredAddress());
		newIns.setRemark2(s.getRemark2());
		newIns.setInsuredAgainst(s.getInsuredAgainst());
		newIns.setOriginalTargetDate(s.getOriginalTargetDate());
		newIns.setNextDueDate(s.getNextDueDate());
		newIns.setDateDeferred(s.getDateDeferred());
		newIns.setWaivedDate(s.getWaivedDate());
		newIns.setCreditApprover(s.getCreditApprover());
		newIns.setOldPolicyNo(s.getOldPolicyNo());
		
		return newIns;
	}
	
	
	public OBInsuranceGC actualDeleteObject(String collateralID,long parent){
		List actualList=new ArrayList();
		OBInsuranceGC replaceIns = new OBInsuranceGC();
		IInsuranceGCJdbc insuranceGCJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
		String parentID=String.valueOf(parent);
		SearchResult allActualInsuranceList= (SearchResult)  insuranceGCJdbc.getAllActualInsurance(parentID);
		actualList=(List) allActualInsuranceList.getResultList();
		for(int i=0;i<actualList.size();i++){
			IInsuranceGC actualObj=(IInsuranceGC)actualList.get(i);
			if(actualObj.getInsuranceCode().equals(collateralID)&& String.valueOf(actualObj.getParentId()).equals(parentID)){
				replaceIns.setCreationDate(actualObj.getCreationDate());
				replaceIns.setInsuranceCode(actualObj.getInsuranceCode());			
				replaceIns.setParentId(actualObj.getParentId());
				replaceIns.setIsProcessed("Y");
				replaceIns.setDeprecated("Y");
				replaceIns.setId(actualObj.getId());
				replaceIns.setVersionTime(actualObj.getVersionTime());
				replaceIns.setCoverNoteNo(actualObj.getCoverNoteNo());
				replaceIns.setEffectiveDate(actualObj.getEffectiveDate());
				replaceIns.setExpiryDate(actualObj.getExpiryDate());
				replaceIns.setReceivedDate(actualObj.getReceivedDate());
				replaceIns.setInsuranceCompany(actualObj.getInsuranceCompany());
				replaceIns.setInsuranceCoverge(actualObj.getInsuranceCoverge());
				replaceIns.setInsuranceCurrency(actualObj.getInsuranceCurrency());
				replaceIns.setInsuranceDefaulted(actualObj.getInsuranceDefaulted());
				replaceIns.setInsurancePolicyAmt(actualObj.getInsurancePolicyAmt());
				replaceIns.setInsurancePolicyNo(actualObj.getInsurancePolicyNo());
				replaceIns.setInsurancePremium(actualObj.getInsurancePremium());
				replaceIns.setInsuranceRequired(actualObj.getInsuranceRequired());
				replaceIns.setInsuredAmount(actualObj.getInsuredAmount());
				replaceIns.setRemark(actualObj.getRemark());
				if(actualObj.getSelectComponent()!=null){
					replaceIns.setSelectComponent(actualObj.getSelectComponent());
				}
				replaceIns.setLastApproveBy(actualObj.getLastApproveBy());
				replaceIns.setLastApproveOn(actualObj.getLastApproveOn());
				replaceIns.setLastUpdatedBy(actualObj.getLastUpdatedBy());
				replaceIns.setLastUpdatedOn(actualObj.getLastUpdatedOn());
				/*if(actualObj.getAllComponent()!=null){
					replaceIns.setAllComponent(actualObj.getAllComponent());
				}*/
				
				//Uma Khot::Insurance Deferral maintainance
				if("PENDING_RECEIVED".equals(actualObj.getInsuranceStatus()) || "UPDATE_RECEIVED".equals(actualObj.getInsuranceStatus())){
					replaceIns.setInsuranceStatus(ICMSConstant.STATE_ITEM_RECEIVED);
				}
				else if(ICMSConstant.STATE_ITEM_PENDING_WAIVER.equals(actualObj.getInsuranceStatus())){
					replaceIns.setInsuranceStatus(ICMSConstant.STATE_ITEM_WAIVED);
				}
				else if(ICMSConstant.STATE_ITEM_PENDING_DEFERRAL.equals(actualObj.getInsuranceStatus())){
					replaceIns.setInsuranceStatus(ICMSConstant.STATE_ITEM_DEFERRED);
				}
				else if(ICMSConstant.STATE_ITEM_PENDING_UPDATE.equals(actualObj.getInsuranceStatus())){
					replaceIns.setInsuranceStatus(ICMSConstant.STATE_ITEM_AWAITING);
				}else{
				replaceIns.setInsuranceStatus(actualObj.getInsuranceStatus());
				}
				replaceIns.setInsuredAddress(actualObj.getInsuredAddress());
				replaceIns.setRemark2(actualObj.getRemark2());
				replaceIns.setInsuredAgainst(actualObj.getInsuredAgainst());
				replaceIns.setOriginalTargetDate(actualObj.getOriginalTargetDate());
				replaceIns.setNextDueDate(actualObj.getNextDueDate());
				replaceIns.setDateDeferred(actualObj.getDateDeferred());
				replaceIns.setWaivedDate(actualObj.getWaivedDate());
				replaceIns.setCreditApprover(actualObj.getCreditApprover());
			}
			
		}
		
		return replaceIns;
		
	}
	
	
	public List actualEditObject(String collateralID,long parent,OBInsuranceGC s){
		List actualList=new ArrayList();
		List editIns=new ArrayList();
		OBInsuranceGC actualObj=new OBInsuranceGC();
		//OBInsuranceGC replaceIns = new OBInsuranceGC();
		IInsuranceGCJdbc insuranceGCJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
		String parentID=String.valueOf(parent);
		SearchResult allActualInsuranceList= (SearchResult)  insuranceGCJdbc.getAllActualInsurance(parentID);
		actualList=(List) allActualInsuranceList.getResultList();
		for(int i=0;i<actualList.size();i++){
			 actualObj=(OBInsuranceGC)actualList.get(i);
			if(actualObj.getInsuranceCode().equals(collateralID)&& String.valueOf(actualObj.getParentId()).equals(parentID)){
				actualObj.setCreationDate(s.getCreationDate());
				actualObj.setInsuranceCode(s.getInsuranceCode());				
				actualObj.setParentId(s.getParentId());
				actualObj.setIsProcessed("Y");
				actualObj.setDeprecated("N");
				//actualObj.setId(actualObj.getId());
				//actualObj.setVersionTime(s.getVersionTime());
				actualObj.setCoverNoteNo(s.getCoverNoteNo());
				actualObj.setEffectiveDate(s.getEffectiveDate());
				actualObj.setExpiryDate(s.getExpiryDate());
				actualObj.setReceivedDate(s.getReceivedDate());
				actualObj.setInsuranceCompany(s.getInsuranceCompany());
				actualObj.setInsuranceCoverge(s.getInsuranceCoverge());
				actualObj.setInsuranceCurrency(s.getInsuranceCurrency());
				actualObj.setInsuranceDefaulted(s.getInsuranceDefaulted());
				actualObj.setInsurancePolicyAmt(s.getInsurancePolicyAmt());
				actualObj.setInsurancePolicyNo(s.getInsurancePolicyNo());
				actualObj.setInsurancePremium(s.getInsurancePremium());
				actualObj.setInsuranceRequired(s.getInsuranceRequired());
				actualObj.setInsuredAmount(s.getInsuredAmount());
				actualObj.setRemark(s.getRemark());
				if(s.getSelectComponent()!=null){
					actualObj.setSelectComponent(s.getSelectComponent());
				}
				/*if(actualObj.getAllComponent()!=null){
					replaceIns.setAllComponent(actualObj.getAllComponent());
				}*/
				actualObj.setLastApproveBy(s.getLastApproveBy());
				actualObj.setLastApproveOn(s.getLastApproveOn());
				actualObj.setLastUpdatedBy(s.getLastUpdatedBy());
				actualObj.setLastUpdatedOn(s.getLastUpdatedOn());
				
				//Uma Khot::Insurance Deferral maintainance
				if("PENDING_RECEIVED".equals(s.getInsuranceStatus()) || "UPDATE_RECEIVED".equals(s.getInsuranceStatus())){
					actualObj.setInsuranceStatus(ICMSConstant.STATE_ITEM_RECEIVED);
				}
				else if(ICMSConstant.STATE_ITEM_PENDING_WAIVER.equals(s.getInsuranceStatus())){
					actualObj.setInsuranceStatus(ICMSConstant.STATE_ITEM_WAIVED);
				}
				else if(ICMSConstant.STATE_ITEM_PENDING_DEFERRAL.equals(s.getInsuranceStatus())){
					actualObj.setInsuranceStatus(ICMSConstant.STATE_ITEM_DEFERRED);
				}
				else if(ICMSConstant.STATE_ITEM_PENDING_UPDATE.equals(s.getInsuranceStatus())){
					actualObj.setInsuranceStatus(ICMSConstant.STATE_ITEM_AWAITING);
				}else{
				actualObj.setInsuranceStatus(s.getInsuranceStatus());
				}
				actualObj.setInsuredAddress(s.getInsuredAddress());
				actualObj.setRemark2(s.getRemark2());
				actualObj.setInsuredAgainst(s.getInsuredAgainst());
				actualObj.setOriginalTargetDate(s.getOriginalTargetDate());
				actualObj.setOldPolicyNo(s.getOldPolicyNo());
				actualObj.setNextDueDate(s.getNextDueDate());
				actualObj.setDateDeferred(s.getDateDeferred());
				actualObj.setWaivedDate(s.getWaivedDate());
				actualObj.setCreditApprover(s.getCreditApprover());
				
				editIns.add(actualObj);
			}
			
		}
		
		return editIns;
		
	}
	
	public List actualDraftObject(String collateralID,long parent,OBInsuranceGC s){
		List actualList=new ArrayList();
		List editIns=new ArrayList();
		OBInsuranceGC actualObj=new OBInsuranceGC();
		//OBInsuranceGC replaceIns = new OBInsuranceGC();
		IInsuranceGCJdbc insuranceGCJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
		String parentID=String.valueOf(parent);
		SearchResult allActualInsuranceList= (SearchResult)  insuranceGCJdbc.getAllActualInsurance(parentID);
		actualList=(List) allActualInsuranceList.getResultList();
		for(int i=0;i<actualList.size();i++){
			 actualObj=(OBInsuranceGC)actualList.get(i);
			if(actualObj.getInsuranceCode().equals(collateralID)&& String.valueOf(actualObj.getParentId()).equals(parentID)){
				actualObj.setCreationDate(s.getCreationDate());
				actualObj.setInsuranceCode(s.getInsuranceCode());				
				actualObj.setParentId(s.getParentId());
				actualObj.setIsProcessed("Y");
				actualObj.setDeprecated(s.getDeprecated());
				//actualObj.setId(actualObj.getId());
				//actualObj.setVersionTime(s.getVersionTime());
				actualObj.setCoverNoteNo(s.getCoverNoteNo());
				actualObj.setEffectiveDate(s.getEffectiveDate());
				actualObj.setExpiryDate(s.getExpiryDate());
				actualObj.setReceivedDate(s.getReceivedDate());
				actualObj.setInsuranceCompany(s.getInsuranceCompany());
				actualObj.setInsuranceCoverge(s.getInsuranceCoverge());
				actualObj.setInsuranceCurrency(s.getInsuranceCurrency());
				actualObj.setInsuranceDefaulted(s.getInsuranceDefaulted());
				actualObj.setInsurancePolicyAmt(s.getInsurancePolicyAmt());
				actualObj.setInsurancePolicyNo(s.getInsurancePolicyNo());
				actualObj.setInsurancePremium(s.getInsurancePremium());
				actualObj.setInsuranceRequired(s.getInsuranceRequired());
				actualObj.setInsuredAmount(s.getInsuredAmount());
				actualObj.setRemark(s.getRemark());
				if(s.getSelectComponent()!=null){
					actualObj.setSelectComponent(s.getSelectComponent());
				}
				/*if(actualObj.getAllComponent()!=null){
					replaceIns.setAllComponent(actualObj.getAllComponent());
				}*/
				actualObj.setLastApproveBy(s.getLastApproveBy());
				actualObj.setLastApproveOn(s.getLastApproveOn());
				actualObj.setLastUpdatedBy(s.getLastUpdatedBy());
				actualObj.setLastUpdatedOn(s.getLastUpdatedOn());
				
				//Uma Khot::Insurance Deferral maintainance
				actualObj.setInsuranceStatus(s.getInsuranceStatus());
				actualObj.setInsuredAddress(s.getInsuredAddress());
				actualObj.setRemark2(s.getRemark2());
				actualObj.setInsuredAgainst(s.getInsuredAgainst());
				actualObj.setOriginalTargetDate(s.getOriginalTargetDate());
				actualObj.setNextDueDate(s.getNextDueDate());
				actualObj.setDateDeferred(s.getDateDeferred());
				actualObj.setWaivedDate(s.getWaivedDate());
				actualObj.setCreditApprover(s.getCreditApprover());
				
				editIns.add(actualObj);
				
			}
			
		}
		
		return editIns;
		
	}
	
	//Uma Khot::Insurance Deferral maintainance

	public void createNewPendingInsurance(IInsuranceGCDao insuranceGCDao,OBInsuranceGC newIns){
	
		OBInsuranceGC newOBInsuranceGC=new OBInsuranceGC();
		newOBInsuranceGC.setOriginalTargetDate(newIns.getExpiryDate());
		newOBInsuranceGC.setLastApproveBy(newIns.getLastApproveBy());
		newOBInsuranceGC.setLastApproveOn(newIns.getLastApproveOn());
		newOBInsuranceGC.setLastUpdatedBy(newIns.getLastUpdatedBy());
		newOBInsuranceGC.setLastUpdatedOn(newIns.getLastUpdatedOn());
		newOBInsuranceGC.setParentId(newIns.getParentId());
		newOBInsuranceGC.setCreationDate(new Date());
		newOBInsuranceGC.setIsProcessed("Y");
		newOBInsuranceGC.setDeprecated("N");
		newOBInsuranceGC.setInsuranceStatus(ICMSConstant.STATE_ITEM_AWAITING);
		
		newOBInsuranceGC.setInsuranceCode("IGC" +insuranceGCDao.getInsCode());
		newOBInsuranceGC=(OBInsuranceGC) insuranceGCDao.createInsurance("stageInsurance", newOBInsuranceGC);
		OBInsuranceGC newIns2 = new OBInsuranceGC();
		newIns2=replicateInsurance(newOBInsuranceGC,newIns2);
		insuranceGCDao.createInsurance("actualInsurance", newIns2);
	
	}
	
	public void updateStageInsurance(IInsuranceGCDao insuranceGCDao,OBInsuranceGC s,String insurance){
	if("PENDING_RECEIVED".equals(s.getInsuranceStatus()) || "UPDATE_RECEIVED".equals(s.getInsuranceStatus())){
		s.setInsuranceStatus(ICMSConstant.STATE_ITEM_RECEIVED);
	}
	else if(ICMSConstant.STATE_ITEM_PENDING_WAIVER.equals(s.getInsuranceStatus())){
		s.setInsuranceStatus(ICMSConstant.STATE_ITEM_WAIVED);
	}
	else if(ICMSConstant.STATE_ITEM_PENDING_DEFERRAL.equals(s.getInsuranceStatus())){
		s.setInsuranceStatus(ICMSConstant.STATE_ITEM_DEFERRED);
	}
	else if(ICMSConstant.STATE_ITEM_PENDING_UPDATE.equals(s.getInsuranceStatus())){
		s.setInsuranceStatus(ICMSConstant.STATE_ITEM_AWAITING);
	}else{
	s.setInsuranceStatus(s.getInsuranceStatus());
	}
	insuranceGCDao.updateInsurance(insurance, s);
	}
}
