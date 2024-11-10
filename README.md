# Описание
Проект для изучения процесса создания REST API с документацией.\
Сервис для создания и отслеживания задач.
# Требования
- [x] Аутентификация с использованием JWT-токенов (по email и паролю)
- [x] Авторизация (2 роли: ADMIN, USER)
- [ ] CRUD операции
  - [x] над пользователями
  - [ ] над задачами
  - [x] над списками задач
  - [ ] над комментариями
  - [ ] над членами списков
- [x] Запуск через docker compose
- [x] Пользователи могут определять какие пользователи могут видеть их задачи
- [x] Документация должна быть написана с помощью Swagger
- [x] Задачи должны иметь тип, входить в список, содержать комментарии и описание
# Стек технологий
<details>
<summary>
Spring Boot
</summary>

### Зависимости
* DevTools
* Lombok
* Web
* JPA
* Postgres
* ModelMapper
* Redis
* Validation
* Liquibase
* Docker Compose
* Security
* Java JWT
* Test containers
* OpenAPI Doc

</details>

<details>
<summary>
БД
</summary>

* PostgreSQL
* Redis

</details>

# Документация
Документация Swagger:\
http://localhost:8080/swagger-ui/index.html

# Запуск и развертывание
Для запуска на компьютере должен быть установлен и запущен Docker.

| Процесс    | Порт |
| ---------- | ---- |
| Приложение | 8080 |
| Redis      | 6379 |
| PostgreSQL | 5432 |

Первый запуск (команды выполняются в директории с `compose.yaml`)
```
docker compose up --build
```
Все последующие запуски
```
docker compose up
```

