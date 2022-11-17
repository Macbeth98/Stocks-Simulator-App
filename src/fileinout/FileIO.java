package fileinout;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * This interface contains methods that will be used to read and write data to files.
 */
public interface FileIO {

  String getFileType();

  Path getFilePathFromString(String filepath);

  void checkDirectory(String directoryPath) throws RuntimeException;

  Map<String, Path> getFilesInDirectory(String directoryPath) throws RuntimeException;

  List<String> readData(String filepath);

  String writeData(String filepath, Object[] data) throws FileNotFoundException;

  String appendData(String filepath, Object[] data) throws FileNotFoundException;

}
