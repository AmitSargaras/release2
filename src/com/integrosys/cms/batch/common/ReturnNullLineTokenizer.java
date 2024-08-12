package com.integrosys.cms.batch.common;

import org.springframework.batch.item.file.mapping.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;

public class ReturnNullLineTokenizer implements LineTokenizer {

	public FieldSet tokenize(String line) {
		return null;
	}

}
