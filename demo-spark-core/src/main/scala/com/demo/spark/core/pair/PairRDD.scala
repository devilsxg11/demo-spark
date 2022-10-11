package com.demo.spark.core.pair

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Copyright：dp.com
 * Author: SongXiaoGuang
 * Date: 2022/10/11.
 * Description:
 */
object PairRDD {

  def main(args: Array[String]): Unit = {
    //创建配置对象
    val conf: SparkConf = new SparkConf()
      .setAppName(s"${PairRDD.getClass.getSimpleName}")
      .setMaster("local[*]") //local[*] ：为当前 spark 作业分配当前计算机的可用 CPU core 的个数
    val sc = new SparkContext(conf)

    //生成RDD
    val textPath = getClass.getClassLoader.getResource("word.txt").getPath
    val textRDD: RDD[String] = sc.textFile(textPath)

    val pairRDD: RDD[(String, Int)] = textRDD.flatMap(line => line.split(" "))
      .map(word => (word, 1))

    pairRDD.foreach(x => println(x))

    val list = List()
    val tuples = list.+:(Tuple2("k1", 1)).+:("k2", 2)
    val rdd = sc.parallelize(tuples)
    rdd.foreach(x => println(x))
    //关闭资源
    sc.stop()
  }
}
