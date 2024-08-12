package com.integrosys.cms.ui.limit.facility.main;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.limit.bus.IFacilityBnmCodes;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.SBCustomerManager;
import com.integrosys.cms.app.customer.bus.SBCustomerManagerHome;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;

public abstract class BNMCodesObjectValidator {
    public static final String THIS_CLASS = "com.integrosys.cms.ui.limit.facility.main.BNMCodesObjectValidator";

    public static ActionErrors validateObject(IFacilityMaster facilityMaster, long cmsSubProfileId) {

        String bumiPutraFlag = retrieveBumiPutraFlag(cmsSubProfileId);
        String legalConstitution = retrieveLegalConstitution(cmsSubProfileId);
        DefaultLogger.debug(THIS_CLASS, "cmsSubProfileId ^^^^^^^^^^^^ " + cmsSubProfileId);
        DefaultLogger.debug(THIS_CLASS, "bumiPutraFlag ^^^^^^^^^^^^ " + bumiPutraFlag);
        DefaultLogger.debug(THIS_CLASS, "legalConstitution ^^^^^^^^^^^^ " + legalConstitution);

        IFacilityBnmCodes facilityBNMCodes = facilityMaster.getFacilityBnmCodes();
		ActionErrors bnmCodesErrors = new ActionErrors();
		// BNMCodes Object
		if (StringUtils.isBlank(facilityBNMCodes.getIndustryCodeEntryCode())) {
			bnmCodesErrors.add("industryCodeEntryCode", new ActionMessage("error.mandatory"));
		}
		if (StringUtils.isBlank(facilityBNMCodes.getSectorCodeEntryCode())) {
			bnmCodesErrors.add("sectorCodeEntryCode", new ActionMessage("error.mandatory"));
		}
		if (StringUtils.isBlank(facilityBNMCodes.getStateCodeEntryCode())) {
			bnmCodesErrors.add("stateCodeEntryCode", new ActionMessage("error.mandatory"));
		}
		if (StringUtils.isBlank(facilityBNMCodes.getBumiOrNrccCodeEntryCode())) {
			bnmCodesErrors.add("bumiOrNrccCodeEntryCode", new ActionMessage("error.mandatory"));
		}
		if (StringUtils.isBlank(facilityBNMCodes.getSmallScaleCodeCodeEntryCode())) {
			bnmCodesErrors.add("smallScaleCodeCodeEntryCode", new ActionMessage("error.mandatory"));
		}
		/*
		 * if
		 * (StringUtils.isBlank(facilityBNMCodes.getPrescribedRateCodeEntryCode
		 * ())) { bnmCodesErrors.add("prescribedRateCodeEntryCode", new
		 * ActionMessage("error.mandatory")); }
		 */
		if (StringUtils.isBlank(facilityBNMCodes.getRelationshipCodeEntryCode())) {
			bnmCodesErrors.add("relationshipCodeEntryCode", new ActionMessage("error.mandatory"));
		}
		/*
		 * if
		 * (StringUtils.isBlank(facilityBNMCodes.getGoodsFinancedCodeOneEntryCode
		 * ())) { bnmCodesErrors.add("goodsFinancedCodeOneEntryCode", new
		 * ActionMessage("error.mandatory")); }
		 */
		/*
		 * if
		 * (StringUtils.isBlank(facilityBNMCodes.getGoodsFinancedCodeTwoEntryCode
		 * ())) { bnmCodesErrors.add("goodsFinancedCodeTwoEntryCode", new
		 * ActionMessage("error.mandatory")); }
		 */
		Character exemptedCodeIndicator = facilityBNMCodes.getExemptedCodeIndicator();
		if (ICMSConstant.TRUE_VALUE.equals(String.valueOf(exemptedCodeIndicator))) {
			if (StringUtils.isBlank(facilityBNMCodes.getExemptedCodeEntryCode())) {
				bnmCodesErrors.add("exemptedCodeEntryCode", new ActionMessage("error.mandatory"));
			}
		}
		if (StringUtils.isNotBlank(facilityBNMCodes.getExemptedCodeEntryCode())
				&& (!ICMSConstant.TRUE_VALUE.equals(String.valueOf(exemptedCodeIndicator)))) {
			bnmCodesErrors.add("exemptedCodeIndicator", new ActionMessage(
					"error.exempt.ind.should.be.yes.when.code.provided"));
		}

		// BNM purpose code are not allow to apply 0410/0420/0430 when BNM
		// sector code is
		// ethier
		// 60/61/62/63/64/65/66/67/68/69/75/57/59/41/42/43/44/46/47/48/49/51/52/53/54
		String[] bnmPurpose1 = { "0410", "0420", "0430" };
		String[] bnmSector1 = { "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "75", "57", "59", "41",
				"42", "43", "44", "46", "47", "48", "49", "51", "52", "53", "54" };
		if (ArrayUtils.contains(bnmSector1, facilityBNMCodes.getSectorCodeEntryCode())
				&& ArrayUtils.contains(bnmPurpose1, facilityBNMCodes.getPurposeCodeEntryCode())) {
			bnmCodesErrors.add("purposeCodeEntryCode", new ActionMessage(
					"error.purpose.code.not.allowed.when.sector.is", "0410/0420/0430",
					"60/61/62/63/64/65/66/67/68/69/75/57/59/41/42/43/44/46/47/48/49/51/52/53/54"));
		}

		String[] bnmIndustry2 = { "9700" };
		String[] bnmSector2 = { "76", "77", "78", "95", "96" };
		// BNM sector code are must be either 76/77/78/95/96 when BNM Industry
		// Code is 9700
		if (!ArrayUtils.contains(bnmSector2, facilityBNMCodes.getSectorCodeEntryCode())
				&& ArrayUtils.contains(bnmIndustry2, facilityBNMCodes.getIndustryCodeEntryCode())) {
			bnmCodesErrors.add("sectorCodeEntryCode", new ActionMessage(
					"error.sector.code.must.be.values.when.industry.is", "76/77/78/95/96", "9700"));
		}

		// 1. BNM Industry code must be 9700 when BNM sector code is either
		// 76/77/78/95/96
		if (ArrayUtils.contains(bnmSector2, facilityBNMCodes.getSectorCodeEntryCode())
				&& !ArrayUtils.contains(bnmIndustry2, facilityBNMCodes.getIndustryCodeEntryCode())) {
			bnmCodesErrors.add("industryCodeEntryCode", new ActionMessage(
					"error.industry.code.must.be.values.when.sector.is", "9700", "76/77/78/95/96"));
		}

		// 2. BNM Industry code must be 8110/8120/8130 when BNM sector code is
		// either 13/17/31/33/34/35/04/05/06/37/38/39/40/45/98
		String[] bnmSector4 = { "13", "17", "31", "33", "34", "35", "04", "05", "06", "37", "38", "39", "40", "45",
				"98" };
		String[] bnmIndustry4 = { "8110", "8120", "8130" };
		if (ArrayUtils.contains(bnmSector4, facilityBNMCodes.getSectorCodeEntryCode())
				&& !ArrayUtils.contains(bnmIndustry4, facilityBNMCodes.getIndustryCodeEntryCode())) {
			bnmCodesErrors.add("industryCodeEntryCode", new ActionMessage(
					"error.industry.code.must.be.values.when.sector.is", "8110/8120/8130",
					"13/17/31/33/34/35/04/05/06/37/38/39/40/45/98"));
		}

		// 3. BNM Industry code must not 8110/8120/8130/9700 when BNM sector code
		// is either
		// 41/42/43/44/46/47/48/49/51/52/53/54/57/59/61/62/63/64/75/79/86
		String[] bnmSector5 = { "41", "42", "43", "44", "46", "47", "48", "49", "51", "52", "53", "54", "57", "59",
				"61", "62", "63", "64", "75", "79", "86" };
		String[] bnmIndustry5 = { "8110", "8120", "8130", "9700" };
		if (ArrayUtils.contains(bnmSector5, facilityBNMCodes.getSectorCodeEntryCode())
				&& ArrayUtils.contains(bnmIndustry5, facilityBNMCodes.getIndustryCodeEntryCode())) {
			bnmCodesErrors.add("industryCodeEntryCode", new ActionMessage(
					"error.industry.code.must.not.be.values.when.sector.is", "8110/8120/8130/9700",
					"41/42/43/44/46/47/48/49/51/52/53/54/57/59/61/62/63/64/75/79/86"));
		}

		// 4. BNM Industry code must be greater than 8999 when BNM sector code
		// is either 71/72/73/74/90/91/92/99
		int industryCode = 0;
		String[] bnmSector6 = { "71", "72", "73", "74", "90", "91", "92", "99" };
		if (StringUtils.isNotBlank(facilityBNMCodes.getIndustryCodeEntryCode())) {
			try {
				industryCode = Integer.parseInt(facilityBNMCodes.getIndustryCodeEntryCode());
			}
			catch (Exception e) {
			}
		}
		if (ArrayUtils.contains(bnmSector6, facilityBNMCodes.getSectorCodeEntryCode()) && !(industryCode > 8999)) {
			bnmCodesErrors.add("industryCodeEntryCode", new ActionMessage(
					"error.industry.code.must.be.greater.than.when.sector.is", "8999", "71/72/73/74/90/91/92/99"));
		}

		// BNM Industry code must be 8110/8120 when BNM sector code is either
		// 02/03/11/12
		String[] bnmSector7 = { "02", "03", "11", "12" };
		String[] bnmIndustry7 = { "8110", "8120" };
		if (ArrayUtils.contains(bnmSector7, facilityBNMCodes.getSectorCodeEntryCode())
				&& !ArrayUtils.contains(bnmIndustry7, facilityBNMCodes.getIndustryCodeEntryCode())) {
			bnmCodesErrors.add("industryCodeEntryCode", new ActionMessage(
					"error.industry.code.must.be.values.when.sector.is", "8110/8120", "02/03/11/12"));
		}

		// 1. BNM purpose code cannot be 0440 when BNM sector code either
		// 76/78/95/96
		// 2. BNM purpose code cannot be 0460 when BNM sector code either
		// 76/78/95/96
		// 4. BNM purpose code cannot be 0990 when BNM sector code either
		// 76/78/95/96
		String[] bnmPurpose8 = { "0440", "0460", "0990" };
		String[] bnmSector8 = { "76", "77", "78", "95", "96" };
		if (ArrayUtils.contains(bnmSector8, facilityBNMCodes.getSectorCodeEntryCode())
				&& ArrayUtils.contains(bnmPurpose8, facilityBNMCodes.getPurposeCodeEntryCode())) {
			bnmCodesErrors.add("purposeCodeEntryCode", new ActionMessage(
					"error.purpose.code.not.allowed.when.sector.is", "0440/0460/0990", "76/77/78/95/96"));
		}

		// BNM purpose code must be either
		// 0110/0120/0131/0132/0139/0311/0312/0313/0314/0315/0316/0321/0322/0323/0324/0329/0440
		// or BNM industry code must be either 5001/5002/5003/5004/5005/5006/5008/5999/5020/5030/5040/5050/8310
		// when BNM exempted flag is Y
		String[] bnmPurpose9 = { "0110", "0120", "0131", "0132", "0139", "0311", "0312", "0313", "0314", "0315",
				"0316", "0321", "0322", "0323", "0324", "0329", "0440"};
		String[] bnmIndustry9 = {"5001", "5002", "5003", "5004", "5005", "5006",
				"5008", "5999", "5020", "5030", "5040", "5050", "8310" };
		if (ICMSConstant.TRUE_VALUE.equals(String.valueOf(exemptedCodeIndicator))
				&& (!ArrayUtils.contains(bnmPurpose9, facilityBNMCodes.getPurposeCodeEntryCode()) ||
						!ArrayUtils.contains(bnmIndustry9, facilityBNMCodes.getIndustryCodeEntryCode()))) {
			bnmCodesErrors
					.add(
							"purposeCodeEntryCode",
							new ActionMessage(
									"error.purpose.code.or.industry.code.must.be.values",
									"0110/0120/0131/0132/0139/0311/0312/0313/0314/0315/0316/0321/0322/0323/0324/0329/0440",
									"5001/5002/5003/5004/5005/5006/5008/5999/5020/5030/5040/5050/8310"));
			bnmCodesErrors
			.add(
					"industryCodeEntryCode",
					new ActionMessage(
							"error.purpose.code.or.industry.code.must.be.values",
							"0110/0120/0131/0132/0139/0311/0312/0313/0314/0315/0316/0321/0322/0323/0324/0329/0440",
							"5001/5002/5003/5004/5005/5006/5008/5999/5020/5030/5040/5050/8310"));

		}

		// BNM Exempted code must be start with "S" whenBNM exepted flag is set
		// to Y and BNM purpose code is within 0100 to 0139
		String[] bnmPurpose10 = { "0100", "0101", "0102", "0103", "0104", "0105", "0106", "0107", "0108", "0109",
				"0110", "0111", "0112", "0113", "0114", "0115", "0116", "0117", "0118", "0119", "0120", "0121", "0122",
				"0123", "0124", "0125", "0126", "0127", "0128", "0129", "0130", "0131", "0132", "0133", "0134", "0135",
				"0136", "0137", "0138", "0139" };
		if (ICMSConstant.TRUE_VALUE.equals(String.valueOf(exemptedCodeIndicator))
				&& ArrayUtils.contains(bnmPurpose10, facilityBNMCodes.getPurposeCodeEntryCode())
				&& (StringUtils.isNotBlank(facilityBNMCodes.getExemptedCodeEntryCode()) && !(facilityBNMCodes
						.getExemptedCodeEntryCode().toUpperCase().startsWith("S")))) {
			bnmCodesErrors.add("exemptedCodeEntryCode", new ActionMessage("error.exempted.code.must.start.with.S",
					"0100", "0139"));
		}

        String[] bnmSector11 = { "61", "41", "42", "43", "77" };
        String[] bnmSector12 = { "62", "44", "46", "47", "78" };
        if (StringUtils.equals(bumiPutraFlag, ICMSConstant.FALSE_VALUE)) {
            // BNM sector code cannot be 61/41/42/43/77 when Residence/Bus Oper Cntry Code (bumiputra flag) is not "Y"
            if (ArrayUtils.contains(bnmSector11, facilityBNMCodes.getSectorCodeEntryCode())) {
                bnmCodesErrors.add("sectorCodeEntryCode", new ActionMessage(
                        "error.sector.code.not.allowed.when.customer.type.is", "61/41/42/43/77", "not Bumi Individual"));
            }
            // Bumi/Indi code cannot be "1" when Residence/Bus Oper Cntry Code (bumiputra flag) is not "Y"
            if (StringUtils.equals(facilityBNMCodes.getBumiOrNrccCodeEntryCode(), "1")) {
                bnmCodesErrors.add("bumiOrNrccCodeEntryCode", new ActionMessage(
                        "error.bumi.nrcc.code.not.allowed.when.customer.type.is", "BUMI", "not Bumi Individual"));
            }
        }
        else if (StringUtils.equals(bumiPutraFlag, ICMSConstant.TRUE_VALUE)) {
            // BNM sector code cannot be 62/44/46/47/78 when Residence/Bus Oper Cntry Code (bumiputra flag) is "Y"
            if (ArrayUtils.contains(bnmSector12, facilityBNMCodes.getSectorCodeEntryCode())) {
                bnmCodesErrors.add("sectorCodeEntryCode", new ActionMessage(
                        "error.sector.code.not.allowed.when.customer.type.is", "62/44/46/47/78", "Bumi Individual"));
            }
            // Bumi/Indi code cannot be "2" when Residence/Bus Oper Cntry Code (bumiputra flag) is "Y"
            if (StringUtils.equals(facilityBNMCodes.getBumiOrNrccCodeEntryCode(), "2")) {
                bnmCodesErrors.add("bumiOrNrccCodeEntryCode", new ActionMessage(
                        "error.bumi.nrcc.code.not.allowed.when.customer.type.is", "NON BUMI", "Bumi Individual"));
            }            
        }

        String[] bnmSector13 = { "76", "77", "78", "95", "96" };
        String[] facilityCodeList = { "773", "232", "771" };
        // BNM sector code cannot be 76/77/78/95/96 when customer class (legal constitution) = 'I' and facility code not equal 773/232/771
        if (StringUtils.equals(legalConstitution, "I")
                && !ArrayUtils.contains(facilityCodeList, facilityMaster.getLimit().getFacilityCode()) ) {
            if (!ArrayUtils.contains(bnmSector13, facilityBNMCodes.getSectorCodeEntryCode())) {
                bnmCodesErrors.add("sectorCodeEntryCode", new ActionMessage(
                        "error.sector.code.must.be.values.when.legal.constitution.faciltiy.code.is", "76/77/78/95/96", "Individual", "not 773/232/771"));
            }
        }

        // Customer class (legal constitution) must be 'I' when BNM sector code = 76/77/78/95/96
        if (ArrayUtils.contains(bnmSector13, facilityBNMCodes.getSectorCodeEntryCode())) {
            if (!StringUtils.equals(legalConstitution, "I")) {
                bnmCodesErrors.add("sectorCodeEntryCode", new ActionMessage(
                        "error.legal.constitution.must.be.values.when.sector.code.is", "Individual", "76/77/78/95/96"));
            }
        }

        /*
		 * if (facilityBNMCodes.getHostTierSequenceNumber() == null) {
		 * bnmCodesErrors.add("hostTierSequenceNumber", new
		 * ActionMessage("error.mandatory")); }
		 */
		if (StringUtils.isBlank(facilityBNMCodes.getPurposeCodeEntryCode())) {
			bnmCodesErrors.add("purposeCodeEntryCode", new ActionMessage("error.mandatory"));
		}
		else if (ICMSConstant.BNM_PURPOSE_CODE_NA.equals(facilityBNMCodes.getPurposeCodeEntryCode())) {
			bnmCodesErrors.add("purposeCodeEntryCode", new ActionMessage("error.bnm.purpose.0000.not.allowed"));
		}
		if (facilityBNMCodes.getExemptedCodeIndicator() == null) {
			bnmCodesErrors.add("exemptedCodeIndicator", new ActionMessage("error.mandatory"));
		}
		DefaultLogger.debug(" FacilityBNMCodes Total Errors", "--------->" + bnmCodesErrors.size());
		return bnmCodesErrors.size() == 0 ? null : bnmCodesErrors;
	}

    /**
     * define bumiPutraFlag based on customer type
     * if customer type = 77, set bumiPutraFlag = Y
     * else set bumiPutraFlag = N
     * @param cmsSubProfileId
     * @return
     */
    private static String retrieveBumiPutraFlag(long cmsSubProfileId) {
        String bumiPutraFlag = ICMSConstant.FALSE_VALUE;
        try {
            ICMSCustomer customer = getCustomerManager().getCustomer(cmsSubProfileId);
            String customerType = customer.getCMSLegalEntity().getCustomerType();

            DefaultLogger.debug(THIS_CLASS, "customerType ^^^^^^^^^^^^ " + customerType);
            if (StringUtils.isNotBlank(customerType) && "77".equals(customerType)) {
                bumiPutraFlag = ICMSConstant.TRUE_VALUE;
            }
        }
		catch (Exception ex) {
            DefaultLogger.debug(THIS_CLASS, "Caught exception, unable to retrieve customer info...");
            DefaultLogger.debug(THIS_CLASS, "Default BumiPutraFlag as N...");
        }

        return bumiPutraFlag;
    }

    private static String retrieveLegalConstitution(long cmsSubProfileId) {
        String legalConstitution = "";
        try {
            ICMSCustomer customer = getCustomerManager().getCustomer(cmsSubProfileId);
            legalConstitution = customer.getCMSLegalEntity().getLegalConstitution();
//            DefaultLogger.debug(THIS_CLASS, "legalConstitution ^^^^^^^^^^^^ " + legalConstitution);
        }
		catch (Exception ex) {
            DefaultLogger.debug(THIS_CLASS, "Caught exception, unable to retrieve customer info...");
        }

        return legalConstitution;
    }

    private static SBCustomerManager getCustomerManager() {
		SBCustomerManager remote = (SBCustomerManager) BeanController.getEJB(ICMSJNDIConstant.SB_CUSTOMER_MGR_JNDI,
				SBCustomerManagerHome.class.getName());
		return remote;
	}
}
