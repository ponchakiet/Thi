package poncha.kiet.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
@Slf4j
public class LoggingFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();
        String method = exchange.getRequest().getMethod().toString();
        String uri = exchange.getRequest().getURI().getPath();

        log.info("=== REQUEST START ===");
        log.info("Time: {}", new Date());
        log.info("Method: {}", method);
        log.info("URI: {}", uri);

        return chain.filter(exchange).doAfterTerminate(() -> {
            long duration = System.currentTimeMillis() - startTime;
            log.info("=== RESPONSE END ===");
            log.info("Method: {}, URI: {}, Duration: {}", method, uri, duration);
        });
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
