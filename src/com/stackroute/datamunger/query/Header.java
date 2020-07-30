package com.stackroute.datamunger.query;

//header class
public class Header {

	private String[] headers;
	/*
	 * this class should contain a member variable which is a String array, to hold
	 * the headers and should override toString() method.
	 */
	public String[] getHeaders() {

		return this.headers;
	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
