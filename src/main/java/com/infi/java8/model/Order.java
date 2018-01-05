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
package com.infi.java8.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/** 
 * @author hongtao 
 * @version  v 0.1 , 2017年12月18日 下午4:32:17
 * @since  JDK 1.8     
 */
@Data
@RequiredArgsConstructor
@ToString
public class Order {

  private final long id;
  private final List<OrderItem> items;

  public static List<Order> orders = LongStream.range(1, 10).mapToObj(i -> {
    List<OrderItem> items = LongStream.range(2, 4).mapToObj(j -> {
      return new OrderItem(Long.valueOf(j), "item" + j);
    }).collect(Collectors.toList());
    return new Order(Long.valueOf(i), items);
  }).collect(Collectors.toList());
}
