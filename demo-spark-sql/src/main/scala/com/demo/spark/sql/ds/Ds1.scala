package com.demo.spark.sql.ds
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}


/**
 * Copyright：dp.com
 * Author: SongXiaoGuang
 * Date: 2022/10/21.
 * Description:
 */
object Ds1 {

  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .appName(s"${Ds1.getClass.getSimpleName}")
      .master("local[*]")
      .getOrCreate()
    //导入隐式转换
    import spark.implicits._

    //可以从该对象中获取到 sparkContext
    val sc: SparkContext = spark.sparkContext

    val list = List("Hadoop","Hive","Spark","Flink")
    val rdd: RDD[String] = sc.parallelize(list)

    val df: DataFrame = rdd.toDF("bigdata")
    println(df.schema)
    df.foreach(x=>println(x))

    val ds: Dataset[String] = rdd.toDS()
    ds.foreach(x=>println(x))

  }
}
