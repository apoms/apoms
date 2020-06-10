package io.aetherit.ats.ws.util;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import io.aetherit.ats.ws.model.ATSSimpleUser;
import io.aetherit.ats.ws.model.type.ATSUserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenUtil {

	private static final String API_KEY =  "ats-app";
	private static final String SECRET_KEY =  "4E18AC8AC1F2A09BA749E72B7AD29EC967A572899E332162AECB49C2D4E18AC8AC1F2A09BA74AC8AC1F2A09BA749E72B7AD29EC967A572899E332162AECQ";
	private static final int EXPIRATION_SECONDS = 1000 * 60 * 60 * 48;			// 48시간

	   /**
	    * Encode JWT.
	    *
	    * @param user the user
	    * @return the string
	    */
	   public static String getJwtToken(ATSSimpleUser simpleUser){
	      byte[] apiKeySecretBytes = SECRET_KEY.getBytes();
	      SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	      Key secretKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	      
	      String userId = simpleUser.getUserId()+"";
	      String userName = simpleUser.getNickName();
	      String phoneNo = simpleUser.getPhoneNo();
	      String statusCode = "LOGIN";
	      
	      if(simpleUser.getType()==ATSUserType.ANOYMOUS) {
	    	  userId = UUID.randomUUID().toString();
	    	  userName = simpleUser.getType().name();
	    	  statusCode = "LOGOUT";
	      }

	      Map<String, Object> headerMap = new HashMap<String, Object>();
	      headerMap.put("typ","JWT");
	      headerMap.put("alg","HS256");

	      Map<String, Object> map= new HashMap<String, Object>();
	      map.put("apiKey", API_KEY);
		  map.put("userName", userName);	
		  map.put("userId", userId);
		  map.put("phoneNo", phoneNo);
		  map.put("statusCode", statusCode); // LOGOUT, LOGIN
		  map.put("clientIp", "");
	      

	      Date expireTime = new Date();
	      expireTime.setTime(expireTime.getTime() + EXPIRATION_SECONDS);
	      
	      String jwt = Jwts.builder()
	            .setHeader(headerMap)
	            .setClaims(map)
	            .setExpiration(expireTime)
	            .signWith(secretKey, signatureAlgorithm)
	            .compact();
	      
	      return jwt;
	      
	      
	   }
    
    private byte[] generateKey(){
		byte[] key = null;
		try {
			key = SECRET_KEY.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if(log.isInfoEnabled()){
				e.printStackTrace();
			}else{
				log.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}
		return key;
	}
    
    
	public boolean checkJwtKey(String jwt) throws Exception{
		try {
			JwtParser parser = Jwts.parserBuilder().setSigningKey(this.generateKey()).build();
			Claims claims = parser.parseClaimsJws(jwt).getBody();
	
	        log.info("Expiration :" + claims.getExpiration());
	        log.info("apiKey :" + claims.get("apiKey"));
	        log.info("userName :" + claims.get("userName"));
	        log.info("userId :" + claims.get("userId"));
	        log.info("phoneNo :" + claims.get("phoneNo"));
	        log.info("statusCode :" + claims.get("statusCode"));
	        
	        return true;
	    } catch (ExpiredJwtException exception) {
	    	log.info("Token Expired!");
	        return false;
	    } catch (JwtException exception) {
	    	log.info("Token auth failed!");
	        return false;
	    }
	}
    
}
