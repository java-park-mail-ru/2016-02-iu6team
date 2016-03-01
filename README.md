# 2016-02-iu6team
Игра DUEL. Дуэльный шутер летающими "кораблями"

Для добавления записей выполнить client.py

Проверка логина:

curl -H 'Content-Type: application/json' -X PUT -d '{"login": "login", "password" : "password" }' localhost:8080/api/user

curl -H 'Content-Type: application/json' -X PUT -d '{"login": "test", "password" : "test" }' localhost:8080/api/user