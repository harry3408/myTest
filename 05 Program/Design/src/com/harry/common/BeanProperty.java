package com.harry.common;

public class BeanProperty {
	private String name;
	private String ref;

	public BeanProperty(String name, String ref) {
		this.name = name;
		this.ref = ref;
	}

	public String getName() {
		return name;
	}

	public String getRef() {
		return ref;
	}
}
