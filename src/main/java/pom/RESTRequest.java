package pom;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

public class RESTRequest {
	
	private Builder request = null;
	private String apiUrl = "";
	public String endpoint = "https://parabank.parasoft.com/parabank/";
	public Properties prop;
	
	public RESTRequest() {
	}
	
	public RESTRequest(String apiPath) {
		this.apiUrl = this.endpoint + apiPath;
	}
	
	private void initBuilder() {
		if(this.apiUrl == "") {
			throw new RuntimeException("Service url is empty");
		}
		Client client = JerseyClientBuilder.newBuilder().build();
		client.register(new LoggingFeature(Logger.getLogger(RESTRequest.class.getName()), Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY, 2048));
		client.property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE);
		client.register(JacksonFeature.class);
		client.register(MultiPartFeature.class);

		WebTarget resource = client.target(apiUrl);
		this.request = resource.request();
		this.request.accept(MediaType.APPLICATION_JSON);
	}
	
	public void configHeader(String key, String value) {
		this.request.header(key, value);
	}

	public String getServiceUrl() {
		return apiUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.apiUrl = serviceUrl;
	}
	
	public Response get() {
		initBuilder();
		return this.request.get();
	}
	
	public Response put(Entity<?> entity) {
		initBuilder();
		return this.request.put(entity);		
	}
	
	public Response post(Entity<?> entity) {
		initBuilder();
		return this.request.post(entity);		
	}
}
