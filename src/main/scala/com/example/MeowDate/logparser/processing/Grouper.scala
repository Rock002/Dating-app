package com.example.MeowDate.logparser.processing

import cats.effect.IO
import com.example.MeowDate.logparser.models.LogEntry
import fs2.Pipe

object Grouper {
  def groupByUserAndDay: Pipe[IO, LogEntry, (String, Vector[LogEntry])] = {
    _.groupAdjacentBy {
      entry => (entry.userId, entry.timestamp.atZone(java.time.ZoneOffset.UTC).toLocalDate)
    } { case ((u1, d1), (u2, d2)) => u1 == u2 && d1 == d2 }.map {
      case ((userId, _), data) => (userId, data.toVector)
    }
  }
}
