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

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

/**
 * @author hongtao
 * @version v 0.1 , 2017年12月18日 下午4:53:10
 * @since JDK 1.8
 */
public class TestLocalDateTime {

  @Test
  public void localDate() {
    System.out.println("test" + Instant.now().toEpochMilli());
    // 创建
    System.out.println(LocalDate.now());
    System.out.println(LocalDate.of(1983, 02, 17));
    System.out.println(LocalDate.of(1983, Month.FEBRUARY, 17));
    // 操作
    System.out.println(LocalDate.of(1983, 02, 17).plusYears(30));
    System.out.println(LocalDate.now().minus(Period.ofDays(1)));
    // 今年的程序员日
    System.out.println(LocalDate.of(LocalDate.now().getYear(), 1, 1).plusDays(256));
    // 本月的第一天
    System.out.println(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
    // 今天之前的一个周六
    System.out.println(LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SATURDAY)));
    // 本月的最后一个工作日
    System.out.println(LocalDate.now().with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY)));

  }

  @Test
  public void localDatetime() {
    // 转换
    LocalDateTime ldt = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
    System.out.println(ldt);
    Date date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    System.out.println(date);
    // 操作
    System.out.println(LocalDateTime.now().plus(Period.ofDays(1)).minus(Duration.ofHours(12)));
  }

  @Test
  public void zonedDateTime() {
    ZoneId.getAvailableZoneIds().forEach(zid -> {
      System.out.println(String.format("%s:%s", zid, ZonedDateTime.now(ZoneId.of(zid))));
    });
  }

  @Test
  public void dateTimeFormatter() {
    System.out.println(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        .withLocale(Locale.CHINESE).format(LocalDateTime.now()));
    System.out.println(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        .withLocale(Locale.CHINESE).format(LocalDateTime.now()));
    System.out.println(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
        .withLocale(Locale.CHINESE).format(LocalDateTime.now()));

    System.out
        .println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
    System.out.println(DateTimeFormatter.ofPattern("z x").format(ZonedDateTime.now()));
  }
}
