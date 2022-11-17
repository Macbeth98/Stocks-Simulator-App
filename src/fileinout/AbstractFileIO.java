package fileinout;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * This class implements the file-inout.FileIO class and is an Abstract class.
 * This class has methods used to store the files.
 */
abstract class AbstractFileIO implements FileIO {

  private final String fileType;

  public AbstractFileIO(String fileType) {
    this.fileType = fileType;
  }

  @Override
  public String getFileType() {
    return this.fileType;
  }


  @Override
  public Path getFilePathFromString(String filepath) {
    return Paths.get(filepath);
  }

  @Override
  public void checkDirectory(String directoryPath) throws RuntimeException {
    File directory = new File(directoryPath);

    if(!directory.exists()) {
      if(!directory.mkdir()) {
        throw new RuntimeException("Directory is not found and cannot be created.");
      }
    }
  }

  @Override
  public Map<String, Path> getFilesInDirectory(String directoryPath) throws RuntimeException {

    Map<String, Path> files = new HashMap<>();

    try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
      paths.filter(Files::isRegularFile)
              .forEach(file -> {
                String filePath = file.toString();
                String fileExt = filePath.substring(filePath.lastIndexOf(".") + 1);
                if (this.fileType.equalsIgnoreCase(fileExt)) {
                  files.put(file.getFileName().toString().split("_")[0], file);
                }
              });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return files;
  }

  @Override
  public List<String> readData(String filepath) throws IllegalArgumentException {

    File file = new File(this.getFilePathFromString(filepath).toString());

    List<String> lines = new ArrayList<>();

    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        lines.add(scanner.nextLine());
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found!");
    }

    return lines;
  }

  private void writeToFile(String filepath, Object[] data, boolean append) throws IOException {
    FileWriter fileWriter = new FileWriter(filepath, append);
    BufferedWriter bw = new BufferedWriter(fileWriter);
    PrintWriter out = new PrintWriter(bw);

    for (Object datum : data) {
      out.println(this.formatData(datum));
    }

    out.flush();
    out.close();
  }

  @Override
  public String writeData(String filepath, Object[] data) throws FileNotFoundException {
    try {
      Path path = this.getFilePathFromString(filepath);
      this.writeToFile(path.toString(), data, false);
      return path.toString();
    } catch (IOException e) {
      throw new FileNotFoundException("File not found or cannot be created.");
    }
  }

  @Override
  public String appendData(String filepath, Object[] data) throws FileNotFoundException {

    try {
      Path path = this.getFilePathFromString(filepath);
      this.writeToFile(path.toString(), data, true);
      return path.toString();
    } catch (IOException e) {
      throw new FileNotFoundException("File not found or cannot be created.");
    }

  }

  protected abstract String formatData(Object data);
}
