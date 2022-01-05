package jedis;

import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BadConcurrentJedisTest {
    private static final ExecutorService pool = Executors.newFixedThreadPool(20);

    private static final Jedis jedis = new Jedis("127.0.0.1", 6379);

    public static void main(String[] args) {
        for (int i = 0;i < 20; i++) {
            pool.execute(new RedisSet());
        }
    }


    public static class RedisSet implements Runnable{
        @Override
        public void run() {
            jedis.set("hello", "world");
        }
    }

}

