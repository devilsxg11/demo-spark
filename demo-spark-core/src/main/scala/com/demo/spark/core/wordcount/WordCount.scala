package com.demo.spark.core.wordcount

import com.demo.spark.core.wordcount.utils.DateUtil
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Copyright：dp.com
 * Author: SongXiaoGuang
 * Date: 2022/9/5.
 * Description:
 */
object WordCount {

  def main(args: Array[String]): Unit = {
    //创建配置对象
    val conf: SparkConf = new SparkConf()
      .setAppName(s"${WordCount.getClass.getSimpleName}")
      .setMaster("local[*]") //local[*] ：为当前spark作业分配当前计算机的可用CPU core的个数
    val sc = new SparkContext(conf)

    //生成RDD
    val textPath = getClass.getClassLoader.getResource("word.txt").getPath
    val textRDD: RDD[String] = sc.textFile(textPath)
    //进行 transformation 操作和 action 操作
    val retRDD: RDD[(String, Int)] = textRDD.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
    //调用Java工具类
    print(DateUtil.getCurrentTime)
    //将最终的数据进行输出
    retRDD.foreach(x => println(x._1 + ": " + x._2))
    //关闭资源
    sc.stop()

  }
}
