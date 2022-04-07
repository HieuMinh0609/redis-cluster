package com.burak.redis.controller;

import com.burak.redis.model.KeyValue;
import com.burak.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @PostMapping(value = "/add")
    public ResponseEntity<String> addToRedis(@RequestBody KeyValue keyValue) {
        redisService.addValue(keyValue.getKey(), keyValue.getValue());
        return ResponseEntity.ok("Success");
    }

    @GetMapping(value = "/get/{key}")
    public ResponseEntity<KeyValue> getFromRedis(@PathVariable final String key) throws Exception {
        String value = redisService.getValue(key);

        if (value != null) {return ResponseEntity.ok(new KeyValue(key, value));}

        return  ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/keys/count")
    public ResponseEntity<Integer> getKeysCount() {
        Integer keysCount = redisService.getKeysCount();
        return ResponseEntity.ok(keysCount);
    }

    @GetMapping(value = "/keys/all")
    public ResponseEntity<Set<String>> getAllKeys() {
        Set<String> keysSet = redisService.getAllKeys();

        if (keysSet != null && !keysSet.isEmpty()) {
            return ResponseEntity.ok(keysSet);
        }
        return ResponseEntity.noContent().build();
    }
}
