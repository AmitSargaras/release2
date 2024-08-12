package com.integrosys.base.uiinfra.common;

import java.io.*;
import javax.servlet.*;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class EncodingFilter implements Filter
{
    private FilterConfig filterConfig;
    public static String ENCODING = "UTF-8";
	public static boolean IS_DEBUG;
	public static boolean IS_ACTIVE;

    public EncodingFilter()
    {
        filterConfig = null;
    }

    public void init(FilterConfig filterConfig)
        throws ServletException
    {
		String temp;
	
		System.out.println("###############################################################################");
		System.out.println("##### [EncodingFilter] CREATED");
	
        this.filterConfig = filterConfig;
        EncodingFilter _tmp = this;
        
		ENCODING = this.filterConfig.getInitParameter("ENCODING");
		if (ENCODING == null)
			ENCODING = "UTF-8";
		
		System.out.println("##### [EncodingFilter] ENCODING: " + ENCODING);
		
		temp = this.filterConfig.getInitParameter("DEBUG_MODE");
		if (temp != null && temp.equals("true"))
			IS_DEBUG = true;
		else
			IS_DEBUG = false;
		
		System.out.println("##### [EncodingFilter] DEBUG_MODE: " + IS_DEBUG);
		
		temp = this.filterConfig.getInitParameter("ACTIVE");
		if (temp != null && temp.equals("true"))
			IS_ACTIVE = true;
		else
			IS_ACTIVE = false;

		System.out.println("##### [EncodingFilter] ACTIVE: " + IS_ACTIVE);
    }

    public void destroy()
    {
        filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        try
        {
			if (IS_ACTIVE)
			{
				request.setCharacterEncoding(ENCODING);
				//response.setContentType("text/html; charset=" + ENCODING); 
				//response.setCharacterEncoding(ENCODING);
	
				EncodingFilter _tmp = this;            
				//ENCODING = filterConfig.getInitParameter("ENCODING");

				if (IS_DEBUG)
				{
					//DefaultLogger.debug(this, "[EncodingFilter]--------------- Encoding scheme is " + ENCODING + " --------------");
					System.out.println("##### [EncodingFilter] ----- Encoding scheme is " + ENCODING + " -----");
				}
			}
        }
        catch (UnsupportedEncodingException usee) { 
			//
		}

		chain.doFilter(request, response);

    }

    public FilterConfig getFilterConfig()
    {
        return filterConfig;
    }

    public void setFilterConfig(FilterConfig cfg)
    {
        filterConfig = cfg;
    }

}

