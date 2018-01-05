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

import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;

/** 
 * @author hongtao 
 * @version  v 0.1 , 2017年12月18日 下午3:16:16
 * @since  JDK 1.8     
 */
public class TestFunctionalInterface {

  @Test // java.util.function
  public void functionInterfaces() {
    Supplier<String> supplier = () -> {
      return "tester";
    };
    System.out.println(supplier.get());
    Consumer<String> consumer = System.out::println;
    consumer.andThen(consumer).accept("bb");
    Predicate<Integer> predicate = i -> i % 2 == 0;
    System.out.println(predicate.test(4));
    BiFunction<Integer, Integer, Integer> add = (a, b) -> {
      return a + b;
    };
    System.out.println(add.apply(3, 4));
  }

  @Test
  public void highOrderFunctionInterfaces() throws Exception {
    // 返回一个函数的函数
    Callable<Runnable> test = () -> () -> System.out.println("hi");
    test.call().run();
  }
}
