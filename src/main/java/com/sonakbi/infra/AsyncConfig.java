package com.sonakbi.infra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        int processors = Runtime.getRuntime().availableProcessors(); // 현재 프로세서 개수

        /**
         * CorePoolSize, MaxPoolSize, QueueCapacity 처리할 태스크(이벤트)가 생겼을 때,
         * ‘현재 일하고 있는 쓰레드 개수’(active thread)가 ‘코어 개수’(core pool size)보다 작으면 남아있는 쓰레드를 사용한다.
         * ‘현재 일하고 있는 쓰레드 개수’가 코어 개수만큼 차있으면 ‘큐 용량’(queue capacity)이 찰때까지 큐에 쌓아둔다.
         * 큐 용량이 다 차면, 코어 개수를 넘어서 ‘맥스 개수’(max pool size)에 다다르기 전까지 새로운 쓰레드를 만들어 처리한다.
         * 맥스 개수를 넘기면 태스크를 처리하지 못한다.
         */
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(processors);
        executor.setMaxPoolSize(processors * 2);
        executor.setQueueCapacity(50);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("AsyncExecutor-");
        executor.initialize();

        return executor;
    }
}
