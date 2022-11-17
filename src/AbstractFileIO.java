import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * This class implements the FileIO class and is an Abstract class.
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
  public String[] readData(String filepath) {

    return new String[0];
  }

  @Override
  public void writeData(String filepath) {

  }

  protected abstract String formatData(Object data);
}
