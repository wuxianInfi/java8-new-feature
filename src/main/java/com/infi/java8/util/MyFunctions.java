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
package com.infi.java8.util;

import java.time.Duration;
import java.time.Instant;

/**
 * @author hongtao
 * @version v 0.1 , 2017年12月1日 下午3:18:13
 * @since JDK 1.8
 */
public class MyFunctions {

  public static void calcTime(String name, Runnable task) {
    Instant start = Instant.now();
    task.run();
    System.out.println(
        String.format("执行【%s】耗时: %d ms", name, Duration.between(start, Instant.now()).toMillis()));
  }
}
