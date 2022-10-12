package com.demo.spark.core.broadcast

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Copyright：dp.com
 * Author: SongXiaoGuang
 * Date: 2022/10/12.
 * Description:
 */
object Broadcast {

  def main(args: Array[String]): Unit = {
    //创建配置对象
    val conf: SparkConf = new SparkConf()
      .setAppName(s"${Broadcast.getClass.getSimpleName}")
      .setMaster("local[*]")
    val sc = new SparkContext(conf)

    val broadVal = sc.broadcast(10)

    val list = List(1, 2, 3, 4)
    val rdd = sc.parallelize(list)

    val result = rdd.map(x => x * broadVal.value)
    result.foreach(x=>println(x))

  }

}
