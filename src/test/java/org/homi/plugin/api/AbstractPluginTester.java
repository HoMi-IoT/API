package org.homi.plugin.api;

import static org.junit.jupiter.api.Assertions.*;

import org.homi.plugin.specification.ISpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AbstractPluginTester {

	private AbstractPlugin plugin;

	@BeforeEach
	void setUp() throws Exception {
		this.plugin = new AbstractPlugin() {

			@Override
			public void setup() {			
				CommanderBuilder<TestSpec> cb = new CommanderBuilder<>(TestSpec.class);
				var c = cb.onCommandEquals(TestSpec.RETURN_NULL, (Object ...args)->{return null;}).build();
				this.addCommander(TestSpec.class, c);

				this.addWorker(()->{System.out.println("Spec worker");});
				this.addWorker(()->{System.out.println("global worker");});
			}

			@Override
			public void teardown() {				
			}
			
		};
	}


	@Test
	void setupPlugin() {
		Assertions.assertDoesNotThrow(()->{ this.plugin.setup();});
	}
	
	@Test
	void setupPluginContainsSpecification() {
		Assertions.assertEquals(0, this.plugin.getSpecifications().size());
		this.plugin.setup();
		Assertions.assertEquals(1, this.plugin.getSpecifications().size());
	}

	@Test
	void getGlobalWorkers() {
		this.plugin.setup();
		Assertions.assertEquals(1, this.plugin.getWorkers().size());
	}

	@Test
	void getCommanderFromSpec() {
		this.plugin.setup();
		Class<? extends ISpecification> spec = this.plugin.getSpecifications().get(0);
		Assertions.assertNotNull(this.plugin.getCommander(spec));
	}
	@Test
	void getCommanderFromSpecClone() {
		this.plugin.setup();
		Assertions.assertNotNull(this.plugin.getCommander(TestSpec2.class));
	}
	
}
