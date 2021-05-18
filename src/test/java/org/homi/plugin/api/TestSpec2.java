package org.homi.plugin.api;

import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.ParameterType;
import org.homi.plugin.specification.SpecificationID;


@SpecificationID(id = "TestSpec")
public enum TestSpec2 implements ISpecification{

	RETURN_NULL(new ParameterType<>(Void.class)),
	RETURN_FLOAT(new ParameterType<>(Integer.class)),
	C1(new ParameterType<>(Void.class));
	

	private ParameterType<?>[] parameterTypes;
	private ParameterType<?> returnType;
	TestSpec2(ParameterType<?> returnType, ParameterType<?> ...parameterTypes ) {
		this.parameterTypes = parameterTypes;
		this.returnType = returnType;
	}

	@Override
	public ParameterType<?>[] getParameterTypes() {
		return this.parameterTypes;
	}
	
	@Override
	public ParameterType<?> getReturnType() {
		return this.returnType;
	}
	
}