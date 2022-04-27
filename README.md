# job4j_auth

RestFull API архитектура.

RestFull API архитектура - это архитектура клиент-серверного приложения базирующаяся на 6 принципах.

1. Универсальный интерфейс взаимодействия. (Uniform Interface)

2. Запросы без состояния. (Stateless)

3. Поддержка кеширования. (Cacheable)

4. Клиент-серверная ориентация.

5. Поддержка слоев (Layered System)

6. Возможность выполнять код на стороне клиента (Code on Demand)

# Описание проекта
REST service для работы с моделью "Person" и CRUD операции к нему

GET/person/ список всех пользователей.

GET/person/{id} - пользователь с id.

POST/person/ - создает пользователя.

PUT/person/ - обновляет пользователя.

DELETE/person/ - удаляет.

# Тестирование с помощью cURL
1. Получаем список всех пользователей
curl -i http://localhost:8080/person/

2. Получаем данные пользователя с id = 1
curl -i http://localhost:8080/pesrson/1

3. Создадим нового пользователя.
curl -H 'Content-Type: application/json' -X POST -d '{"login":"job4j@gmail.com","password":"123"}' http://localhost:8080/person/
В этом запросе обязательно нужно указать тип данных -H 'Content-Type: application/json'

4. Обновим созданного пользователя.
curl -i -H 'Content-Type: application/json' -X PUT -d '{"id":"5","login":"support@job4j.com","password":"123"}' http://localhost:8080/person/

5. И теперь удалим пользователя.
curl -i -X DELETE http://localhost:8080/person/5
