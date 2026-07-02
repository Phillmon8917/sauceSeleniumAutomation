# SauceDemo Automation

BDD test automation for [saucedemo.com](https://www.saucedemo.com/), built with Java, Selenium WebDriver, Cucumber and TestNG, with Allure reporting.

## Stack

- **Java 21 / Maven**
- **Selenium WebDriver 4.45**
- **Cucumber** (`cucumber-java` + `cucumber-testng`) for Gherkin-style BDD
- **TestNG** as the test runner
- **Allure** for reporting (screenshots, logs and failure videos attached per scenario)
- **[solidground-selenium](https://github.com/Phillmon8917/solidground-selenium)** — the in-house Selenium automation library (page object base classes, element/browser/assertion actions, smart waits, fault reporting and logging) that this project is built on top of

## Project structure

```
src/main/java/com/saucedemo/
  base/BasePage.java              project base page, extends solidground's BasePage
  config/DriverFactory.java       creates/holds the WebDriver per thread
  config/Pages.java               caches one page-object instance per scenario
  pages/                          page objects (Login, Inventory, Cart, Checkout x2, NavigationMenu)
  extensions/video/               screen recording (not part of solidground-selenium)

src/test/java/com/saucedemo/
  runners/TestNGRunner.java       Cucumber + TestNG entry point
  hooks/Hooks.java                per-scenario lifecycle: screenshot/log/video handling
  stepdefinitions/                Gherkin step implementations

src/test/resources/features/      login.feature, cart.feature, checkout.feature
```

## Setup

1. Copy `.env.example` to `.env` and adjust if needed:
   ```
   URL=https://www.saucedemo.com/
   BROWSER=chrome
   HEADLESS=false
   ```
2. Have Chrome, Firefox or Edge installed (`BROWSER` selects which one; Selenium Manager resolves the matching driver automatically).

## Running the tests

```bash
mvn test
```

Run a subset by Cucumber tag:

```bash
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@login and @negative"
```

## Reporting

Every scenario run produces, attached to Allure:

- a **screenshot** at the end of the scenario (pass or fail)
- the **execution log** slice for that scenario (solidground's `LoggerUtil` output)
- a **screen recording**, but only kept and attached when the scenario **fails** — passing scenarios have their recording discarded to avoid filling up disk space. Failed-scenario recordings are also kept on disk under `target/videos/failed/`.

View the report:

```bash
allure serve target/allure-results
```

(or `mvn allure:report` / `mvn allure:serve` if you don't have the Allure CLI installed globally.)

## Locator strategy

Locators use SauceDemo's `data-test` attributes, which is what the app publishes as its stable automation hook — CSS classes and generated ids on this app can carry incidental state (e.g. an inconsistent trailing space) or change with app state.

One thing worth calling out: the per-product "Add to cart" / "Remove" button is **not** located by its `data-test` id. That id (`add-to-cart-<slug>` / `remove-<slug>`) is derived from the product name and flips depending on whether the item is already in the cart, so pinning to one value is fragile. Instead, `InventoryPage` and `CartPage` locate the button relative to the product's name text, scoped to the item's container via `data-test="inventory-item"`, which stays stable regardless of cart state, product ordering, or which page (inventory vs. cart) renders it.

## Notes

- `.env`, execution logs, Allure results and screen recordings are all git-ignored — they're run artifacts, not source.
- Each scenario gets its own browser session (`DriverFactory`) and its own cached set of page objects (`Pages`), so scenarios are isolated and can be run in parallel.
