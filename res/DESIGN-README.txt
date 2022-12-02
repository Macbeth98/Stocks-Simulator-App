Design Description:

Model:

- The model that the controller interacts with is of the PortfolioList interface type. 
PortfolioListImpl is the class that implements this interface. The PortfolioListImpl is composed of
List of Portfolios. The PortfolioListImpl class has a private Map variable that maps the portfolio
name and the Portfolio type. The PortfolioListImpl has methods that can create Portfolio manually
or through a file, fetch the current Portfolios, and it returns a Portfolio type Object when queried
using Portfolio Name. The PortfolioListImpl class automatically saves the created Portfolio
to a file.
    The Portfolio is an interface representing a Portfolio and is implemented by PortfolioImpl
class. The PortfolioImpl class is composed of list of PortfolioItem. Each PortfolioItem represents
a unique StockObject and its corresponding data. The PortfolioItem is a class which gives access to
methods that gives the data about the StockObject, the quantity of the stock and the cost per share
of the stock.
    The StockObject is an interface that represents a single stock and is implemented by
StockObjectImpl class. This class manages the stock's data, and it stores the price of the stock
on a date when the date was queried. The price as of now is being assigned by using random class.
But once a price is assigned for a stock at a date, that price will persist.

-> Accommodating the Flexible Portfolios in the model.
- The model has now new type of PortfolioList called FlexiblePortfolioList which represents the list of Flexible Portfolios.
- The FlexiblePortfolioList extends the PortfolioList and acts as the main model for the FlexiblePortfolioController flow and in the same way PortfolioList for inflexible Portfolio flow.
- There is an AbstractPortfolioList which abstracts the common method between these two Lists and also add their own helper methods.
- The FlexiblePortfolioList has a list of FlexiblePortfolio that contains methods pertaining to the flexible Portfolio such as Add stock, sell stock, etc.

-> Design changes made.
-- [ ]  Creating a protected constructor in PortfolioImpl as FlexiblePortfolioImpl extends it.
 - [ ]  Changing the variable access modifiers in PortfolioImpl from private to protected.
 - [ ]  Abstracted PortfolioList and PortfolioListImpl.
 - [ ]  Identified limitations of `java.util.Date` and changed to `java.time.LocalDate`

 -> Changes for Assignment 6 (Stocks Part 3). Accommodating the Portfolio Strategies.
 - Created a new Interface called FPortfolioListWithStrategy which extends the previous FlexiblePortfolioList interface.
 - This new interface has two methods, one for applying a one time investment on a given date with the given distribution of stocks.
 - The second is executing a portfolio investment strategy over period of time.
 - There is a new class called FPortfolioListWithStrategyImpl which implements the FFPortfolioListWithStrategy interface. This interface is the main controller facing model interface.
 - When the methods for Investment strategies were called, the above-mentioned class will delegate the functionality to a new interface called PortfolioStrategy which represents a methods
   that can be executed on a Portfolio.
 - The class called PSDollarCostAverage implements this interface and the methods that are delegated by FPortfolioListWithStrategyImpl class will be executed here.
 - For future executed strategies, we are using CompletableFuture class which executes a given piece of code at a given time.

Controller:
- The controller interface is defined in src/FlexiblePortfolioController.java.
- It contains only a single method(void goController()) that the controller class, src/PortfolioControllerImpl.java, implements.
- This class has a public constructor which takes two arguments, of Reader and Appendable interface types respectively.
Changes in controller for Assignment5:
- All the controller files are stored in the controller package.
- The FlexiblePortfolioController interface defines the controller interface for FlexiblePortfolios.
- The GenericPortfolioController interface defines the controller interface to handle both kinds of portfolios.
- The only change in the previous controller class (PortfolioControllerImpl) is that the switch case now follows the command design pattern.
- The commands for the Inflexible Portfolio controller reside in the (controller.portfolio.command package)
- The commands for the Flexible Portfolio controller reside in the (controller.flexibleportfolio.command package)
- The FlexiblePortfolioControllerImpl class also follows the command design pattern for the switch case.
Changes in controller for Assignment6:
- The GUI controller interface and implementation are stored in the controller.guicontroller package.
- The Features interface contains the methods used by the controller to react to the options chosen/inputs given by the user.
- The GUIController.java contains the implementations of these methods.
- The previous text based UI's controller for flexible and inflexible portfolios still remain in the same controller package and are
completely unchanged.

View:

- The view interface is defined in src/PortfolioView.java.
- The methods defined in the view interface as used to render the text based user interface in the application.
- The implementation of this interface is in the class, src/PortfolioListImpl.java.
- The view class has a public constructor which takes one argument, of Appendable interface type.
Changes for Assignment5:
- All the view files are stored in the view package.
- The FlexiblePortfolio views are stored in the view.flexibleportfolio package, with the interface and implementation
- The FlexiblePortfolioView interface extends the previous PortfolioView interface to inherit all it's methods
- The FlexiblePortfolioViewImpl class (implements FlexiblePortfolioView) also extends the PortfolioViewImpl class to inherit the implementations of the previous PortfolioView interface and override implementations as necessary.
- The GenericPortfolioView interface contains the methods and prompts for the generic portfolio controller, in the view package.
- The GenericPortfolioViewImpl class implements the aforementioned interface and it's methods for the menus and prompts for generic portfolios.
Changes for Assignment6:
- All the view frames for the gui are stored in the view.guiview package.
- The view methods are defined in the IView interface.
- There is an AbstractFrame class that implements the IView interface and defines a basic JFrame that is inherited by all other frames
rendered in the GUI of our application.
- MainFrameView.java contains the main menu rendered by our application.
- Every menu option renders a frame defined in its own class. Ex: CostBasisForm.java is rendered when the controller clicks the
Get Cost Basis menu option and so on.
- The text based views are still in the views package and are completely unmodified.