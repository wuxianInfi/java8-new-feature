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

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.Test;

import com.infi.java8.util.MyFunctions;
import com.infi.java8.util.ThreadUtil;

/**
 * @author hongtao
 * @version v 0.1 , 2017年12月1日 下午5:48:11
 * @since JDK 1.8
 */
public class TestParallelStream {

  // @Test //性能测试
  public void performance() {
    System.out.println(Runtime.getRuntime().availableProcessors());
    MyFunctions.calcTime("串行",
        () -> LongStream.rangeClosed(1, 1000000000L).mapToDouble(Math::sqrt).sum());
    MyFunctions.calcTime("并行",
        () -> LongStream.rangeClosed(1, 1000000000L).parallel().mapToDouble(Math::sqrt).sum());
  }

  // @Test //并行,以最后的为准
  public void parallel() {
    IntStream.rangeClosed(1, 10).parallel().sequential().parallel().forEach(System.out::println);

    Arrays.asList("a1", "a2", "a3").parallelStream() // 直接parallelStream也可以
        .forEach(System.out::println);
  }

  @Test // 由于只有4个核,所以对于CPU bound的操作,提高并行度只会增加线程上下文切换的成本,不会带来任何的性能优势
  public void parallelism() {
    System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "8");
    IntStream.rangeClosed(1, 10).parallel().forEach(i -> {
      ThreadUtil.sleep(500L);
      System.out.println(i);
    });
    MyFunctions.calcTime("并行8并行度",
        () -> LongStream.rangeClosed(1, 1000000000L).mapToDouble(Math::sqrt).sum());
  }

  // @Test // 一旦强制有序,并行处理就会失效
  public void forEachOrdered() {
    IntStream.rangeClosed(1, 5).parallel().forEachOrdered(System.out::println);
  }
}
