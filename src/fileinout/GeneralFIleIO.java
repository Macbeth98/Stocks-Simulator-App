package fileinout;

/**
 * This class has methods that will save the given data into String format and save to text file.
 * The class extends AbstractFileIo, an abstract class that implements the fileinout.FileIO interface.
 */
public class GeneralFIleIO extends AbstractFileIO {

  public GeneralFIleIO() {
    super("txt");
  }

  @Override
  protected String formatData(Object data) {
    return data.toString();
  }
}
