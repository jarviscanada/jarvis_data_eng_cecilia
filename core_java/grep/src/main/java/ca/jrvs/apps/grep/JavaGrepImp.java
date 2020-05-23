package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep {

  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

  private String regex;
  private String rootPath;
  private String outFile;

  public static void main(String[] args){
    //configure logger
    BasicConfigurator.configure();

    if (args.length != 3){
      throw new IllegalArgumentException("USAGE:JavaGrep regex rootPath outFile");
    }
    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch (Exception e) {
      javaGrepImp.logger.error(e.getMessage(), e);
    }
  }

  @Override
  public void process() throws IOException {

    List<String> matchedLines = new ArrayList<>();
    for (File file: listFiles(this.rootPath) )
      for (String line : readLines(file)) {
        if (containsPattern(line)) {
          matchedLines.add(line);
        }
      }
    writeToFile(matchedLines);
  }

  //Traverse a given directory and return all files
  @Override
  public List<File> listFiles(String rootDir) {
    List<File> fileList = new ArrayList<>();
    File fileRoot = new File(rootDir);
    for (File f : fileRoot.listFiles()) {
      if (f.isDirectory()) {
          fileList.addAll(listFiles(f.getAbsolutePath()));
        }
        fileList.add(f);
      }
    return fileList;
  }

  //Read a file and return all the lines
  @Override
  public List<String> readLines(File inputFile) throws IOException {
    List<String> lines = new ArrayList<>();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(inputFile));
      String line = reader.readLine();
      while (line != null){
        lines.add(line);
        line = reader.readLine();
      }

    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
    return lines;
  }

  //check if a line contains the regex pattern
  @Override
  public boolean containsPattern(String line) {
    Pattern pattern = Pattern.compile(this.regex);
    Matcher matcher = pattern.matcher(line);
    return matcher.find();
  }

  //Write lines to a file
  @Override
  public void writeToFile(List<String> lines) throws IOException {
    try{
      BufferedWriter writer = new BufferedWriter(new FileWriter(this.outFile));
      for(String string : lines){
        writer.write(string + System.lineSeparator());
      }
      writer.close();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }
  //Save CLI arguments into private member variables which are encapsulated
  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }
}
