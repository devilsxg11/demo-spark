package com.demo.spark.sql

import org.apache.spark.sql.SparkSession

/**
 * Copyright：dp.com
 * Author: SongXiaoGuang
 * Date: 2022/10/26.
 * Description:
 */
object SparkSQLDemo{

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder()
      .appName(s"${SparkSQLDemo.getClass.getSimpleName}")
      .master("local[*]")
      .enableHiveSupport() //开启对 Hive 的支持
      .getOrCreate()


    spark.sql("use dim")
    spark.sql("show tables").show()
  }

}
