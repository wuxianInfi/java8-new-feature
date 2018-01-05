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

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * @author hongtao
 * @version v 0.1 , 2017年12月1日 下午5:41:49
 * @since JDK 1.8
 */
public class TestOperateEndStream {


  @Test // 分组
  public void groupBy() {
    Map<String, Long> wordCount = Lists.newArrayList("aa", "bb", "cc", "aa", "bb").stream()
        .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
    System.out.println(wordCount);
  }

  @Test // 最大最小
  public void maxMin() {
    IntStream.of(1, 2, 3).boxed().max(Comparator.comparingInt(o -> o))
        .ifPresent(System.out::println);
    IntStream.of(1, 2, 3).boxed().max(Comparator.comparingInt(o -> o))
        .ifPresent(System.out::println);

  }

  @Test // 规约
  public void reduce() {
    Stream.of("1.0", "2.0", "3.0").mapToDouble(o -> Double.valueOf(o)).reduce(Double::sum)
        .ifPresent(System.out::println);

  }

  @Test // 收集
  public void collect() {
    System.out.println(IntStream.range(1, 20).boxed().map(o -> String.valueOf(o))
        .collect(Collectors.joining(",", "[", "]")));
  }

  @Test // 自定义收集
  public void customCollect() {

  }

  @Test // 分区
  public void partion() {
    System.out.println(
        IntStream.range(1, 20).boxed().collect(Collectors.partitioningBy(o -> o % 2 == 0)));
  }


  @Test // 任一匹配、全部匹配
  public void match() {
    System.out.println(Stream.of(1, 2, 4, 8).anyMatch(o -> o > 5));
    System.out.println(Stream.of(1, 2, 4, 8).allMatch(o -> o > 5));
  }

}
