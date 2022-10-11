package com.demo.spark.core.partition

import com.demo.spark.core.persist.PersistDemo
import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, RangePartitioner, SparkConf, SparkContext}

/**
 * Copyright：dp.com
 * Author: SongXiaoGuang
 * Date: 2022/10/11.
 * Description:
 */
object PartitionDemo {

  def main(args: Array[String]): Unit = {
    //创建配置对象
    val conf: SparkConf = new SparkConf()
      .setAppName(s"${PersistDemo.getClass.getSimpleName}")
      .setMaster("local[*]") //local[*] ：为当前 spark 作业分配当前计算机的可用 CPU core 的个数
    val sc = new SparkContext(conf)
    val list = List("Hadoop","Hive","Spark","Flink")
    val rdd1 = sc.parallelize(list)
    println("current partitions num: " + rdd1.partitions.length)

    val rdd2 = rdd1.repartition(4)
    println("repartition num: " + rdd2.partitions.length)

    val retRDD: RDD[(String, Int)] = rdd1.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)

    val rdd3 = rdd1.map(word => (word, 1)).reduceByKey(new HashPartitioner(2), _ + _)
    rdd3.map(_._1).saveAsTextFile("file:///tmp1")



    val rdd4 = rdd1.map(word => (word, 1)).reduceByKey(_ + _)
    rdd4.partitionBy(new RangePartitioner(2, rdd3))
      .map(_._1).saveAsTextFile("file:///tmp2")

  }
}
