package org.homi.plugin.api;

import org.homi.plugin.specification.ISpecification;
import  static org.homi.plugin.specification.Constraints.*;

import java.util.List;

import org.homi.plugin.specification.SpecificationHelper;
import org.homi.plugin.specification.SpecificationID;
import org.homi.plugin.specification.TypeDef;

@SpecificationID(id = "TestSpec")
public enum TestSpec implements ISpecification{

	RETURN_NULL(Void.class),
	RETURN_STRING(String.class),
	RETURN_INTEGER(Integer.class),
	RETURN_FLOAT(Float.class),
	RETURN_OBJECT(Object.class),
	RETURN_WRONG_TYPE(Float.class),
	SEND_STRING(String.class, String.class),
	SEND_CONSTRAINED_STRING(String.class, new TypeDef<>(String.class, notNull(), contains("18"))),
	SEND_CONSTRAINED_Integer(Integer.class, new TypeDef<>(Integer.class, notNull(), isEqualTo(14))),
	SEND_INTEGER(Void.class, Integer.class),
	SEND_CUSTOM(Void.class, Custom.class);
	
	private List<TypeDef<?>> parameterTypes;
	private TypeDef<?> returnType;
	TestSpec(Object returnType, Object...parameterTypes ) {
		try {
			this.returnType = SpecificationHelper.processType(returnType);
			this.parameterTypes = SpecificationHelper.processTypes(parameterTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<TypeDef<?>> getParameterTypes() {
		return this.parameterTypes;
	}
	
	@Override
	public TypeDef<?> getReturnType() {
		return this.returnType;
	}
	
}