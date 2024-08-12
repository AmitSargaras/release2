package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.sharesecurity.bus.SBShareSecurityManager;
import com.integrosys.cms.app.sharesecurity.bus.SBShareSecurityManagerHome;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Nov 10, 2006 Time: 10:45:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class CollateralUiUtil {
	public static void setTrxLocation(OBTrxContext ctx, ICollateral obCollateral) throws CollateralException {
		List aBCALocationByColList = CollateralProxyFactory.getProxy().getDisTaskBcaLocationList(
				obCollateral.getCollateralID());
		if (aBCALocationByColList.size() > 0) {
			OBBookingLocation aBkgLoctn = (OBBookingLocation) aBCALocationByColList.get(0);
			ctx.setTrxCountryOrigin(aBkgLoctn.getCountryCode());
			ctx.setTrxOrganisationOrigin(aBkgLoctn.getOrganisationCode());
		}
		else {
			ctx.setTrxCountryOrigin(obCollateral.getCollateralLocation());
			ctx.setTrxOrganisationOrigin(obCollateral.getSecurityOrganization());
		}
	}

	public static boolean isCollateralCanRead(ITeam team, List locationList) {
		boolean isColCountryBelongTeam = false;
		boolean isColOrgBelongTeam = false;
		if (team == null) {
			return false;
		}
		String[] teamCountry = team.getCountryCodes();
		String[] teamOrg = team.getOrganisationCodes();

		if (locationList != null) {
			int size = teamCountry == null ? 0 : teamCountry.length;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < locationList.size(); j++) {
					OBBookingLocation o1 = (OBBookingLocation) locationList.get(j);
					if ((o1 == null) || (o1.getCountryCode() == null)) {
						continue;
					}
					if (o1.getCountryCode().equals(teamCountry[i])) {
						isColCountryBelongTeam = true;
						break;
					}
				}

			}
		}
		else {
			isColCountryBelongTeam = true;
		}
		if (!isColCountryBelongTeam) {
			return false;
		}

		if (locationList != null) {
			int size = teamOrg == null ? 0 : teamOrg.length;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < locationList.size(); j++) {
					OBBookingLocation o1 = (OBBookingLocation) locationList.get(j);
					if (o1 == null) {
						continue;
					}
					if (o1.getOrganisationCode() == null) {
						return true;
					}
					if (o1.getOrganisationCode().equals(teamOrg[i])) {
						isColOrgBelongTeam = true;
						break;
					}
				}
			}
		}
		else {
			isColOrgBelongTeam = true;
		}

		if (!isColOrgBelongTeam) {
			return false;
		}

		return true;
	}

	public static boolean isColCanUpdateBySSC(ITeam team, List onlyBcaLocList, String colLocation) {

		String[] teamCountry = team.getCountryCodes();

		if ("MY".equals(colLocation) || "IN".equals(colLocation)) {
			for (int i = 0; i < teamCountry.length; i++) {
				if (colLocation.equals(teamCountry[i])) {
					return true;
				}

				for (int j = 0; j < onlyBcaLocList.size(); j++) {
					OBBookingLocation o1 = (OBBookingLocation) onlyBcaLocList.get(j);
					if (o1.getCountryCode().equals(teamCountry[i])) {
						return true;
					}
				}
			}
		}
		else {

			for (int j = 0; j < onlyBcaLocList.size(); j++) {
				OBBookingLocation o1 = (OBBookingLocation) onlyBcaLocList.get(j);
				if ("MY".equals(o1.getCountryCode()) || "IN".equals(o1.getCountryCode())) {
					for (int i = 0; i < teamCountry.length; i++) {
						if (o1.getCountryCode().equals(teamCountry[i])) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public static boolean isCollateralCanUseTab(ArrayList bcalocationList, ArrayList locList) {
		boolean canUpdate = false;
		for (int i = 0; i < locList.size(); i++) {
			for (int j = 0; j < bcalocationList.size(); j++) {
				OBBookingLocation ob = (OBBookingLocation) bcalocationList.get(j);
				if (((String) locList.get(i)).equals(ob.getCountryCode())) {
					canUpdate = true;
					break;
				}
			}
		}
		return canUpdate;
	}

	public static ICollateral setCollateralSourceIds(ICollateral col) {
		try {
			SBShareSecurityManagerHome home = (SBShareSecurityManagerHome) (BeanController.getEJBHome(
					ICMSJNDIConstant.SB_SHARE_SECURITY_MGR_JNDI, SBShareSecurityManagerHome.class.getName()));
			SBShareSecurityManager manager = home.create();
			List shareColNames = manager.getSharedSecNameForCollateral(new Long(col.getCollateralID()));
			col.setSourceSecIdAliases(shareColNames);
		}
		catch (Exception ex) {
		}
		return col;
	}

	public static ArrayList getLVBeanList(Collection labelColl, Collection valueColl) {
		ArrayList beanList = new ArrayList();
		for (Iterator iLabel = labelColl.iterator(), iValue = valueColl.iterator(); iLabel.hasNext();) {
			beanList.add(getLVBean((String) iLabel.next(), (String) iValue.next()));
		}
		return beanList;
	}

	public static CMSLabelValueBean getLVBean(String label, String value) {
		label = label.replace('&', ' ');
		label = label.replace('\'', ' ');
		label = label.replace('\"', ' ');
		return new CMSLabelValueBean(label, value);
	}

	public static String getActionNameBySubType(String subTypeCode) {
		if ("CS200".equals(subTypeCode)) {
			return "CashCashCollateral";
		}
		if ("CS201".equals(subTypeCode)) {
			return "CashHKDUSDCollateral";
		}
		if ("CS202".equals(subTypeCode)) {
			return "CashFdCollateral";
		}
		if ("CS203".equals(subTypeCode)) {
			return "CashRepoCollateral";
		}
		if ("CS208".equals(subTypeCode)) {
			return "CashSameCollateral";
		}

		if ("DC300".equals(subTypeCode)) {
			return "DocDervISDCollateral";
		}
		if ("DC301".equals(subTypeCode)) {
			return "DocDervNetCollateral";
		}
		if ("DC302".equals(subTypeCode)) {
			return "DocGenCreditCollateral";
		}
		if ("DC303".equals(subTypeCode)) {
			return "DocLoUCollateral";
		}
		if ("DC304".equals(subTypeCode)) {
			return "DocComfortCollateral";
		}
		if ("DC305".equals(subTypeCode)) {
			return "DocPledgeCollateral";
		}
		if ("DC306".equals(subTypeCode)) {
			return "DocDeedSubCollateral";
		}
		if ("DC307".equals(subTypeCode)) {
			return "DocLoICollateral";
		}
		if ("DC308".equals(subTypeCode)) {
			return "DocDoACollateral";
		}
		if ("DC309".equals(subTypeCode)) {
			return "DocAgreementCollateral";
		}
		if ("GT400".equals(subTypeCode)) {
			return "GteBankSameCollateral";
		}
		if ("GT401".equals(subTypeCode)) {
			return "GteBankDiffCollateral";
		}
		if ("GT402".equals(subTypeCode)) {
			return "GteSLCSameCollateral";
		}
		if ("GT403".equals(subTypeCode)) {
			return "GteSLCDiffCollateral";
		}
		if ("GT404".equals(subTypeCode)) {
			return "GteCorpRelCollateral";
		}
		if ("GT405".equals(subTypeCode)) {
			return "GteCorp3rdCollateral";
		}
		if ("GT406".equals(subTypeCode)) {
			return "GteGovtCollateral";
		}
		if ("GT407".equals(subTypeCode)) {
			return "GteIndemCollateral";
		}
		if ("GT408".equals(subTypeCode)) {
			return "GteIndivCollateral";
		}
		if ("GT409".equals(subTypeCode)) {
			return "GteGovtLinkCollateral";
		}
		if ("GT410".equals(subTypeCode)) {
			return "GteInwardLCCollateral";
		}

		if ("PT700".equals(subTypeCode)) {
			return "PropAgriCollateral";
		}
		if ("PT701".equals(subTypeCode)) {
			return "PropCommGeneralCollateral";
		}
		if ("PT702".equals(subTypeCode)) {
			return "PropCommShopCollateral";
		}
		if ("PT703".equals(subTypeCode)) {
			return "PropIndusCollateral";
		}
		if ("PT704".equals(subTypeCode)) {
			return "PropResStdCollateral";
		}
		if ("PT705".equals(subTypeCode)) {
			return "PropResLuxCollateral";
		}
		if ("PT706".equals(subTypeCode)) {
			return "PropSpHotelCollateral";
		}
		if ("PT707".equals(subTypeCode)) {
			return "PropLandUrbanCollateral";
		}
		if ("PT708".equals(subTypeCode)) {
			return "PropRuralCollateral";
		}
		if ("PT709".equals(subTypeCode)) {
			return "PropSpOtherCollateral";
		}
		if ("PT710".equals(subTypeCode)) {
			return "PropCommServiceAptCollateral";
		}
		if ("PT711".equals(subTypeCode)) {
			return "PropIndusSpecCollateral";
		}

		if ("IN500".equals(subTypeCode)) {
			return "InsCrdtCollateral";
		}
		if ("IN501".equals(subTypeCode)) {
			return "InsKeymanCollateral";
		}
		if ("IN502".equals(subTypeCode)) {
			return "InsCrdtDerivCollateral";
		}
		if ("IN503".equals(subTypeCode)) {
			return "InsSwapCollateral";
		}

		if ("MS600".equals(subTypeCode)) {
			return "MarksecMainLocalCollateral";
		}
		if ("MS601".equals(subTypeCode)) {
			return "MarksecMainForeignCollateral";
		}
		if ("MS602".equals(subTypeCode)) {
			return "MarksecBillCollateral";
		}
		if ("MS603".equals(subTypeCode)) {
			return "MarksecGovtForeignSameCollateral";
		}
		if ("MS604".equals(subTypeCode)) {
			return "MarksecGovtForeignDiffCollateral";
		}
		if ("MS605".equals(subTypeCode)) {
			return "MarksecOtherListedLocalCollateral";
		}
		if ("MS606".equals(subTypeCode)) {
			return "MarksecOtherListedForeignCollateral";
		}
		if ("MS607".equals(subTypeCode)) {
			return "MarksecNonListedLocalCollateral";
		}
		if ("MS608".equals(subTypeCode)) {
			return "MarksecCustSecCollateral";
		}
		if ("MS609".equals(subTypeCode)) {
			return "MarksecSCBSecCollateral";
		}
		if ("MS610".equals(subTypeCode)) {
			return "MarksecBondLocalCollateral";
		}
		if ("MS611".equals(subTypeCode)) {
			return "MarksecBondForeignCollateral";
		}

		if ("CF800".equals(subTypeCode)) {
			return "CommodityMain";
		}
		if ("CF801".equals(subTypeCode)) {
			return "CommodityMain";
		}
		if ("CF802".equals(subTypeCode)) {
			return "CommodityMain";
		}
		if ("CF803".equals(subTypeCode)) {
			return "CommodityMain";
		}
		if ("CF804".equals(subTypeCode)) {
			return "CommodityMain";
		}
		if ("CF805".equals(subTypeCode)) {
			return "CommodityMain";
		}

		if ("AB100".equals(subTypeCode)) {
			return "AssetGenChargeCollateral";
		}
		if ("AB101".equals(subTypeCode)) {
			return "AssetSpecPlantCollateral";
		}
		if ("AB102".equals(subTypeCode)) {
			return "AssetSpecVehiclesCollateral";
		}
		if ("AB103".equals(subTypeCode)) {
			return "AssetSpecOtherCollateral";
		}
		if ("AB104".equals(subTypeCode)) {
			return "AssetRecSpecAgentCollateral";
		}
		if ("AB105".equals(subTypeCode)) {
			return "AssetRecGenAgentCollateral";
		}
		if ("AB106".equals(subTypeCode)) {
			return "AssetRecSpecNonAgentCollateral";
		}
		if ("AB107".equals(subTypeCode)) {
			return "AssetRecOpenCollateral";
		}
		if ("AB109".equals(subTypeCode)) {
			return "AssetAircraftCollateral";
		}
		if ("AB108".equals(subTypeCode)) {
			return "AssetPostDatedChqsCollateral";
		}
		if ("AB110".equals(subTypeCode)) {
			return "AssetSpecGoldCollateral";
		}
		if ("AB111".equals(subTypeCode)) {
			return "AssetVesselCollateral";
		}

		if ("OT900".equals(subTypeCode)) {
			return "OthersaCollateral";
		}

		if ("MANUAL".equals(subTypeCode)) {
			return "MISecurity";
		}

		if ("CL001".equals(subTypeCode)) {
			return "NoCollateral";
		}
		"PropSpOtherCollateral".substring(0, "PropSpOtherCollateral".indexOf("Collateral"));
		return null;
	}
}
