package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.SERVICE_COLLATERAL_OBJ;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_DETAILS;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_VIEW_STOCK_STATEMENT_IMAGES;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.SELECTED_INDEX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.imageTag.bus.IImageTagDao;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.ImageTagDaoImpl;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.imageTag.IImageTagConstants;

public class ViewStockStatementImageCommand extends AbstractCommand {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE},
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, ICMSCustomer.class.getName(), GLOBAL_SCOPE },
				{ EVENT, String.class.getName(), REQUEST_SCOPE },
				{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
				{ SESSION_VIEW_STOCK_STATEMENT_IMAGES, List.class.getName(), SERVICE_SCOPE },
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ EVENT, String.class.getName(), REQUEST_SCOPE },
				{ SELECTED_INDEX, String.class.getName(), REQUEST_SCOPE },
				{ SESSION_VIEW_STOCK_STATEMENT_IMAGES, List.class.getName(), SERVICE_SCOPE },
				{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
		});
	}
	
	public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String event = (String) inputMap.get(EVENT);
		ICollateralTrxValue collateralTrx = (ICollateralTrxValue) inputMap.get(SERVICE_COLLATERAL_OBJ);
		
		IGeneralChargeDetails sessionChargeDetails = (IGeneralChargeDetails) inputMap.get(SESSION_DUE_DATE_AND_STOCK_DETAILS);
		ICMSCustomer cust = (ICMSCustomer) inputMap.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		IImageTagDao tagDao =(IImageTagDao)BeanHouse.get("imageTagDao");
		Long checklistItemId = tagDao.getChecklistItemIdForViewStockStatement(cust.getCifId(), sessionChargeDetails.getDocCode());
		
		IImageTagDetails existingTagDetails=tagDao.getCustImageListForView("actualOBImageTagDetails", cust.getCifId(), IImageTagConstants.RECURRENTDOC_DOC,String.valueOf(checklistItemId));
		
		if(existingTagDetails!=null){
			
			List tagDetailList=tagDao.getTagImageList("actualOBImageTagMap",String.valueOf(existingTagDetails.getId()), "N");
			if (tagDetailList != null) {
				if (!tagDetailList.isEmpty()) {
					for (int i = 0; i < tagDetailList.size(); i++) {
						OBImageUploadAdd sObject = (OBImageUploadAdd) tagDetailList.get(i);

						ImageTagDaoImpl imageTagDaoImpl = new ImageTagDaoImpl();
						String categoryName = imageTagDaoImpl.getEntryNameFromCode(sObject.getCategory());
						sObject.setTypeOfDocument(categoryName);
					}
				}
			}
			resultMap.put(SESSION_VIEW_STOCK_STATEMENT_IMAGES, tagDetailList);
		}
		
		resultMap.put(EVENT, event);
		resultMap.put(SERVICE_COLLATERAL_OBJ, collateralTrx);
		
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
