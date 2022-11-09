### TO DOs:

- DO: Integrate AlphaVantage API to fetch stock prices for given dates - use abstract class and extend it to support API, and potentially other sources - ANANT
- BUG FIX: Portfolio Value should be same for same date

- SUPPORT FOR DIFFERENT FILE FORMATS WHILE SAVING/LOADING PORTFOLIO
- DO: Purchase functionality: Buying a specific stock on a specific date.
- DO: Add commission fee per transaction, standardise and make it configurable.
- FIX: Only purchase whole numbers pls

---

- DO: Cost basis for a portfolio -> Store purchase history per portfolio


QUESTIONS FOR PROF:
- Clarification for Buy/Sell on a specific date - Can we sell stock today, on the basis of it's price on a past date? Does it mean that the user has sold it on that day? Or today, using the price of that day?
- Using a database server - purchase history, portfolio attributes, can we store in a database?
- DISCUSS: Loading file should be in controller?