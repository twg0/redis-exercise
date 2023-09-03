package com.example.redisexercise;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class RedisExerciseApplicationTests {

	@Autowired
	StringRedisTemplate redisTemplate;

	@Test
	void testStrings() {
		final String key = "newKey";

		final ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();

		stringStringValueOperations.set(key, "1"); // redis set 명령어
		final String result_1 = stringStringValueOperations.get(key); // redis get 명령어

		System.out.println("result_1 = " + result_1);

		stringStringValueOperations.increment(key); // redis incr 명령어
		final String result_2 = stringStringValueOperations.get(key);

		System.out.println("result_2 = " + result_2);
	}

	@Test
	public void testList() {
	    // given
		final String key = "newkey";

		final ListOperations<String, String> stringStringListOperations = redisTemplate.opsForList();
		stringStringListOperations.rightPush(key, "H");
		stringStringListOperations.rightPush(key, "e");
		stringStringListOperations.rightPush(key, "l");
		stringStringListOperations.rightPush(key, "l");
		stringStringListOperations.rightPush(key, "o");

		stringStringListOperations.rightPushAll(key, " ", "a", "a", "b", "b");
		// when
		final String character_1 = stringStringListOperations.index(key, 1);
		final Long size = stringStringListOperations.size(key);
		final List<String> ResultRange = stringStringListOperations.range(key, 0, 9);
		// then
		System.out.println("character_1 = " + character_1);
		System.out.println("size = " + size);
		System.out.println("ResultRange = " + Arrays.toString(ResultRange.toArray()));
	}

	@Test
	public void testSet() {
	    // given
		String key = "sabarada";
		SetOperations<String, String> stringStringSetOperations = redisTemplate.opsForSet();

		stringStringSetOperations.add(key, "H");
		stringStringSetOperations.add(key, "e");
		stringStringSetOperations.add(key, "l");
		stringStringSetOperations.add(key, "l");
		stringStringSetOperations.add(key, "o");

	    // when
		Set<String> sabarada = stringStringSetOperations.members(key);
		Long size = stringStringSetOperations.size(key);
	    // then
		System.out.println("members = " + Arrays.toString(sabarada.toArray()));
		System.out.println("size = " + size);

		Cursor<String> cursor = stringStringSetOperations.scan(key,
			ScanOptions.scanOptions().match("*").count(3).build());

		while (cursor.hasNext()) {
			System.out.println("cursor = " + cursor.next());
		}
	}

	@Test
	public void testHash() {
	    // given
		String key = "newKey";

		HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();

		stringObjectObjectHashOperations.put(key, "Hello", "newKey");
		stringObjectObjectHashOperations.put(key, "Hello2", "newKey");
		stringObjectObjectHashOperations.put(key, "Hello3", "newKey");
	    // when
		Object Hello = stringObjectObjectHashOperations.get(key, "Hello");
		Map<Object, Object> entries = stringObjectObjectHashOperations.entries(key);
		Long size = stringObjectObjectHashOperations.size(key);
		// then
		System.out.println("Hello = " + Hello);
		System.out.println("entries = " + entries.get("Hello2"));
		System.out.println("size = " + size);
	}
}
