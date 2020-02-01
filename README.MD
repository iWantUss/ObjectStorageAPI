##ObjectStorageAPI
Данный проект разрабатывается с целью удобного и быстрого доступа к облачным данным
за счёт промежуточного сохранения данных на локальной машине.

Хранилище реализует паттерн ``Компоновщик (Дерево, Composite)``
###Приступая к работе
Для соединения с YandexObjectStorage необхдимо [настроить AWS SDK](https://cloud.yandex.ru/docs/storage/instruments/aws-sdk-java).
При инициализации `ComponentsStorage` передайте в конструктор:
```
val yandexConfig: YandexObjectStorageConfig = YandexObjectStorageConfig(
         /*YOUR_SECRET_KEY*/,
         /*YOUR_ACCESS_KEY*/
     ),
val backet: String = /*Имя заранее созданного бакета*/,
val basePathLocalHost: String = /*Локальная директория для хранения компонентов на диске*/
```

