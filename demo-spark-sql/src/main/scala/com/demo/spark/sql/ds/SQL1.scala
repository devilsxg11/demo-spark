package com.demo.spark.sql.ds

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
 * Copyright：dp.com
 * Author: SongXiaoGuang
 * Date: 2022/10/24.
 * Description:
 */
object SQL1 {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder()
      .appName(s"${SQL1.getClass.getSimpleName}")
      .master("local[*]")
      .getOrCreate()

    //json 数据
    val jsonPath = getClass.getClassLoader.getResource("person.json").getPath
    val df: DataFrame = spark.read.json(jsonPath)

    // 创建临时视图
    df.createOrReplaceTempView("person")
    // 运行 SQL 语句
    val person = spark.sql("SELECT * FROM person")

    person.show()
  }
}
