package pom;

import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RobotKeywords
public class RESTRequest {
	
	private Builder request = null;
	private String apiUrl = "";
	public String endpoint = "https://parabank.parasoft.com/";
	public Properties prop;
	
	private Client client;
	
	public RESTRequest() {
		initBuilder();
	}
	
	public RESTRequest(String apiPath) {
		initBuilder();
		this.apiUrl = this.endpoint + apiPath;
		buildingRequest();
	}
	
	private void initBuilder() {
		client = JerseyClientBuilder.newBuilder().build();
		client.property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE);
	}
	
	public void buildingRequest() {
		if(this.apiUrl == "") {
			throw new RuntimeException("Service url is empty");
		}
		WebTarget resource = client.target(apiUrl);
		this.request = resource.request();		
	}
	
	public void setCookie(Cookie cookie) {
		this.request.cookie(cookie);
	}
	
	public void configHeader(String key, String value) {
		this.request.header(key, value);
	}

	public String getServiceUrl() {
		return apiUrl;
	}

	public void setServiceUrl(String apiPath) {
		this.apiUrl = this.endpoint + apiPath;
		buildingRequest();
	}
	
	public Response get() {
		logMethod("GET");
		Response response = this.request.get();
		return response;
	}
	
	public Response put(Entity<?> entity) {
		logMethod("PUT");
		Response response = this.request.put(entity);
		return response;		
	}
	
	public Response post(Entity<?> entity) {
		logMethod("POST");
		Response response = this.request.post(entity);
		return response;		
	}
	
	private void logMethod(String method) {
		System.err.print(method.toUpperCase() + " " + this.apiUrl + "\n");
	}
	
	public void logRequest(String params) {
		if(params != null) {
			System.err.print("request: " + params + "\n");
		}
	}
	
	public void logResponse(String params) {
		if(params != null) {
			System.err.print("response: " + params + "\n");
		}
	}
	
	public String logObjectAsString(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}
}
