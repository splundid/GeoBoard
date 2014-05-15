package models


import java.util.Date
import play.api.db.DB
import anorm._

case class Thread (id: Long, board: String, page: Long, posts_number: Long, first_post:Long,  date:Date)

object Thread {
  def getThreads(board:String, page: Long): Option[Seq[Thread]] = {
    DB.withConnection {implicit c =>
      val query = SQL("SELECT * FROM Threads WHERE Board={board} AND Page={page}").on("board" -> board, "page" -> page)
      val result = query().collect {
        case Row(id: Long, board: String, page:Long, posts_number:Long, first_post_id: Long, date:String) =>

          val format = new java.text.SimpleDateFormat("yyyy-MM-dd")
          Thread(id, board, page, posts_number, first_post_id, format.parse(date))

      }
      Some (result.toSeq)
    }
  }
  def create(board:String, first_post_id:Long):Option[Thread] = {

    DB.withConnection {implicit c =>
      val dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd")
      val current_date =  dateFormat.format(Date)
      val query = SQL("INSERT INTO Threads(Board,Page,Posts_number,First_post_id,Date) " +
        "values ({board},{page},{posts_number},{first_post_id},{date}").on('board->board,'page->0,'posts_number->1, 'first_post_id-> first_post_id, 'date-> current_date)
      val result = query.executeInsert()
      result match {
        case Some(id:Long) => Some(Thread(id,board,0,1, first_post_id, dateFormat.parse(current_date)))
        case None => None
      }
    }
  }
  def delete(id:Long) {}
  def delete(thread:Thread) {}




}
