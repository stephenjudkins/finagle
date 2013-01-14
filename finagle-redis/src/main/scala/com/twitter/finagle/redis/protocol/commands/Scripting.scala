package com.twitter.finagle.redis.protocol

import com.twitter.finagle.redis.util._
import org.jboss.netty.buffer.{ChannelBuffer, ChannelBuffers}


case class Eval(script: ChannelBuffer, keys: Seq[ChannelBuffer], args: Seq[ChannelBuffer]) extends Command {
  def command = Commands.EVAL
  def toChannelBuffer = RedisCodec.toUnifiedFormat(Seq(
    CommandBytes.EVAL,
    script,
    StringToChannelBuffer(keys.length.toString)
  ) ++ keys ++ args
  )
}

case class EvalSha(sha: String, keys: Seq[ChannelBuffer], args: Seq[ChannelBuffer]) extends Command {
  def command = Commands.EVALSHA
  def toChannelBuffer = RedisCodec.toUnifiedFormat(Seq(
    CommandBytes.EVALSHA,
    StringToChannelBuffer(sha),
    StringToChannelBuffer(keys.length.toString)
  ) ++ keys ++ args
  )

}

case class ScriptLoad(script: ChannelBuffer) extends Command {
  def command = Commands.SCRIPT_LOAD
  def toChannelBuffer = RedisCodec.toUnifiedFormat(Seq(
    CommandBytes.SCRIPT,
    CommandBytes.LOAD,
    script
  ))
}