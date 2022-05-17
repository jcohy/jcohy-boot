package com.jcohy.boot.test.docker;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.Container;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.shaded.com.google.common.base.Charsets;
import org.testcontainers.shaded.com.google.common.io.Resources;
import org.testcontainers.utility.DockerImageName;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2022/4/28:18:56
 * @since 2022.04.0
 */
public final class MongoDBContainerBuilder {

    private MongoDBContainerBuilder() {
    }

    private final List<MongoDBContainer> containers = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(MongoDBContainerBuilder.class);

    private MongoDBContainerBuilder(Builder builder) throws IOException, InterruptedException {
        for (int i = 0; i < builder.node; i++) {
            containers.add(initMongoNode(builder.port.get(i), builder.version));
        }
        log.info("MongoDB 集群初始化...");
        Thread.sleep(5_000);

        log.info("配置副本集");
        String replicaConfig = Resources.toString(Resources.getResource("replica-set-config.js"), Charsets.UTF_8)
                .replace("\n", "").trim();
        log.info("副本集配置: {}", replicaConfig);
        Container.ExecResult execResult = containers.get(0).execInContainer("/usr/bin/mongo", "--eval", replicaConfig);

        log.info("副本集结果 (ERR): {}", execResult.getStderr());
        log.info("副本集结果 (OUT): {}", execResult.getStdout());
        log.info("副本集结果初始化...");

        Thread.sleep(10_000);
        log.info("MongoDB 启动...");
    }

    public void start() {
        containers.forEach(MongoDBContainer::start);
    }

    public void stop() {
        containers.forEach(MongoDBContainer::stop);
    }

    public static Builder builder() {
        return new Builder();
    }

    public MongoDBContainer initMongoNode(int port, String version) {

        return new MongoDBContainer(DockerImageName.parse(version)).withExposedPorts(port)
                .waitingFor(Wait.forListeningPort().withStartupTimeout(Duration.ofMillis(10)))
                .withLogConsumer(
                        (outputFrame) -> log.debug("[Mongo - {}] : {}", port, outputFrame.getUtf8String().trim()))
                .withCommand("mongod --storageEngine wiredTiger --replSet reactive");
    }

    static class Builder {

        /**
         * 默认为一个实例，端口号为 27017.
         */
        private List<Integer> port = Collections.singletonList(27017);

        /**
         * mongo 版本号.
         */
        private String version;

        /**
         * 默认加载 resource 目录下集群配置文件.
         */
        private String replicaResource;

        /**
         * 节点数量.
         */
        private int node;

        public Builder port(List<Integer> port) {
            this.port = port;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder replicaResource(String replicaResource) {
            this.replicaResource = replicaResource;
            return this;
        }

        public Builder node(int node) {
            this.node = node;
            return this;
        }

        public MongoDBContainerBuilder build() throws IOException, InterruptedException {
            if (node != port.size()) {
                log.error("参数不一致");
            }
            else {
                return new MongoDBContainerBuilder(this);
            }
            return null;
        }

    }

}
