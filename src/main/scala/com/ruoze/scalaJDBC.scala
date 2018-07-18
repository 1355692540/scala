package com.ruoze
import java.sql.DriverManager

import scalikejdbc._
object scalaJDBC {

  def main(args: Array[String]) {
    Class.forName("com.mysql.jdbc.Driver")
    ConnectionPool.singleton("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF8","root","root")

    // ad-hoc session provider on the REPL
    implicit val session = AutoSession

    case class User(id: Int, name: String, age: Int)
    val user=new User(4,"zzy",18)

    //插入语句()
    sql"insert into user (id, name,age) values (?,?,?)".bind(user.id,user.name,user.age).update().apply()
    //查询语句
    val userses: List[User] = DB readOnly { implicit session =>
      sql"SELECT * from user".map(rs => User(rs.int("id"), rs.string("name"), rs.int("age"))).list().apply()
    }
    for (usr <- userses ) {
      println(usr.id+" "+user.name+" "+user.age)
    }


  }

}