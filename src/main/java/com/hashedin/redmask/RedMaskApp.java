package com.hashedin.redmask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashedin.redmask.common.DataMasking;
import com.hashedin.redmask.config.MaskConfiguration;
import com.hashedin.redmask.factory.DataMaskFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

@Command(description = "Redmask Tool - a CLI proxyless administration tool to mask "
    + "sensitive information in a data warehouse",
    name = "redmask",
    mixinStandardHelpOptions = true,
    version = "redmask 1.0")
public class RedMaskApp implements Callable<Integer> {

  private static final Logger log = LoggerFactory.getLogger(RedMaskApp.class);

  /*
   *  TODO :Implement static masking feature and may be we will need a 
   *  different masking config structure for static masking.
   */
  @Option(names = {"-f", "--configFilePath"}, required = true,
      description = "Complete file path of json containing masking configurations.")
  private String configFilePath;

  @Option(names = {"-r", "--dryRun"},
      description = "When true, this will just generates sql file with required queries. "
          + "It will not make any changes to DB.")
  private boolean dryRun = true;

  @Option(names = {"-m", "--mode"},
      description = "When static, this will just generates a new table to house the masked data. "
          + "When dynamic a new view will be created with the masked data.")
  private String mode;

  public static void main(String[] args) throws IOException {
    log.info("Starting redmask application.");
    System.exit(new CommandLine(new RedMaskApp()).execute(args));
  }

  @Override
  public Integer call() {

    MaskConfiguration config = null;

    try {
      config = new ObjectMapper().readValue(new File(configFilePath), MaskConfiguration.class);
    } catch (Exception ex) {
      // log the exception and exit the application.
      log.error("Exception while reading masking config json file: ", ex);
      log.error("Terminating the Redmask Application.");
      return 0;
    }
    DataMasking dataMasking = DataMaskFactory.buildDataMask(config, dryRun, mode);
    dataMasking.generateSqlQueryForMasking();
    dataMasking.executeSqlQueryForMasking();
    log.info("Closing redmask application.");
    return 0;
  }

}
