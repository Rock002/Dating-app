package com.example.MeowDate.logparser.processing

import cats.effect.IO
import com.example.MeowDate.logparser.models.LogEntry
import fs2.io.file.{Files, Path}
import fs2.{Stream, text}

object Reader {
  def readLogFile(path: Path): Stream[IO, LogEntry] = {
    Files[IO].readAll(path)
      .through(text.utf8.decode)
      .through(text.lines)
      .filter(_.nonEmpty)
      .map(LogEntry.parse)
      .collect {case Some(entry) => entry}
  }
}
