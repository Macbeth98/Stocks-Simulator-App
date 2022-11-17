import java.nio.file.Path;
import java.util.Map;

/**
 * This interface contains methods that will be used to read and write data to files.
 */
public interface FileIO {

  String getFileType();
  void checkDirectory(String directoryPath) throws RuntimeException;

  Map<String, Path> getFilesInDirectory (String directoryPath) throws RuntimeException;

  String[] readData(String filepath);

  void writeData(String filepath);

}
