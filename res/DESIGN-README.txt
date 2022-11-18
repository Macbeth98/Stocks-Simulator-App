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