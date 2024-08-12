/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/OBCollateralTrxValue.java,v 1.4 2003/07/11 10:10:39 lyng Exp $
 */
package com.integrosys.cms.app.image.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.ui.image.IImageUpload;
import com.integrosys.cms.ui.image.IImageUploadAdd;

/**
 * This class defines transaction data for use with CMS.
 * 
 * @author $Govind: Sahu $<br>
 * @version $Revision: 0.0 $
 * @since $Date: 2011/03/16 20:10:39 $ Tag: $Name: $
 */
public class OBImageUploadTrxValue extends OBCMSTrxValue implements IImageUploadTrxValue {

	private IImageUploadAdd imageUploadAdd;

	private IImageUploadAdd stagingImageUploadAdd;

	
	/**
	 * Default Constructor
	 */
	public OBImageUploadTrxValue() {
		super();
		super.setTransactionType("IMAGE_UPLOAD");
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICollateralTrxValue
	 */
	public OBImageUploadTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * @return the imageUploadAdd
	 */
	public IImageUploadAdd getImageUploadAdd() {
		return imageUploadAdd;
	}

	/**
	 * @param imageUploadAdd the imageUploadAdd to set
	 */
	public void setImageUploadAdd(IImageUploadAdd imageUploadAdd) {
		this.imageUploadAdd = imageUploadAdd;
	}

	/**
	 * @return the stagingImageUploadAdd
	 */
	public IImageUploadAdd getStagingImageUploadAdd() {
		return stagingImageUploadAdd;
	}

	/**
	 * @param stagingImageUploadAdd the stagingImageUploadAdd to set
	 */
	public void setStagingImageUploadAdd(IImageUploadAdd stagingImageUploadAdd) {
		this.stagingImageUploadAdd = stagingImageUploadAdd;
	}

	
}