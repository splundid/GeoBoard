package models


import java.util.Date
case class Thread (id: Long, board: String, page: Long, posts_number: Long,  date:Date)

object Thread {
  def getThreads(board:String, page: Long): Option[List[Thread]] = None
  def create(board:String):Option[Thread] = None
  def delete(id:Long) {}
  def delete(thread:Thread) {}




}
