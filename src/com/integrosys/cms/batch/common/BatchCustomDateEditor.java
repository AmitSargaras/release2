package com.integrosys.cms.batch.common;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import java.text.SimpleDateFormat;


public class BatchCustomDateEditor extends CustomDateEditor{

	public BatchCustomDateEditor() {
		super(new SimpleDateFormat("ddMMyyyy"),false);
	}

}

