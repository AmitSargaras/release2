/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/IFixedAssetOthers.java,v 1.2 2005/03/16 05:09:15 wltan Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

/**
 * This interface represents Fixed Asset/Others of the Asset of type General
 * Charge.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/03/16 05:09:15 $ Tag: $Name: $
 */
public interface IFixedAssetOthers extends IGeneralChargeSubType {

	/**
	 * To get cmsAssetGchrgFAOID
	 * @return long cmsAssetGchrgFAOID
	 */
	public long getAssetGCFixedAssetOthersID();

	/**
	 * To set assetGCFixedAssetOthersID
	 * @param assetGCFixedAssetOthersID of type long
	 */
	public void setAssetGCFixedAssetOthersID(long assetGCFixedAssetOthersID);

	/**
	 * To get fxasstOthrID
	 * @return String fxasstOthrID
	 */
	public String getFAOID();

	/**
	 * To set fxasstOthrID
	 * @param fxasstOthrID fxasstOthrID of type String
	 */
	public void setFAOID(String fxasstOthrID);

	/**
	 * To get description
	 * @return String description
	 */

	public String getDescription();

	/**
	 * To set description
	 * @param description description of type String
	 */
	public void setDescription(String description);
}
