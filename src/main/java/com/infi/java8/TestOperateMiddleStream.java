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

import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.Test;

import com.infi.java8.model.Order;

/**
 * @author hongtao
 * @version v 0.1 , 2017年12月1日 下午5:41:49
 * @since JDK 1.8
 */
public class TestOperateMiddleStream {


  @Test // 过滤
  public void filter() {
    LongStream.rangeClosed(1, 10).filter(i -> i % 2 == 0).forEach(System.out::println);
  }

  @Test // 转换
  public void map() {
    LongStream.range(1, 10).mapToObj(i -> "aa" + i).forEach(System.out::println);
  }

  @Test // 排序
  public void sort() {
    Stream.of(3, 4, 6, 1, 2).sorted((a, b) -> a.compareTo(b)).forEach(System.out::println);
  }

  @Test // 去重
  public void distinct() {
    Stream.of(3, 4, 6, 1, 2).mapToInt(o -> o % 4).distinct().forEach(System.out::println);
  }

  @Test // 分页
  public void skipLimit() {
    Stream.of(3, 4, 6, 1, 2).skip(2).limit(2).forEach(System.out::println);
  }

  @Test // 扁平化
  public void flatMap() {
    String joiningNames = Order.orders.stream().flatMap(order -> order.getItems().stream())
        .map(item -> item.getItemName()).collect(Collectors.joining(","));
    System.out.println(joiningNames);
  }


  @Test // 调试
  public void peer() {
    Order.orders.stream().filter(o -> o.getId() > 5).peek(System.out::println)
        .collect(Collectors.toList());
  }

}
