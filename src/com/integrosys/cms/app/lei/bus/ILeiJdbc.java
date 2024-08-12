package com.integrosys.cms.app.lei.bus;

import java.util.List;
import com.integrosys.cms.app.fileUpload.bus.OBLeiDetailsFile;

public interface ILeiJdbc {
        
	public String generateReferenceNumber();
	public String generateExternalReferenceNo();
	public List<OBLeiDetailsFile> getUploadDetailsListForLeiValidation();
	public void updateLeiValidationFlag(OBLeiDetailsFile obLeiDetailsFile);
	public void updateMainProfileValidatedLeiExpiryDateFromScheduler(final String partyId,final String leiCode, final java.sql.Date leiExpDate);
	public void updateSubProfileValidatedLeiExpiryDateFromScheduler(final String partyId,final String leiCode,final java.sql.Date leiExpDate);

}
