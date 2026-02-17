package com.example.MeowDate.logparser.models

import java.time.Instant

case class LogEntry(timestamp: Instant, userId: String, message: String, raw: String)

object LogEntry {
  private val pattern = """\[(\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}Z)\] user:(\S+) (.+)""".r

  def parse(line: String): Option[LogEntry] = line match {
    case pattern(timestampStr, userId, message) => {
      scala.util.Try(Instant.parse(timestampStr)).toOption.map {
        instant => LogEntry(instant, userId, message, line)
      }
    }
    case _ => None
  }


}
