package com.integrosys.cms.app.imageTag.proxy;

import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.IImageTagMap;
import com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.imageTag.ImageTagException;

public interface IImageTagProxyManager {

	public Long createImageTagDetails(IImageTagDetails imageTagMap)
			throws ImageTagException;
	public Long createImageTagMap(IImageTagMap imageTagMap)
	throws ImageTagException;
	public Long checkerCreateImageTagMap(IImageTagMap imageTagMap)
	throws ImageTagException;
	public Long checkerApproveUpdateImageTagMap(IImageTagMap imageTagMap)
	throws ImageTagException;
	public IImageTagTrxValue makerCloseRejectedImageTag(ITrxContext anITrxContext, IImageTagTrxValue anIImageTagTrxValue) throws ImageTagException,TrxParameterException,TransactionException;
	public List getCustImageList(String custId,String category) throws ImageTagException;
	public List getTagImageList(String tagId,String untaggedFilter) throws ImageTagException;
	public List getStagingTagImageList(String tagId,String untaggedFilter) throws ImageTagException;
	public String getFromPageByImageTagMapByTagId(String tagId) throws ImageTagException;
	public List getCollateralId(String key) throws ImageTagException;
	public IImageTagTrxValue checkerRejectImageTag(ITrxContext anITrxContext, IImageTagTrxValue anIImageTagTrxValue) throws ImageTagException,TrxParameterException,TransactionException;
	public IImageTagTrxValue makerCreateImageTagDetails(ITrxContext anITrxContext, IImageTagDetails anICCImageTagDetails)throws ImageTagException,TrxParameterException,TransactionException;
	public IImageTagTrxValue getImageTagByTrxID(String aTrxID) throws ImageTagException,TransactionException,CommandProcessingException;
	public IImageTagTrxValue checkerApproveImageTag(ITrxContext anITrxContext, IImageTagTrxValue anIImageTagTrxValue) throws ImageTagException,TrxParameterException,TransactionException;
	public IImageTagTrxValue makerUpdateImageTagDetails(ITrxContext anITrxContext, IImageTagTrxValue anIImageTagTrxValue,IImageTagDetails imageTagDetails) throws ImageTagException,TrxParameterException,TransactionException;
	public IImageTagTrxValue makerUpdateRejectedImageTagDetails(ITrxContext anITrxContext, IImageTagTrxValue anIImageTagTrxValue,IImageTagDetails imageTagDetails) throws ImageTagException,TrxParameterException,TransactionException;
	public IImageTagDetails getExistingImageTag(IImageTagDetails imageTagDetails) throws ImageTagException;
	public IImageTagTrxValue getImageTagTrxByID(String imageTagId) throws ImageTagException,TransactionException,CommandProcessingException;
	public List getCustImageListByCriteria(IImageTagDetails tagDetails) throws ImageTagException;
	
	// code start:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
	List<String> getTagId(String custId);
	List<String> getTaggedImageId(List<String> imageIdList,List<String> tagIdList);
	// code end:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
	
	Map<Long, String> getImageIdTaggedStatusMap(String checklistDocItemId);
	public List getImageIdDocDescMap(String checklistDocItemId,String checkListType);
	
}
