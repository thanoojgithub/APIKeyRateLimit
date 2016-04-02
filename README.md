# APIKeyRateLimit
Spring Boot REST example
---------------------------------


Requirement:

You are provided with hotels database in CSV (Comma Separated Values) format.

We need you to implement HTTP service, according to the API requirements described below. You may use any language or platform that you like: C#/Java/Scala/etc.

RateLimit: API calls need to be rate limited (request per 10 seconds) based on API Key provided in each http call.
  - On exceeding the limit, api key must be suspended for next 5 minutes. 
  - Api key can have different rate limit set, in this case from configuration, and if not present there must be a global rate limit applied.
  - Search hotels by CityId
  - Provide optional sorting of the result by Price (both ASC and DESC order).
Note: Please don’t use any external library or key-value store for RateLimit. You need to create a InMemory Implementation for RateLimit. 

-----------------run---------------

c:\> mvn clean install

c:\> java -jar APIKeyRateLimit-1.0.jar --spring.config.location=D:\APIKeyRateLimit\application.properties
