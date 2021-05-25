package org.homi.plugin.api;

import java.io.Serializable;

public class Custom implements Serializable {

	private static final long serialVersionUID = -3452650400370022336L;
	
	public String s;
	private int o;
	
	public Custom(String s, int o) {
		this.s = s;
		this.o = o;
	}
	
	@Override
	public String toString() {
		return  s + " " + o;
		
	}
	
}
