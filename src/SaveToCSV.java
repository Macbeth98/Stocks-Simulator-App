/**
 * This class has methods that will save the given data into CSV file.
 * The class extends AbstractFileIo, an abstract class that implements the FileIO interface.
 */
public class SaveToCSV extends AbstractFileIO{

  public SaveToCSV() {
    super("csv");
  }

  @Override
  protected String formatData(Object data) {
    return data.toString();
  }
}
