package ghx
package repl

import scala.concurrent._
import scala.concurrent.duration._
import scala.util._

import fr.hmil.roshttp._
import tabular._

object `package` {
  implicit class FutureWithAwait[A](private val fut: Future[A]) extends AnyVal {
    def await(d: Duration) = Await.result(fut, d)
    def await5s            = fut await  5.seconds
    def await30s           = fut await 30.seconds

    def to_s(show: A => String) =
      fut.value match {
        case None             => "Future(?)"
        case Some(Failure(e)) => s"Future(ex: $e)"
        case Some(Success(x)) => {
          val s = show(x)
          if (s.contains("\n")) s"Future(\n$s\n)" else s"Future($s)"
        }
      }
  }

  implicit class HeaderMapWithToS[T >: String](private val headers: HeaderMap[T]) extends AnyVal {
    def to_s = if (headers.isEmpty) "" else s"""
      |Headers:
      |  ${headers.showkv mkString "\n  "}
    """.stripMargin.trim
  }

  implicit class HttpRequestWithToS(private val req: HttpRequest) extends AnyVal {
    import req._
    def to_s = {
      val bodyString = body.fold("")(body => new HttpResponse(0, body.content, headers).bodyString)
      s"""
        |$method $url
        |${headers.to_s}
        |$bodyString
      """.stripMargin.trim
    }
  }

  implicit class HttpResponseWithToS(private val rsp: HttpResponse) extends AnyVal {
    import rsp._

    def bodyString = if (body.trim.isEmpty) "" else s"""
      |Body:
      |$body
    """.stripMargin

    def to_s = s"""
      |Status: $statusCode
      |${headers.to_s}
      |$bodyString
    """.stripMargin.trim
  }
}
