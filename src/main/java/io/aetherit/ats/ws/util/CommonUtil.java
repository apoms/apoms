package io.aetherit.ats.ws.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


public class CommonUtil {

	static String charset = "utf-8";
	/**
	 * local PC
	 */
//	static String host = "http://192.168.0.119:3001";
	/**
	 * develope Server
	 */
	static String host = "http://192.168.0.87:8081";
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	

	/**
	 * DATE FORMAT Validation
	 * @param format
	 * @param value
	 * @param locale
	 * @return
	 */
	public boolean isValidFormat(String format, String value, Locale locale) {
	    LocalDateTime ldt = null;
	    DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format, locale);

	    try {
	        ldt = LocalDateTime.parse(value, fomatter);
	        String result = ldt.format(fomatter);
	        return result.equals(value);
	    } catch (DateTimeParseException e) {
	        try {
	            LocalDate ld = LocalDate.parse(value, fomatter);
	            String result = ld.format(fomatter);
	            return result.equals(value);
	        } catch (DateTimeParseException exp) {
	            try {
	                LocalTime lt = LocalTime.parse(value, fomatter);
	                String result = lt.format(fomatter);
	                return result.equals(value);
	            } catch (DateTimeParseException e2) {
	                // Debugging purposes
	                //e2.printStackTrace();
	            }
	        }
	    }

	    return false;
	}
	
	
    /**
     * MAP to Object 변환 Util Method
     * @param map
     * @param objClass
     * @return
     */
    public static Object convertMapToObject(Map<?, ?> map, Object objClass){ 
    	String keyAttribute = null; 
    	String setMethodString = "set"; 
    	String methodString = null; 
    	Iterator<?> itr = map.keySet().iterator(); 
    	while(itr.hasNext()){ 
    		keyAttribute = (String) itr.next(); 
    		methodString = setMethodString+keyAttribute.substring(0,1).toUpperCase()+keyAttribute.substring(1); 
    		try { 
    			Method[] methods = objClass.getClass().getDeclaredMethods(); 
    			for(int i=0;i<=methods.length-1;i++){ 
    				if(methodString.equals(methods[i].getName())){ 
//    					System.out.println("invoke : "+methodString); 
    					methods[i].invoke(objClass, map.get(keyAttribute)); 
    				} 
    			} 
    		} catch (SecurityException e) { 
    			e.printStackTrace();
    		} catch (IllegalAccessException e) { 
    			e.printStackTrace(); 
    		} catch (IllegalArgumentException e) { 
    			e.printStackTrace(); 
    		} catch (InvocationTargetException e) { 
    			e.printStackTrace(); 
    		} 
    	} return objClass; 
    }

    
    public String getRoundDouble(double count) {
    	return Math.round(count) + "";
    }
    
    /**
     * Transaction ID 생성 Util Method
     * @param preFix
     * @return
     */
    public String getTransactionId(char preFix) {
    	String trxId = preFix + "-" + UUID.randomUUID().toString();
    	
    	return trxId.toUpperCase();
    }
	
}
