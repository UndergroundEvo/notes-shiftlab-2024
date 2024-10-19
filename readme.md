
# Техническое задание "Заметки"

Программа представляет собой приложение по созданию, просмотру и редактированию заметок.  
Есть возможность добавлять и удалять теги к заметкам, поиск заметок по названию.

## Библиотеки

- Koin: для реализации инъекции зависимостей
- Room: хранение данных о заметках в таблице
- KSP: для генерации кода библиотек
- Kotlin Serialization: для Type-safety навигации
- Compose Navigation: для навигации между экранами
- Gson Converter: для TypeConverter из list в json для хранения в БД

## Модули приложения

- app: содержит MainActivity и Koin модули для DI
- design: общие composable элементы
- shared:data: общие data элементы ~~(используется для уведомлений)~~
- shared:notes общая логика заметок и бизнес логики
- feature:notelist основного экрана с выбором заметок
- feature:noteview экран для просмотра/редактирования заметок

## Фичи
- Поиск по названию заметки или по названию тега
- Теги для запоминаемости раскрашиваться в зависимости от содержимого (`tagsColor.kt`)
- При создавании заметки, сначала создаться заметка, потом заполняется, при заполнении заметка автоматически заполняется, тем самым сохраняя черновик
- от MaterialTheme есть автоматически темная и светлая тема
