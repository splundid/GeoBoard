package controllers

import play.api._
import play.api.mvc._
import scala.collection.immutable.HashSet
object Application extends Controller {
  def boards = HashSet("b", "a", "rf", "d")

  def index = Action {
    Ok(views.html.index("Fuck you."))
  }
  def board(board:String, page:Long) = if (boards.contains(board)) {
    Action {
      Ok ("This is cool board " + board + " page " + page)
    }
  }
  else {
      TODO
  }

}