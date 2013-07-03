package com.clickity;

import java.io.IOException;
import java.io.OutputStream;

import com.clickity.model.*;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpParser;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;

public final class Clickity {
	
	 static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	 static final JsonFactory JSON_FACTORY = new JacksonFactory();
//	 static final JsonFactory JSON_FACTORY = new GsonFactory();
	 	 
	 static final HttpRequestFactory requestFactory =
		        HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
			          public void initialize(HttpRequest request) {
			        	request.addParser(new JsonHttpParser(JSON_FACTORY));
			          }
			        });

	final String BASE_URI = "https://api.clickity.io/v2";
	final String API_KEY;
	
	public Clickity(String apiKey) {
		API_KEY = apiKey;
	}
	
	public Email[] GetEmails(String mailboxId, EmailSearchCriteria criteria) throws IOException {
		GenericUrl url = new GenericUrl(BASE_URI + "/emails?key=" + API_KEY + "&mailbox=" + mailboxId);
		HttpRequest request = requestFactory.buildGetRequest(url);
		return request.execute().parseAs(Email[].class);
	}
	
	public Email GetEmail(String emailId) throws IOException {
		GenericUrl url = new GenericUrl(BASE_URI + "/email/" + emailId + "?key=" + API_KEY);
		HttpRequest request = requestFactory.buildGetRequest(url);
		return request.execute().parseAs(Email.class);
	}
	
	public Boolean DeleteAllEmail(String mailboxId) throws IOException {
		GenericUrl url = new GenericUrl(BASE_URI + "/emails?key=" + API_KEY + "&mailbox=" + mailboxId);
		HttpRequest request = requestFactory.buildPostRequest(url, null);
		String result = request.execute().parseAsString();
		return true;
	}
	
	public Boolean DeleteEmail(String emailId) throws IOException {
		GenericUrl url = new GenericUrl(BASE_URI + "/email/" + emailId + "?key=" + API_KEY);
		HttpRequest request = requestFactory.buildPostRequest(url, null);
		String result = request.execute().parseAsString();
		return true;
	}
	
	public OutputStream GetAttachment(String attachmentId) throws IOException {
		GenericUrl url = new GenericUrl(BASE_URI + "/attachment/" + attachmentId + "?key=" + API_KEY);
		HttpRequest request = requestFactory.buildGetRequest(url);
		OutputStream stream = null;
		request.execute().download(stream);
		return stream;
	}
	
	public String GetRawEmail(String rawId) throws IOException {
		GenericUrl url = new GenericUrl(BASE_URI + "/raw/" + rawId + "?key=" + API_KEY);
		HttpRequest request = requestFactory.buildGetRequest(url);
		return request.execute().parseAsString();
	}
}
