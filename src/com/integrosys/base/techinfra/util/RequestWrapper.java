/*
 * 
 */
package com.integrosys.base.techinfra.util;

import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
// TODO: Auto-generated Javadoc
/**
 * The Class RequestWrapper.
 */
public class RequestWrapper extends HttpServletRequestWrapper {
	private static Logger logger = LoggerFactory.getLogger(RequestWrapper.class);
	//private static final String whiteList = "[^A-Za-z0-9\\s\\@\\,\\.\\:\\|\\(\\)\\/\\_\\-\\&\\!\\#\\$\\%\\^\\*\\?\\~\\=\\+\\<\\>]";
    /** The bos. */
    //private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
//	private  ByteArrayOutputStream bos = new ByteArrayOutputStream();
    
	
	 // check for <SCRIPT>, <OBJECT>, <APPLET>, <EMBED>, <FORM>, <STYLE>
    private static final String VALID_XSS_RE_PATTERN
            = ".*</?(\\s*[Ss][Cc][Rr][Ii][Pp][Tt]\\s*|"
            + "\\s*[Oo][Bb][Jj][Ee][Cc][Tt]\\s*|"
            + "\\s*[Aa][Pp][Pp][Ll][Ee][Tt]\\s*|"
            + "\\s*[Ee][Mm][Bb][Ee][Dd]\\s*|"
            + "\\s*[Ff][Oo][Rr][Mm]\\s*|"
            + "\\s*[Ss][Tt][Yy][Ll][Ee]\\s*)>.*";

    // check for "javascript:", "vbscript:", "livescript:", "href=", "src=", "style=", "http-equiv=" , "onload="
    private static final String VALID_XSS_RE_PATTERN2
            = "[Jj][Aa][Vv][Aa][Ss][Cc][Rr][Ii][Pp][Tt]\\s*:|"
            + "[Vv][Bb][Ss][Cc][Rr][Ii][Pp][Tt]\\s*:|"
            + "[Ll][Ii][Vv][Ee][Ss][Cc][Rr][Ii][Pp][Tt]\\s*:|"
            + "[Hh][Rr][Ee][Ff]\\s*=|"
            + "[Ss][Rr][Cc]\\s*=|"
            + "[Ss][Tt][Yy][Ll][Ee]\\s*=|"
            + "[Hh][Tt][Tt][Pp]\\s*-\\s*[Ee][Qq][Uu][Ii][Vv]\\s*="
            + "[Oo][Nn][Ll][Oo][Aa][Dd]\\s*=|";

    private static Pattern re1 = null;
    private static Pattern re2 = null;
	
	
    /** The id. */
    private long id;

    
    
    public static void init() {
        try {
            re1 = Pattern.compile(VALID_XSS_RE_PATTERN);
            re2 = Pattern.compile(VALID_XSS_RE_PATTERN2);
        }
        catch (PatternSyntaxException e) {
            // should never reach here, syntax of the RE must be correct
            System.out.println("Exception: " + e);
        }
        if ( re1 == null ) {
            DefaultLogger.debug(RequestWrapper.class, "Regular expression could not be constructed: "+ VALID_XSS_RE_PATTERN );
            throw new RuntimeException();
        }
        if( re2 == null ){
            DefaultLogger.debug(RequestWrapper.class, "Regular expression could not be constructed: "+ VALID_XSS_RE_PATTERN2 );
            throw new RuntimeException();
        }
    }

    /**
     * Instantiates a new request wrapper.
     *
     * @param requestId the request id
     * @param request the request
     */
    public RequestWrapper(Long requestId, HttpServletRequest request) {
        super(request);
        this.id = requestId;
       /* try {
            IOUtils.copy(request.getInputStream(), bos);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
    
    public RequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
        init();
    }
 
    /*
     (non-Javadoc)
     * @see javax.servlet.ServletRequestWrapper#getInputStream()
     
//    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(bos.toByteArray());
        return new ServletInputStream() {
           // private TeeInputStream tee = new TeeInputStream(RequestWrapper.super.getInputStream(), bos,true);

            @Override
            public int read() throws IOException {
                return tee.read();
            }

			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}
            
            @Override
            public int read() throws IOException {
                return inputStream.read();
            }
            
            @Override
            public int readLine(byte[] b, int off, int len) throws IOException {
                return inputStream.read(b, off, len);
            }

            public boolean isFinished() {
                return inputStream.available() > 0;
            }

            public boolean isReady() {
                return true;
            }

//			public void setReadListener(ReadListener arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			
			
		
        };
        
        
    }
    
    public void setBody(String body) {
	    bos = new ByteArrayOutputStream();
	    try {
	    	//Added by Rupesh
	        bos.write(body.getBytes("UTF-8"));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public String getBody() throws UnsupportedEncodingException {
		//Added by Rupesh
		//Forcefully parsing request body to UTF-8 format for supporting Thai character
	    return new String(bos.toByteArray(), "UTF-8");
	}
	
    
    
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bos.toByteArray())));
    }

    *//**
     * To byte array.
     *
     * @return the byte[]
     *//*
    public byte[] toByteArray(){
    	
        return bos.toByteArray();
    }

    *//**
     * Gets the id.
     *
     * @return the id
     *//*
    public long getId() {
        return id;
    }

    *//**
     * Sets the id.
     *
     * @param id the new id
     *//*
    public void setId(long id) {
        this.id = id;
    }
    
    
    
    
    Kamlesh Added For Cross site script start
    @Override
   public String[] getParameterValues(String parameter) {
//		logger.info("InarameterValues .. parameter .......");
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = cleanXSS(values[i]);
			encodedValues[i]=allowWhiteList(encodedValues[i]);
		}
		return encodedValues;
	}
   @Override
	public String getParameter(String parameter) {
//		logger.info("Inarameter .. parameter .......");
		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		}
//		logger.info("Inarameter RequestWrapper ........ value .......");
		value= cleanXSS(value);
		value=allowWhiteList(value);
		return value;
	}
   @Override
	public String getHeader(String name) {
//		logger.info("Ineader .. parameter .......");
		String value = super.getHeader(name);
		if (value == null)
			return null;
//		logger.info("Ineader RequestWrapper ........... value ....");
		value= cleanXSS(value);
		value=allowWhiteList(value);
		return value;
	}

	private String cleanXSS(String value) {
		// You'll need to remove the spaces from the html entities below
//		logger.info("InnXSS RequestWrapper ..............." + value);
//		value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
//		value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
//		value = value.replaceAll("'", "& #39;");
//		
//		
//		
//		value = value.replaceAll("eval\\((.*)\\)", "");
//		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
//
//		value = value.replaceAll("(?i)<script.*?>.*?<script.*?>", "");
//		value = value.replaceAll("(?i)<script.*?>.*?</script.*?>", "");
//		value = value.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "");
//		value = value.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");
//		
//		value = value.replaceAll("<script>", "");
//		value = value.replaceAll("</script>", "");
//		
//		value = value.replaceAll("\\/", "& #47;");
		
		
		Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");

        // Avoid anything in a src='...' type of expression
        scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");

        scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");

        // Remove any lonesome </script> tag
        scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");

        // Remove any lonesome <script ...> tag
        scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");

        // Avoid eval(...) expressions
        scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");

        // Avoid expression(...) expressions
        scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");

        // Avoid javascript:... expressions
        scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");

        // Avoid vbscript:... expressions
        scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
        value = scriptPattern.matcher(value).replaceAll("");

        // Avoid onload= expressions
        scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        value = scriptPattern.matcher(value).replaceAll("");
        
        value = value.replaceAll("<script>", "");
		value = value.replaceAll("</script>", "");
		//value = value.replaceAll("script", "");
		value = value.replaceAll("/script", "");
		value = value.replaceAll("alert", "");
		value = value.replaceAll("%3c", "");
		value = value.replaceAll("%3C", "");
		value = value.replaceAll("%3e", "");
		value = value.replaceAll("%3E", "");
		value = value.replaceAll(";", "");
		value = value.replaceAll("\\(", "");
		value = value.replaceAll("\\)", "");
		value = value.replaceAll("'", "");
//		value = value.replaceAll("\\/", "-");
		value = value.replaceAll("&", "");
		
//		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");

		value = value.replaceAll("(?i)<script.*?>.*?<script.*?>", "");
		value = value.replaceAll("(?i)<script.*?>.*?</script.*?>", "");
		value = value.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "");
		value = value.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");
		
//		logger.info("OutnXSS RequestWrapper ........ value ......." + value);  
		return value;
	}
	*/
    
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values==null)  {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXSS(values[i], "param");
        }
        return encodedValues;
    }

    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        return cleanXSS(value, "param");
    }

    public String getHeader(String name) {
        String value = super.getHeader(name);
        return cleanXSS(value, "header");
    }

    private String cleanXSS(String value, String messageKey) {
        if (value == null || "".equals(value)) {
            return value;
        }
        if (re1 != null && re2 != null) {
            if (re1.matcher(value).matches() || re2.matcher(value).matches()) {
                value = value.replaceAll("<", " ").replaceAll(">", " ");
            }
        }
        ResourceBundle bundle1 = ResourceBundle.getBundle("ofa_env");
		String whiteList = bundle1.getString("security.whitelist");
		value=value.replaceAll(whiteList, "");  
//		logger.info("completed allowWhiteList:" + value);  
        return value;
    }
	
/*	private String allowWhiteList(String value) {
		// You'll need to remove the spaces from the html entities below
//		logger.info("inside allowWhiteList:" + value);
		ResourceBundle bundle1 = ResourceBundle.getBundle("ofa_env");
		String whiteList = bundle1.getString("security.whitelist");
//		logger.info("whiteList:" + whiteList);
		Pattern pattern=Pattern.compile(whiteList);
		
		Matcher m = pattern.matcher(value);
		if(!m.find()){
			value=value.replaceAll(whiteList, "");  
		}
		
		logger.info("completed allowWhiteList:" + value);  
		return value;
	}*/
	
	/*Kamlesh Added For Cross site script END*/
}