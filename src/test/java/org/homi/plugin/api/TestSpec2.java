package org.homi.plugin.api;


import org.homi.plugin.specification.ISpecification;
import org.homi.plugin.specification.SpecificationHelper;
import org.homi.plugin.specification.SpecificationID;
import org.homi.plugin.specification.TypeDef;


@SpecificationID(id = "TestSpec")
public enum TestSpec2 implements ISpecification{

	RETURN_NULL(Void.class),
	RETURN_FLOAT(Custom.class),
	C1(Void.class);
	



	private TypeDef<?>[] parameterTypes;
	private TypeDef<?> returnType;
	TestSpec2(Object returnType, Object...parameterTypes ) {
		try {
			this.returnType = SpecificationHelper.processType(returnType);
			this.parameterTypes = new TypeDef<?>[parameterTypes.length];
			for(int i =0; i<parameterTypes.length; i++) {
				this.parameterTypes[i] = SpecificationHelper.processType(parameterTypes[i]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public TypeDef<?>[] getParameterTypes() {
		return this.parameterTypes;
	}
	
	@Override
	public TypeDef<?> getReturnType() {
		return this.returnType;
	}
	
	
}