package ca.jrvs.apps.grep;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JavaGrepLambda extends JavaGrepImp{

  public static void main(String[] args){
    if (args.length != 3){
      throw new IllegalArgumentException("USAGE: regex rootPath outFile");
    }
    JavaGrepLambda javaGrepLambda = new JavaGrepLambda();
    javaGrepLambda.setRegex(args[0]);
    javaGrepLambda.setRootPath(args[1]);
    javaGrepLambda.setOutFile(args[2]);

    try{
      javaGrepLambda.process();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<File> listFiles(String rootDir) {
    List<File> fileLambda = new ArrayList<>();
    try{
      fileLambda = Files.walk(Paths.get(rootDir)).map(Path::toFile).filter(File::isFile).collect(
          Collectors.toList());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fileLambda;
  }

  @Override
  public List<String> readLines(File inputFile){
    List<String> lineLambda = new ArrayList<>();
    try{
      lineLambda = Files.lines(inputFile.toPath()).collect(Collectors.toList());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return lineLambda;
  }
}
