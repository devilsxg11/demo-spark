package com.demo.spark.sql.ds

/**
 * Copyright：dp.com
 * Author: SongXiaoGuang
 * Date: 2022/10/17.
 * Description:
 */
case class Person(id: Long, name: String, age: Long) extends java.io.Serializable  {
  override def toString: String =  "id: " + id +"， name: " + name + "， age: " + age

}
