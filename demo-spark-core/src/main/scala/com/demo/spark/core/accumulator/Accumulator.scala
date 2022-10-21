package com.demo.spark.core.accumulator

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Copyright：dp.com
 * Author: SongXiaoGuang
 * Date: 2022/10/12.
 * Description:
 */
object Accumulator {

  def main(args: Array[String]): Unit = {
    //创建配置对象
    val conf: SparkConf = new SparkConf()
      .setAppName(s"${Accumulator.getClass.getSimpleName}")
      .setMaster("local[*]")
    val sc = new SparkContext(conf)
    val accum = sc.longAccumulator("My Accumulator")

    val list = Array(1, 2, 3, 4)
    val rdd = sc.parallelize(list)

    rdd.foreach(x => accum.add(x))

    println(accum.value)

  }
}
