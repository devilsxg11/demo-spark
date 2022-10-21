package com.demo.spark.core.serializer

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Copyright：dp.com
 * Author: SongXiaoGuang
 * Date: 2022/10/17.
 * Description:
 */
object KryoSerializerDemo {
  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf()
      .setAppName(s"${KryoSerializerDemo.getClass.getSimpleName}")
      .setMaster("local[*]")

    conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    conf.registerKryoClasses(Array(classOf[Person]))

    val sc = new SparkContext(conf)
    val list = List(new Person("tom",18),
      new Person("Jerry", 10),
      new Person("bob", 13))
    val rdd = sc.parallelize(list)
    rdd.foreach(e=>println(e))

  }

}
