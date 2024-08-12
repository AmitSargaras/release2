/**
 * Copyright 2015-2016 aurionpro solutions, .  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is not free software; you cannot redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  aurionpro solutions designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 * <p>
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * <p>
 * Please contact aurionpro solutions, public ltd., visit www.aurionprosolutions.com if you need additional information or
 * have any questions.
 */
package com.integrosys.base.uiinfra.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionMapping;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.RequestWrapper;
import com.integrosys.base.techinfra.util.StopWatch;
import com.integrosys.cms.app.customer.bus.TraceDisabledException;



public class CORSFilter implements Filter {
	
//		private FilterConfig filterConfig;
//	    public static String ENCODING = "UTF-8";
//		public static boolean IS_DEBUG;
//		public static boolean IS_ACTIVE;
//	public static final String TRANSACTION_TOKEN_KEY = "TRANSACTION_TOKEN_KEY";
//
//	public static final String TOKEN_KEY = "TOKEN_KEY";

	private FilterConfig filterConfig;
	
	private static final String crossSiteScriptXssToken = "&lt;,&gt;,&#40;,&#41;,&#35;,&amp;,&quot;,<script>,</script>,&lt;script&gt;,&lt;/script&gt;,alert,&lt;script>,r<script&gt;,</script&gt;,&lt;/script>,',;,=,--,select,update,delete,drop,>,<,[,]";
	private static String defaultCorrsSiteScriptXssToken [] = {"&lt;","&gt;","&#40;","&#41;","&#35;","&amp;","&quot;","<script>","</script>"," or "," or='","&lt;script&gt;","&lt;/script&gt;","alert","&lt;script>","r<script&gt;","</script&gt;","&lt;/script>","'",";","--","select","update","delete","drop",">","<","[","]"};
	private static String defaultSqlInjectionsTokens [] = {"select","update","delete","drop"};
	private static final String sqlInjectionsTokens = "select,update,delete,drop";

//	private AtomicLong id = new AtomicLong(1);
	/** The Constant REQUEST_PREFIX. */
//	private static final String REQUEST_PREFIX = "Request: ";

	/** The Constant RESPONSE_PREFIX. */
//	private static final String RESPONSE_PREFIX = "Response: ";
	
	
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = ((HttpServletRequest) request).getSession();

		RequestWrapper requestWrapper;
//		System.out.println("CORSFILTER session.getId()===== "+session.getId());
//		System.out.println("CORSFILTER session.getLastAccessedTime()===== "+session.getLastAccessedTime());
		
		String uri = request.getRequestURI();
//		System.out.println("CORSFILTER request.getRequestURI() =>"+uri);
//		System.out.println("CORSFILTER request.getAuthType() =>"+request.getAuthType());
//		System.out.println("CORSFILTER request.getContextPath() =>"+request.getContextPath());
//		System.out.println("CORSFILTER request.getPathInfo() =>"+request.getPathInfo());
//		System.out.println("CORSFILTER request.getQueryString() =>"+request.getQueryString());
////		System.out.println("CORSFILTER request.getRemoteAddr() =>"+request.getRemoteAddr());
//		System.out.println("CORSFILTER request.getRemoteHost() =>"+request.getRemoteHost());
//		System.out.println("CORSFILTER request.getRemoteUser() =>"+request.getRemoteUser());
//		System.out.println("CORSFILTER request.getContentType() =>"+request.getContentType());
//		System.out.println("CORSFILTER request.getRequestedSessionId() =>"+request.getRequestedSessionId());
//		System.out.println("CORSFILTER request.getSession() =>"+request.getSession());
//		System.out.println("CORSFILTER request.getRequestURL() =>"+request.getRequestURL());
//		System.out.println("CORSFILTER request.getInputStream() =>"+request.getInputStream());
//		System.out.println("CORSFILTER request.getReader() =>"+request.getReader());
//		System.out.println("CORSFILTER request.getCharacterEncoding() =>"+request.getCharacterEncoding());
//		System.out.println("CORSFILTER request.getServerName() =>"+request.getServerName());
//		System.out.println("CORSFILTER request.getServerPort() =>"+request.getServerPort());
//		System.out.println("CORSFILTER request.getServletPath() =>"+request.getServletPath());
		/*Cookie[] cookies = request.getCookies();
		 request.getCookies()[1].*/
		
//		System.out.println("CORSFILTER request.getServletPath() =>"+request.getServletPath());
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		String loginID = "";
		String teamID = "";
		String eventID = "";
		ActionMapping actionMapping=null;
		if(request.getMethod().equalsIgnoreCase("TRACE")){
//			System.out.println("request.getServletPath() => CORSFilter.java (TRACE Method)==="+request.getServletPath());
			throw new TraceDisabledException(actionMapping,request,response);
		}
		try{
	
	
		try {
			
			response.addHeader("X-Frame-Options", "SAMEORIGIN");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods","POST");
//			response.setHeader("Access-Control-Allow-Methods","GET, HEAD, POST, OPTIONS");
			response.setHeader("Allow","POST");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers","x-requested-with,origin,securityId,Content-Type,Set-Cookie");
			
		//	response.setHeader("Access-Control-Allow-Headers","x-requested-with,origin,securityId,Content-Type");
			
			response.setHeader("Cache-Control","no-store, must-revalidate, max-age=0,no-cache"); //HTTP 1.1
			response.setHeader("Cache-Directive","no-cache"); //HTTP 1.1
			response.setHeader("Pragma","no-cache"); //HTTP 1.0 
			response.setHeader("Pragma-directive","no-cache"); //HTTP 1.1
			response.setDateHeader("Expires", 0); //prevents caching 
			response.setHeader("-1","Expires");
			//As per Security
			response.setHeader("X-XSS-Protection","1; mode=block");
			response.setHeader("X-Content-Type-Options", "nosniff");
			
			String cspHeader = "default-src 'self' mycustproto:; script-src 'self' "+ getBaseUrl(request) +" 'unsafe-inline' 'unsafe-eval'; connect-src 'self'; img-src 'self' data: * javascript: *; style-src 'self' 'unsafe-inline'";
			response.setHeader("Content-Security-Policy", cspHeader);
			response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");

		//	response.setHeader("SET-COOKIE", "JSESSIONID=" + session.getId() +";Path=" +request.getContextPath()+ "; secure ; HttpOnly");

			
			//response.setHeader("SET-COOKIE", "JSESSIONID=" + session.getId() +";Path=/; secure ; HttpOnly");
			
			
//			long requestId = id.incrementAndGet();
			/*requestWrapper = new RequestWrapper(new RequestWrapper((HttpServletRequest)request));
			String newBody = requestWrapper.getBody();
			System.out.println("newBody=== "+newBody);
			
			System.out.println("requestWrapper.getid() === "+requestWrapper.getId());
			
			System.out.println("CORSFILTER requestWrapper.getRequestURI() =>"+uri);
			System.out.println("CORSFILTER requestWrapper.getAuthType() =>"+requestWrapper.getAuthType());
			System.out.println("CORSFILTER requestWrapper.getContextPath() =>"+requestWrapper.getContextPath());
			System.out.println("CORSFILTER requestWrapper.getPathInfo() =>"+requestWrapper.getPathInfo());
			System.out.println("CORSFILTER requestWrapper.getQueryString() =>"+requestWrapper.getQueryString());
			System.out.println("CORSFILTER requestWrapper.getRemoteAddr() =>"+requestWrapper.getRemoteAddr());
			System.out.println("CORSFILTER requestWrapper.getRemoteHost() =>"+requestWrapper.getRemoteHost());
			System.out.println("CORSFILTER requestWrapper.getRemoteUser() =>"+requestWrapper.getRemoteUser());
			System.out.println("CORSFILTER requestWrapper.getContentType() =>"+requestWrapper.getContentType());
			System.out.println("CORSFILTER requestWrapper.getRequestedSessionId() =>"+requestWrapper.getRequestedSessionId());
			System.out.println("CORSFILTER requestWrapper.getSession() =>"+requestWrapper.getSession());
			System.out.println("CORSFILTER requestWrapper.getRequestURL() =>"+requestWrapper.getRequestURL());
//			System.out.println("CORSFILTER requestWrapper.getInputStream() =>"+requestWrapper.getInputStream());
//			System.out.println("CORSFILTER requestWrapper.getReader() =>"+requestWrapper.getReader());
			System.out.println("CORSFILTER requestWrapper.getCharacterEncoding =>"+requestWrapper.getCharacterEncoding());
			System.out.println("CORSFILTER requestWrapper.getServerName() =>"+requestWrapper.getServerName());
			System.out.println("CORSFILTER requestWrapper.getServerPort() =>"+requestWrapper.getServerPort());
			System.out.println("CORSFILTER requestWrapper.getServletPath() =>"+requestWrapper.getServletPath());*/
			
//			System.out.println("response.getOutputStream()=>"+response.getOutputStream());
//			System.out.println("response.getCharacterEncoding=>"+response.getCharacterEncoding());
			/*if (request.getContentType() != null && (request.getContentType().toLowerCase().indexOf("multipart/form-data")) > -1 ) {
				System.out.println("Upload/ Multipart Request from Filter: "+request.getContentType());
			}else{
				String newBody = requestWrapper.getBody();
				System.out.println("newBody=== "+newBody);
				
//				if(newBody.equalsIgnoreCase("")){
//					newBody="{}";
//				}
				if(!"".equalsIgnoreCase(newBody)){
				if(newBody!=null){
					try {
						loopThroughJson(new JSONObject(newBody),response);
						
					} 
					catch (JSONException e) {
						e.printStackTrace();
						if(isCrossSiteScriptPattern(newBody.toString(),null)){
							response.reset();
							response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
							return;
						}
					}
					catch (IOException e) {
						e.printStackTrace();
						response.reset();
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						return;
						
					}
				}
				}
				
				if("".equalsIgnoreCase(newBody)){
					newBody="{}";
				}
				
				requestWrapper.setBody(newBody);	
			}*/
		}
		catch (Exception ex) {
			DefaultLogger.debug("Corsfilter", "ERROR\t" + "USER\t:[\t" + loginID + "\t]\tTEAM\t:[\t" + teamID + "\t]\t" + ex.getMessage());
		}
		
		DefaultLogger.debug("TraceFilter", "USER\t:[\t" + loginID + "\t]\tTEAM\t:[\t" + teamID + "\t]\tSTART\t--> nextPage[\t"
				+ uri + "\t]\tevent\t[\t" + eventID + "\t] (\t0\t ms)");

//		filterChain.doFilter(request, response);
		chain.doFilter(new RequestWrapper((HttpServletRequest)request), response);
		
}catch(Exception e){
	e.printStackTrace();
}
	}
	
	 public FilterConfig getFilterConfig()
	    {
	        return filterConfig;
	    }

	    public void setFilterConfig(FilterConfig cfg)
	    {
	        filterConfig = cfg;
	    }
	    

	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		
		System.out.println("###############################################################################");
		System.out.println("##### [CORSFilter] CREATED");

	}

	
	@Override
	public void destroy() {
		this.filterConfig = null;

	}
	
	
	/*public static void loopThroughJson(Object input,HttpServletResponse response) throws JSONException,IOException {

//		StringBuilder msg = new StringBuilder();
//		StringBuilder actual_msg = new StringBuilder();
//		msg.append(RESPONSE_PREFIX);
//		msg.append("request id=").append((response.getId()));
//		try {
//			msg.append("; payload=").append(
//					new String(response.toByteArray(), response
//							.getCharacterEncoding()));
//			actual_msg.append(new String(response.toByteArray(), response
//							.getCharacterEncoding()));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		System.out.println(actual_msg.toString());
		
//		String log_dummy_msg = WebConfiguration.getProperty("LOG_DUMMY_MSG");
//		String basePath = WebConfiguration.getProperty("DUMMY_JASON_PATH");
//		if("Y".equalsIgnoreCase(log_dummy_msg) && null != basePath){
//			String key = request.getRequestURI().substring((request.getContextPath()+"rest/").length()+1);
//			System.out.println(key +"="+key.replaceAll("/", "_").toString()+".json");
//			File f = new File(basePath+key.replaceAll("/", "_")+".json");
//			if(f.exists()){
//				f.delete();
//			}
//			try {
//				
//				f.createNewFile();
//				BufferedWriter writer = new BufferedWriter(new FileWriter(f));
//				writer.append(actual_msg.toString());
//				writer.flush();
//				writer.close();
//				Properties properties = new Properties();
//				
//				FileOutputStream fileOut = new FileOutputStream(basePath+"Json.properties",true);
//				
//				properties.setProperty(key, key.replaceAll("/", "_").toString()+".json");
//				properties.store(fileOut, null);
//				fileOut.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		
			 
		 if(!(input instanceof JSONObject) && !(input instanceof JSONArray) ){
			 if(isCrossSiteScriptPattern( ""+input,null)){
					//throw new IOException(RestConstants.MSGKEY_SECURITY_BREACH);
					throw new IOException("Invalid character in url");
				}
		 }
	        if (input instanceof JSONObject) {
	            Iterator<?> keys = ((JSONObject) input).keys();
	            while (keys.hasNext()) {
	                String key = (String) keys.next();
	                if (((JSONObject) input).get(key) instanceof JSONObject){
	                	 loopThroughJson((new JSONObject(((JSONObject) input).get(key).toString())),response);
	                }
	                else if (!(((JSONObject) input).get(key) instanceof JSONArray)){
	                    System.out.println(key + "=" + ((JSONObject) input).get(key));
	                    if(isCrossSiteScriptPattern( ""+((JSONObject) input).get(key),key)){
//							throw new IOException(RestConstants.MSGKEY_SECURITY_BREACH);
	                    	throw new IOException("Invalid character in url");
						}
	                    
	                }else{
	                    loopThroughJson((new JSONArray(((JSONObject) input).get(key).toString())),response);
	                }
	            }
	        }
	        if (input instanceof JSONArray) {
	            for (int i = 0; i < ((JSONArray) input).length(); i++) {
	            	 if (((JSONArray) input).get(i) instanceof JSONObject){
	            		JSONObject a = ((JSONArray) input).getJSONObject(i);
	                	loopThroughJson(a,response);
	            	 }else{
	            		 System.out.println(((JSONArray) input).get(i));
	            		 if(isCrossSiteScriptPattern( ""+((JSONArray) input).get(i),null)){
//								throw new IOException(RestConstants.MSGKEY_SECURITY_BREACH);
	            			 throw new IOException("Invalid character in url");
							}
	            	 }
	            }
	        }

	    }
	
	public static boolean isCrossSiteScriptPattern(String input,String key){
		String propertyTokenArray [] = null;
		String scriptArray[] = null;
		
		String sqlinjectionArray[] = null;
		
		if(crossSiteScriptXssToken != null && crossSiteScriptXssToken.length() > 0){
			propertyTokenArray = crossSiteScriptXssToken.split(",");
		}
		
		if(sqlInjectionsTokens!=null && sqlInjectionsTokens.length() > 0) {
			sqlinjectionArray=sqlInjectionsTokens.split(",");
		}
		
		scriptArray  = propertyTokenArray;
		if(scriptArray == null || scriptArray.length == 0)
			scriptArray = defaultCorrsSiteScriptXssToken;
		
		if(sqlinjectionArray == null || sqlinjectionArray.length == 0)
			sqlinjectionArray = defaultSqlInjectionsTokens;
		
		
		boolean quotChk = false; 
		if(input != null)
		for (int i = 0; i < scriptArray.length; i++) {
			
			
			
			quotChk = input.toUpperCase().contains(scriptArray[i].toUpperCase());
			
			if(quotChk && Arrays.asList(sqlinjectionArray).contains(scriptArray[i])) {
				
				quotChk = (input.toUpperCase().contains(scriptArray[i].toUpperCase()+" ") || input.toUpperCase().contains(" "+scriptArray[i].toUpperCase()));
				
				if(scriptArray[i].toUpperCase().equals("SELECT") && !quotChk)
					quotChk = input.toUpperCase().contains("SELECT*");
				
				if(quotChk) {
					
					quotChk=testSqlInjectionKeyWords(input,scriptArray[i]);
				}
			}
			
			if(quotChk){
				if( key!=null && key.equalsIgnoreCase("LASTACTION") || key.equalsIgnoreCase("printFor") 
				 || key.equalsIgnoreCase("widgetOrder") || key.equalsIgnoreCase("CRITERIA") || key.equalsIgnoreCase("imageData")|| (key.equalsIgnoreCase("email")&&   ";".equals(scriptArray[i].toUpperCase()))){

					quotChk = false; 
				}else{
					break;
				}
			}
		}
		return quotChk;
	}
	
private static boolean testSqlInjectionKeyWords(String input,String matchedKeyword) {
		
		boolean isBadRequest=true;
		Pattern pattern = Pattern.compile(matchedKeyword, Pattern.CASE_INSENSITIVE);

	    Matcher matcher = pattern.matcher(input);
        while(matcher.find()) {
            int start=matcher.start();
            
            Pattern pattern1 = Pattern.compile(matchedKeyword+" Authorized|"+matchedKeyword+" Rejected", Pattern.CASE_INSENSITIVE);
            String inputCheck=input.substring(start);
            Matcher matcher1 = pattern1.matcher(inputCheck);
            if(matcher1.lookingAt()) {
            	isBadRequest=false;
            }
            
        }
		return isBadRequest;
		
	}*/
	private String getBaseUrl(HttpServletRequest request) {

        String scheme = request.getScheme() + "://";

        String serverName = request.getServerName();

        String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();

        String contextPath = request.getContextPath();

        return scheme + serverName + serverPort + contextPath;

    }
}