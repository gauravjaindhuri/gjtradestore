package com.demo.dao.model;

public class CounterParty {

	String id;
	String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CounterParty(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
