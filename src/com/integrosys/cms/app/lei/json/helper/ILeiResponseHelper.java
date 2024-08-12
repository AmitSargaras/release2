package com.integrosys.cms.app.lei.json.helper;

import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.lei.json.dto.LeiRequestDTO;
import com.integrosys.cms.app.lei.json.dto.LeiResponseDTO;;

public interface ILeiResponseHelper {
	
	public LeiResponseDTO mockLeiResponse(ICMSCustomer obCustomer,LeiRequestDTO leiDetails) throws Exception;
	
}
