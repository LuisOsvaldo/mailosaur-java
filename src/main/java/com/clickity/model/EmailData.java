package com.clickity.model;

import com.google.api.client.util.Key;

public class EmailData {
	@Key("links")
	public Link[] links;
	@Key
	public String body;
}
