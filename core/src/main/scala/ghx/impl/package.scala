package ghx
package impl

import scala.concurrent._
import scala.util._

import cats._, data._
import fr.hmil.roshttp._
import io.circe._, parser._
import tabular._

object `package` {
  def runtimeException(msg: String) = new RuntimeException(msg)

  implicit class FutureWithToS[A](private val fut: Future[A]) extends AnyVal {
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

  implicit class HeaderMapWithToS[A >: String](private val headers: HeaderMap[A]) extends AnyVal {
    def to_s = if (headers.isEmpty) "" else s"""
      |Headers:
      |  ${headers.toSeq.sortBy(_._1).showkv mkString "\n  "}
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

  implicit class StringWithCirceDecode(private val s: String) extends AnyVal {
    def parseJson: ParsingFailure Xor Json = parse(s)
  }
}
