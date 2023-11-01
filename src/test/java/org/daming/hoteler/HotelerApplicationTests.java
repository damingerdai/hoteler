package org.daming.hoteler;

import org.daming.hoteler.config.DisableGraphQLWebsocketAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisableGraphQLWebsocketAutoConfiguration
class HotelerApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("Hello, this is ci env");
		System.out.println(System.getenv("CI"));
	}

}
