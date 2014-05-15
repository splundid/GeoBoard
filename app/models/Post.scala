package models

import java.util.Date
import play.api.db.DB
import anorm._


case class Post(id: Long, thread_id: Long, date:Date, text:String, theme:String)
object Post {
  def getPostById(id:Long):Option[Post] = {
    DB.withConnection {implicit c =>
      val query = SQL("SELECT * FROM Posts WHERE id={id}").on("id"->id)
      val format = new java.text.SimpleDateFormat("yyyy-MM-dd")
      query().headOption.map {
        case Row(id:Long, thread_id:Long, date:String, text:String, theme:String)
        => Post(id, thread_id, format.parse(date), text, theme)}
    }
  }
  def getLastPosts(thread_id:Long):Seq[Post] = {
    DB.withConnection {implicit c =>
      val query = SQL("SELECT * FROM Posts WHERE thread_id={thread_id}").on("thread_id"->thread_id)
      val format = new java.text.SimpleDateFormat("yyyy-MM-dd")
      query().map {case Row(id:Long, thread_id:Long, date:String, text:String, theme:String) =>
        Post(id, thread_id, format.parse(date), text, theme)
      }.toList
    }
  }
  def create_post(thread: Thread, text:String, theme:String):Option[Post] =  None
  def create_post(board:String, text:String, theme:String):Option[Post] = {
    DB.withTransaction {implicit c =>
      val dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd")
      val current_date =  dateFormat.format(Date)
      val query = SQL("INSERT INTO Posts(Thread_id,Date,Text,Theme) " +
        "values ({thread_id},{date},{text},{theme}").on('thread_id->0,'date->current_date,'text->text, 'theme-> theme)
      val result = query.executeInsert()
      val update_query = {id:Long => SQL("UPDATE Posts SET Thread_id={id}").on('id->id) }
      result match {
        case Some(id:Long) => Thread.create(board, id) match {
          case Some(thread) => update_query(thread.id).execute()
                      Some(Post(id, thread.id, dateFormat.parse(current_date), text, theme))

          case None  => None
        }
        case None => None
      }
    }
  }
  def get_posts(thread:Thread):Option[List[Post]] = None
  def delete(id:Long) {}
  def delete(post:Post) {}


}
