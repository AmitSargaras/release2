package com.integrosys.cms.batch.sibs.creditapplication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.batch.item.file.mapping.FieldSet;

import com.integrosys.cms.batch.common.mapping.AbstractFieldSetMapper;

/**
 * Method used to map data obtained from a file into an object.
 */
public class CAODTLFieldSetMapper extends AbstractFieldSetMapper {

	private int decimalTwoPlace;

	public int getDecimalTwoPlace() {
		return decimalTwoPlace;
	}

	public void setDecimalTwoPlace(int decimalTwoPlace) {
		this.decimalTwoPlace = decimalTwoPlace;
	}

	public Object doMapLine(FieldSet fs) {

		List records = new ArrayList();
		records.add(fs.readString(0));
		records.add(fs.readString(1));
		records.add(fs.readString(2));
		records.add(fs.readString(3));
		records.add(fs.readString(4));
		records.add(fs.readString(5));
        records.add(new Double(fs.readDouble(6) / 100));
        records.add(fs.readString(7));
		records.add(new Double(fs.readDouble(8) / 100));
		records.add(new Long(fs.readLong(9)));
		records.add(fs.readString(10));
		records.add(new Long(fs.readLong(11)));
		records.add(fs.readString(12));
		records.add(fs.readString(13));

		if (fs.readString(14) != null && !fs.readString(14).equals("")) {
			records.add(fs.readDate(14, getDateFormat()));
		}
		else {
			records.add((Date) null);
		}

		if (fs.readString(15) != null && !fs.readString(15).trim().equals("")) {
			records.add(fs.readDate(15, getDateFormat()));
		}
		else {
			records.add((Date) null);
		}

		records.add(new Double(fs.readDouble(16) / 100));
		records.add(fs.readString(17));
		records.add(fs.readString(18));
		records.add(new Double(fs.readDouble(19) / 100));
		records.add(fs.readString(20));
		records.add(new Double(fs.readDouble(21) / 100));
		records.add(fs.readString(22));
		records.add(new Double(fs.readDouble(23) / 100));
		records.add(fs.readString(24));
		records.add(new Double(fs.readDouble(25) / 100));
		records.add(fs.readString(26));
		records.add(new Double(fs.readDouble(27) / 100));
		records.add(fs.readString(28));
		records.add(new Double(fs.readDouble(29) / 100));
		records.add(fs.readString(30));
		records.add(new Double(fs.readDouble(31) / 100));
		records.add(fs.readString(32));
		records.add(new Double(fs.readDouble(33) / 100000));
		records.add(fs.readString(34));
		records.add(new Double(fs.readDouble(35) / 100000));
		records.add(fs.readString(36));
		records.add(new Double(fs.readDouble(37) / 100000));
		records.add(fs.readString(38));
		records.add(fs.readString(39));
		records.add(new Double(fs.readDouble(40) / 1000000000));

		return records;
	}
}
