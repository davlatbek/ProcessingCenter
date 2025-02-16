# Запуск приложения, тестов и генерация отчета

## 1. Чтобы собрать приложение, введите находясь в директории проекта в командной строке

_$ mvn package -DskipTests_

## 2. Чтобы запустить приложение локально

_$ java -jar target/processingcenter-0.0.1-SNAPSHOT.jar_

## 3. Чтобы запустить тесты введите

_$ mvn clean test_

## 4. Для того, чтобы сгенерировать Allure репорт по всем тестам

_$ allure serve allure-results_


# Создание эмулятора процессинга и веб-интерфейса для него

## Общая задача:
- Создать систему процессинга денежных средств (как в банке) 
  - существуют постоянно обновляемые данные о передвижении денежных средств, 
содержащиеся в некоторой базе данных 
  - система должна обрабатывать эти данные и совершать транзакции

## Технологии:
- База данных **MySQL** или **PostgreSQL**. Структура базы данных оставлена на усмотрение исполнителя задания.
- Сама система должна представлять собой веб­приложение, написанное на языке Java 7-8, развернутое с помощью **Tomcat 7**
- Для создания тестов возможно использовать **Selenium API**
- Для разработки рекомендуем использовать  **IntelliJ Idea**

## Требования к системе:
- Должна быть возможность совершать следующие операции через веб-интерфейс:
  - Управлять банковскими счетами:
    - добавлять/удалять банковские счета (пользовательские аккаунты)
    - получать список всех существующих банковских счетов
    - получать остаток средств на указанном банковском счете
  - Управлять денежными средствами:
    - вычитать из баланса указанную сумму с указанного банковского счета
    - добавлять указанную сумму на указанный банковский счет
    - переводить указанную сумму с одного банковского счета на другой
  - Система должна запрещать появление отрицательного баланса на банковском счете
  - Система должна обеспечивать консистентность данных при любых нагрузках
  - Запрещается использовать Optimistic/Pessimistic Locking (и другие техники) средствами СУБД
  
## Тестирование системы:
- Тесты должны симулировать ситуацию, и проверя корректность конечного состояния банковских счетов.
 
## Ожидаемый результат:
- Итогом выполнения задания будут являться:
  - работающая ссылка на веб­приложение
  - исходный код
  - набор тестов