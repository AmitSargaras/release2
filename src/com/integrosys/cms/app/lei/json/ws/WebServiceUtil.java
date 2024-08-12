package com.integrosys.cms.app.lei.json.ws;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;

public class WebServiceUtil {
	
	public static final String AUTHENTICATION_TYPE ="Authentication Type";
	public static final String AUTHORIZATION = "Authorization";
	public static final String API_VERSION ="Version";
	public static final String CLIENT_TIMESTAMP ="CLIENT_TIMESTAMP";
	public static final String ACCEPT_LANGUAGE ="Accept-Language";

	public static <T> String entityToString(HttpEntity<T> entity) {
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			return ow.writeValueAsString(entity);
		} catch (Exception e) {
			DefaultLogger.debug(WebServiceUtil.class, e.getMessage(), e);
			return "";
		}
		
	}
	
	public static HttpHeaders createLeiHttpHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
	
}
