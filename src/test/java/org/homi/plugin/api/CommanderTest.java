package org.homi.plugin.api;

import java.util.HashMap;
import java.util.concurrent.Future;

import org.homi.plugin.api.exceptions.InternalPluginException;
import org.homi.plugin.specification.exceptions.ArgumentLengthException;
import org.homi.plugin.specification.exceptions.InvalidArgumentException;
import org.homi.plugin.specification.exceptions.SpecificationViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommanderTest {

	private Commander<TestSpec> commander;
	
	@BeforeEach
	void setUp() throws Exception {
		var mappings = new HashMap<String, IReceiver<?>>();
		
		mappings.put("RETURN_NULL", (Object ...args)->{
			return null;
			});
		mappings.put("RETURN_STRING", (Object ...args)->{
			return "String";
			});
		mappings.put("RETURN_INTEGER", (Object ...args)->{
			return 2;
			});
		mappings.put("RETURN_FLOAT", (Object ...args)->{
			return 1.1f;
			});
		mappings.put("RETURN_OBJECT", (Object ...args)->{
			return new Object();
			});
		mappings.put("RETURN_WRONG_TYPE", (Object ...args)->{
			return "";
			});
//		mappings.put("RETURN_PRIMITIVE_INT", (Object ...args)->{
//			return 1;
//			});
		mappings.put("SEND_STRING", (Object ...args)->{
			return (String)args[0];
			});
		mappings.put("SEND_INTEGER", (Object ...args)->{
			int x = (int) args[0];
			return null;
			});
		mappings.put("SEND_CUSTOM", (Object ...args)->{
			Custom x = (Custom) args[0];
			System.out.println(x);
			return null;
			});
		
		this.commander = new Commander<TestSpec>(TestSpec.class, mappings);
	}
	
	@Test
	void instantiateNewCommander() {
		Commander<?> c = new Commander<TestSpec>(TestSpec.class, null);
	}
	
	@Test
	void executingAValidCommandWithNoArguments() {
		Assertions.assertDoesNotThrow( ()->{
			this.commander.execute(TestSpec.RETURN_NULL);
			});
	}
	
	@Test
	void executeMustReturnValue() throws InvalidArgumentException, ArgumentLengthException, InternalPluginException {
		Object o = this.commander.execute(TestSpec.RETURN_NULL);
	}

	@Test
	void executeReturnsNullWhenTypeIsVoid() throws InvalidArgumentException, ArgumentLengthException, InternalPluginException  {
		Assertions.assertNull(this.commander.execute(TestSpec.RETURN_NULL));
	}
	
	@Test
	void executeReturnValueMustMatchSpecWhenNotNull() throws InvalidArgumentException, ArgumentLengthException, InternalPluginException {
		Assertions.assertEquals(this.commander.execute(TestSpec.RETURN_OBJECT).getClass(), TestSpec.RETURN_OBJECT.getReturnType().getTypeClass());
		Assertions.assertEquals(this.commander.execute(TestSpec.RETURN_STRING).getClass(), TestSpec.RETURN_STRING.getReturnType().getTypeClass());
		Assertions.assertEquals(this.commander.execute(TestSpec.RETURN_FLOAT).getClass(), TestSpec.RETURN_FLOAT.getReturnType().getTypeClass());
		Assertions.assertEquals(this.commander.execute(TestSpec.RETURN_INTEGER).getClass(), TestSpec.RETURN_INTEGER.getReturnType().getTypeClass());
	}
	
	@Test
	void executeThrowsErrorWHenReturnValueDoesNotMatchSpec() {
		Assertions.assertThrows(Exception.class, ()->{this.commander.execute(TestSpec.RETURN_WRONG_TYPE);});
	}
	
	@Test
	void excecuteCallReturnValueImplicitlyCastsToAssignedType() {
		Assertions.assertThrows(ClassCastException.class, ()->{float i = this.commander.execute(TestSpec.RETURN_INTEGER);});
		Assertions.assertThrows(ClassCastException.class, ()->{String i = this.commander.execute(TestSpec.RETURN_INTEGER);});
		Assertions.assertDoesNotThrow(()->{int i = this.commander.execute(TestSpec.RETURN_INTEGER);});
	}
	
	@Test
	void executingCommandFromSpecCloneShouldNotThrow() {
		Assertions.assertDoesNotThrow(()->{this.commander.execute(TestSpec2.RETURN_NULL);});
	}

	@Test
	void executingAFaultySpecCloneShouldNotThrow() {
		Assertions.assertDoesNotThrow(()->{this.commander.execute(TestSpec2.RETURN_FLOAT);});
	}
	
	@Test
	void executingAnInvalidCommandShouldThrow() {
		Assertions.assertThrows(Exception.class,()->{this.commander.execute(TestSpec2.C1);});
	}
	

	@Test
	void executeValidatesParameterCount() {
		Assertions.assertDoesNotThrow(()->{this.commander.execute(TestSpec.SEND_STRING, "Hey Omri");});
		Assertions.assertThrows(SpecificationViolationException.class, ()->{this.commander.execute(TestSpec.SEND_STRING, "Hey Omri",  "bye Omri");});
		Assertions.assertThrows(SpecificationViolationException.class, ()->{this.commander.execute(TestSpec.SEND_STRING);});
	}
	
	@Test
	void executeValidatesParameterTypes() {
		Assertions.assertDoesNotThrow(()->{this.commander.execute(TestSpec.SEND_INTEGER, 15);});
		Assertions.assertThrows(SpecificationViolationException.class, ()->{this.commander.execute(TestSpec.SEND_INTEGER, "hey Omri");});
		Assertions.assertThrows(SpecificationViolationException.class, ()->{this.commander.execute(TestSpec.SEND_INTEGER, new Object());});
	}
	
	@Test
	void executeCommandAsynchronously() {
		this.commander.executeAsync(TestSpec.SEND_INTEGER, 15);
		Assertions.assertThrows(ClassCastException.class, ()->{
				Future<Float> i = this.commander.executeAsync(TestSpec.RETURN_INTEGER);
				Float f = i.get();
			});
		Assertions.assertThrows(Exception.class, ()->{(this.commander.executeAsync(TestSpec.SEND_INTEGER, "hey Omri")).get();});
	}
	
	
	@Test
	void sendSirializable() {
		Assertions.assertDoesNotThrow(()->{this.commander.execute(TestSpec.SEND_CUSTOM, new Custom("Omri", 15) );});
		Assertions.assertThrows(Exception.class, ()->{this.commander.execute(TestSpec.SEND_CUSTOM, new String("Omri") );});
	}
}
