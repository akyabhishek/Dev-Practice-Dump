package com.bharath.spring.springcore.properties;

import java.util.Properties;

public class Languages {

	private Properties language;

	public Properties getLanguage() {
		return language;
	}

	public void setLanguage(Properties language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return String.format("Languages [language=%s]", language);
	}
}
