/*
 * Copyright 2014-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.infi.java8;

import java.lang.annotation.Repeatable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


/**
 * @author hongtao
 * @version v 0.1 , 2017年12月1日 下午5:46:51
 * @since JDK 1.8
 */
public class TestOtherNewFeature {

  @Test
  public void threadLocal() {
    ThreadLocal<Map<String, String>> tl = ThreadLocal.withInitial(HashMap::new);
    tl.get().put("key1", "test");
    Assert.assertEquals(tl.get().get("key1"), "test");
  }

  @Test
  public void optional() {
    Assert.assertEquals(Optional.ofNullable(null).orElse("A"), "A");
    Assert.assertFalse(Optional.empty().isPresent());
    Assert.assertTrue(Optional.of(1).filter(i -> i % 2 == 0).orElse(null) == null);

    Optional.empty().orElseThrow(IllegalArgumentException::new);
  }

  @Test
  public void hashmap() {
    Map<String, Long> wordCount = Lists.newArrayList("aa", "bb", "cc", "aa", "bb").stream()
        .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
    System.out.println(wordCount.getOrDefault("dd", 1L));
    System.out.println(wordCount.putIfAbsent("aa", 5L));
    wordCount.replaceAll((k, v) -> k.length() + v);
    System.out.println(wordCount);
    Long count = wordCount.computeIfAbsent("test", k -> k.length() + 5L);
    System.out.println(count);

  }

  @Test
  public void atomic() {
    AtomicLong al = new AtomicLong();
    Assert.assertTrue(al.updateAndGet(x -> x + 1) == 1);
    Assert.assertTrue(al.getAndAdd(2) == 1);

    LongAdder la = new LongAdder();
    la.increment();
    la.decrement();
    Assert.assertTrue(la.longValue() == 0);

    DoubleAccumulator da = new DoubleAccumulator((a, b) -> a + b, 10.0);
    da.accumulate(1.0);
    da.accumulate(2.0);
    da.accumulate(3.0);
    Assert.assertTrue(da.get() == 16);
  }

  @Test
  public void repeatableAnnotation() {
    Hint[] hints = TestClass.class.getAnnotationsByType(Hint.class);
    Arrays.asList(hints).forEach(h -> {
      System.out.println(h.value());
    });
  }

  @Test
  public void base64() {
    System.out.println("base64:" + StandardCharsets.UTF_8.decode(Base64.getDecoder()
        .decode(Base64.getEncoder().encode(StandardCharsets.UTF_8.encode("test")))));
  }

  @Test
  public void parallelPrefix() {
    int[] array = new int[] {2, 3, 1, 0, 5};
    Arrays.parallelPrefix(array, (l, r) -> l + r); // out: [2, 5, 6, 6, 11]
    System.out.println(Arrays.toString(array));
  }

  @Test
  public void compareThenCompare() {
    Function<Product, String> func = p -> p.getName();
    Stream.of(new Product("aa", 1.0), new Product("bb", 1.0), new Product("cc", 2.0))
        .sorted(Comparator.comparing(func).thenComparingDouble(Product::getPrice)).forEach(p -> {
          System.out.println(p);
        });
  }

  @Data
  @AllArgsConstructor
  @ToString
  private static class Product {
    private final String name;
    private final double price;
  }

  @Repeatable(Hints.class)
  @interface Hint {
    String value();
  }

  @interface Hints {
    Hint[] value();
  }

  @Hint("hint1")
  @Hint("hint2'")
  class TestClass {

  }
}
