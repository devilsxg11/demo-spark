package com.demo.spark.streaming

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext, Time}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Copyright：dp.com
 * Author: SongXiaoGuang
 * Date: 2022/11/15.
 * Description:
 */
object SQLWordCount {

  def main(args: Array[String]): Unit = {
    // 初始化 SparkContext
    val conf: SparkConf = new SparkConf()
      .setAppName(s"${SQLWordCount.getClass.getSimpleName}")
      .setMaster("local[*]")
    val sc:SparkContext = new SparkContext(conf)

    // 每隔5秒分一个批次
    val ssc = new StreamingContext(sc, Seconds(5))

    // 加载数据
    val lines: ReceiverInputDStream[String] =
      ssc.socketTextStream("10.115.88.47",8888)

    // 按空格分割，转换成 word
    val words = lines.flatMap(_.split(" "))

    words.foreachRDD { rdd =>

      // 获取 SparkSession 单例
      val spark = SparkSession.builder.config(rdd.sparkContext.getConf).getOrCreate()
      import spark.implicits._

      // 转换 RDD[String] to DataFrame
      val wordsDataFrame = rdd.toDF("word")

      // 创建临时视图 temporary view
      wordsDataFrame.createOrReplaceTempView("words")

      // 使用 SQL 查询 temporary view 并打印
      val wordCountsDataFrame =
        spark.sql("select word, count(*) as total from words group by word")
      wordCountsDataFrame.show()
    }

    ssc.start()
    ssc.awaitTermination()

    ssc.stop(stopSparkContext = true,stopGracefully = true)

  }
}
