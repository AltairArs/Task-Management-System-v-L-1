# Описание
Проект для изучения процесса создания REST API с документацией.\
Сервис для создания и отслеживания задач.
# Требования
- [x] Аутентификация с использованием JWT-токенов (по email и паролю)
- [ ] Авторизация (2 роли: ADMIN, USER)
- [ ] CRUD операция над задачами
- [x] Запуск через docker compose
- [ ] Пользователи могут определять какие пользователи могут видеть их задачи
- [x] Документация должна быть написана с помощью Swagger
- [ ] Задачи должны иметь тип, входить в список, содержать комментарии и описание
# Стек технологий
# Тесты
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

