package reader

import scalaz._
/**
 * @author Isaias
 */
trait UsersReader {

  def fetch(id: Int) = Reader((repo: repositories.UserRepository) =>
    repo.fetch(id))

  def fetchAll = Reader((repo: repositories.UserRepository) =>
    repo.fetchAll)
    
}