# Java Grep App 

## Introduction 
The purpose for this application is to search all the files in a root directory recursively, then output all the lines that have matched given regex pattern and collect them to a specific file. This project was made to practice java development by using popular tools like IntelliJ IDE and Maven. It also help to gain knowledge of using Java I/O and Java 8 features like Stream API and Lambda Expression.

## Usage 
This app take three arguments:
  - regex pattern: the regex pattern that need to be searched for
  
  - path of root directory: the path to the directory that will be searched recursively
  
  - output file directory: the file that stores all the matched lines 
  
 ### Format of arguments: 
    <regex pattern> <path of root directory> <output file directory>
 ### Example of Usage: 
     .*IllegalArgumentException.* ./grep/src /tmp/javaGrep.out

## Pseudocode & Structure 

The relations between three java files show below : 

![](https://github.com/jarviscanada/jarvis_data_eng_cecilia/blob/develop/core_java/grep/asset/Diagram.png)

The basic pseudocode show below :
```
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```
Methods in JavaGrep.java interface are implemented by JavaGrepImp.java and JavaGrepLambda.java. Methods and fuctionalities show below :
  - listFiles: Traverse a given directory and return all files
  
  - readLines: Read a file and return all the lines
  - containsPattern: Check if a line contains the regex pattern
  - writeToFiles: Write lines to a file
  - getters and setters : Save CLI arguments into private member variables (encapsulation)

## Performance Issues
In JavaGrepImp.java, method listFiles and method readLines are implemented to load all the files into memory by using Java IO method recursively and save all the matching lines into a specific file. When the searching files have large size, this method would lead memory filled and machine crash. To solve this problem, JavaGrepLambda.java implement this two method by using Java 8 Lambda and Stream API. 
## Improvement 
 1. Java 8 Stream API allow parallelizable operation which can reduce the processing time on multi-core machines
 2. Add original file path behind each matching line in final output file, so it will help user easily locate 
 3. Ability to protect machine when load large size of files 
