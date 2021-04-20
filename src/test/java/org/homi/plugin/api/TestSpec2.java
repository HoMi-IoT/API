package org.homi.plugin.api;

import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationID;


@SpecificationID(id = "TestSpec")
public enum TestSpec2 implements ISpecification{

	RETURN_NULL(Void.class),
	RETURN_FLOAT(Integer.class),
	C1(Void.class);
	

	private Class<?>[] parameterTypes;
	private Class<?> returnType;
	TestSpec2(Class<?> returnType, Class<?> ...parameterTypes ) {
		this.parameterTypes = parameterTypes;
		this.returnType = returnType;
	}

	@Override
	public Class<?>[] getParameterTypes() {
		return this.parameterTypes;
	}
	
	@Override
	public Class<?> getReturnType() {
		return this.returnType;
	}
	
}