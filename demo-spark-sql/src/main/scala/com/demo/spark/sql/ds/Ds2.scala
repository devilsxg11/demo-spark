package com.demo.spark.sql.ds

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
 * Copyright：dp.com
 * Author: SongXiaoGuang
 * Date: 2022/10/21.
 * Description:
 */
object Ds2 {

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder()
      .appName(s"${Ds2.getClass.getSimpleName}")
      .master("local[*]")
      .getOrCreate()

    val jsonPath = getClass.getClassLoader.getResource("person.json").getPath
    println(jsonPath)
    val schema = StructType{
      List(
        StructField("id", IntegerType),
        StructField("name", StringType),
        StructField("age", IntegerType),
      )
    }
    // 1. 创建 DataFrame，也可以不设置 schema
    val df: DataFrame = spark.read.schema(schema).json(jsonPath)
    df.show()

    // 1. 创建 Dataset，也可以不设置 schema
    import spark.implicits._
    val ds: Dataset[Person] = spark.read.schema(schema).json(jsonPath).as[Person]
    ds.show()
  }
}
