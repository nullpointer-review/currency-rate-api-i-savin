Необходимо разработать REST-сервис на базе Spring Framework (можно Spring Boot)
для получения курса иностранной валюты к рублю. В качестве источника данных следует
использовать ресурс [ЦБ России](http://www.cbr.ru/scripts/Root.asp).

Выбор библиотек и все допущения остаются на усмотрение разработчика.

Результат выполения задания должен содержать в себе исходный код приложения, 
а также инструкцию по сборке и запуску. Решение небходимо разместить на 
[гитхабе](https://github.com/) и предоставить ссылку.


API Приложения
--------------

### Запрос

    GET /api/rate/{code}/{date}


### Параметры

* code [строка 3 символа, обязателен] - трехсимвольный код валюты
* date [дата в формате yyyy-MM-dd, строка, опционален] - дата актуальности курса

Если в запросе не указана дата актуальности курса, то такой датой считается
следующий день от текущей даты осуществления запроса.

### Ответ

```json
{
    "code": "<Код валюты>",
    "rate": "<Курс>",
    "date": "<Дата актуальности>"
}
```

Ответ содержит следующие поля:

* code [строка 3 символа, обязателен] - трехсимвольный код валюты
* rate [десятичное число, строка, обязателен] - курс валюты
* date [дата в формате yyyy-MM-dd, строка, обязателен] - дата актуальности курса


### Примеры запросов

    GET http://example.com/currency/api/rate/USD

    GET http://example.com/currency/api/rate/USD/2015-09-24


### Пример ответа

```json
{
    "code": "USD",
    "rate": "66.0410",
    "date": "2015-09-24"
}
```

Курс доллара США на 24 сентября 2015 года составляет 66 рублей и 4,1 копейки.

### Сборка проекта

Проект собирается с помощью Apache Maven. Предусмотрено два вида сборки - в виде war-архива для последующей установки
на сервер приложений и в виде исполняемого jar-файла.
* Для сборки war-архива нужно в корне проекта выполнить команду mvn clean package
Архив CurrencyRates.war будет собран в папке targets. После установки на сервер приложений доступ осуществляется
по адресу http://localhost:8080/CurrencyRates/currency/api/USD/2003-02-02
* Для сборки исполняемого jar-архива нужно в корне проекта выполнить команду mvn clean package -Pstandalone.
Для запуска сервиса нужно выполнить команду java -jar target/CurrencyRates.jar, доступ к сервису осуществляется
по адресу http://localhost:8080/currency/api/EUR/2000-09-24
