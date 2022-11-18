## Assignment 5 - Stocks and Portfolios Pt 2
---

Introduction: This application provides a user a text based interface to create, save and load
multiple portfolios. Each portfolio consists of one or more stocks and their quantities.

Features of this application:

1. **User can manually create a portfolio**: User can input a portfolio name, stocks and their
   respective quantities and create a portfolio of these stocks. This portfolio will be saved to
   file, in a folder and persisted through multiple usages of the application.

2. **User can create a portfolio through a file**: User can input a valid portfolio name, and the
   absolute path of a file manually written which is of the correct format (you can refer to sample
   csv file for reference), to create a portfolio. This portfolio will be saved to file, in a folder
   and persisted through multiple usages of the application.

3. **User can examine the composition a portfolio** : User can examine the composition of an
   existing portfolio. User will be shown all the portfolios that already exist as part of the
   application, they can input a particular portfolio's name and view it's composition.

4. **User can see the value of a given portfolio on a given date**: User can view the value of a
   given portfolio for a specific date in the past. A date in the future given as input will be
   treated as an error.


### Flexible Portfolios
--

Introduction: In this assignment we introduce the following features to a new type of portfolios defined as flexible portfolios.
We first prompt the user to choose the type of portfolio they want to work on, i.e, either flexible or the earlier (inflexible) type.

Features of flexible portfolio:

1. **Modifying a portfolio**: User can modify a created portfolio by buying or selling stocks bought on a certain date.
2. **Commission Fee**: User can specify a commission fee per transaction. Negative commission values will not be allowed.
3. **AlphaVantage API integration**: All stock prices are fetched from the AlphaVantage open API. The API response is cached into a json file and the API is not called more than once for the same stock.
4. **Performance Graph**: The user can query each flexible portfolio for it's performance over a given date range. The graph is generated over a horizontal and vertical scale of stars and evenly distributed timestamps respectively.