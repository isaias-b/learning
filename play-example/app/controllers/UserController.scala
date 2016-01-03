package controllers

import commons._
import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import scala.util._
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import recoveries._
import javax.inject.Inject
import scalaz._

class UserController @Inject() (repo: repositories.UserRepository) extends Controller with reader.UsersReader {

  def get(idStr: String) = Action.async {
    Future {
      val result: Try[Result] = for {
        id <- idStr.safeToInt
        userOpt <- run(fetch(id))
      } yield Ok(userOpt.toString)
      result.toSafeResult
    }
  }

  def getAll = Action.async {
    Future {
      val result: Try[Result] = for {
        users <- run(fetchAll)
      } yield Ok(users.toString)
      result.toSafeResult
    }
  }
  
  private def run[A](reader: Reader[repositories.UserRepository, A]): A = reader(repo)

}
