package com.example.MeowDate.logparser.processing

import cats.effect.IO
import com.example.MeowDate.logparser.models.LogEntry
import fs2.io.file.{Path, writeAll}
import fs2.{Pipe, Stream, text}

import java.nio.file.StandardOpenOption._
import java.time.LocalTime

object Writer {
  def writeUserLogs(outputDir: Path, userId: String, entries: Vector[LogEntry]): Stream[IO, Unit] = {
    val fileName = s"user-$userId-${LocalTime.now()}.log"
    val filePath = java.nio.file.Path.of(outputDir + fileName)

    val writePipe: Pipe[IO, Byte, Nothing] = writeAll(
        filePath, List(CREATE, APPEND)
    )

    Stream.emits(entries.map(_.raw))
      .intersperse("\n")
      .through(text.utf8.encode)
      .through(writePipe)
  }

}
