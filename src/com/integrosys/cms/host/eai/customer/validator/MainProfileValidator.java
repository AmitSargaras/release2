package com.integrosys.cms.host.eai.customer.validator;

import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.host.eai.CreditGrade;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.MandatoryFieldMissingException;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;
import com.integrosys.cms.host.eai.customer.EAICustomerHelper;
import com.integrosys.cms.host.eai.customer.MainProfileDetails;
import com.integrosys.cms.host.eai.customer.bus.CustomerAddress;
import com.integrosys.cms.host.eai.customer.bus.CustomerIdInfo;
import com.integrosys.cms.host.eai.customer.bus.MainProfile;

/**
 * @author $Author: marvin $<br>
 * @author Thurein <br>
 * @version $1.1$
 */
public class MainProfileValidator implements IEaiMessageValidator {
	EaiValidationHelper vH;

	public MainProfileValidator() {
		vH = EaiValidationHelper.getInstance();
	}

	public void validate(EAIMessage scimessage) throws EAIMessageValidationException {

		MainProfileDetails mpd = EAICustomerHelper.getInstance().retrieveMainProfileDetail(scimessage);
		MainProfile profile = mpd.getMainProfile();
		String source = scimessage.getMsgHeader().getSource();

		if (profile == null) {
			throw new MandatoryFieldMissingException("MainProfile");
		}

		// To support Tagging Message Acknowledgement message
		// If both UpdateStatusIndicator & ChangeIndicator are empty , skip
		// operation .
		if (StringUtils.isEmpty("" + mpd.getMainProfile().getUpdateStatusIndicator())
				&& StringUtils.isEmpty("" + mpd.getMainProfile().getChangeIndicator())) {
			DefaultLogger.info(this, "Tagging Acknowledgement Message , skipping persisting operation.");

			return;
		}

		vH.validateString(profile.getCIFId(), "CIF ID", true, 1, 20);

		vH.validateString(profile.getSource(), "CIF Source", true, 1, 4);

		vH.validateString(profile.getCustomerNameLong(), "Customer Name Long", true, 1, 150);

		vH.validateString(profile.getCustomerNameShort(), "Customer Name Short", true, 1, 50);

		vH.validateStdCode(profile.getCustomerClass(), source, "56");

		vH.validateString(profile.getDomicileCountry(), "Domicile Country", true, 1, 2);

		vH.validateStdCodeAllowNull(profile.getCustomerType(), source, "8");

		vH.validateString(profile.getCustomerStartDate(), "Customer Start Date", false, 1, 8);

		// relax validation on nature of biz category for add pledgor in CMS
		// where standard code not part of extraction criteria
		// vH.validateStdCodeAllowNull(profile.getNatureOfBiz(), source, "17");

		vH.validateString(profile.getAccountOfficerID(), "Account Officer ID", false, 1, 10);

		vH.validateString(profile.getAccountOfficerName(), "Account Officer Name", false, 1, 40);

		if (profile.getCustomerBranch() != null) {
			vH.validateString(profile.getCustomerBranch().getStandardCodeNumber(),
					"Customer Branch - StandardCodeNumber", false, 0, 40);
			vH.validateString(profile.getCustomerBranch().getStandardCodeValue(),
					"Customer Branch - StandardCodeValue", false, 0, 40);
		}

		if (profile.getIdInfo() != null) {
			validateIdInfo(profile.getIdInfo());
		}

		if (profile.getAddress() != null) {
			validateAddress(profile.getAddress(), source);
		}

		if (mpd.getCreditGrade() != null) {
			validateCreditGrade(mpd.getCreditGrade());
		}
	}

	private void validateIdInfo(Vector idInfos) {
		Iterator iter = idInfos.iterator();
		while (iter.hasNext()) {
			CustomerIdInfo custIdInfo = (CustomerIdInfo) iter.next();

			vH.validateString(custIdInfo.getIdNumber(), "ID Number", true, 0, 100);

			vH.validateStdCode(custIdInfo.getIdType(), "ID Type", "ID_TYPE");

			vH.validateDate(custIdInfo.getIdStartDate(), "ID Start Date", false);

			vH.validateString(custIdInfo.getCountryIssued(), "Country Issued", false, 0, 2);

		}
	}

	private void validateAddress(Vector addresses, String source) {
		Iterator iter = addresses.iterator();
		while (iter.hasNext()) {
			CustomerAddress custAddress = (CustomerAddress) iter.next();

			vH.validateString(custAddress.getAddress1(), "Address 1", false, 0, 40);

			vH.validateString(custAddress.getAddress2(), "Address 2", false, 0, 40);

			vH.validateString(custAddress.getAddress3(), "Address 3", false, 0, 40);

			vH.validateString(custAddress.getAddress4(), "Address 4", false, 0, 40);

			vH.validateString(custAddress.getAddress5(), "Address 4", false, 0, 40);

			vH.validateString(custAddress.getPostCode(), "Post Code", false, 0, 9);

			vH.validateString(custAddress.getCountry(), "Country", false, 0, 10);

			vH.validateStdCodeAllowNull(custAddress.getAddressType(), source, "4");

		}
	}

	private void validateCreditGrade(Vector creditGrades) {
		Iterator iter = creditGrades.iterator();
		while (iter.hasNext()) {
			CreditGrade creditGrade = (CreditGrade) iter.next();

			vH.validateString(creditGrade.getCIFId(), "CreditGrade - CIF ID", true, 0, 20);

			vH.validateMandatoryFieldForLong("Credit Grade ID", creditGrade.getCreditGradeId());

			if ((creditGrade.getCreditGradeId() == 0)) {
				throw new MandatoryFieldMissingException("Credit Grade - Credit Grade ID");
			}

			vH.validateStdCodeAllowNull(creditGrade.getCreditGradeType(), "Credit Grade Type", "18");

			vH.validateStdCodeAllowNull(creditGrade.getCreditGradeCode(), "Credit Grade Code", "19");

			vH.validateString(creditGrade.getCreditGradeEffectiveDate(), "Credit Grade Effective Date", false, 0, 8);

			vH.validateString(creditGrade.getCreditGradeReasonForChange(), "Credit Grade Reason For Change", false, 0,
					500);

			vH.validateString(creditGrade.getUpdateStatusIndicator(), "CreditGrade - Update Status Indicator", false,
					0, 1);

			vH.validateString(creditGrade.getChangeIndicator(), "CreditGrade - Change Indicator", false, 0, 1);

		}
	}

}
