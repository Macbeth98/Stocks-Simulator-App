package fileinout;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * This interface contains methods that will be used to read and write data to files.
 */
public interface FileIO {

  /**
   * Gets the file type of the class.
   *
   * @return returns the file type.
   */
  String getFileType();

  /**
   * Gets the file imn PATH format when given in string
   *
   * @param filepath the path of the file.
   * @return returns the file path in Path format.
   */
  Path getFilePathFromString(String filepath);

  /**
   * Checks whether directory is there or not. if not it will create the directory.
   *
   * @param directoryPath the path of the directory.
   * @throws RuntimeException if the directory cannot be created.
   */
  void checkDirectory(String directoryPath) throws RuntimeException;

  /**
   * Gets all the files in the given directory.
   *
   * @param directoryPath the path of the directory.
   * @return the map between file name and filepath.
   * @throws RuntimeException if directory does not exist and cannot be created.
   */
  Map<String, Path> getFilesInDirectory(String directoryPath) throws RuntimeException;

  /**
   * reads the data from a file.
   *
   * @param filepath the path of the file where the data needs to read.
   * @return the data read in the form of strings.
   */
  List<String> readData(String filepath);

  /**
   * Writes the data into a file.
   *
   * @param filepath the path of the file where the data needs to be written.
   * @param data     data that needs to be written.
   * @return returns the filepath.
   * @throws FileNotFoundException if the file is not found.
   */
  String writeData(String filepath, Object[] data) throws FileNotFoundException;

  /**
   * Appends the data to an existing file.
   *
   * @param filepath the path of the file where the data needs to be appended.
   * @param data     data that needs to be appended.
   * @return
   * @throws FileNotFoundException if the file is not found.
   */
  String appendData(String filepath, Object[] data) throws FileNotFoundException;

}
