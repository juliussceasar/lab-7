## Содержание <a name="Содержание"></a> 
* [Задание лабораторной работы.](#Задание)
* [Код на сервере.](#Пояснения)

### Задание <a name="Задание"></a>
![Alt-текст](https://imgur.com/DFByCmn)
[Содержание](#Содержание)

### Код на сервере. <a name="Пояснения"></a>
+ Запуск программы начинается [из метода main класса Main](/src/Server/Main.java).
+ Там создаются: 
1. CollectionManager, управляющий коллекцией (создание и манипулирование);
2. DatabaseManager, управляющий коллекцией в бд (то же самое на бд);
3. CommandReceiver, описывающий основную логику команд (делегирует выполнение CollectionManager, ответ выполнения отправляет как объект SerializedMessage на клиент)
4. Controller, считывает данные, поступающие с клиента 
+ Чтобы запустить сервер, пометить папку \lab-7\src\Server как source, Client пометить как excluded.
+ Чтобы запустить клиент, пометить \lab-7\src\Client как source, Server как excluded.

+ Клиент и сервер находятся в обмене данными, эти данные передаются посредством [сериализованных команды](/src/Server/Commands/SerializedCommands).
Они выделены в 4 типа:
1. SerializedArgumentCommand - класс для сериализации команд с аргументом.
2. SerializedObjectCommand - класс для сериализации команд с объектами. Например add.
3. SerializedCombinedCommand - класс для сериализации комбинированных команд(с объектом и аргументом). Нужен только в update.
4. SerializedMessage - класс, который несет в себе обычное текстовое сообщение. Используется для уведомлений.

+ Получив одну из сериализованных команд, мы должны определить ее тип, делается это в [трансляторе](/src/Server/Utils/CommandHandler/Translating.java)
```Java
if (o instanceof SerializedCommand) {
            SerializedCommand serializedCommand = (SerializedCommand) o;
            ACommand command = serializedCommand.getCommand();
            command.execute(serializedCommand, socket, commandReceiver);
        }
```


+ После execute команды, мы попадаем в соответствующий метод [CommandReceiver](/src/Server/Commands/CommandReceiver.java)
```Java
public void show(SerializedCommand command, Socket socket) throws IOException, DatabaseException {
        if (checkUser(command.getLogin(), command.getPassword(), socket)) {
            sendObject(socket, new SerializedMessage(collectionManager.show()));
            System.out.println(String.format("Клиенту %s:%s отправлен результат работы команды SHOW", socket.getInetAddress(), socket.getPort()));
        }
    }
```
