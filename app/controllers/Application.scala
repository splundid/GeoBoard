package controllers

import play.api._
import play.api.mvc._
import scala.collection.immutable.HashSet
object Application extends Controller {
  def boards = HashSet("b", "a", "rf", "d")

  def index = Action {
    Ok(views.html.index("Welcome."))
  }
  def board(board:String, page:Long) = if (boards.contains(board)) {
    Action { req =>
      models.Thread.getThreads(board, page) match {
        case Some(seq) => Ok(views.html.board(board, seq))
        case None => Ok("Couldn't find any threads")
      }
      //Ok ("This is cool board " + board + " page " + page)
    }
  }
  else {
      TODO
  }

}