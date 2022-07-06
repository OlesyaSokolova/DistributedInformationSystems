# JAXB, JDBC

# Часть 1:
Модифицирована задача 1 на использование десериализации JAXB для
встретившихся элементов node. Для этого в проекте настроена генерация оберток JAXB:  
  1. Настроено использование плагина jaxb для gradle;  
  2. Настроены пути до файла схемы и каталог для генерации кода;  

# Часть 2:

К проекту подключен jdbc драйвер posgtgresql. Использется DriverManager
для получения соединений.  
Разработана схема данных базы SQL для хранения объектов, определенных [схемой OSM](lab2/lab2part2/src/main/schema/OSMSchema.xsd).  
Создан [класс](lab2/lab2part2/src/main/java/ru/nsu/fit/sokolova/dis/utils/DataBaseManager.java), ответственный за инициализацию БД с помощью DDL SQL.  
Созданы [классы DAO](lab2/lab2part2/src/main/java/ru/nsu/fit/sokolova/dis/dao) для работы с таблицами основных сущностей OSM и
сохранению их в БД (имплементируют интерфейс ModelDao.java).  
Реализован загрузчик данных элементов node в БД с использованием трех стратегий, замерены скорости вставки (записей в секунду):    
  1. Конструирование INSERT как строки и исполнение Statement.exequteQuery (стартегия "STRING");
  2. Использование PreparedStatement (стратегия "PREPARED_STATEMENT");
  3. Использование batch механизма и вставка порциями данных (стратегия "BATCH").
  
Результат выполнения программы при обработке 10000 элементов node:

    Limit of elements to process: 10000  
    Input xml file: /home/olesya/IdeaProjects/RU-NVS.osm.bz2  
    Started counting...Please wait  
    Speed for strategy "PREPARED_STATEMENT": 3645.2150716905635 insertions/seconds.  
    Speed for strategy "BATCH": 10362.085308056872 insertions/seconds.  
    Speed for strategy "STRING": 2354.512168856343 insertions/seconds.

 
