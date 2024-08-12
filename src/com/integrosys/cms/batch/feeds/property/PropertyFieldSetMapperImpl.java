package com.integrosys.cms.batch.feeds.property;

import org.springframework.batch.item.file.mapping.FieldSet;

import com.integrosys.cms.batch.common.mapping.AbstractFieldSetMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class PropertyFieldSetMapperImpl extends AbstractFieldSetMapper {

    private static final String DATE_FORMAT = "MM/dd/yyyy";

    public Object doMapLine(FieldSet fs) {

        OBPropertyFile obp = new OBPropertyFile();
        obp.setCifID(fs.readString(1));
        obp.setSourceSecurityId(fs.readString(3));
//		obp.setValuerName(fs.readString(11));

//        if ( fs.readString(12) == null || fs.readString(12).trim().equals("") ) {
//            obp.setValuationDate( null );
//        } else {
//            obp.setValuationDate(fs.readDate(12, DATE_FORMAT));
//        }

//        obp.setValuationFrequency(fs.readInt(13));

//        if ( fs.readString(14) == null || fs.readString(14).trim().equals("") ) {
//            obp.setReValuationDate( null );
//        } else {
//            obp.setReValuationDate(fs.readDate(14, DATE_FORMAT));
//        }

        if ( fs.readString(23) == null || fs.readString(23).trim().equals("") ) {
            // use for check later in file loader
            obp.setCollateralID( -999999999 );
        } else {
            obp.setCollateralID( fs.readLong(23) );
        }

        obp.setValuerCode(fs.readString(24));

        if ( fs.readString(25) == null || fs.readString(25).trim().equals("") ) {
            // use for check later in file loader
            obp.setOmv( -9999999999.99 );
        } else {
            obp.setOmv( fs.readDouble(25) );
        }

        if ( fs.readString(26) == null || fs.readString(26).trim().equals("") ) {
            // use for check later in file loader
            obp.setFsv( -9999999999.99 );
        } else {
            obp.setFsv( fs.readDouble(26) );
        }

        if ( fs.readString(27) == null || fs.readString(27).trim().equals("") ) {
            // use for check later in file loader
            obp.setReservedPrice( -9999999999.99 );
        } else {
            obp.setReservedPrice( fs.readDouble(27) );
        }

        return obp;
	}

//	public Object mapLine(FieldSet fs) {
//		String aa = null;
//
//		IValuation valuationItem = new OBValuation();
//
//		valuationItem.setValuerName(fs.readString(23));
//		valuationItem.setCMV(new Amount(Long.parseLong(fs.readString(24)), aa));
//		valuationItem.setFSV(new Amount(Long.parseLong(fs.readString(25)), aa));
//		valuationItem.setReservePrice(new Amount(Long.parseLong(fs.readString(26)), aa));
//		valuationItem.setCollateralID(Long.parseLong(fs.readString(27)));
//
//		return valuationItem;
//	}
}
