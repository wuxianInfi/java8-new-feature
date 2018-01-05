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

import java.util.concurrent.locks.StampedLock;

/**
 * @author hongtao
 * @version v 0.1 , 2017年12月4日 下午6:11:44
 * @since JDK 1.8
 */
public class TestStampedLock {

  private double x, y;
  private final StampedLock sl = new StampedLock();

  void move(double deltaX, double deltaY) { // 排他锁
    long stamp = sl.writeLock(); // 返回票据
    try {
      x += deltaX;
      y += deltaY;
    } finally {
      sl.unlockWrite(stamp);
    }
  }

  // 乐观锁案例
  double distanceFromOrigin() { // a read-only method
    long stamp = sl.tryOptimisticRead(); // 获得一个乐观锁
    double currentX = x, currentY = y;
    if (!sl.validate(stamp)) { // 检查发出乐观锁之后同时是否有其他写锁发生？
      stamp = sl.readLock(); // 如果没有,我们再次获得一个读悲观锁
      try {
        currentX = x; // 将两个字段读入本地局部变量
        currentY = y;
      } finally {
        sl.unlockRead(stamp);
      }
    }
    return Math.sqrt(currentX * currentX + currentY * currentY);
  }

  // 悲观锁案例
  void moveIfAtOrigin(double newX, double newY) {
    long stamp = sl.readLock();
    try {
      while (x == 0.0 && y == 0.0) { // 循环检查状态是否符合
        long ws = sl.tryConvertToWriteLock(stamp); // 尝试将读锁转为写锁
        if (ws != 0L) { // 确认转换写锁成功,替换票据
          stamp = ws;
          x = newX;
          y = newY;
          break;
        } else { // 如果不能转换成功，显示释放读锁，直接进行写锁重试
          sl.unlockRead(stamp);
          stamp = sl.writeLock();
        }
      }
    } finally {
      sl.unlock(stamp); // 释放写锁和读锁
    }
  }

}
