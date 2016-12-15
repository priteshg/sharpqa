set SELENIUM_VERSION=2.25.0
set HUB_URL=http://localhost:4444/grid/register
start java -jar selenium-server-standalone-%SELENIUM_VERSION%.jar -role hub
start java -jar selenium-server-standalone-%SELENIUM_VERSION%.jar -role webdriver -nodeConfig myConfig.json -hub %HUB_URL%


