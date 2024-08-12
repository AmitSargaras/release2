/**
 * 
 */
package com.integrosys.cms.batch.sibs.collateral;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.item.file.mapping.FieldSet;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.batch.common.mapping.AbstractFieldSetMapper;

/**
 * @author gp loh
 * @date 02oct 08 1820hr
 *
 */
public class CollateralSMFFieldSetMapper extends AbstractFieldSetMapper {

    private int decimalTwoPlace;
    private int decimalFivePlace;

    public int getDecimalTwoPlace() {
        return decimalTwoPlace;
    }

    public void setDecimalTwoPlace(int decimalTwoPlace) {
        this.decimalTwoPlace = decimalTwoPlace;
    }

    public int getDecimalFivePlace() {
        return decimalFivePlace;
    }

    public void setDecimalFivePlace(int decimalFivePlace) {
        this.decimalFivePlace = decimalFivePlace;
    }/* (non-Javadoc)
	 * @see org.springframework.batch.item.file.mapping.FieldSetMapper#mapLine(org.springframework.batch.item.file.mapping.FieldSet)
	 */
	public Object doMapLine(FieldSet fs) {
		// TODO Auto-generated method stub

		OBCollateralSMF obCollSMF = new OBCollateralSMF();

			obCollSMF.setRecordType( fs.readString(0) );
			obCollSMF.setSourceSecurityID( fs.readString(1) );
			obCollSMF.setOriginatingSecurityCurrency( fs.readString(2) );
			obCollSMF.setCmsSecurityCurrency( fs.readString(3) );
			obCollSMF.setSecurityLocation( fs.readString(4) );
			obCollSMF.setBranchName( fs.readString(5) );
			obCollSMF.setSecurityCustodianType( fs.readString(6) );
			obCollSMF.setSecurityCustodianValue( fs.readString(7) );
			obCollSMF.setLegalEnforceability( fs.readString(8) );

			if ( fs.readString(9) != null && !fs.readString(9).trim().equals("")) {
                obCollSMF.setLegalEnforceabilityDate( fs.readDate(9, getDateFormat()) );
            }

            obCollSMF.setEquityType( fs.readString( 10) );

			// check for empty/null CDS Number (optional field)
			if ( fs.readString(11).trim().equals("") || fs.readString(11) == null ) {
				obCollSMF.setCdsNumber( -999999999 );
			} else {
				obCollSMF.setCdsNumber( fs.readLong(11) );
			}

			obCollSMF.setRegisteredOwner( fs.readString(12) );

//            StringBuffer noOfUnitRawStringBuffer = new StringBuffer(fs.readString(13));
//            if (getDecimalTwoPlace() > 0) {
//                noOfUnitRawStringBuffer.insert(fs.readString(13).length() - getDecimalTwoPlace(), '.');
//            }
//            obCollSMF.setNumberOfUnits( new Double(noOfUnitRawStringBuffer.toString()).doubleValue() );
            obCollSMF.setNumberOfUnits( fs.readDouble(13) );

			// check for empty/null UNIT PRICE decimal(22,5) (optional field)
			if ( fs.readString(14).trim().equals("") || fs.readString(14) == null ) {
				obCollSMF.setUnitPrice( -99999999999999999.99999 );
			} else {
//                StringBuffer unitPriceRawStringBuffer = new StringBuffer(fs.readString(14));
//                if (getDecimalFivePlace() > 0) {
//                    unitPriceRawStringBuffer.insert(fs.readString(14).length() - getDecimalFivePlace(), '.');
//                }
//                obCollSMF.setUnitPrice( new Double(unitPriceRawStringBuffer.toString()).doubleValue() );
                obCollSMF.setUnitPrice( fs.readDouble(14) );
            }

			Date tempDate = null;
			DateFormat dfmt = new SimpleDateFormat(getDateFormat());
			try {
				tempDate = dfmt.parse("01011900");
			} catch (Exception e) {
				DefaultLogger.error(this, "error in parsing date", e);
			}

			// check for empty/null Security Maturity Date (optional field)
			if ( fs.readString(15).trim().equals("") || fs.readString(15) == null ) {
				obCollSMF.setSecurityMaturityDate( tempDate );
			} else {
				obCollSMF.setSecurityMaturityDate( fs.readDate(15, getDateFormat()) );
			}

			obCollSMF.setStockExchange( fs.readString(16) );
			obCollSMF.setCountryOfStockExchange( fs.readString(17) );
			obCollSMF.setStockCode( fs.readString(18) );
			obCollSMF.setApplicationNumber( fs.readString(19) );
			obCollSMF.setFacilityName( fs.readString(20) );
			obCollSMF.setSequenceNumber( fs.readString(21) );
			obCollSMF.setEndLineIndicator( fs.readString(22) );
		
		return obCollSMF;
	}

}
