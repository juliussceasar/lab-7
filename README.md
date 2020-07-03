## Содержание <a name="Содержание"></a> 
* [Задание лабораторной работы.](#Задание)
* [Код на сервере.](#Пояснения)

### Задание <a name="Задание"></a>
![Alt-текст](https://imgur.com/DFByCmn)
[Содержание](#Содержание)

### Код на сервере. <a name="Пояснения"></a>
+ Запуск программы начинается [из метода main класса Main](/src/Main.java).
+ Там создаётся CollectionManager, управляющий коллекцией (создание и манипулирование);
DatabaseManager, управляющий коллекцией в бд (то же самое на бд);
CommandReceiver, описывающий основную логику команд (делегирует выполнение CollectionManager, ответ выполнения отправляет как объект SerializedMessage на клиент)
Controller, считывает данные, поступающие с клиента 
+ Чтобы запустить сервер, пометить папку \lab-7\src\Server как source, Client пометить как excluded.
+ Чтобы запустить клиент, пометить \lab-7\src\Client как source, Server как excluded.

+ Клиент и сервер находятся в обмене данными, эти данные передаются посредством [сериализованных команды](/src/Commands/SerializedCommands).
Они выделены в 4 типа:
1. SerializedArgumentCommand - класс для сериализации команд с аргументом.
2. SerializedObjectCommand - класс для сериализации команд с объектами. Например add.
3. SerializedCombinedCommand - класс для сериализации комбинированных команд(с объектом и аргументом). Нужен только в update.
4. SerializedMessage - класс, который несет в себе обычное текстовое сообщение. Используется для уведомлений.

+ Получив одну из сериализованных команд, мы должны определить ее тип, делается это в [декрипторе](/src/Utils/CommandHandler/Decrypting.java)
```Java
if (o instanceof SerializedArgumentCommand) {  // Проверка на причастность к одной из сериалованных команд.
    SerializedArgumentCommand argumentCommand = (SerializedArgumentCommand) o; // Приводим типы.
    Command command = argumentCommand.getCommand(); // Получаем команду.
    String arg = argumentCommand.getArg(); // Получаем аргумент.
    command.execute(arg, socket);  // Вызываем конкретный класс команды. Внимание! Абстрактный класс команды изменен, не поленись зайди и посмотри что там изменилось.
}
```


+ После экзекюта команды, мы попадаем в соответствующий метод [ресивера](/src/Commands/CommandReceiver.java)
```Java
public void clear() throws IOException {
    CollectionManager.clear();  // Делаем нужную работу через менеджер коллекции.
    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream(  // Создаем выходной поток объектов для клиента. 
    out.writeObject(new SerializedMessage("Коллекция успешно очищена."));  // Шлем сообщение на клиент.
    logger.info(String.format("Клиенту %s:%s отправлен результат работы команды CLEAR", socket.getInetAddress(), socket.getPort())); // Логгируем
}
```
