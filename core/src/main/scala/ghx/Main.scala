package ghx

import scala.concurrent._, ExecutionContext.Implicits._

import cats.data._
import fr.hmil.roshttp._
import ghx.impl._

object Main {
  def main(args: Array[String]): Unit = {
  }
}

object GitHub {
  val baseRequest =
    HttpRequest("https://api.github.com/") withHeader ("Accept", "application/vnd.github.v3+json")

  def user(name: String) = User(name)

  def repo(owner: String, name: String) = user(owner) repo name
}

final case class User(name: String) extends AnyVal {
  def repo(name: String) = Repo(this, name)

  def repos = ListRepos(this)

  override def toString = name
}

final case class Repo(owner: User, name: String) {
  /** https://developer.github.com/v3/pulls/#get-a-single-pull-request */
  def pullRequest(number: Int) = {
    val req = GitHub.baseRequest withPath s"/repos/$owner/$name/pulls/$number"
    def errMsg0 = s"Failed to get ${req.path}."
    def err1                    = runtimeException(errMsg0)
    def err2(rsp: HttpResponse) = runtimeException(s"$errMsg0 Response:\n${rsp.to_s}")
    req.send()
      .flatMap {
        case rsp if rsp.statusCode == 200 => rsp.body.parseJson match {
          case Xor.Right(json) => Future successful json
          case Xor.Left(e)     => Future failed (err2(rsp) initCause e)
        }
        case rsp => Future failed (err2(rsp))
      }
      .transform(identity, err1 initCause _)
  }
}

final case class ListRepos(
        user: User,
       _type: ListReposType,
       _sort: ListReposSort,
  _direction: ListReposDirection
) {
}

sealed class ListReposType(val value: String)
case object ListReposDefault extends ListReposType("owner")
case object ListReposAll     extends ListReposType("all")
case object ListReposOwner   extends ListReposType("owner")
case object ListReposMember  extends ListReposType("member")

sealed class ListReposSort(val value: String)
case object ListReposSortDefault    extends ListReposSort("full_name")
case object ListReposSortByCreated  extends ListReposSort("created")
case object ListReposSortByUpdated  extends ListReposSort("updated")
case object ListReposSortByPushed   extends ListReposSort("pushed")
case object ListReposSortByFullName extends ListReposSort("full_name")

sealed class ListReposDirection(val value: String)
case object ListRepoosDirectionAsc  extends ListReposDirection("asc")
case object ListRepoosDirectionDesc extends ListReposDirection("desc")
