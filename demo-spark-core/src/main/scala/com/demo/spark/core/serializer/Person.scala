package com.demo.spark.core.serializer

/**
 * Copyright：dp.com
 * Author: SongXiaoGuang
 * Date: 2022/10/17.
 * Description:
 */
class Person(val name: String, val age: Int) extends java.io.Serializable  {
  override def toString: String = "name: " + name + "， age: " + age
}
