package com.example.trailblazer.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginRateLimiter {
    public final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.builder()
                                   .capacity(5)
                                   .refillGreedy(5, Duration.ofMinutes(1))
                                   .build();
        return Bucket.builder()
                     .addLimit(limit)
                     .build();
    }

    public boolean tryConsume(String clientIp) {
        Bucket bucket = buckets.computeIfAbsent(clientIp, key -> createNewBucket());
        return bucket.tryConsume(1);
    }
}
