/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recurrentDoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchResultItem;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.app.transaction.SBCMSTrxManagerHome;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/09/21 12:30:04 $ Tag: $Name: $
 */
public class ShowRecurrentMasterItemCommand extends AbstractCommand {

	/**
	 * Default Constructor
	 */
	public ShowRecurrentMasterItemCommand() {
		
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "type", "java.lang.String", REQUEST_SCOPE } ,
		{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
		{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
			GLOBAL_SCOPE },
			{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
		
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "globalDocChkList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "globalDocChkList", "java.util.ArrayList", SERVICE_SCOPE }		
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String chkTemplateType = (String) map.get("type");
		ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		
		String custClassification=null;
		String custRating=null;
	//	DefaultLogger.debug(this, "limit:"+limit);
		if(null!=limit){
		 custClassification=limit.getAssetClassification();
		 custRating=limit.getRamRating();
		 DefaultLogger.debug(this, "custRating before:"+custRating);
		 if(null!=custRating) {
			 custRating=custRating.trim();
		 }
		}
		
		//double custTotalSancAmt=limit.getRequiredSecurityCoverage();
		double custTotalSancAmt=Double.parseDouble(customer.getTotalSanctionedLimit());
		String custSegment=customer.getCustomerSegment();
		String custGuarantor =customer.getCMSLegalEntity().getSubLine();
		DefaultLogger.debug(this, "Inside doExecute(), type=" + chkTemplateType);
		HashMap itemsMap=new HashMap();
		ICheckList checkList=(ICheckList)map.get("checkList");
		
		DefaultLogger.debug(this, "custClassification:"+custClassification+ " partyId:"+customer.getCifId());
		DefaultLogger.debug(this, "custRating:"+custRating);
		DefaultLogger.debug(this, "custTotalSancAmt:"+custTotalSancAmt+" customer.getTotalSanctionedLimit():"+customer.getTotalSanctionedLimit());
		DefaultLogger.debug(this, "custSegment:"+custSegment+" custGuarantor:"+custGuarantor);
		//DefaultLogger.debug(this, "checkList:"+checkList);
		if(checkList!=null){
		ICheckListItem[] checkListItems=checkList.getCheckListItemList();
		
	//	DefaultLogger.debug(this, "checkListItems:"+checkListItems);
		if(checkListItems!=null){
	for(int w=0;w<checkListItems.length;w++){
		
	//	DefaultLogger.debug(this, " checkListItems[w].getItemCode():"+checkListItems[w].getItemCode());
		itemsMap.put(checkListItems[w].getItemCode(), checkListItems[w].getItemCode());
	}
}
		}
		ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
		ArrayList globalDocChkList = new ArrayList();
		DocumentSearchCriteria criteria = new DocumentSearchCriteria();
		criteria.setDocumentType("REC");
		String[] splitList = chkTemplateType.split("-");
		SearchResult sr = null;
		try {
			sr = proxy.getDocumentItemList(criteria);
	//		DefaultLogger.debug(this, "criteria inside try:"+criteria);
		}
		catch (CheckListTemplateException ex) {
			throw new CommandProcessingException("failed to retrieve document item list using criteria [" + criteria
					+ "]", ex);
		}

		try{
	//		DefaultLogger.debug(this, "sr:"+sr);	
		if (sr != null && sr.getResultList() != null) {
	//		DefaultLogger.debug(this, "sr.getResultList()");
			java.util.Vector vector= (java.util.Vector) sr.getResultList();
			
			if(checkList.getCheckListID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE){
				
		//		DefaultLogger.debug(this, "checkList.getCheckListID():"+checkList.getCheckListID()+" vector:"+vector.size());
				//System.out.println(":::::vector.size:::::::"+vector.size());
				for(int i=0;i<vector.size();i++){
					//boolean isRecurrent=false;
//					boolean isRating=false;
//					boolean isSegment=false;
//					boolean isTotalSancAmt=false;
//					boolean isClassification=false;
//					boolean isGuarantor=false;
					boolean showAll=false;
					//System.out.println(":::::0000000000000000:::::::"+vector.get(i));
					DocumentSearchResultItem resultItem=(DocumentSearchResultItem)vector.get(i);
					
		//			DefaultLogger.debug(this, "resultItem:"+resultItem);
					
				//	System.out.println(":::::1111111111111111111111:::::::"+splitList);
					if(splitList!=null){
		//				DefaultLogger.debug(this, "splitList:"+splitList);
						//System.out.println(":::::1111111111111111111111:::::::");
						if(splitList.length!=0){
						//	System.out.println(":::::22222222222222222222:::::::"+splitList.length);
							for(int j=0;j<splitList.length;j++){
								//System.out.println(":::::22222222222222222222111111111111111111111111111:::::::"+splitList[j]);
							//	System.out.println(":::::22222222222222222222111111111111111111111111111:::::::"+resultItem.getStatementType());
		//						DefaultLogger.debug(this, "splitList[j]:"+splitList[j]+" resultItem.getStatementType():"+resultItem.getStatementType()+" resultItem.getItemCode():"+resultItem.getItemCode()); 
					if(splitList[j].equals(resultItem.getStatementType())){
					//	System.out.println(":::::33333333333333333333:::::::");
						
						if(itemsMap.get(resultItem.getItemCode())==null){
		//						DefaultLogger.debug(this, "itemsMap.get(resultItem.getItemCode())");
							//System.out.println(":::::444444444444444444444:::::::");
							String rating= resultItem.getRating();
	                        String[] ratingArray=rating.split("-");
	                        String segment= resultItem.getSegment();
	                        String[] segmentArray=segment.split("-");
	                        String totalSancAmt=resultItem.getTotalSancAmt();
	                        String classification= resultItem.getClassification();
	                        String[] classificationArray=classification.split("-");
	                        String guarantor=resultItem.getGuarantor();
	                      //  System.out.println(":::::555555555555555555:::::::");
						  
			//			  DefaultLogger.debug(this, " rating:"+rating+" ratingArray:"+ratingArray+" segment:"+segment+" segmentArray:"+segmentArray+" totalSancAmt:"+totalSancAmt+" classification:"+classification+" classificationArray:"+classificationArray+" guarantor:"+guarantor);
	                        if(rating.equals("")&&segment.equals("")&&totalSancAmt.equals("")&&classification.equals("")&&guarantor.equals("")){
							
				///			DefaultLogger.debug(this, "inside all if ");
	                        	showAll=true;
	                        	//continue;
	                        }
	              //          	DefaultLogger.debug(this, "showAll:"+showAll);
	                        /*if(totalSancAmt!=null&&!totalSancAmt.equals("")){
	                        double dtotalSancAmt=Double.parseDouble(totalSancAmt);
	                        if(dtotalSancAmt<=custTotalSancAmt)
	                            isTotalSancAmt=true;
	                        }
	                      
	                        if(custClassification!=null){
							  if(classificationArray!=null){
							  for(int c =0; c< classificationArray.length ;c++){
								  if(custClassification.equalsIgnoreCase(classificationArray[c]))
									  isClassification=true;
									  
							  }
							  }
	                        }
	                        
	                        if(custSegment!=null){
							  if(segmentArray!=null){
							  for(int b =0; b< segmentArray.length ;b++){
								  if(custSegment.equalsIgnoreCase(segmentArray[b]))
									  isSegment=true;
							  }
							  }
	                        }
	                        if(custRating!=null){ 
	                        if(ratingArray!=null){
		                        	for(int a =0; a< ratingArray.length ;a++){
		                        		if(custRating.equalsIgnoreCase(ratingArray[a]))
		  								  isRating=true;
		                        	}
		                        }
	                        }
	                        
	                       
	                        
	                        if(custGuarantor!=null){ 
	                        if(custGuarantor.equalsIgnoreCase(guarantor))
	                        	isGuarantor=true;
	                        }*/
	                        
	                        
	                        if(showAll){
				//			DefaultLogger.debug(this, "adding resultItem in globalDocChkList");
	                        	globalDocChkList.add(resultItem);
	                        }
	                        /*else{
	                        if(isClassification||isGuarantor||isRating||isSegment||isTotalSancAmt){
	                        	globalDocChkList.add(resultItem);    	
	                        }
	                        }*/	
	                        
	                      //  System.out.println("::::::isClassification::::::"+isClassification);
	                      //  System.out.println("::::::isGuarantor::::::"+isGuarantor);
	                      //  System.out.println("::::::isRating::::::"+isRating);
	                     //   System.out.println("::::::isSegment::::::"+isSegment);
	                     //   System.out.println("::::::isTotalSancAmt::::::"+isTotalSancAmt);
	                     //   System.out.println("::::::showAll::::::"+showAll);
						}
					}
							}
						}
					}
				}
				
				Collections.sort(globalDocChkList);
			}else{
				
	//		DefaultLogger.debug(this, "inside else if checklist exist");
	//		DefaultLogger.debug(this, "vector.size():"+vector.size());
			//System.out.println(":::::vector.size:::::::"+vector.size());
			for(int i=0;i<vector.size();i++){
				boolean isRating=true;
				boolean isSegment=true;
				boolean isTotalSancAmt=true;
				boolean isClassification=true;
				boolean isGuarantor=true;
				boolean showAll=false;
				//System.out.println(":::::0000000000000000:::::::"+vector.get(i));
				DocumentSearchResultItem resultItem=(DocumentSearchResultItem)vector.get(i);
		//		DefaultLogger.debug(this, "resultItem:"+resultItem);
				if(splitList!=null){
					if(splitList.length!=0){
						for(int j=0;j<splitList.length;j++){
						
	//					DefaultLogger.debug(this, " splitList[j]:"+splitList[j]+" resultItem.getStatementType():"+resultItem.getStatementType()+" resultItem.getItemCode():"+resultItem.getItemCode());
				if(splitList[j].equals(resultItem.getStatementType())){
					
					if(itemsMap.get(resultItem.getItemCode())==null){
				
						String rating= resultItem.getRating();
                        String[] ratingArray=rating.split("-");
                        String segment= resultItem.getSegment();
                        String[] segmentArray=segment.split("-");
                        String totalSancAmt=resultItem.getTotalSancAmt();
                        String classification= resultItem.getClassification();
                        String[] classificationArray=classification.split("-");
                        String guarantor=resultItem.getGuarantor();
                     
		//			  DefaultLogger.debug(this, " rating:"+rating+" ratingArray:"+ratingArray+" segment:"+segment+" segmentArray:"+segmentArray+" totalSancAmt:"+totalSancAmt+" classification:"+classification+" classificationArray:"+classificationArray+" guarantor:"+guarantor);
					  
                        if(rating.equals("")&&segment.equals("")&&totalSancAmt.equals("")&&classification.equals("")&&guarantor.equals("")){
		//				DefaultLogger.debug(this, "inside if for all cond");
                        	showAll=true;
                        	//continue;
                        }
       //                 DefaultLogger.debug(this, "showAll:"+showAll);
                        if(totalSancAmt!=null&&!totalSancAmt.equals("")){
                        double dtotalSancAmt=Double.parseDouble(totalSancAmt);
                        if(!(dtotalSancAmt<=custTotalSancAmt))
                            isTotalSancAmt=false;
                        }
                      
                        if(custClassification!=null){
						  if(classificationArray!=null&& !classification.equals("")){
							  boolean value=false;
						  for(int c =0; c< classificationArray.length ;c++){
							 
							  if(custClassification.equalsIgnoreCase(classificationArray[c])){
								  value=true;
							  }
						  }
						  if(!value){
							  isClassification=false;
						  }
						  }
                        }
                        
                        if(custSegment!=null){
						  if(segmentArray!=null && ! segment.equals("")){
							  boolean value=false;
						  for(int b =0; b< segmentArray.length ;b++){
							
							  if(custSegment.equalsIgnoreCase(segmentArray[b]))
							  {
								  value=true;
							  }
							 
						  }
						  if(!value){
							  isSegment=false;
						  }
						  }
                        }
                        if(custRating!=null){ 
                        if(ratingArray!=null&& ! rating.equals("")){
                        	boolean value=false;
	                        	for(int a =0; a< ratingArray.length ;a++){
	                        		
	                        		if(custRating.equalsIgnoreCase(ratingArray[a]))
	                        		{
	                        			value=true;
									  }
	                        		
	                        	}
	                        	if(!value){
									  isRating=false;
								  }
	                        }
                        }
                        
                       
                        
                        if(custGuarantor!=null){
                        	 if(guarantor!=null&&!guarantor.equals("")){	
                        if(!custGuarantor.equalsIgnoreCase(guarantor))
                        	isGuarantor=false;
                        }
                        }
         //               DefaultLogger.debug(this, " isClassification:"+isClassification+" isGuarantor:"+isGuarantor+" isRating:"+isRating+" isSegment:"+isSegment+" isTotalSancAmt:"+isTotalSancAmt);
                        if(showAll){
		//				 DefaultLogger.debug(this,"inside showall adding into globalDocChkList");
                        	globalDocChkList.add(resultItem);
                        }else{
			//			DefaultLogger.debug(this,"inside else");
                        if(isClassification&&isGuarantor&&isRating&&isSegment&&isTotalSancAmt){
			//			DefaultLogger.debug(this,"inside isClassification&&isGuarantor&&isRating&&isSegment&&isTotalSancAmt adding into globalDocChkList");
                        	globalDocChkList.add(resultItem);    	
                        }
                        }	
					}
					}
				}
						}
					}
			}
		//	 DefaultLogger.debug(this, " globalDocChkList:"+globalDocChkList.size());
			Collections.sort(globalDocChkList);
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
		}

		resultMap.put("globalDocChkList", globalDocChkList);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	private SBCMSTrxManager getTrxManager() throws TrxOperationException {
		SBCMSTrxManager mgr = (SBCMSTrxManager) BeanController.getEJB(ICMSJNDIConstant.SB_CMS_TRX_MGR_HOME,
				SBCMSTrxManagerHome.class.getName());

		if (null == mgr) {
			throw new TrxOperationException("failed to find cms trx manager remote interface using jndi name ["
					+ ICMSJNDIConstant.SB_CMS_TRX_MGR_HOME + "]");
		}
		else {
			return mgr;
		}
	}
}
