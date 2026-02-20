package com.example.MeowDate.logparser

import com.example.MeowDate.logparser.processing.{Grouper, Reader, Writer}
import fs2.io.file.Path

object LogProcessor {
  def mainProcess(inputPath: Path, outputDir: Path) = {
    Reader.readLogFile(inputPath)
      .through(Grouper.groupByUserAndDay)
      .flatMap{
        case (userId, entries) => Writer.writeUserLogs(outputDir, userId, entries)
      }
      .compile
      .drain
  }
}
