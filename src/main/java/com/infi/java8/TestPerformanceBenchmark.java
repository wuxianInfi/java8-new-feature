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

import java.util.function.Supplier;
import java.util.stream.LongStream;

import org.junit.Test;

import com.infi.java8.util.MyFunctions;

/**
 * @author hongtao
 * @version v 0.1 , 2017年12月19日 下午1:17:25
 * @since JDK 1.8
 */
public class TestPerformanceBenchmark {

  @Test // http://www.oracle.com/technetwork/java/jvmls2013kuksen-2014088.pdf
  public void performanceBenchmark() {
    MyFunctions.calcTime("lambda",
        () -> LongStream.rangeClosed(1, 10000000000L).forEach(i -> lambda()));
    MyFunctions.calcTime("anonymous",
        () -> LongStream.rangeClosed(1, 10000000000L).forEach(i -> anonymous()));
  }

  private Supplier<String> lambda() {
    String localString = "test";
    return () -> localString;
  }

  private Supplier<String> anonymous() {
    String localString = "test";
    return new Supplier<String>() {
      @Override
      public String get() {
        return localString;
      }
    };
  }
}
