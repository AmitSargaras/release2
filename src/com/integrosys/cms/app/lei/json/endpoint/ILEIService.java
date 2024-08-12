package com.integrosys.cms.app.lei.json.endpoint;

import com.integrosys.cms.app.lei.json.dto.LeiRequestDTO;

public interface ILEIService {
	
	public LeiRequestDTO getLeiRequest(String leiCode) throws Exception;
}
