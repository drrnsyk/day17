package vttp2022.ssf.day17boardgames.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class BoardgameRepository {
    
    @Autowired
    @Qualifier("redislab")
    private RedisTemplate<String, String> redisTemplate;
    
    // function to get the data from the redis database
    public String getFromRedis (String id) {

        // give the id (taken from the path variable in url) and retrive the payload from redis
        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        // get the payload and store it in a String
        String value = valueOp.get(id);

        return value;
        
    }

    public Integer count() {
        Set<String> keys = redisTemplate.keys("[0-9]*");
        return keys.size();
    }
    public List<String> keys() {
        Set<String> keys = redisTemplate.keys("[0-9]*");
        List<String> result = new LinkedList<>(keys);
        return result.stream()
                .map(v -> Integer.parseInt(v))
                .sorted()
                .map(v -> v.toString())
                .toList();
    }

}
