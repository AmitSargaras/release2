package com.integrosys.cms.host.eai.security.bus;

import java.util.List;
import java.util.Map;

import com.integrosys.cms.host.eai.document.actualtrxhandler.CheckListItemsNonPreApprovalDocHandler;
import com.integrosys.cms.host.eai.security.bus.asset.AssetSecurity;

/**
 * Jdbc routine used by eai security package.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface ISecurityJdbc {
	/**
	 * to retrieve collateral margin given collateral country code and sub type
	 * code
	 * 
	 * @param countryCode collateral location country code
	 * @param collateralSubTypeCode collateral subtype code
	 * @return margin for the collateral sub type for certain country
	 */
	public double retrieveMargin(String countryCode, String collateralSubTypeCode);

	/**
	 * To search a list of asset vehicle security using the list of the cms
	 * collateral ids and property [parameter name, value] pair map
	 * 
	 * @param cmsCollateralIdList list of cms collateral ids, can be null if
	 *        just wan to match others parameter
	 * @param assetVehicleParameters key is the parameter name, value is the
	 *        value for the parameter, belong to the asset vehicle security
	 * @return a list of asset vehicle security matched the criteria passed in
	 * @see com.integrosys.cms.host.eai.security.bus.asset.AssetSecurity
	 */
	public List searchAssetVehicleCollaterals(List cmsCollateralIdList, Map assetVehicleParameters);

	/**
	 * To search a list of asset vessel security using the list of the cms
	 * collateral ids and property [parameter name, value] pair map
	 * 
	 * @param cmsCollateralIdList list of cms collateral ids, can be null if
	 *        just wan to match others parameter
	 * @param assetVesselParameters key is the parameter name, value is the
	 *        value for the parameter, belong to the asset vessel security
	 * @return a list of asset vessel security matched the criteria passed in
	 * @see com.integrosys.cms.host.eai.security.bus.asset.AssetSecurity
	 */
	public List searchAssetVesselCollaterals(List cmsCollateralIdList, Map assetVesselParameters);

	/**
	 * To search a list of asset plant and equipment security using the list of
	 * the cms collateral ids and property [parameter name, value] pair map
	 * 
	 * @param cmsCollateralIdList list of cms collateral ids, can be null if
	 *        just wan to match others parameter
	 * @param assetPlantEquipParameters key is the parameter name, value is the
	 *        value for the parameter, belong to the asset plant and equipment
	 *        security
	 * @return a list of asset plant and equipment security matched the criteria
	 *         passed in
	 * @see com.integrosys.cms.host.eai.security.bus.asset.AssetSecurity
	 */
	public List searchAssetPlantEquipCollaterals(List cmsCollateralIdList, Map assetPlantEquipParameters);

	/**
	 * To search a list of asset aircraft security using the list of the cms
	 * collateral ids and property [parameter name, value] pair map
	 * 
	 * @param cmsCollateralIdList list of cms collateral ids, can be null if
	 *        just wan to match others parameter
	 * @param assetAircraftParameters key is the parameter name, value is the
	 *        value for the parameter, belong to the asset aircraft security
	 * @return a list of asset aircraft security matched the criteria passed in
	 * @see com.integrosys.cms.host.eai.security.bus.asset.AssetSecurity
	 */
	public List searchAssetAircraftCollaterals(List cmsCollateralIdList, Map assetAircraftParameters);

	public Long getCmsSecurityIdByOldCmsSecurityId(long oldCmsSecurityId);

	public Long getInsuranceIdByOldInsuranceId(long cmsCollateralId, long oldCmsInsuranceSecId);

	public Long getPledgorIdByOldCMSPledgorId(long CMSPledgorId);

	/**
	 * To retrieve the basic info of asset security vehicle, good status and
	 * PBR/PBT indicator, to be used by the
	 * {@link CheckListItemsNonPreApprovalDocHandler} to determine whether to
	 * add the non-pre-approval documents into the checklist.
	 * @param cmsCollateralId CMS Collateral internal key
	 * @return asset vehicle object, consits of two fields mentioned above.
	 */
	public AssetSecurity retrieveAssetVehiclePbrPbtGoodsStatusDetail(long cmsCollateralId);

	/**
	 * To check whether Collateral is deleted in the system.
	 * @param cmsCollateralId CMS Collateral internal key
	 * @param losSecurityId LOS Collateral Id
	 * @param sourceId Source of the Collateral.
	 * @return whether collateral is deleted in the system.
	 */
	public boolean isCollateralDeleted(Long cmsCollateralId, String losSecurityId, String sourceId);

}
