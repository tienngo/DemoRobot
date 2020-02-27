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
		client.register(new LoggingFeature(LogManager.getLogManager().getLogger(RESTRequest.class.getName()), Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY,null));
		client.property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE);
		client.register(JacksonFeature.class);
		client.register(MultiPartFeature.class);
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
		logRequest(this.request);
		Response response = this.request.get();
		Response returnResponse = response;
//		System.err.print("\n" + response.readEntity(String.class) + "\n");
		return returnResponse;
	}
	
	public Response put(Entity<?> entity) {
		logRequest(this.request);
		Response response = this.request.put(entity);
		Response returnResponse = response;
//		System.err.print("\n" + response.readEntity(String.class) + "\n");
		return returnResponse;		
	}
	
	public Response post(Entity<?> entity) {
		logRequest(this.request);
		Response response = this.request.post(entity);
		Response returnResponse = response;
//		System.err.print("\n" + returnResponse.readEntity(String.class) + "\n");
		return response;		
	}
	
	private void logRequest(Builder request) {
//		System.err.print("test");
//		try {
//			System.err.print(new ObjectMapper().writeValueAsString());
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//
//			System.err.print(e.getMessage());
//		}
	}
}
