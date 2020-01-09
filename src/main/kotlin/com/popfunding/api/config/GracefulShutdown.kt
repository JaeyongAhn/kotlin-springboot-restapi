package com.popfunding.api.config

import org.apache.catalina.connector.Connector
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextClosedEvent
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

const val TIMEOUT: Long = 3 * 60

class GracefulShutdown : TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

    val logger = logger()

    var connector: Connector? = null

    override fun customize(connector: Connector?) {
        this.connector = connector
    }

    override fun onApplicationEvent(event: ContextClosedEvent) {
        logger.warn("Tomcat Application is closing")
        this.connector?.pause();
        val executor: Executor? = this.connector?.protocolHandler?.executor
        if (executor is ThreadPoolExecutor) {
            try {
                val threadPoolExecutor: ThreadPoolExecutor = executor
                threadPoolExecutor.shutdown()
                if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                    logger.warn("Tomcat thread pool did not shut down gracefully. Proceeding with forceful shutdown")

                    threadPoolExecutor.shutdownNow()
                    if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                        logger.error("Tomcat thread pool did not terminate")
                    }
                } else {
                    logger.info("Tomcat thread pool has been gracefully shutdown")
                }
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }
}