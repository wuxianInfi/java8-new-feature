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
import java.util.stream.IntStream;

import org.junit.Test;

/**
 * @author hongtao
 * @version v 0.1 , 2017年12月19日 下午1:17:42
 * @since JDK 1.8
 */
public class TestException {

  @Test
  public void notCool() {
    try {
      IntStream.rangeClosed(-10, 10).boxed().filter(i -> 10 / i > 2).map(Integer::doubleValue)
          .collect(Collectors.toList());
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    // lambda的问题是出了异常比较难排查,唯一的方法就是使用独立的命名函数
    try {
      IntStream.rangeClosed(-10, 10).boxed().filter(i -> checkCondition(i))
          .map(Integer::doubleValue).collect(Collectors.toList());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private boolean checkCondition(int i) {
    return 10 / i > 2;
  }
}
