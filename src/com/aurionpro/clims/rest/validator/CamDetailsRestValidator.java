package com.aurionpro.clims.rest.validator;

/**
 * Purpose : Used for Validating CAM details
 *
 **/

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import com.integrosys.cms.ui.manualinput.aa.*;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixEntry;
import com.integrosys.cms.app.approvalmatrix.proxy.IApprovalMatrixProxy;
import com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue;
import com.integrosys.cms.app.creditApproval.proxy.ICreditApprovalProxy;
import com.integrosys.cms.app.manualinput.aa.proxy.SBMIAAProxy;
import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.udf.bus.IUdfDao;
import com.integrosys.cms.ui.common.UIUtil;

public class CamDetailsRestValidator {

	private static ICreditApprovalProxy creditApprovalProxy;

	/**
	 * @return the creditApprovalProxy
	 */
	public static ICreditApprovalProxy getCreditApprovalProxy() {
		return creditApprovalProxy;
	}

	/**
	 * @param creditApprovalProxy
	 *            the creditApprovalProxy to set
	 */
	public void setCreditApprovalProxy(ICreditApprovalProxy creditApprovalProxy) {
		this.creditApprovalProxy = creditApprovalProxy;
	}

	public static ActionErrors validateInput(AADetailForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		int riskGrade = 0;
		if (!(errorCode = Validator.checkString(aForm.getDocRemarks(), false, 1, 500)).equals(Validator.ERROR_NONE)) {
			errors.add("docRemarks",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "500"));
		}

		String aaNum = aForm.getAaNum();

		if ((aaNum == null) || aaNum.equals("")) {
			errors.add("camNumber", new ActionMessage("error.string.mandatory"));
		} else {
			if (aaNum.trim().length() > 18) {
				errors.add("aaNum", new ActionMessage("error.manualinput.cam.CAMNumber.lengthError"));
			}
		}

		String ramRatingYear = aForm.getRamRatingYear();
		if ((ramRatingYear == null) || ramRatingYear.equals("")) {
			errors.add("ramRatingYear", new ActionMessage("error.string.mandatory"));
		}
		String ramRatingType = aForm.getRamRatingType();
		if ((ramRatingType == null) || ramRatingType.equals("")) {
			errors.add("ramRatingType", new ActionMessage("error.string.mandatory"));
		}

		// Uma Khot:Added for Valid Rating CR

		Date currDate = DateUtil.getDate();
		String ramRatingFinalizationDate = aForm.getRamRatingFinalizationDate();
		if (null != ramRatingType && "RAM_RATED".equals(ramRatingType)) {
			if ((ramRatingFinalizationDate == null) || "".equals(ramRatingFinalizationDate)) {
				errors.add("ramRatingFinalizationDate", new ActionMessage("error.string.mandatory"));
			}
		}
		if ((null != ramRatingFinalizationDate) && !"".equals(ramRatingFinalizationDate.trim())) {
			int value = currDate.compareTo(DateUtil.convertDate(locale, ramRatingFinalizationDate));
			if (value < 0) {
				errors.add("ramRatingFinalizationDate", new ActionMessage("error.date.future"));
			}
		}

		String aaApprovalDate = "camDate";

		// End by Pramod Katkar
		Date d = DateUtil.getDate();
		if ((aForm.getAaApprovalDate() != null) && (aForm.getAaApprovalDate().trim().length() != 0)) {

			if (!(errorCode = Validator.checkDate(aForm.getAaApprovalDate(), false, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add(aaApprovalDate, new ActionMessage("error.date.format.invalid"));
			} else {
				int a = d.compareTo(DateUtil.convertDate(locale, aForm.getAaApprovalDate()));
				if (a < 0) {
					errors.add(aaApprovalDate,
							new ActionMessage("error.date.compareDate.cannotBelater", "CAM Date", "Current Date"));
				}
			}
		} else {
			errors.add(aaApprovalDate, new ActionMessage("error.date.mandatory"));
		}

		// Shiv CAM
		// Start : added by Sachin D. Patil
		if ((aForm.getAaApprovalDate() != null) && (aForm.getAaApprovalDate().trim().length() != 0)) {
			if ((aForm.getAnnualReviewDate() != null) && (aForm.getAnnualReviewDate().trim().length() != 0)) {
				Date d2 = DateUtil.convertDate(locale, aForm.getAaApprovalDate());
				Date d1 = DateUtil.convertDate(locale, aForm.getAnnualReviewDate());
				if (d1 != null && d2 != null) {
					int a = d1.compareTo(d2);

					if (a < 0) {
						errors.add("expiryDate",
								new ActionMessage("error.date.compareDate.cannotBeEarlier", "Expiry Date", "CAM Date"));
					}
				}
			}
		}

		if ((aForm.getAaApprovalDate() != null) && (aForm.getAaApprovalDate().trim().length() != 0)) {
			if ((aForm.getExtendedNextReviewDate() != null)
					&& (aForm.getExtendedNextReviewDate().trim().length() != 0)) {
				Date d2 = DateUtil.convertDate(locale, aForm.getAaApprovalDate());
				Date d1 = DateUtil.convertDate(locale, aForm.getExtendedNextReviewDate());
				if (d1 != null && d2 != null) {
					int a = d1.compareTo(d2);

					if (a < 0) {
						errors.add("extendedNextReviewDate", new ActionMessage("error.date.compareDate.cannotBeEarlier",
								"Extension Date", "CAM Date"));
					}
				}
			}
		}

		if ((aForm.getAnnualReviewDate() != null) && (aForm.getAnnualReviewDate().trim().length() != 0)) {

			if ((aForm.getExtendedNextReviewDate() != null)
					&& (aForm.getExtendedNextReviewDate().trim().length() != 0)) {
				Date d2 = DateUtil.convertDate(locale, aForm.getAnnualReviewDate());
				Date d1 = DateUtil.convertDate(locale, aForm.getExtendedNextReviewDate());
				if (d1 != null && d2 != null) {
					int a = d1.compareTo(d2);

					if (a < 0) {
						errors.add("extendedNextReviewDate", new ActionMessage("error.date.compareDate.cannotBeEarlier",
								"Extension Date", "Expiry Date"));
					}
				}
			}

		}

		if ((aForm.getCamLoginDate() != null) && (aForm.getCamLoginDate().trim().length() != 0)) {

			if (!(errorCode = Validator.checkDate(aForm.getCamLoginDate(), false, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("camLoginDate", new ActionMessage("error.date.format1"));
			} else {
				int a = d.compareTo(DateUtil.convertDate(locale, aForm.getCamLoginDate()));
				DefaultLogger.debug("vaidation ***********************************", "Eroororr date " + a);
				if (a < 0) {
					errors.add("camLoginDate", new ActionMessage("error.date.compareDate.cannotBelater",
							"CAM Login Date", "Current Date"));
				}
			}
		} else {
			errors.add("camLoginDate", new ActionMessage("error.date.mandatory"));
		}

		if ((aForm.getInterimReviewDate() != null) && (aForm.getInterimReviewDate().trim().length() != 0)) {
			if (!(errorCode = Validator.checkDate(aForm.getInterimReviewDate(), false, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("interimReviewDate", new ActionMessage("error.date.mandatory", "1", "256"));
			}
		}

		if (!(errorCode = Validator.checkNumber(aForm.getRamRating(), true, 0, 10)).equals(Validator.ERROR_NONE)) {
			errors.add("approvingOfficerGrade",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "1", "10"));
		} else {
			riskGrade = Integer.parseInt(aForm.getRamRating());
		}

		if (aForm.getAssetClassification().trim().equalsIgnoreCase("")) {
			errors.add("assetClassification", new ActionMessage("error.assetclassification.mandatory"));
		}

		if (aForm.getRbiAssetClassification().trim().equalsIgnoreCase("")) {
			errors.add("rbiAssetClassification", new ActionMessage("error.rbiassetclassification.mandatory"));
		}
		double totalSactionedAmount = 0.D;

		// Phase 3 CR:comma separated
		String formTotalSactionedAmount = UIUtil.removeComma(aForm.getTotalSactionedAmount());
		if (!Validator.checkNumber(formTotalSactionedAmount, true, 0, 9999999999999999999999999.D).equals("noerror")) {
			errors.add("totalSactionedAmount", new ActionMessage("error.string.invalidCharacter", "Sactioned Amount"));
		} else {
			totalSactionedAmount = Double.parseDouble(formTotalSactionedAmount);
		}

		String commetteApproval = "";
		String fullyCashCollateral = "";
		String boardApproval = "";

		if (aForm.getCommitteApproval().trim().isEmpty()) {
			commetteApproval = null;
		} else {
			commetteApproval = aForm.getCommitteApproval();
		}

		if (aForm.getFullyCashCollateral().trim().isEmpty()) {
			fullyCashCollateral = null;
		} else {
			fullyCashCollateral = aForm.getFullyCashCollateral();
		}

		if (aForm.getBoardApproval().trim().isEmpty()) {
			boardApproval = null;
		} else {
			boardApproval = aForm.getBoardApproval();
		}

		// *******************Start*********************validation for credit approver
		// and CAM UDF**********************************
		// added for CAM Online webservice approach.Need to remove validation for this.
		String approver1 = aForm.getCreditApproval1();
		String approver2 = aForm.getCreditApproval2();
		String approver3 = aForm.getCreditApproval3();
		String approver4 = aForm.getCreditApproval4();
		String approver5 = aForm.getCreditApproval5();
		boolean notSeq = false;
		if (approver1.equals("") && !approver2.equals("")) {
			notSeq = true;
		}
		if (approver2.equals("") && !approver3.equals("")) {
			notSeq = true;
		}
		if (approver3.equals("") && !approver4.equals("")) {
			notSeq = true;
		}
		if (approver4.equals("") && !approver5.equals("")) {
			notSeq = true;
		}
		if (notSeq) {
			errors.add("notSeqError", new ActionMessage("error.cam.notSeq"));
		}

		AAUIHelper helper = new AAUIHelper();
		SBMIAAProxy proxy = helper.getSBMIAAProxy();
		int norApprover = 0;
		int srApprover = 0;
		String[] approver = { approver1, approver2, approver3, approver4, approver5 };

		for (int i = 0; i < approver.length; i++) {
			List lst = new ArrayList();
			try {
				lst = proxy.getCheckCreditApproval(approver[i]);
			} catch (SearchDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (lst.size() == 1) {
				String[] creditApprover = (String[]) lst.get(0);
				if (creditApprover[1].equals("Y")) {
					srApprover = srApprover + 1;
				} else {
					norApprover = norApprover + 1;
				}

				// set ApproverName
				if (i == 0) {
					aForm.setCreditApproval1(approver1);
				}
				if (i == 1) {
					aForm.setCreditApproval2(approver2);
				}
				if (i == 2) {
					aForm.setCreditApproval3(approver3);
				}
				if (i == 3) {
					aForm.setCreditApproval4(approver4);
				}
				if (i == 4) {
					aForm.setCreditApproval5(approver5);
				}
			} else {
			}
		}

		Set setApprover = (Set) new HashSet();
		int j = 0;
		for (int i = 0; i < approver.length; i++) {
			if (!approver[i].trim().equals("")) {
				setApprover.add(approver[i]);
				j = j + 1;
			}
		}
		if (j != setApprover.size()) {
			errors.add("minCreditApprover", new ActionMessage("error.cam.duplicate"));
		}

		IApprovalMatrixProxy approvalMatrixProxy = (IApprovalMatrixProxy) BeanHouse.get("approvalMatrixProxy");
		IApprovalMatrixTrxValue trxValue = approvalMatrixProxy.getApprovalMatrixGroup();

		// Sort the staging entries.
		IApprovalMatrixEntry[] entriesArr = trxValue.getApprovalMatrixGroup().getFeedEntries();

		// Start:code added for Fully Cash Collateral
		if (fullyCashCollateral != null && fullyCashCollateral.trim().equalsIgnoreCase("on")) {
			if (fulCashCollApprover(norApprover, srApprover)) {
				errors.add("minCreditApprover", new ActionMessage("error.cam.fullCashColApprover"));
			}
		}

		if (fullyCashCollateral != null && fullyCashCollateral.trim().equalsIgnoreCase("Y")) {
			if (fulCashCollApprover(norApprover, srApprover)) {
				errors.add("minCreditApprover", new ActionMessage("error.cam.fullCashColApprover"));
			}
		}

		// End :code added for Fully Cash Collateral
		if (fullyCashCollateral == null || fullyCashCollateral.trim().equals("")) {
			if (aForm.getRamRating() != null || !aForm.getRamRating().trim().equals("")) {
				if (riskGrade != 0) {
					Double db1 = new Double(entriesArr[riskGrade - 1].getLevel1());
					double dbValue1 = db1.doubleValue() * 1000000;

					Double db2 = new Double(entriesArr[riskGrade - 1].getLevel2());
					double dbValue2 = db2.doubleValue() * 1000000;

					Double db3 = new Double(entriesArr[riskGrade - 1].getLevel3());
					double dbValue3 = db3.doubleValue() * 1000000;

					Double db4 = new Double(entriesArr[riskGrade - 1].getLevel4());
					double dbValue4 = db4.doubleValue() * 1000000;

					switch (riskGrade) {
					case 1:
						if (totalSactionedAmount <= dbValue1) {
							if (level1(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level1"));
							}
						} else if (totalSactionedAmount <= dbValue2) {
							if (level2(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level2"));
							}
						} else if (totalSactionedAmount <= dbValue3) {
							if (level3(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level3"));
							}
						} else if (totalSactionedAmount <= dbValue4) {
							if (commetteApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level4"));
							}
						} else if (totalSactionedAmount > dbValue4) {
							if (boardApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level5"));
							}
						}
						break;

					case 2:

						if (totalSactionedAmount <= dbValue1) {
							if (level1(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level1"));
							}
						} else if (totalSactionedAmount <= dbValue2) {
							if (level2(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level2"));
							}
						} else if (totalSactionedAmount <= dbValue3) {
							if (level3(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level3"));
							}
						} else if (totalSactionedAmount <= dbValue4) {
							if (commetteApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level4"));
							}
						} else if (totalSactionedAmount > dbValue4) {
							if (boardApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level5"));
							}
						}
						break;
					case 3:
						if (totalSactionedAmount <= dbValue1) {
							if (level1(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level1"));
							}
						} else if (totalSactionedAmount <= dbValue2) {
							if (level2(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level2"));
							}
						} else if (totalSactionedAmount <= dbValue3) {
							if (level3(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level3"));
							}
						} else if (totalSactionedAmount <= dbValue4) {
							if (commetteApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level4"));
							}
						} else if (totalSactionedAmount > dbValue4) {
							if (boardApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level5"));
							}
						}
						break;
					case 4:
						if (totalSactionedAmount <= dbValue1) {
							if (level1(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level1"));
							}
						} else if (totalSactionedAmount <= dbValue2) {
							if (level2(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level2"));
							}
						} else if (totalSactionedAmount <= dbValue3) {
							if (level3(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level3"));
							}
						} else if (totalSactionedAmount <= dbValue4) {
							if (commetteApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level4"));
							}
						} else if (totalSactionedAmount > dbValue4) {
							if (boardApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level5"));
							}
						}
						break;
					case 5:
						if (totalSactionedAmount <= dbValue1) {
							if (level1(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level1"));
							}
						} else if (totalSactionedAmount <= dbValue2) {
							if (level2(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level2"));
							}
						} else if (totalSactionedAmount <= dbValue3) {
							if (level3(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level3"));
							}
						} else if (totalSactionedAmount <= dbValue4) {
							if (commetteApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level4"));
							}
						} else if (totalSactionedAmount > dbValue4) {
							if (boardApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level5"));
							}
						}
						break;
					case 6:
						if (totalSactionedAmount <= dbValue1) {
							if (level1(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level1"));
							}
						} else if (totalSactionedAmount <= dbValue2) {
							if (level2(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level2"));
							}
						} else if (totalSactionedAmount <= dbValue3) {
							if (level3(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level3"));
							}
						} else if (totalSactionedAmount <= dbValue4) {
							if (commetteApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level4"));
							}
						} else if (totalSactionedAmount > dbValue4) {
							if (boardApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level5"));
							}
						}
						break;
					case 7:
						if (totalSactionedAmount <= dbValue1) {
							if (level1(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level1"));
							}
						} else if (totalSactionedAmount <= dbValue2) {
							if (level2(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level2"));
							}
						} else if (totalSactionedAmount <= dbValue3) {
							if (level3(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level3"));
							}
						} else if (totalSactionedAmount <= dbValue4) {
							if (commetteApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level4"));
							}
						} else if (totalSactionedAmount > dbValue4) {
							if (boardApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level5"));
							}
						}
						break;
					case 8:
						if (totalSactionedAmount <= dbValue1) {
							if (level1(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level1"));
							}
						} else if (totalSactionedAmount <= dbValue2) {
							if (level2(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level2"));
							}
						} else if (totalSactionedAmount <= dbValue3) {
							if (level3(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level3"));
							}
						} else if (totalSactionedAmount <= dbValue4) {
							if (commetteApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level4"));
							}
						} else if (totalSactionedAmount > dbValue4) {
							if (boardApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level5"));
							}
						}
						break;
					case 9:
						if (totalSactionedAmount <= dbValue1) {
							if (level1(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level1"));
							}
						} else if (totalSactionedAmount <= dbValue2) {
							if (level2(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level2"));
							}
						} else if (totalSactionedAmount <= dbValue3) {
							if (level3(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level3"));
							}
						} else if (totalSactionedAmount <= dbValue4) {
							if (commetteApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level4"));
							}
						} else if (totalSactionedAmount > dbValue4) {
							if (boardApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level5"));
							}
						}
						break;
					case 10:
						if (totalSactionedAmount <= dbValue1) {
							if (level1(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level1"));
							}
						} else if (totalSactionedAmount <= dbValue2) {
							if (level2(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level2"));
							}
						} else if (totalSactionedAmount <= dbValue3) {
							if (level3(norApprover, srApprover)) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level3"));
							}
						} else if (totalSactionedAmount <= dbValue4) {
							if (commetteApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level4"));
							}
						} else if (totalSactionedAmount > dbValue4) {
							if (boardApproval == null) {
								errors.add("minCreditApprover", new ActionMessage("error.cam.level5"));
							}
						}
						break;
					default:
						errors.add("minCreditApprover", new ActionMessage("error.date.mandatory"));
						break;
					}
				}
			}
		}

		IUdfDao udfDao = (IUdfDao) BeanHouse.get("udfDao");
		List udfMandatoryList = udfDao.getUdfByMandatory("2");

		List udfNumericList = udfDao.getUdfByFieldTypeId("2", 7);

		if (udfMandatoryList != null) {
			for (int udf = 0; udf < udfMandatoryList.size(); udf++) {
				IUdf iUdf = (IUdf) udfMandatoryList.get(udf);
				switch (iUdf.getSequence()) {
				case 1:
					if (aForm.getUdf1() == null || aForm.getUdf1().trim().equals("")) {
						errors.add("1udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 2:
					if (aForm.getUdf2() == null || aForm.getUdf2().trim().equals("")) {
						errors.add("2udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 3:
					if (aForm.getUdf3() == null || aForm.getUdf3().trim().equals("")) {
						errors.add("3udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 4:
					if (aForm.getUdf4() == null || aForm.getUdf4().trim().equals("")) {
						errors.add("4udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 5:
					if (aForm.getUdf5() == null || aForm.getUdf5().trim().equals("")) {
						errors.add("5udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 6:
					if (aForm.getUdf6() == null || aForm.getUdf6().trim().equals("")) {
						errors.add("6udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 7:
					if (aForm.getUdf7() == null || aForm.getUdf7().trim().equals("")) {
						errors.add("7udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 8:
					if (aForm.getUdf8() == null || aForm.getUdf8().trim().equals("")) {
						errors.add("8udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 9:
					if (aForm.getUdf9() == null || aForm.getUdf9().trim().equals("")) {
						errors.add("9udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 10:
					if (aForm.getUdf10() == null || aForm.getUdf10().trim().equals("")) {
						errors.add("10udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 11:
					if (aForm.getUdf11() == null || aForm.getUdf11().trim().equals("")) {
						errors.add("11udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 12:
					if (aForm.getUdf12() == null || aForm.getUdf12().trim().equals("")) {
						errors.add("12udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 13:
					if (aForm.getUdf13() == null || aForm.getUdf13().trim().equals("")) {
						errors.add("13udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 14:
					if (aForm.getUdf14() == null || aForm.getUdf14().trim().equals("")) {
						errors.add("14udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 15:
					if (aForm.getUdf15() == null || aForm.getUdf15().trim().equals("")) {
						errors.add("15udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 16:
					if (aForm.getUdf16() == null || aForm.getUdf16().trim().equals("")) {
						errors.add("16udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 17:
					if (aForm.getUdf17() == null || aForm.getUdf17().trim().equals("")) {
						errors.add("17udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 18:
					if (aForm.getUdf18() == null || aForm.getUdf18().trim().equals("")) {
						errors.add("18udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 19:
					if (aForm.getUdf19() == null || aForm.getUdf19().trim().equals("")) {
						errors.add("19udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 20:
					if (aForm.getUdf20() == null || aForm.getUdf20().trim().equals("")) {
						errors.add("20udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 21:
					if (aForm.getUdf21() == null || aForm.getUdf21().trim().equals("")) {
						errors.add("21udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 22:
					if (aForm.getUdf22() == null || aForm.getUdf22().trim().equals("")) {
						errors.add("22udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 23:
					if (aForm.getUdf23() == null || aForm.getUdf23().trim().equals("")) {
						errors.add("23udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 24:
					if (aForm.getUdf24() == null || aForm.getUdf24().trim().equals("")) {
						errors.add("24udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 25:
					if (aForm.getUdf25() == null || aForm.getUdf25().trim().equals("")) {
						errors.add("25udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 26:
					if (aForm.getUdf26() == null || aForm.getUdf26().trim().equals("")) {
						errors.add("26udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 27:
					if (aForm.getUdf27() == null || aForm.getUdf27().trim().equals("")) {
						errors.add("27udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 28:
					if (aForm.getUdf28() == null || aForm.getUdf28().trim().equals("")) {
						errors.add("28udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 29:
					if (aForm.getUdf29() == null || aForm.getUdf29().trim().equals("")) {
						errors.add("29udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 30:
					if (aForm.getUdf30() == null || aForm.getUdf30().trim().equals("")) {
						errors.add("30udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 31:
					if (aForm.getUdf31() == null || aForm.getUdf31().trim().equals("")) {
						errors.add("31udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 32:
					if (aForm.getUdf32() == null || aForm.getUdf32().trim().equals("")) {
						errors.add("32udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 33:
					if (aForm.getUdf33() == null || aForm.getUdf33().trim().equals("")) {
						errors.add("33udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 34:
					if (aForm.getUdf34() == null || aForm.getUdf34().trim().equals("")) {
						errors.add("34udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 35:
					if (aForm.getUdf35() == null || aForm.getUdf35().trim().equals("")) {
						errors.add("35udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 36:
					if (aForm.getUdf36() == null || aForm.getUdf36().trim().equals("")) {
						errors.add("36udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 37:
					if (aForm.getUdf37() == null || aForm.getUdf37().trim().equals("")) {
						errors.add("37udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 38:
					if (aForm.getUdf38() == null || aForm.getUdf38().trim().equals("")) {
						errors.add("38udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 39:
					if (aForm.getUdf39() == null || aForm.getUdf39().trim().equals("")) {
						errors.add("39udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 40:
					if (aForm.getUdf40() == null || aForm.getUdf40().trim().equals("")) {
						errors.add("40udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 41:
					if (aForm.getUdf41() == null || aForm.getUdf41().trim().equals("")) {
						errors.add("41udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 42:
					if (aForm.getUdf42() == null || aForm.getUdf42().trim().equals("")) {
						errors.add("42udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 43:
					if (aForm.getUdf43() == null || aForm.getUdf43().trim().equals("")) {
						errors.add("43udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 44:
					if (aForm.getUdf44() == null || aForm.getUdf44().trim().equals("")) {
						errors.add("44udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 45:
					if (aForm.getUdf45() == null || aForm.getUdf45().trim().equals("")) {
						errors.add("45udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 46:
					if (aForm.getUdf46() == null || aForm.getUdf46().trim().equals("")) {
						errors.add("46udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 47:
					if (aForm.getUdf47() == null || aForm.getUdf47().trim().equals("")) {
						errors.add("47udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 48:
					if (aForm.getUdf48() == null || aForm.getUdf48().trim().equals("")) {
						errors.add("48udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 49:
					if (aForm.getUdf49() == null || aForm.getUdf49().trim().equals("")) {
						errors.add("49udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 50:
					if (aForm.getUdf50() == null || aForm.getUdf50().trim().equals("")) {
						errors.add("50udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 51:
					if (aForm.getUdf51() == null || aForm.getUdf51().trim().equals("")) {
						errors.add("51udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 52:
					if (aForm.getUdf52() == null || aForm.getUdf52().trim().equals("")) {
						errors.add("52udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 53:
					if (aForm.getUdf53() == null || aForm.getUdf53().trim().equals("")) {
						errors.add("53udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 54:
					if (aForm.getUdf54() == null || aForm.getUdf54().trim().equals("")) {
						errors.add("54udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 55:
					if (aForm.getUdf55() == null || aForm.getUdf55().trim().equals("")) {
						errors.add("55udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 56:
					if (aForm.getUdf56() == null || aForm.getUdf56().trim().equals("")) {
						errors.add("56udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 57:
					if (aForm.getUdf57() == null || aForm.getUdf57().trim().equals("")) {
						errors.add("57udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 58:
					if (aForm.getUdf58() == null || aForm.getUdf58().trim().equals("")) {
						errors.add("58udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 59:
					if (aForm.getUdf59() == null || aForm.getUdf59().trim().equals("")) {
						errors.add("59udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 60:
					if (aForm.getUdf60() == null || aForm.getUdf60().trim().equals("")) {
						errors.add("60udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 61:
					if (aForm.getUdf61() == null || aForm.getUdf61().trim().equals("")) {
						errors.add("61udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 62:
					if (aForm.getUdf62() == null || aForm.getUdf62().trim().equals("")) {
						errors.add("62udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 63:
					if (aForm.getUdf63() == null || aForm.getUdf63().trim().equals("")) {
						errors.add("63udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 64:
					if (aForm.getUdf64() == null || aForm.getUdf64().trim().equals("")) {
						errors.add("64udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 65:
					if (aForm.getUdf65() == null || aForm.getUdf65().trim().equals("")) {
						errors.add("65udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 66:
					if (aForm.getUdf66() == null || aForm.getUdf66().trim().equals("")) {
						errors.add("66udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 67:
					if (aForm.getUdf67() == null || aForm.getUdf67().trim().equals("")) {
						errors.add("67udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 68:
					if (aForm.getUdf68() == null || aForm.getUdf68().trim().equals("")) {
						errors.add("68udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 69:
					if (aForm.getUdf69() == null || aForm.getUdf69().trim().equals("")) {
						errors.add("69udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 70:
					if (aForm.getUdf70() == null || aForm.getUdf70().trim().equals("")) {
						errors.add("70udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 71:
					if (aForm.getUdf71() == null || aForm.getUdf71().trim().equals("")) {
						errors.add("71udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 72:
					if (aForm.getUdf72() == null || aForm.getUdf72().trim().equals("")) {
						errors.add("72udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 73:
					if (aForm.getUdf73() == null || aForm.getUdf73().trim().equals("")) {
						errors.add("73udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 74:
					if (aForm.getUdf74() == null || aForm.getUdf74().trim().equals("")) {
						errors.add("74udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 75:
					if (aForm.getUdf75() == null || aForm.getUdf75().trim().equals("")) {
						errors.add("75udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 76:
					if (aForm.getUdf76() == null || aForm.getUdf76().trim().equals("")) {
						errors.add("76udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 77:
					if (aForm.getUdf77() == null || aForm.getUdf77().trim().equals("")) {
						errors.add("77udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 78:
					if (aForm.getUdf78() == null || aForm.getUdf78().trim().equals("")) {
						errors.add("78udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 79:
					if (aForm.getUdf79() == null || aForm.getUdf79().trim().equals("")) {
						errors.add("79udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 80:
					if (aForm.getUdf80() == null || aForm.getUdf80().trim().equals("")) {
						errors.add("80udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 81:
					if (aForm.getUdf81() == null || aForm.getUdf81().trim().equals("")) {
						errors.add("81udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 82:
					if (aForm.getUdf82() == null || aForm.getUdf82().trim().equals("")) {
						errors.add("82udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 83:
					if (aForm.getUdf83() == null || aForm.getUdf83().trim().equals("")) {
						errors.add("83udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 84:
					if (aForm.getUdf84() == null || aForm.getUdf84().trim().equals("")) {
						errors.add("84udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 85:
					if (aForm.getUdf85() == null || aForm.getUdf85().trim().equals("")) {
						errors.add("85udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 86:
					if (aForm.getUdf86() == null || aForm.getUdf86().trim().equals("")) {
						errors.add("86udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 87:
					if (aForm.getUdf87() == null || aForm.getUdf87().trim().equals("")) {
						errors.add("87udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 88:
					if (aForm.getUdf88() == null || aForm.getUdf88().trim().equals("")) {
						errors.add("88udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 89:
					if (aForm.getUdf89() == null || aForm.getUdf89().trim().equals("")) {
						errors.add("89udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 90:
					if (aForm.getUdf90() == null || aForm.getUdf90().trim().equals("")) {
						errors.add("90udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 91:
					if (aForm.getUdf91() == null || aForm.getUdf91().trim().equals("")) {
						errors.add("91udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 92:
					if (aForm.getUdf92() == null || aForm.getUdf92().trim().equals("")) {
						errors.add("92udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 93:
					if (aForm.getUdf93() == null || aForm.getUdf93().trim().equals("")) {
						errors.add("93udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 94:
					if (aForm.getUdf94() == null || aForm.getUdf94().trim().equals("")) {
						errors.add("94udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 95:
					if (aForm.getUdf95() == null || aForm.getUdf95().trim().equals("")) {
						errors.add("95udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 96:
					if (aForm.getUdf96() == null || aForm.getUdf96().trim().equals("")) {
						errors.add("96udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 97:
					if (aForm.getUdf97() == null || aForm.getUdf97().trim().equals("")) {
						errors.add("97udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 98:
					if (aForm.getUdf98() == null || aForm.getUdf98().trim().equals("")) {
						errors.add("98udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 99:
					if (aForm.getUdf99() == null || aForm.getUdf99().trim().equals("")) {
						errors.add("99udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				case 100:
					if (aForm.getUdf100() == null || aForm.getUdf100().trim().equals("")) {
						errors.add("100udfError", new ActionMessage("error.string.mandatory.udf", iUdf.getFieldName()));
					}
					break;
				default:
					break;
				}

			}
		}

		if (udfNumericList != null) {
			for (int udf = 0; udf < udfNumericList.size(); udf++) {
				IUdf iUdf = (IUdf) udfNumericList.get(udf);
				switch (iUdf.getSequence()) {

				case 1:
					if (!(aForm.getUdf1() == null || aForm.getUdf1().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf1().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("1udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 2:
					if (!(aForm.getUdf2() == null || aForm.getUdf2().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf2().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("2udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 3:
					if (!(aForm.getUdf3() == null || aForm.getUdf3().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf3().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("3udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 4:
					if (!(aForm.getUdf4() == null || aForm.getUdf4().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf4().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("4udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 5:
					if (!(aForm.getUdf5() == null || aForm.getUdf5().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf5().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("5udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 6:
					if (!(aForm.getUdf6() == null || aForm.getUdf6().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf6().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("6udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 7:
					if (!(aForm.getUdf7() == null || aForm.getUdf7().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf7().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("7udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 8:
					if (!(aForm.getUdf8() == null || aForm.getUdf8().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf8().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("8udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 9:
					if (!(aForm.getUdf9() == null || aForm.getUdf9().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf9().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("9udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 10:
					if (!(aForm.getUdf10() == null || aForm.getUdf10().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf10().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("10udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 11:
					if (!(aForm.getUdf11() == null || aForm.getUdf11().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf11().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("11udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 12:
					if (!(aForm.getUdf12() == null || aForm.getUdf12().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf12().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("12udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 13:
					if (!(aForm.getUdf13() == null || aForm.getUdf13().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf13().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("13udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 14:
					if (!(aForm.getUdf14() == null || aForm.getUdf14().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf14().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("14udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 15:
					if (!(aForm.getUdf15() == null || aForm.getUdf15().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf15().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("15udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 16:
					if (!(aForm.getUdf16() == null || aForm.getUdf16().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf16().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("16udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 17:
					if (!(aForm.getUdf17() == null || aForm.getUdf17().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf17().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("17udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 18:
					if (!(aForm.getUdf18() == null || aForm.getUdf18().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf18().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("18udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 19:
					if (!(aForm.getUdf19() == null || aForm.getUdf19().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf19().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("19udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 20:
					if (!(aForm.getUdf20() == null || aForm.getUdf20().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf20().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("20udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 21:
					if (!(aForm.getUdf21() == null || aForm.getUdf21().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf21().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("21udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 22:
					if (!(aForm.getUdf22() == null || aForm.getUdf22().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf22().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("22udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 23:
					if (!(aForm.getUdf23() == null || aForm.getUdf23().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf23().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("23udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 24:
					if (!(aForm.getUdf24() == null || aForm.getUdf24().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf24().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("24udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 25:
					if (!(aForm.getUdf25() == null || aForm.getUdf25().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf25().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("25udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 26:
					if (!(aForm.getUdf26() == null || aForm.getUdf26().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf26().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("26udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 27:
					if (!(aForm.getUdf27() == null || aForm.getUdf27().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf27().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("27udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 28:
					if (!(aForm.getUdf28() == null || aForm.getUdf28().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf28().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("28udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 29:
					if (!(aForm.getUdf29() == null || aForm.getUdf29().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf29().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("29udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 30:
					if (!(aForm.getUdf30() == null || aForm.getUdf30().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf30().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("30udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 31:
					if (!(aForm.getUdf31() == null || aForm.getUdf31().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf31().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("31udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 32:
					if (!(aForm.getUdf32() == null || aForm.getUdf32().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf32().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("32udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 33:
					if (!(aForm.getUdf33() == null || aForm.getUdf33().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf33().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("33udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 34:
					if (!(aForm.getUdf34() == null || aForm.getUdf34().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf34().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("34udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 35:
					if (!(aForm.getUdf35() == null || aForm.getUdf35().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf35().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("35udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 36:
					if (!(aForm.getUdf36() == null || aForm.getUdf36().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf36().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("36udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 37:
					if (!(aForm.getUdf37() == null || aForm.getUdf37().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf37().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("37udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 38:
					if (!(aForm.getUdf38() == null || aForm.getUdf38().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf38().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("38udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 39:
					if (!(aForm.getUdf39() == null || aForm.getUdf39().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf39().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("39udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 40:
					if (!(aForm.getUdf40() == null || aForm.getUdf40().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf40().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("40udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 41:
					if (!(aForm.getUdf41() == null || aForm.getUdf41().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf41().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("41udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 42:
					if (!(aForm.getUdf42() == null || aForm.getUdf42().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf42().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("42udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 43:
					if (!(aForm.getUdf43() == null || aForm.getUdf43().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf43().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("43udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 44:
					if (!(aForm.getUdf44() == null || aForm.getUdf44().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf44().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("44udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 45:
					if (!(aForm.getUdf45() == null || aForm.getUdf45().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf45().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("45udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 46:
					if (!(aForm.getUdf46() == null || aForm.getUdf46().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf46().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("46udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 47:
					if (!(aForm.getUdf47() == null || aForm.getUdf47().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf47().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("47udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 48:
					if (!(aForm.getUdf48() == null || aForm.getUdf48().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf48().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("48udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 49:
					if (!(aForm.getUdf49() == null || aForm.getUdf49().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf49().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("49udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 50:
					if (!(aForm.getUdf50() == null || aForm.getUdf50().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf50().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("50udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 51:
					if (!(aForm.getUdf51() == null || aForm.getUdf51().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf51().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("51udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 52:
					if (!(aForm.getUdf52() == null || aForm.getUdf52().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf52().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("52udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 53:
					if (!(aForm.getUdf53() == null || aForm.getUdf53().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf53().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("53udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 54:
					if (!(aForm.getUdf54() == null || aForm.getUdf54().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf54().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("54udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 55:
					if (!(aForm.getUdf55() == null || aForm.getUdf55().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf55().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("55udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 56:
					if (!(aForm.getUdf56() == null || aForm.getUdf56().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf56().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("56udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 57:
					if (!(aForm.getUdf57() == null || aForm.getUdf57().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf57().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("57udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 58:
					if (!(aForm.getUdf58() == null || aForm.getUdf58().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf58().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("58udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 59:
					if (!(aForm.getUdf59() == null || aForm.getUdf59().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf59().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("59udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 60:
					if (!(aForm.getUdf60() == null || aForm.getUdf60().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf60().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("60udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 61:
					if (!(aForm.getUdf61() == null || aForm.getUdf61().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf61().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("61udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 62:
					if (!(aForm.getUdf62() == null || aForm.getUdf62().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf62().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("62udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 63:
					if (!(aForm.getUdf63() == null || aForm.getUdf63().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf63().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("63udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 64:
					if (!(aForm.getUdf64() == null || aForm.getUdf64().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf64().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("64udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 65:
					if (!(aForm.getUdf65() == null || aForm.getUdf65().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf65().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("65udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 66:
					if (!(aForm.getUdf66() == null || aForm.getUdf66().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf66().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("66udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 67:
					if (!(aForm.getUdf67() == null || aForm.getUdf67().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf67().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("67udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 68:
					if (!(aForm.getUdf68() == null || aForm.getUdf68().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf68().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("68udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 69:
					if (!(aForm.getUdf69() == null || aForm.getUdf69().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf69().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("69udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 70:
					if (!(aForm.getUdf70() == null || aForm.getUdf70().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf70().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("70udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 71:
					if (!(aForm.getUdf71() == null || aForm.getUdf71().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf71().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("71udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 72:
					if (!(aForm.getUdf72() == null || aForm.getUdf72().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf72().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("72udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 73:
					if (!(aForm.getUdf73() == null || aForm.getUdf73().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf73().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("73udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 74:
					if (!(aForm.getUdf74() == null || aForm.getUdf74().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf74().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("74udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 75:
					if (!(aForm.getUdf75() == null || aForm.getUdf75().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf75().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("75udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 76:
					if (!(aForm.getUdf76() == null || aForm.getUdf76().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf76().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("76udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 77:
					if (!(aForm.getUdf77() == null || aForm.getUdf77().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf77().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("77udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 78:
					if (!(aForm.getUdf78() == null || aForm.getUdf78().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf78().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("78udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 79:
					if (!(aForm.getUdf79() == null || aForm.getUdf79().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf79().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("79udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 80:
					if (!(aForm.getUdf80() == null || aForm.getUdf80().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf80().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("80udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 81:
					if (!(aForm.getUdf81() == null || aForm.getUdf81().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf81().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("81udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 82:
					if (!(aForm.getUdf82() == null || aForm.getUdf82().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf82().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("82udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 83:
					if (!(aForm.getUdf83() == null || aForm.getUdf83().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf83().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("83udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 84:
					if (!(aForm.getUdf84() == null || aForm.getUdf84().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf84().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("84udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 85:
					if (!(aForm.getUdf85() == null || aForm.getUdf85().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf85().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("85udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 86:
					if (!(aForm.getUdf86() == null || aForm.getUdf86().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf86().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("86udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 87:
					if (!(aForm.getUdf87() == null || aForm.getUdf87().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf87().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("87udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 88:
					if (!(aForm.getUdf88() == null || aForm.getUdf88().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf88().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("88udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 89:
					if (!(aForm.getUdf89() == null || aForm.getUdf89().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf89().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("89udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 90:
					if (!(aForm.getUdf90() == null || aForm.getUdf90().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf90().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("90udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 91:
					if (!(aForm.getUdf91() == null || aForm.getUdf91().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf91().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("91udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 92:
					if (!(aForm.getUdf92() == null || aForm.getUdf92().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf92().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("92udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 93:
					if (!(aForm.getUdf93() == null || aForm.getUdf93().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf93().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("93udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 94:
					if (!(aForm.getUdf94() == null || aForm.getUdf94().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf94().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("94udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 95:
					if (!(aForm.getUdf95() == null || aForm.getUdf95().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf95().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("95udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 96:
					if (!(aForm.getUdf96() == null || aForm.getUdf96().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf96().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("96udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 97:
					if (!(aForm.getUdf97() == null || aForm.getUdf97().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf97().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("97udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 98:
					if (!(aForm.getUdf98() == null || aForm.getUdf98().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf98().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("98udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 99:
					if (!(aForm.getUdf99() == null || aForm.getUdf99().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf99().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("99udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;
				case 100:
					if (!(aForm.getUdf100() == null || aForm.getUdf100().trim().equals(""))) {
						if (!Validator.ERROR_NONE.equals(errorCode = Validator
								.checkNumber(aForm.getUdf100().toString().trim(), false, 0, 999999999999999.D))) {
							errors.add("100udfError", new ActionMessage("error.integer.udf", iUdf.getFieldName()));
						}
					}
					break;

				default:

					break;
				}

			}
		}

		// *******************END*********************validation for credit approver and

		// Start:Uma Khot:CRI Field addition enhancement

		if (null == aForm.getIsSingleBorrowerPrudCeiling() || "No".equals(aForm.getIsSingleBorrowerPrudCeiling())) {
			String detailsOfRbiApprovalForSingle = aForm.getDetailsOfRbiApprovalForSingle();

			if (aForm.getRbiApprovalForSingle() == null || aForm.getRbiApprovalForSingle().equals("")) {
				errors.add("rbiApprovalSingleError", new ActionMessage("error.string.mandatory"));
			}
			if ((detailsOfRbiApprovalForSingle == null) || detailsOfRbiApprovalForSingle.equals("")) {
				errors.add("detailsOfRbiApprovalForSingleErr", new ActionMessage("error.string.mandatory"));
			} else {
				if (!(errorCode = Validator.checkString(detailsOfRbiApprovalForSingle, false, 1, 150))
						.equals(Validator.ERROR_NONE)) {
					errors.add("detailsOfRbiApprovalForSingleErr",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "150"));
				}
			}
		}

		if (null == aForm.getIsGroupBorrowerPrudCeiling() || "No".equals(aForm.getIsGroupBorrowerPrudCeiling())) {

			String detailsOfRbiApprovalForGroup = aForm.getDetailsOfRbiApprovalForGroup();

			if (aForm.getRbiApprovalForGroup() == null || aForm.getRbiApprovalForGroup().equals("")) {
				errors.add("rbiApprovalGroupError", new ActionMessage("error.string.mandatory"));
			}

			if ((detailsOfRbiApprovalForGroup == null) || detailsOfRbiApprovalForGroup.equals("")) {
				errors.add("detailsOfRbiApprovalForGroupErr", new ActionMessage("error.string.mandatory"));
			} else {
				if (!(errorCode = Validator.checkString(detailsOfRbiApprovalForGroup, false, 1, 150))
						.equals(Validator.ERROR_NONE)) {
					errors.add("detailsOfRbiApprovalForGroupErr",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "150"));
				}
			}
		}

		if ("Yes".equals(aForm.getIsNonCooperativeBorrowers())
				&& "Yes".equals(aForm.getIsDirectorAsNonCooperativeForOther())) {
			// String detailsOfRbiApprovalForSingle =
			// aForm.getDetailsOfRbiApprovalForSingle();
			String nameOfDirectorsAndCompany = aForm.getNameOfDirectorsAndCompany();
			if ((nameOfDirectorsAndCompany == null) || nameOfDirectorsAndCompany.equals("")) {
				errors.add("nameOfDirectorsAndCompanyErr", new ActionMessage("error.string.mandatory"));
			} else {
				if (!(errorCode = Validator.checkString(nameOfDirectorsAndCompany, false, 1, 150))
						.equals(Validator.ERROR_NONE)) {
					errors.add("nameOfDirectorsAndCompanyErr",
							new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "150"));
				}
			}
		}

		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());

		return errors;

	}

	private static boolean fulCashCollApprover(int norApprover, int srApprover) {
		if ((norApprover + srApprover) < 2) {
			return true;
		}
		return false;
	}

	private static boolean level1(int norApprover, int srApprover) {
		if ((norApprover + srApprover) < 3) {
			return true;
		}
		return false;
	}

	private static boolean level2(int norApprover, int srApprover) {
		if ((norApprover + srApprover) < 3 || srApprover < 1) {
			return true;
		}
		return false;
	}

	private static boolean level3(int norApprover, int srApprover) {
		if ((norApprover + srApprover) < 3 || srApprover < 2) {
			return true;
		}
		return false;
	}

}