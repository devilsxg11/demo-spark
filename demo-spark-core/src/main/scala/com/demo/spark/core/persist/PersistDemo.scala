package com.demo.spark.core.persist

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * Copyright：dp.com
 * Author: SongXiaoGuang
 * Date: 2022/10/11.
 * Description:
 */
object PersistDemo {
  def main(args: Array[String]): Unit = {
    //创建配置对象
    val conf: SparkConf = new SparkConf()
      .setAppName(s"${PersistDemo.getClass.getSimpleName}")
      .setMaster("local[*]") //local[*] ：为当前 spark 作业分配当前计算机的可用 CPU core 的个数
    val sc = new SparkContext(conf)
    val list = List("Hadoop","Hive","Spark","Flink")
    val rdd = sc.parallelize(list)
    println("---->" + rdd.partitions.length)
    rdd.cache()
    // 第一次 action 操作触发一次从头到尾的计算，
    // 此时才会执行 rdd.cache()，把rdd 放到缓存中
    println(rdd.count())
    // 第二次 action 操作，不需要从头到尾的计算，使用上面缓存的 rdd
    rdd.take(2).foreach(e=>println(e))
    //关闭资源
    sc.stop()

  }
}
