package com.integrosys.cms.batch.sibs.creditapplication;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.batch.common.mapping.AbstractFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSet;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Method used to map data obtained from a file into an object.
 */

/**
 * @author gp loh
 *         date : 26sep08
 *         time : 0020hr
 *         <p/>
 *         <p/>
 *         Record Type								Alpha	M
 *         AA No.									Alpha	M
 *         Source Limit ID							Numeric	M
 *         Outer Source Limit ID					Numeric	M
 *         Facility Type Code						Alpha	O
 *         Account No.								Alpha	M/O
 *         Account Status							Alpha	O
 *         NPL Status								Alpha	M
 *         Settled									Date	O
 *         Amount Partially Written Off			Numeric	M/O
 *         NPL Date								Date	M/O
 *         Interest in Suspense					Numeric	O
 *         Specific Provision Charge to Account	Numeric	O
 *         Part Payment Received					Numeric	O
 *         Latest Date Doubtful					Date	O
 *         Judgement Date							Date	O
 *         Judgement Sum							Numeric	O
 *         Date Write-Off							Date	O
 *         Amount Written Off						Numeric	O
 *         Months Installments Arrears				Numeric	O
 *         Months Interest Arrears					Numeric	O
 *         Facility Sequence						Numeric	M
 *         Account Type							Alpha	M
 *         Account Sequence						Numeric	M
 *         Location Country						Alpha	M
 *         Location Organisation Code				Alpha	M
 */

public class CANplClosedAccFieldSetMapper extends AbstractFieldSetMapper {

    private int decimalTwoPlace;

    public int getDecimalTwoPlace() {
        return decimalTwoPlace;
    }

    public void setDecimalTwoPlace(int decimalTwoPlace) {
        this.decimalTwoPlace = decimalTwoPlace;
    }

    public Object doMapLine(FieldSet fs) {
        DateFormat dfmt = new SimpleDateFormat(getDateFormat());
        CreditApplicationNplClosedAcc caNplClosedAcc = new CreditApplicationNplClosedAcc();
        caNplClosedAcc.setRecordType(fs.readString(0));
        caNplClosedAcc.setAaNumber(fs.readString(1));
        caNplClosedAcc.setSourceLimitID(fs.readString(2));
        caNplClosedAcc.setOuterSourceLimitID(fs.readString(3));
        caNplClosedAcc.setFacilityTypeCode(fs.readString(4));
        caNplClosedAcc.setAccountNo(fs.readString(5));
        caNplClosedAcc.setAccountStatus(fs.readString(6));
        caNplClosedAcc.setNplStatus(fs.readString(7));

        Date temp = null;
        double defaultdouble = -999999999999.99;
        try {
            // default to earliest valid date
            temp = dfmt.parse("01011900");
        } catch (Exception ex) {
            DefaultLogger.error(this, "** Date format parse exception.", ex);
            ex.printStackTrace();
        }

        // Date settled empty/null value check
        if (fs.readString(8).trim().equals("") || fs.readString(8) == null) {
            caNplClosedAcc.setDateSettled(temp);
        } else {
            caNplClosedAcc.setDateSettled(fs.readDate(8, getDateFormat()));
        }

        // Amount empty/null value check
        if (fs.readString(9).trim().equals("") || fs.readString(9) == null) {
            // default
            caNplClosedAcc.setAmtPartiallyWrittenOff(defaultdouble);
        } else {
            caNplClosedAcc.setAmtPartiallyWrittenOff(fs.readDouble(9));
        }

        // date NPPL is empty/null check
        if (fs.readString(10).trim().equals("") || fs.readString(10) == null) {
            caNplClosedAcc.setDateNpl(temp);
        } else {
            caNplClosedAcc.setDateNpl(fs.readDate(10, getDateFormat()));
        }

        // Interest In Suspense empty/null value check
        if (fs.readString(11).trim().equals("") || fs.readString(11) == null) {
            // default
            //System.out.println("\n " + this + "-->  intrst in susp is empty/null.");
            caNplClosedAcc.setInterestInSuspense(defaultdouble);
        } else {
            caNplClosedAcc.setInterestInSuspense(fs.readDouble(11));
        }

        // Specific Provision charge to account empty/null value check
        if (fs.readString(12).trim().equals("") || fs.readString(12) == null) {
            // default
            //System.out.println("\n " + this + "-->  spec provision charge to acc is empty/null.");
            caNplClosedAcc.setSpecProvisionChrgToAccount(defaultdouble);
        } else {
            caNplClosedAcc.setSpecProvisionChrgToAccount(fs.readDouble(12));
        }

        // partial payment rx is empty/null check
        if (fs.readString(13).trim().equals("") || fs.readString(13) == null) {
            // default
            caNplClosedAcc.setPartPaymentReceived(defaultdouble);
        } else {
            caNplClosedAcc.setPartPaymentReceived(fs.readDouble(13));
        }

        // date latest doubtful is empty/null check
        if (fs.readString(14).trim().equals("") || fs.readString(14) == null) {
            caNplClosedAcc.setDateLatestDoubtful(temp);
        } else {
            caNplClosedAcc.setDateLatestDoubtful(fs.readDate(14, getDateFormat()));
        }

        // date judegment is empty/null check
        if (fs.readString(15).trim().equals("") || fs.readString(15) == null) {
            caNplClosedAcc.setDateJudgement(temp);
        } else {
            caNplClosedAcc.setDateJudgement(fs.readDate(15, getDateFormat()));
        }

        // judgement sum is empty/null check
        if (fs.readString(16).trim().equals("") || fs.readString(16) == null) {
            caNplClosedAcc.setJudgementSum(defaultdouble);
        } else {
            caNplClosedAcc.setJudgementSum(fs.readDouble(16));
        }

        // date write-off is empty/null check
        if (fs.readString(17).trim().equals("") || fs.readString(17) == null) {
            caNplClosedAcc.setDateWriteOff(temp);
        } else {
            caNplClosedAcc.setDateWriteOff(fs.readDate(17, getDateFormat()));
        }

        // amount write-off is empty/null check
        if (fs.readString(18).trim().equals("") || fs.readString(18) == null) {
            caNplClosedAcc.setAmountWrittenOff(defaultdouble);
        } else {
            caNplClosedAcc.setAmountWrittenOff(fs.readDouble(18));
        }

        // Months Installments Arrears is empty/null check
        if (fs.readString(19).trim().equals("") || fs.readString(19) == null) {
            caNplClosedAcc.setMonthsInstallmentsArrears(defaultdouble);
        } else {
            caNplClosedAcc.setMonthsInstallmentsArrears(fs.readDouble(19));
        }

        // Months Interest Arrears is empty/null check
        if (fs.readString(20).trim().equals("") || fs.readString(20) == null) {
            caNplClosedAcc.setMonthsInterestArrears(defaultdouble);
        } else {
            caNplClosedAcc.setMonthsInterestArrears(fs.readDouble(20));
        }

        // mandatory fields
        caNplClosedAcc.setFacilitySequence(fs.readLong(21));
        caNplClosedAcc.setAccountType(fs.readString(22));
        caNplClosedAcc.setAccountSequence(fs.readLong(23));
        caNplClosedAcc.setLocationCountry(fs.readString(24));
        caNplClosedAcc.setLocationOrgCode(fs.readString(25));

        //Andy Wong, 17 July 2009: new fields enhancement
        caNplClosedAcc.setFacilityStatus(fs.readString(26));
        caNplClosedAcc.setCancelRejectCode(fs.readString(27));
        if(StringUtils.isNotBlank(fs.readString(28))){
            caNplClosedAcc.setCancelRejectDate(fs.readDate(28, getDateFormat()));
        }
        caNplClosedAcc.setEndLineIndicator(fs.readString(29));

        return caNplClosedAcc;
    }
}
