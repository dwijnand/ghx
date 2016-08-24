package ghx
package repl

import scala.concurrent._
import scala.concurrent.duration._
import scala.util._

import fr.hmil.roshttp._

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

  implicit final class HttpRequestWithToS(private val req: HttpRequest) extends AnyVal {
    def to_s: String = {
      import req._

      val headersString = if (headers.isEmpty) "" else s"""
        |Headers:
        |  ${headers._map map (t => t._1 + ": " + t._2) mkString "\n  "}
      """.stripMargin.trim

      s"""
        |$method $url
        |$headersString
      """.stripMargin.trim
    }
  }

}
