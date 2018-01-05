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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * @author hongtao
 * @version v 0.1 , 2017年12月1日 下午5:22:37
 * @since JDK 1.8
 */
public class TestGenerateStream {

  @Test // 直接构造
  public void of() {
    Stream.of(1, 2, 3).forEach(System.out::println);
    Stream.of(1, 2, 3).map(i -> {
      return "test" + i;
    }).forEach(System.out::println);
  }

  @Test // 迭代
  public void iterate() {
    Stream.iterate(1, n -> n * 2).limit(10).forEach(System.out::println);
  }

  @Test // 函数生成
  public void generate() {
    Stream.generate(() -> "test").limit(3).forEach(System.out::println);
    Stream.generate(Math::random).limit(10).forEach(System.out::println);
  }

  @Test // Collection
  public void streams() {
    Lists.newArrayList(1, 2, 3).stream().forEach(System.out::println);
  }

  @Test // 基本数据类型
  public void primitive() {
    IntStream.of(1, 20).boxed().forEach(i -> System.out.println(i.getClass()));
  }

  @Test // 流合并
  public void concat() {
    Stream.concat(Stream.of(1, 5, 3), Stream.of(2, 4)).sorted().forEach(System.out::println);
  }

  @Test // NIO files
  public void fileBasic() throws IOException {
    Files.walk(Paths.get("/Users/hongtao/desktop"), 2).limit(100).map(String::valueOf)
        .filter(path -> path.endsWith(".jar")).sorted().forEach(System.out::println);
  }

}
