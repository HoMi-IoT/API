package org.homi.plugin.api;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommanderBuilderTest {

	private CommanderBuilder<TestSpec> commanderBuilder;
	
	@BeforeEach
	void setUp() throws Exception {
		this.commanderBuilder = new CommanderBuilder<>(TestSpec.class);
	}

	@Test
	void instantiateNewCommanderBuilder() {
		CommanderBuilder<?> cb = new CommanderBuilder<>(TestSpec.class);
	}
	
	@Test
	void addACommandAndActionPair() {
		commanderBuilder.onCommandEquals(TestSpec.RETURN_NULL, (Object ...args)->{return null;});
	}
	
	@Test
	void chainAddACommandAndActionPair() {
		commanderBuilder.onCommandEquals(TestSpec.RETURN_NULL, (Object ...args)->{return null;})
			.onCommandEquals(TestSpec.RETURN_INTEGER, (Object ...args)->{return 1;});
	}
	
	@Test
	void BuildACommander() {
		commanderBuilder.onCommandEquals(TestSpec.RETURN_NULL, (Object ...args)->{return null;})
			.onCommandEquals(TestSpec.RETURN_INTEGER, (Object ...args)->{return 1;})
			.build();
	}
	
	@Test
	void useCommandsOnBuiltCommander() throws TypeMismatchException, ClassCastException, IllegalArgumentException, ClassNotFoundException, IOException {
		Commander<TestSpec> c = commanderBuilder.onCommandEquals(TestSpec.RETURN_NULL, (Object ...args)->{return null;})
				.onCommandEquals(TestSpec.RETURN_INTEGER, (Object ...args)->{return 1;})
				.build();
		c.execute(TestSpec.RETURN_NULL);
	}

}
