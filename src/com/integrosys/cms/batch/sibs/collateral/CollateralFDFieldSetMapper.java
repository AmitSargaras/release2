/**
 * 
 */
package com.integrosys.cms.batch.sibs.collateral;

import org.springframework.batch.item.file.mapping.FieldSet;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.batch.common.mapping.AbstractFieldSetMapper;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


/**
 * @author gp loh
 * @date 03oct 08 0100hr
 *
 */
public class CollateralFDFieldSetMapper extends AbstractFieldSetMapper {

    private int decimalTwoPlace;
    private int decimalNinePlace;

    public int getDecimalTwoPlace() {
        return decimalTwoPlace;
    }

    public void setDecimalTwoPlace(int decimalTwoPlace) {
        this.decimalTwoPlace = decimalTwoPlace;
    }

    public int getDecimalNinePlace() {
        return decimalNinePlace;
    }

    public void setDecimalNinePlace(int decimalNinePlace) {
        this.decimalNinePlace = decimalNinePlace;
    }

    /* (non-Javadoc)
	 * @see org.springframework.batch.item.file.mapping.FieldSetMapper#mapLine(org.springframework.batch.item.file.mapping.FieldSet)
	 */
	public Object doMapLine(FieldSet fs) {
		// TODO Auto-generated method stub
		OBCollateralFD obCollFD = new OBCollateralFD(); 

			obCollFD.setRecordType( fs.readString(0) );
			obCollFD.setSecurityID( fs.readString(1) );

			//length:19 pos:21-39  desc:New Deposit/Source Reference Number
			obCollFD.setReferenceNo( fs.readString(2) );

			// type:Alpha len:19 pos:40-58    desc:New Deposit Receipt No
			obCollFD.setReceiptNumber( fs.readString(3) );

			//type:Alpha length:4 pos:59-62    desc :New Deposit Amount Currency Code
			obCollFD.setNewAmountCurrency( fs.readString(4) );

			// type:Numeric  len:17  decimal(15,2)  pos:63-79    desc:New Deposit amount
//            StringBuffer newAmountRawStringBuffer = new StringBuffer(fs.readString(5));
//            if (getDecimalTwoPlace() > 0) {
//                newAmountRawStringBuffer.insert(fs.readString(5).length() - getDecimalTwoPlace(), '.');
//            }
//            obCollFD.setNewAmount( new Double(newAmountRawStringBuffer.toString()).doubleValue() );
            obCollFD.setNewAmount( fs.readDouble(5) );

			// type:Alpha len:1 , pos:80-80, desc: O - Others, B-Bank
			obCollFD.setNewThirdPartyBank( fs.readString(6) );

			// type:Numeric, len:20	, decimal(11,9)	, pos:81-100, desc: New FDR Rate
//            StringBuffer fdrRateRawStringBuffer = new StringBuffer(fs.readString(7));
//            if (getDecimalNinePlace() > 0) {
//                fdrRateRawStringBuffer.insert(fs.readString(7).length() - getDecimalNinePlace(), '.');
//            }
//            obCollFD.setNewFDRRate( new Double(fdrRateRawStringBuffer.toString()).doubleValue() );
            obCollFD.setNewFDRRate( fs.readDouble(7) );

			// Date, len:8, pos:101-108 , desc:New  Issue Date (mandatory)
			//obCollFD.setNewIssueDate( fs.readDate(8, "ddMMyyyy") );

			// Date, len:8 , pos:109-116, desc:New Deposit Maturity Date
			Date tempDate = null;
			DateFormat dfmt = new SimpleDateFormat(getDateFormat());
			try {
				tempDate = dfmt.parse("01011900");
			} catch (Exception e) {
				DefaultLogger.error(this, "error in parsing date", e);
			}

			if ( fs.readString(8).trim().equals("") ||  fs.readString(8) == null) {
				obCollFD.setNewIssueDate( tempDate );
			} else {
				obCollFD.setNewIssueDate( fs.readDate(8, getDateFormat()) );
			}

			if ( fs.readString(9).trim().equals("") ||  fs.readString(9) == null) {
//				obCollFD.setNewMaturityDate( tempDate );
			} else {
				obCollFD.setNewMaturityDate( fs.readDate(9, getDateFormat()) );
			}


//			if ( fs.readString(9).trim().equals("") ||  fs.readString(9).trim() == null) {
//				obCollFD.setNewMaturityDate( tempDate );
//			} else {
//				obCollFD.setNewMaturityDate( fs.readDate(9, "ddMMyyyy") );
//			}

			obCollFD.setEndLineIndicator( fs.readString(10) );

		return obCollFD;
	} // end of mapLine(0

}
