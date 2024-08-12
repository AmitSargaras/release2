/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/common/constant/ICMSSecuritySubTypeConstant.java,v 1.3 2003/08/05 03:14:24 pooja Exp $
 */
package com.integrosys.cms.ui.common.constant;

/**
 * This interface contains commonly used constants in CMS.
 * 
 * @author $Author: pooja $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/05 03:14:24 $ Tag: $Name: $
 */
public class ICMSSecuritySubTypeConstant {
	public ICMSSecuritySubTypeConstant() {
	}

	public String getUrl(String securitySubType)

	{
		String url = new String();

		if (securitySubType.equals("GTEINDEM")) {
			url = "GteIndemCollateral.do?event=read";
		}
		if (securitySubType.equals("GTEGOVT")) {
			url = "GteGovtCollateral.do?event=read";
		}
		if (securitySubType.equals("GTECORP3RD")) {
			url = "GteCorp3rdCollateral.do?event=read";
		}
		if (securitySubType.equals("GTECORPREL")) {
			url = "GteCorpRelCollateral.do?event=read";
		}
		if (securitySubType.equals("GTESLCDIFF")) {
			url = "GteSLCDiffCollateral.do?event=read";
		}
		if (securitySubType.equals("GTESLCSAME")) {
			url = "GteSLCSameCollateral.do?event=read";
		}
		if (securitySubType.equals("GTEBANKDIFF")) {
			url = "GteBankDiffCollateral.do?event=read";
		}
		if (securitySubType.equals("GTEBANKSAME")) {
			url = "GteBankSameCollateral.do?event=read";
		}
		if (securitySubType.equals("INSKEYMAN")) {
			url = "InsKeymanCollateral.do?event=read";
		}
		if (securitySubType.equals("INSCRDT")) {
			url = "InsCrdtCollateral.do?event=read";
		}
		if (securitySubType.equals("INSCRDTDERIV")) {
			url = "InsCrdtDerivCollateral.do?event=read";
		}
		if (securitySubType.equals("INSSWAP")) {
			url = "InsSwapCollateral.do?event=read";
		}
		if (securitySubType.equals("COMMSOFT")) {
			url = ".do?event=read";
		}
		if (securitySubType.equals("COMMCHEM")) {
			url = ".do?event=read";
		}
		if (securitySubType.equals("COMMENERGY")) {
			url = ".do?event=read";
		}
		if (securitySubType.equals("COMMNONFERROTHER")) {
			url = ".do?event=read";
		}
		if (securitySubType.equals("COMMNONFERR")) {
			url = ".do?event=read";
		}
		if (securitySubType.equals("COMMFERR")) {
			url = ".do?event=read";
		}
		if (securitySubType.equals("MARKSECBOND")) {
			url = ".do?event=read";
		}
		if (securitySubType.equals("MARKSECBILL")) {
			url = "MarksecBillCollateral.do?event=read";
		}
		if (securitySubType.equals("MARKSECMAINFOREIGN")) {
			url = "MarksecMainForeignCollateral.do?event=read";
		}
		if (securitySubType.equals("MARKSECMAINLOCAL")) {
			url = "MarksecMainLocalCollateral.do?event=read";
		}
		if (securitySubType.equals("MARKSECBONDLOCAL")) {
			url = "MarksecBondLocalCollateral.do?event=read";
		}
		if (securitySubType.equals("MARKSECSCBSEC")) {
			url = "MarksecSCBSecCollateral.do?event=read";
		}
		if (securitySubType.equals("MARKSECCUSTSEC")) {
			url = "MarksecCustSecCollateral.do?event=read";
		}
		if (securitySubType.equals("MARKSECNONLISTEDLOCAL")) {
			url = "MarksecNonListedLocalCollateral.do?event=read";
		}
		if (securitySubType.equals("MARKSECOTHERLISTEDFOREIGN")) {
			url = "MarksecOtherListedForeignCollateral.do?event=read";
		}
		if (securitySubType.equals("MARKSECOTHERLISTEDLOCAL")) {
			url = "MarksecOtherListedLocalCollateral.do?event=read";
		}
		if (securitySubType.equals("MARKSECGOVTFOREIGNDIFF")) {
			url = "MarksecGovtForeignDiffCollateral.do?event=read";
		}
		if (securitySubType.equals("MARKSECGOVTFOREIGNSAME")) {
			url = "MarksecGovtForeignSameCollateral.do?event=read";
		}
		if (securitySubType.equals("PROPAGRI")) {
			url = "PropAgriCollateral.do?event=read";
		}
		if (securitySubType.equals("PROPRURAL")) {
			url = "PropRuralCollateral.do?event=read";
		}
		if (securitySubType.equals("PROPCOMMGENERAL")) {
			url = "PropCommGeneralCollateral.do?event=read";
		}
		if (securitySubType.equals("PROPCOMMSHOP")) {
			url = "PropCommShopCollateral.do?event=read";
		}
		if (securitySubType.equals("PROPINDUS")) {
			url = "PropIndusCollateral.do?event=read";
		}
		if (securitySubType.equals("PROPRESSTD")) {
			url = "PropResStdCollateral.do?event=read";
		}
		if (securitySubType.equals("PROPRESLUX")) {
			url = "PropResLuxCollateral.do?event=read";
		}
		if (securitySubType.equals("PROPSPHOTEL")) {
			url = "PropSpHotelCollateral.do?event=read";
		}
		if (securitySubType.equals("PROPSPOTHER")) {
			url = "PropSpOtherCollateral.do?event=read";
		}
		if (securitySubType.equals("PROPLANDURBAN")) {
			url = "PropLandUrbanCollateral.do?event=read";
		}
		if (securitySubType.equals("ASSETPDC")) {
			url = ".do?event=read";
		}
		if (securitySubType.equals("ASSETRECOPEN")) {
			url = ".do?event=read";
		}
		if (securitySubType.equals("ASSETRECSPECNONAGENT")) {
			url = ".do?event=read";
		}
		if (securitySubType.equals("GTEINDIV")) {
			url = "GteIndivForm.do?event=read";
		}
		if (securitySubType.equals("ASSETRECGENAGENT")) {
			url = ".do?event=read";
		}
		if (securitySubType.equals("ASSETRECSPECAGENT")) {
			url = ".do?event=read";
		}
		if (securitySubType.equals("ASSETSPECOTHER")) {
			url = ".do?event=read";
		}
		if (securitySubType.equals("ASSETSPECVEHICLES")) {
			url = ".do?event=read";
		}
		if (securitySubType.equals("ASSETSPECPLANT")) {
			url = ".do?event=read";
		}
		if (securitySubType.equals("AIRCRAFTSPEC")) {
			url = ".do?event=read";
		}
		if (securitySubType.equals("ASSETGENCHARGE")) {
			url = ".do?event=read";
		}
		if (securitySubType.equals("CASHDIFF")) {
			url = "CashDiffCollateral.do?event=read";
		}
		if (securitySubType.equals("CASHHKDUSD")) {
			url = "CashHKDUSDCollateral.do?event=read";
		}
		if (securitySubType.equals("CASHSAME")) {
			url = "CashSameCollateral.do?event=read";
		}
		if (securitySubType.equals("DOCLOU")) {
			url = "DocLoUCollateral.do?event=read";
		}
		if (securitySubType.equals("DOCGENCREDIT")) {
			url = "DocGenCreditCollateral.do?event=read";
		}
		if (securitySubType.equals("DOCDERVNET")) {
			url = "DocDervNetCollateral.do?event=read";
		}
		if (securitySubType.equals("DOCDERVISDA")) {
			url = "DocDervISDCollateral.do?event=read";
		}
		if (securitySubType.equals("ASSETSPECAIRCRAFT")) {
			url = ".do?event=read";
		}

		return url;
	}

}