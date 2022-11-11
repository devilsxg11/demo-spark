package com.demo.spark.streaming

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Copyright：dp.com
 * Author: SongXiaoGuang
 * Date: 2022/10/31.
 * Description:
 */
object WordCount {

  def main(args: Array[String]): Unit = {

    // 初始化 SparkContext
    val conf: SparkConf = new SparkConf()
      .setAppName(s"${WordCount.getClass.getSimpleName}")
      .setMaster("local[*]")
    val sc:SparkContext = new SparkContext(conf)

    // 每隔5秒分一个批次
    val ssc = new StreamingContext(sc, Seconds(5))

    //1.加载数据
    val lines: ReceiverInputDStream[String] =
      ssc.socketTextStream("10.115.88.47",8888)

    //2.处理数据
    val resultDS: DStream[(String, Int)] =
      lines.flatMap(_.split(" "))
        .map((_, 1))
        .reduceByKey(_ + _)

    //3.输出结果
    resultDS.print()

    //4.启动并等待结果
    ssc.start()
    ssc.awaitTermination()

    //5.关闭资源
    ssc.stop(stopSparkContext = true,stopGracefully = true)

  }
}
