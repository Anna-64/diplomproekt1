## Дипломный проект профессии «Тестировщик»
В рамках дипломного проекта требовалось автоматизировать тестирование комплексного сервиса покупки тура, 
взаимодействующего с СУБД и API Банка.

База данных хранит информацию о заказах, платежах, статусах карт, способах оплаты.

Для покупки тура есть два способа: с помощью карты и в кредит. 
В приложении используются два отдельных сервиса оплаты: Payment Gate и Credit Gate.

## Запуск приложения

## Подготовительный этап
- Установить и запустить IntelliJ IDEA;
- Установать Node.js;
- Установить Docker Desktop;

 в Docker 
1) клонировать репозиторий с Github git clone https://github.com/Anna-64/diplomproekt.git

   в IntelliJ IDEA
2) Открыть проект в IntelliJ IDEA, отокрыть терминал (Fn + Alt + F12);
3) переходим в папку gate-simulator (cd gate-simulator);
4) запускаем банковский симулятор коммандой npm start;
5) открываем в терминале (+) новую вкладку и вводим команду docker-compose up -d --force-recreate;
6) открываем в терминале (+) новую вкладку и запускаем jar файл коммандой java -jar aqa-shop.jar.
7) открываем страницу в Google Chrome http://localhost:8080/.
8) после работы с Docker останавливаем контейнеры docker-compose down
9) после работы с банковским симулятор в терминале нажимаем ctl + C


 ## Запуск тестового приложения
в IDEA открываем в терминале (+) новую вкладку и вводим команду:
- команда для запуска с подключением MySQL: java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar aqa-shop.jar
- команда для запуска с подключением PostgreSQL: java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar aqa-shop.jar
   
### Запустить авто-тесты
- команда для запуска с подключением MySQL: ./gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app allureServe
- команда для запуска с подключением PostgreSQL: ./gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app allureServe
 Для завершения работы allureServe выполнить команду: Ctrl + С
