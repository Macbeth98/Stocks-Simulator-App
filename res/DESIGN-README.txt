Design Description:

Model:

- The model that the controller interacts with is of the PortfolioList interface type. 
PortfolioListImpl is the class that implements this interface. It has 


Controller:

- The controller interface is defined in src/PortfolioController.java.
- It contains only a single method(void go()) that the controller class, src/PortfolioControllerImpl.java, implements.
- This class has a public constructor which takes two arguments, of Reader and Appendable interface types respectively.

View:

- The view interface is defined in src/PortfolioView.java.
- The methods defined in the view interface as used to render the text based user interface in the application.
- The implementation of this interface is in the class, src/PortfolioListImpl.java.
- The view class has a public constructor which takes one argument, of Appendable interface type.