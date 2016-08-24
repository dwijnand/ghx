package ghx

object Main {
  def main(args: Array[String]): Unit = {
    val testRepo = Repo(User("dwijnand"), "test-repo")
  }
}

final case class User(value: String) extends AnyVal
final case class Repo(owner: User, name: String)

object `package` {
}
