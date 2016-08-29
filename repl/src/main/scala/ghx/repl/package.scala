package ghx
package repl

import scala.concurrent._
import scala.concurrent.duration._

object `package` {
  implicit class FutureWithAwait[A](private val fut: Future[A]) extends AnyVal {
    def await(d: Duration) = Await.result(fut, d)
    def await5s            = fut await  5.seconds
    def await30s           = fut await 30.seconds
  }
}
