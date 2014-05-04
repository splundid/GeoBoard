package models

import java.sql.Date



case class Post(id: Long, thread_id: Long, date:Date, text:String, theme:String)
object Post {
  def create_post(thread: Thread, text:String, theme:String):Option[Post] =  None
  def get_posts(thread:Thread):Option[List[Post]] = None
  def delete(id:Long) {}
  def delete(post:Post) {}


}
