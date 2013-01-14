package com.twitter.finagle.redis
import com.twitter.finagle.redis.protocol._
import org.jboss.netty.buffer.ChannelBuffer
import com.twitter.util.Future
import util.{CBToString, ReplyFormat}

trait Scripts { self: BaseClient =>
  def eval(script: ChannelBuffer, keys: Seq[ChannelBuffer], args: Seq[ChannelBuffer]) =
    doRequest(Eval(script, keys, args)) {
      case MBulkReply(message) => Future.value(ReplyFormat.toChannelBuffers(message))
      case EmptyMBulkReply() => Future.value(List())
    }

  def evalSha(sha: String, keys: Seq[ChannelBuffer], args: Seq[ChannelBuffer]) =
    doRequest(EvalSha(sha, keys, args)) {
      case MBulkReply(message) => Future.value(ReplyFormat.toChannelBuffers(message))
      case EmptyMBulkReply() => Future.value(List())
    }

  def scriptLoad(script: ChannelBuffer) = doRequest(ScriptLoad(script)) {
    case BulkReply(message) => Future.value(CBToString(message))
  }

}