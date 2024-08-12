package com.integrosys.cms.app.image.trx;

import java.util.HashMap;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.ui.image.IImageUpload;
import com.integrosys.cms.ui.image.IImageUploadAdd;

/**
 * This interface represents a ImageUpload trx value.
 * 
 * @author $Govind: Sahu $
 * @version $Revision: 0.0 $
 * @since $Date: 2011/03/15 03:26:30 $ Tag: $Name: $
 */
public interface IImageUploadTrxValue extends ICMSTrxValue {
	/**
	 * Get actual collateral contained in this transaction.
	 * 
	 * @return object of collateral type
	 */
	public IImageUploadAdd getImageUploadAdd();

	/**
	 * Set actual collateral to this transaction.
	 * 
	 * @param collateral of type ICollateral
	 */
	public void setImageUploadAdd(IImageUploadAdd imageUploadAdd);

	/**
	 * Get staging collateral contained in this transaction.
	 * 
	 * @return staging collateral object
	 */
	public IImageUploadAdd getStagingImageUploadAdd();

	/**
	 * Set staging collateral to this transaction.
	 * 
	 * @param stagingCollateral of type ICollateral
	 */
	public void setStagingImageUploadAdd(IImageUploadAdd stagingImageUpload);
}

