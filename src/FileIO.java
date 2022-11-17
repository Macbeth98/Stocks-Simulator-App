/**
 * This interface contains methods that will be used to read and write data to files.
 */
public interface FileIO {

  String[] readData(String filepath);

  void writeData(String filepath);

}
