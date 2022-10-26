package com.demo.spark.sql.ds

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

import scala.util.parsing.json.JSON

/**
 * Copyright：dp.com
 * Author: SongXiaoGuang
 * Date: 2022/10/21.
 * Description:
 */
object Ds3 {

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder()
      .appName(s"${Ds3.getClass.getSimpleName}")
      .master("local[*]")
      .getOrCreate()


    val jsonPath = getClass.getClassLoader.getResource("person.json").getPath
    val df: DataFrame = spark.read.json(jsonPath)
    import spark.implicits._

    // 1 DF -> [RDD,DS]
    // 1.1 DF -> RDD
    val df2RDD = df.rdd
    df2RDD.foreach(x=>println(x))

    // 1.2 DF -> DS
    val df2DS: Dataset[Person] = df.as[Person]

    // 2 DS -> [RDD,DF]
    // 2.1 DS -> RDD
    val dsToRDD: RDD[Row]  = df.rdd
    dsToRDD.foreach(x=>println(x))

    // 2.2 DS -> DF
    val ds2DF: DataFrame = df2DS.toDF()
    ds2DF.show()

    // 3 RDD -> [DF,DS]
    val sc: SparkContext = spark.sparkContext
    val list = List("Hadoop","Hive","Spark","Flink")
    val rdd: RDD[String] = sc.parallelize(list)
    // 3.1 RDD -> DF
    val rdd2DF: DataFrame = rdd.toDF("bigdata")
    rdd2DF.show()

    // 3.2 RDD -> DS
    val rdd2DS: Dataset[String] = rdd.toDS()
    rdd2DS.show()


  }
}
