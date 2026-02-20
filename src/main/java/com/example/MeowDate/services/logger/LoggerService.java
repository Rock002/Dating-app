package com.example.MeowDate.services.logger;

import cats.effect.IO;
import com.example.MeowDate.logparser.LogProcessor$;
import fs2.io.file.Path;
import scala.runtime.BoxedUnit;
import cats.effect.unsafe.IORuntime;

public class LoggerService {
    public void processLogFileAsync(String inputPath, String outputDir) {
        LogProcessor$ module = LogProcessor$.MODULE$;
        IO<BoxedUnit> program = module.mainProcess(
                Path.apply(inputPath), Path.apply(outputDir)
        );
        IORuntime runtime = IORuntime.builder().build();

        program.unsafeRunAsyncOutcome(result -> {
            if (result.isError()) {
                System.out.println("error");
            } else if (result.isCanceled()){
                System.out.println("canceled");
            } else {
                System.out.println("succes");
            }
            return BoxedUnit.UNIT;
        }, runtime);
    }
}
