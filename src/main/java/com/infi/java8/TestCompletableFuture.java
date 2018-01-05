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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

import com.infi.java8.util.MyFunctions;
import com.infi.java8.util.ThreadUtil;

/**
 * @author hongtao
 * @version v 0.1 , 2017年12月1日 下午2:08:30
 * @since JDK 1.8
 */
public class TestCompletableFuture {

  private final AtomicInteger threadCount = new AtomicInteger(0);
  private final ExecutorService executor = Executors.newFixedThreadPool(5, r -> {
    Thread thread = new Thread(r);
    thread.setDaemon(true);
    thread.setName("my-executor-" + threadCount.incrementAndGet());
    return thread;
  });

  @Test // 基本用法
  public void basicUsage() throws InterruptedException, ExecutionException, TimeoutException {
    CompletableFuture<Integer> cf1 = new CompletableFuture<>();
    executor.submit(() -> {
      ThreadUtil.sleep(1000L);
      cf1.complete(1);
      return 1;
    });
    System.out.println(cf1.get());
  }

  @Test // 超时
  public void timeout() throws InterruptedException, ExecutionException, TimeoutException {
    CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> {
      ThreadUtil.sleep(1000L);
      return 1;
    });
    System.out.println(cf1.get(500, TimeUnit.MILLISECONDS));
  }


  @Test // ForkJoin任务
  public void forkJoin() {
    // complete future相比与parallel可以更灵活的控制线程池,适用于IO绑定的任务;parallel适用于CPU绑定的任务
    MyFunctions.calcTime("testForkJoin", () -> {
      int sum = IntStream.rangeClosed(0, 10).mapToObj(i -> {
        return CompletableFuture.supplyAsync(() -> {
          ThreadUtil.sleep(10L);
          return i;
        }, executor);
      }).collect(Collectors.toList()).stream().map(f -> f.join()).mapToInt(i -> i).sum();
      System.out.println(sum);
    });
  }

  @Test // 之前CompletionStage完成后作为参数输入继续后续CF操作
  public void thenAccept() {
    MyFunctions.calcTime("thenAccept", () -> {
      CompletableFuture.completedFuture(5).thenAccept(t -> {
        ThreadUtil.sleep(1000L);
        System.out.println(t + 10);
      }).join();
    });

    MyFunctions.calcTime("thenAcceptAsync", () -> {
      CompletableFuture.completedFuture(5).thenAcceptAsync(t -> {
        ThreadUtil.sleep(1000L);
        System.out.println(t + 10);
      }, executor).join();
    });
  }

  @Test // 任一CompletionStage完成的时候执行action,谁先执行完就用谁的结果计算
  public void applyToEither() throws InterruptedException, ExecutionException {
    MyFunctions.calcTime("applyToEither", () -> {
      CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
        ThreadUtil.sleep(50L);
        return 1;
      }, executor);
      CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
        ThreadUtil.sleep(1000L);
        return 2;
      }, executor);
      CompletableFuture<Integer> f3 = f1.applyToEither(f2, t -> {
        return t + 10;
      });
      try {
        System.out.println(f3.get());
      } catch (InterruptedException | ExecutionException e) {
      }
    });
  }

  @Test // 跟thenAccept类似，之前CompletionStage完成时执行action,与thenAccept的区别可以是不同类型
  public void thenCompose() throws InterruptedException, ExecutionException {
    CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
      ThreadUtil.sleep(50L);
      return 10;
    }, executor);
    CompletableFuture<String> f2 = f1.thenCompose(i -> {
      return CompletableFuture.supplyAsync(() -> {
        return "Test" + i;
      });
    });
    System.out.println(f2.get());
  }

  @Test // 组合的CF并不会等之前的CF执行完成后再执行，没有先后依赖顺序
  public void thenCombine() throws InterruptedException, ExecutionException {
    CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> {
      ThreadUtil.sleep(100L);
      return 100;
    });
    CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
      ThreadUtil.sleep(200L);
      return "tester";
    });
    CompletableFuture<String> cf3 = cf1.thenCombine(cf2, (x, y) -> {
      return y + x;
    });
    System.out.println(cf3.get());
  }
}
