package com.integrosys.cms.batch.common.item;

import java.util.Date;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.batch.common.BatchUtil;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: 15-Jun-2007 Time: 16:59:21 To
 * change this template use File | Settings | File Templates.
 */
public class BatchHeaderAndFooterValidator {
	public static final String STATUS_GOOD = "00";

	public static final String STATUS_INVALID_MESSAGEID = "01";

	public static final String STATUS_INVALID_DATE = "02";

	public static final String STATUS_INVALID_SOURCE = "03";

	public static final String STATUS_TOTAL_RECORDS_NOT_MATCH = "04";

	public static final String STATUS_FILE_CONTENT_ERRORS = "05";

	public static final String STATUS_TOTAL_HASH_NOT_MATCH = "06";

	private static BatchUtil batchUtil = new BatchUtil();

	public void checkFile(OBBatchFile obBatchFile) throws Exception {
		validateBatchCommon(obBatchFile);
		validateBatchMessageId(obBatchFile);
		validateBatchDate(obBatchFile);
		validateBatchSource(obBatchFile);
		validateTotalRecord(obBatchFile);
		validateBatchStatus(obBatchFile);
		validateBatchHashTotal(obBatchFile);
	}

	private void validateBatchCommon(OBBatchFile obBatchFile) throws Exception {
		OBFileHeader obHeader = obBatchFile.getObFileHeader();
		OBFileBody obBody = obBatchFile.getObFileBody();
		OBFileFooter OBFooter = obBatchFile.getObFileFooter();

		if (obBatchFile == null) {
			DefaultLogger.debug("BatchHeaderAndFooterValidator", "BatchFile is null!");
			throw new Exception(STATUS_FILE_CONTENT_ERRORS);
		}
		if ((obHeader == null) || (obBody == null) || (OBFooter == null)) {
			DefaultLogger.debug("BatchHeaderAndFooterValidator", "BatchFile is null!");
			throw new Exception(STATUS_FILE_CONTENT_ERRORS);
		}
		DefaultLogger.debug("obHeader", obHeader);
		DefaultLogger.debug("obBody", obBody);
		DefaultLogger.debug("OBFooter", OBFooter);

	}

	private void validateBatchMessageId(OBBatchFile obBatchFile) throws Exception {
		String messageId = obBatchFile.getObFileHeader().getMessageId();
		if (messageId == null) {
			DefaultLogger.debug("BatchHeaderAndFooterValidator", "Message ID is null!");
			throw new Exception(STATUS_INVALID_MESSAGEID);
		}
	}

	private void validateBatchDate(OBBatchFile obBatchFile) throws Exception {
		Date cDate = obBatchFile.getObFileHeader().getDate();
		if (cDate == null) {
			DefaultLogger.debug("BatchHeaderAndFooterValidator", "Date  is null!");
			throw new Exception(STATUS_INVALID_DATE);
		}
	}

	private void validateBatchSource(OBBatchFile obBatchFile) throws Exception {
		String source = obBatchFile.getObFileHeader().getSource();
		if (source == null) {
			DefaultLogger.debug("BatchHeaderAndFooterValidator", "Source  is null!");
			throw new Exception(STATUS_INVALID_SOURCE);
		}
		else {
			try {
				batchUtil.validateCommonCodeCategory(CategoryCodeConstant.CATEGROY_SYS_CODE, source, true);
			}
			catch (Exception e) {
				DefaultLogger.debug("BatchHeaderAndFooterValidator", "Source  is invalid common code!");
				throw new Exception(STATUS_INVALID_SOURCE);
			}
		}
	}

	private void validateTotalRecord(OBBatchFile obBatchFile) throws Exception {
		long headerRecords = obBatchFile.getObFileHeader().getTotalRecords();
		long actRecords = obBatchFile.getObFileBody().getTotalRecord();
		if (headerRecords != actRecords) {
			DefaultLogger.debug("BatchHeaderAndFooterValidator", "Total records not match!");
			throw new Exception(STATUS_TOTAL_RECORDS_NOT_MATCH);
		}

	}

	private void validateBatchStatus(OBBatchFile obBatchFile) throws Exception {
		String mseesagStuaus = obBatchFile.getObFileHeader().getMessageStaus();

		if (!STATUS_GOOD.equals(mseesagStuaus)) {
			DefaultLogger.debug("BatchHeaderAndFooterValidator", "Status is invalid!");
			throw new Exception(STATUS_FILE_CONTENT_ERRORS);
		}
	}

	private void validateBatchHashTotal(OBBatchFile obBatchFile) throws Exception {
		long footerHashTotal = obBatchFile.getObFileFooter().getTotalHash();
		long actHashTotal = obBatchFile.getObFileBody().getHashTotal();
		if (footerHashTotal != actHashTotal) {
			DefaultLogger.debug("BatchHeaderAndFooterValidator", "Total hash not match!");
			throw new Exception(STATUS_TOTAL_HASH_NOT_MATCH);
		}
	}

}
