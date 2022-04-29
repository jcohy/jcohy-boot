package com.jcohy.boot.test.docker;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


/**
 * 描述: .
 * <p>
 *     Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 * @author jiac
 * @version 2022.04.0 2022/4/28:23:14
 * @since 2022.04.0
 */
class MongoDBContainerBuilderTest {

	@Test
	@Disabled
	void testMongoDBContainer() throws IOException, InterruptedException {
		MongoDBContainerBuilder build = MongoDBContainerBuilder.builder()
				.version("mongo:4.0.1")
				.node(3)
				.replicaResource("replica-set-config.js")
				.port(List.of(27017, 27017, 27017))
				.build();

	}
}
