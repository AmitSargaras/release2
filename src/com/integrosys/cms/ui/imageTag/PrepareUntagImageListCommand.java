package com.integrosys.cms.ui.imageTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckListDAO;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMasterJdbc;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue;
import com.integrosys.cms.app.imageTag.trx.OBImageTagTrxValue;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListDAO;
import com.integrosys.cms.app.recurrent.bus.RecurrentDAOFactory;

/**
 * This command creates a Image Tag
 * 
 * @author abhijit.rudrakshawar
 * 
 * 
 */

public class PrepareUntagImageListCommand extends AbstractCommand {
	private IImageTagProxyManager imageTagProxyManager;
	private ICollateralProxy collateralProxy;
	private ICheckListProxyManager checkListProxyManager;
	private ILimitProxy limitProxy;

	public IImageTagProxyManager getImageTagProxyManager() {
		return imageTagProxyManager;
	}

	public void setImageTagProxyManager(
			IImageTagProxyManager imageTagProxyManager) {
		this.imageTagProxyManager = imageTagProxyManager;
	}

	public ICollateralProxy getCollateralProxy() {
		return collateralProxy;
	}

	public void setCollateralProxy(ICollateralProxy collateralProxy) {
		this.collateralProxy = collateralProxy;
	}

	public ICheckListProxyManager getCheckListProxyManager() {
		return checkListProxyManager;
	}

	public void setCheckListProxyManager(
			ICheckListProxyManager checkListProxyManager) {
		this.checkListProxyManager = checkListProxyManager;
	}

	public ILimitProxy getLimitProxy() {
		return limitProxy;
	}

	public void setLimitProxy(ILimitProxy limitProxy) {
		this.limitProxy = limitProxy;
	}

	public String[][] getParameterDescriptor() {
		DefaultLogger.debug(this, "******** getParameterDescriptor Call: ");
		return (new String[][] {
				{"ImageTagMapObj","com.integrosys.cms.app.imageTag.bus.OBImageTagDetails",FORM_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "customerName", "java.lang.String", REQUEST_SCOPE }
				});
	}

	public String[][] getResultDescriptor() {
		DefaultLogger.debug(this, "********  getResultDescriptor Call: ");
		return (new String[][] { 
				{ "imageTagTrxValue", "com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue", SERVICE_SCOPE },
				{"ImageTagMapObj","com.integrosys.cms.app.imageTag.bus.OBImageTagDetails",FORM_SCOPE },
				{ "tageedImageList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "tageedImageList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "failitySourceID", "java.Lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "camInfo", "com.integrosys.cms.app.limit.bus.OBLimitProfile", REQUEST_SCOPE },
				{ "customerName", "java.lang.String", REQUEST_SCOPE }
				   });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @throws CommandProcessingException
	 *             on errors
	 * @throws CommandValidationException
	 *             on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap result = new HashMap();
		HashMap returnMap = new HashMap();
		HashMap collateralCodeMap = getCollateralInfo();
		DefaultLogger.debug(this, "Enter in doExecute()");
		String custLimitProfileID=(String) map.get("custLimitProfileID");
		String event=(String) map.get("event");
		IImageTagDetails imageTagDetails = (IImageTagDetails) map.get("ImageTagMapObj");
		IImageTagDetails existingTagDetails=null;
		/*if(!imageTagDetails.getDocType().equals("CAM_NOTE")){*/
		existingTagDetails=getImageTagProxyManager().getExistingImageTag(imageTagDetails);
		
		IImageTagTrxValue trxValueIn = new OBImageTagTrxValue();
		if(existingTagDetails!=null){
			try {
				trxValueIn=getImageTagProxyManager().getImageTagTrxByID((Long.toString(existingTagDetails.getId())));
				if(!(trxValueIn.getStatus().equals("ACTIVE")))
				{
					result.put("wip", "wip");
				}
			} catch (ImageTagException e) {
				e.printStackTrace();
				throw new CommandProcessingException("Error while retriveing existing image details",e);
			} catch (TransactionException e) {
				e.printStackTrace();
				throw new CommandProcessingException("Error while retriveing existing image details",e);
			}
			List imageList = new ArrayList();
			imageList = getImageTagProxyManager().getTagImageList(Long.toString(existingTagDetails.getId()),IImageTagConstants.STATUS_NO);
			if(imageList!=null){
			DefaultLogger.debug(this, "==============================137================Image Size : "+imageList.size());
			}
			result.put("tageedImageList", imageList);
			result.put("obImageTagAddList", imageList);
		}else{
			result.put("tageedImageList", new ArrayList());
			result.put("obImageTagAddList", new ArrayList());
		}

		//preparing ImageTagDetails object for view.
		if(IImageTagConstants.SECURITY_DOC.equals(imageTagDetails.getDocType())){
			try {
				ICollateral collateral = getCollateralProxy().getCollateral(imageTagDetails.getSecurityId(), false);
				imageTagDetails.setSecTypeLabel(collateral.getCollateralType().getTypeName());
				imageTagDetails.setSecSubtypeLabel(collateral.getCollateralSubType().getSubTypeName());
				imageTagDetails.setSecurityIdLabel(collateral.getCollateralID()+" - "+collateralCodeMap.get(collateral.getCollateralCode()));
			} catch (CollateralException e) {
				e.printStackTrace();
			}
			
		}else if(IImageTagConstants.FACILITY_DOC.equals(imageTagDetails.getDocType())){
			try {
				ILimit limit = getLimitProxy().getLimit(imageTagDetails.getFacilityId());
				DefaultLogger.debug(this,"Got Limit"+limit);
				imageTagDetails.setFacilityIdLabel(limit.getLimitRef()+" - "+limit.getFacilityName());
				//for Displaying the source system in label, As one facility name can exist in multiple source system
				result.put("failitySourceID", limit.getSourceId());
			} catch (LimitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		//else it will be CAM  no need to prepare for label
		try {
			if(!imageTagDetails.getDocType().equals("CAM_NOTE")){
				//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
				if(IImageTagConstants.EXCHANGE_OF_INFO.equals(imageTagDetails.getDocType())){
						IRecurrentCheckListDAO recurrentCheckListDAO = RecurrentDAOFactory.getRecurrentCheckListDAO();
						String recurrentDocDesc = recurrentCheckListDAO.getRecurrentDocDesc(Long.parseLong(imageTagDetails.getDocDesc()), "Annexure");
						
						imageTagDetails.setDocDescLabel(recurrentDocDesc);
					}
					//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
				else{
				ICheckListItem checkListItem = getCheckListProxyManager().getCheckListItemById(Long.parseLong(imageTagDetails.getDocDesc()));
			String label=checkListItem.getItemDesc();
			imageTagDetails.setDocDescLabel(label);
			}
			}
			else{
				//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
				if(IImageTagConstants.EXCHANGE_OF_INFO.equals(imageTagDetails.getDocType())){
						IRecurrentCheckListDAO recurrentCheckListDAO = RecurrentDAOFactory.getRecurrentCheckListDAO();
						String recurrentDocDesc = recurrentCheckListDAO.getRecurrentDocDesc(Long.parseLong(imageTagDetails.getDocDesc()), "Annexure");
						
						imageTagDetails.setDocDescLabel(recurrentDocDesc);
					}
					//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
				else{
				String label1=imageTagDetails.getDocDesc();
				label1=label1.substring(0,13);
			//	ICheckListDAO checkListDAO = (ICheckListDAO)BeanHouse.get("collateralDao");
				ILimitProfile camInfo= CheckListDAOFactory.getCheckListDAO().retriveCam(label1.trim());
				
				result.put("camInfo", camInfo);
				imageTagDetails.setDocDesc(label1);
			}
//			String label=checkListItem.getItemCode()+"("+checkListItem.getCheckListItemID()+")";
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (CheckListException e) {
			e.printStackTrace();
		}

		
		result.put("customerName", map.get("customerName"));
		result.put("custLimitProfileID", custLimitProfileID);
		result.put("ImageTagMapObj", imageTagDetails);
		result.put("imageTagTrxValue", trxValueIn);
		result.put("event", event);
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		DefaultLogger.debug(this, "Going out of doExecute()");
		return returnMap;
	}
	public HashMap getCollateralInfo() {
		HashMap map = new HashMap();
		ICollateralNewMasterJdbc collateralNewMasterJdbc = (ICollateralNewMasterJdbc) BeanHouse.get("collateralNewMasterJdbc");
		SearchResult result = collateralNewMasterJdbc.getAllCollateralNewMaster();
		ArrayList list = (ArrayList) result.getResultList();
		for (int ab = 0; ab < list.size(); ab++) {
			ICollateralNewMaster newMaster = (ICollateralNewMaster) list.get(ab);
			map.put(newMaster.getNewCollateralCode(), newMaster.getNewCollateralDescription());

		}
		return map;
	}

}
