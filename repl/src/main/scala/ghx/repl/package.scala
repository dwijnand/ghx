package ghx
package repl

import scala.concurrent._
import scala.concurrent.duration._
import scala.util._

object `package` {
  implicit final class FutureWithAwait[A](private val fut: Future[A]) extends AnyVal {
    def await(d: Duration) = Await.result(fut, d)
    def await5s            = fut await  5.seconds
    def await30s           = fut await 30.seconds

    def to_s(show: A => String): String =
      fut.value match {
        case None             => "Future(?)"
        case Some(Failure(e)) => s"Future(ex: $e)"
        case Some(Success(x)) => s"Future(${show(x)})"
      }
  }
}
