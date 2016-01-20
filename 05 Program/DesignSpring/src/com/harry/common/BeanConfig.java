package com.harry.common;

import java.util.ArrayList;
import java.util.List;

public class BeanConfig {
	private String id;
	private String className;
	private boolean isSingleton;
	private List<BeanProperty> beanProperties = new ArrayList<BeanProperty>();

	public BeanConfig(String id, String className, boolean isSingleton) {
		super();
		this.id = id;
		this.className = className;
		this.isSingleton = isSingleton;
	}

	public String getId() {
		return id;
	}

	public String getClassName() {
		return className;
	}

	public boolean isSingleton() {
		return isSingleton;
	}

	public void addBeanProperty(BeanProperty beanProperty) {
		this.beanProperties.add(beanProperty);
	}

	public List<BeanProperty> getBeanProperty() {
		return beanProperties;
	}
}
