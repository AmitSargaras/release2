package com.integrosys.cms.batch.feeds.stock;

import org.springframework.batch.item.file.mapping.FieldSet;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.batch.common.mapping.AbstractFieldSetMapper;

public class StockPriceFieldSetMapperImpl extends AbstractFieldSetMapper {

	public Object doMapLine(FieldSet fs) {
		IStockPrice stockPriceItem = new OBStockPrice();
		setDateFormat("ddMMyyyy");
		stockPriceItem.setRecordType(fs.readString(0));

		stockPriceItem.setStockCode(fs.readString(1));
		stockPriceItem.setCounterName(fs.readString(2));

		stockPriceItem.setExchangeCode(fs.readString(3));
		stockPriceItem.setUnitPrice(Double.valueOf(fs.readString(4)));
		stockPriceItem.setUnitPriceCurrency(fs.readString(5));

		stockPriceItem.setIsinCode(fs.readString(6));
		stockPriceItem.setStockExcTraBoardCode(fs.readString(7));
		stockPriceItem.setStockExcTraBoardDes(fs.readString(8));
		stockPriceItem.setStockTypes(fs.readString(9));
		stockPriceItem.setListedSharesQuantity(Long.parseLong((fs.readString(10))));
		stockPriceItem.setParValue(Double.valueOf(fs.readString(11)));
		stockPriceItem.setPaidUpCapital(Double.valueOf(fs.readString(12)));

		if ( fs.readString(13).equals("") || fs.readString(13) == null ) {
			stockPriceItem.setExpiryDate(DateUtil.convertDate(fs.readString(13)));
		} else {
			stockPriceItem.setExpiryDate( fs.readDate(13, getDateFormat() ) );
		}

		stockPriceItem.setShareStatus(fs.readString(14));
		stockPriceItem.setStockExcSusCouIndicator(fs.readString(15));

        if ( fs.readString(16).equals("") || fs.readString(16) == null ) {
			stockPriceItem.setDateLaunched(DateUtil.convertDate(fs.readString(16)));
		} else {
			stockPriceItem.setDateLaunched( fs.readDate(16, getDateFormat() ) );
		}

        return stockPriceItem;
	}

}
