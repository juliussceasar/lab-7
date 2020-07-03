package Utils.Database;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import BasicClasses.*;
import Collection.CollectionManager;
import Exceptions.DatabaseException;

import Utils.HashEncrypter;
import org.apache.ibatis.jdbc.ScriptRunner;

//Singleton
public class DatabaseManager {
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "koshara2001";
    private final String salty = "im_tired_of_physics";
    private final HashEncrypter hashEncrypter;

    public DatabaseManager(HashEncrypter hashEncrypter) {
        this.hashEncrypter = hashEncrypter;
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Драйвер подключён");
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер не подключён");
            e.printStackTrace();
            System.exit(1);
        }
        buildTables();
    }

    public void buildTables(){
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            ScriptRunner sr = new ScriptRunner(connection);
            try {
                Files.walk(Paths.get("C:/Users/mi/Documents/lab-6/src/Server/Utils/Database/SQL"))
                        .filter(Files::isRegularFile)
                        .forEach(path -> {
                            Reader reader = null;
                            try {
                                reader = new BufferedReader(new FileReader(String.valueOf(path)));
                                sr.runScript(reader);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Добавляет элемент в базу данных.
     *
     * @param humanBeing  добавляемый элемент
     * @param username имя пользователя (создателя элемента)
     * @return назначенный базой данных id для этого элемента
     * @throws DatabaseException если что-то пошло не так при работе с базой данных
     */
    public Integer addElement(HumanBeing humanBeing, String username) throws DatabaseException {
        return this.<Integer>handleQuery((Connection connection) -> {
            try {
                Car car = humanBeing.getCar();
                String addCarSql = "INSERT INTO car (name, cool)" +
                        "VALUES (?, ?)";

                PreparedStatement statement = connection.prepareStatement(addCarSql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, car.getName());
                statement.setBoolean(2, car.getCool());
                statement.executeUpdate();

                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {

                    String addElementSql = "INSERT INTO humanBeing (id, name, coordinateX, coordinateY, creationDate, " +
                            "realHero, hasToothpick, impactSpeed, soundtrackName, weaponType, mood, login)" +
                            " SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
                            " FROM \"user\"" +
                            " WHERE \"user\".login = ?";

                    PreparedStatement elementStatement = connection.prepareStatement(addElementSql, Statement.RETURN_GENERATED_KEYS);
                    Coordinates coordinates = humanBeing.getCoordinates();
                    elementStatement.setLong(1, humanBeing.getId());
                    elementStatement.setString(2, humanBeing.getName());
                    elementStatement.setDouble(3, coordinates.getX());
                    elementStatement.setDouble(4, coordinates.getY());
                    elementStatement.setTimestamp(5, Timestamp.valueOf(humanBeing.getCreationDate().toLocalDateTime()));
                    elementStatement.setBoolean(6, humanBeing.getRealHero());
                    elementStatement.setBoolean(7, humanBeing.getHasToothpick());
                    elementStatement.setFloat(8, humanBeing.getImpactSpeed());
                    elementStatement.setString(9, humanBeing.getSoundtrackName());
                    elementStatement.setString(10, String.valueOf(humanBeing.getWeaponType()));
                    elementStatement.setString(11, String.valueOf(humanBeing.getMood()));
                    elementStatement.setString(12, username);
                    elementStatement.setString(13, username);


                    elementStatement.executeUpdate();
                    ResultSet result = elementStatement.getGeneratedKeys();
                    result.next();

                    System.out.println("В коллекцию добавлен элемент");
                    return result.getInt(1);
                }
            } catch (SQLException e) {
                System.out.println("Ошибка при добавлении элемента!" + e.getMessage());
            }
            return null;
        });
    }

    /**
     * Обработка запроса без возврата значения.
     *
     * @param queryBody тело запроса (Consumer)
     * @throws DatabaseException если что-то пошло не так при работе с базой данных
     */
    public void handleQuery(Consumer<Connection> queryBody) throws DatabaseException {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            queryBody.accept(connection);
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при работе с базой данных: " + e.getMessage());
        }
    }

    /**
     * Обработка запроса с возвратом значения.
     *
     * @param queryBody тело запроса (Function)
     * @param <T>       тип возвращаемого значения
     * @return запрошенное у базы данных значение
     * @throws DatabaseException если что-то пошло не так при работе с базой данных
     */
    public <T> T handleQuery(Function<Connection, T> queryBody) throws DatabaseException {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            return queryBody.apply(connection);
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при работе с базой данных: " + e.getMessage());
        }
    }


    /**
     * Удаляет элемент из базы данных по id.
     *
     * @param id       id удаляемого элемента
     * @param username пользователь, который пытается удалить элемент
     * @return true, если успешно; false, если нет
     * @throws DatabaseException если что-то пошло не так при работе с базой данных
     */
    public boolean removeById(Long id, String username) throws DatabaseException {
        return handleQuery((Connection connection) -> {
            String query =
                    "DELETE from humanbeing" +
                            " USING \"user\"" +
                            " WHERE humanBeing.id = ? and \"user\".login = humanBeing.login" +
                            " AND \"user\".login = ?";


            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, id);
                statement.setString(2, username);
                int rowsDeleted = statement.executeUpdate();
                return rowsDeleted > 0;
            } catch (SQLException e) {
                System.out.println("Произошла ошибка при удалении элемента по id");
                return false;
            }
        });
    }

    /**
     * Обновляет элемент базы данных с указанным id.
     *
     * @param humanBeing  новый элемент
     * @param id       id обновляемого элемента
     * @param username пользователь, который пытается обновить элемент
     * @return true, если успешно; false, если нет
     * @throws DatabaseException если что-то пошло не так при работе с базой данных
     */
    public boolean updateById(HumanBeing humanBeing, Long id, String username) throws DatabaseException {
        return handleQuery((Connection connection) -> {
                try {
                    connection.createStatement().execute("BEGIN TRANSACTION;");

                    String query =
                            "UPDATE humanBeing" +
                                    " SET name = ?," +
                                    "coordinateX = ?," +
                                    "coordinateY = ?," +
                                    "realHero = ?," +
                                    "hasToothPick = ?," +
                                    "impactSpeed = ?," +
                                    "soundtrackName = ?," +
                                    "weaponType = ?," +
                                    "mood = ?," +
                                    " FROM humanBeing, \"user\"" +
                                    " WHERE humanBeing.id = ? AND \"user\".login = ? and \"user\".login = humanBeing.login;" +
                                    //
                                    " UPDATE car" +
                                    " SET name = ?," +
                                    "cool = ?," +
                                    " FROM humanBeing, \"user\"" +
                                    " WHERE humanBeing.id = ? AND \"user\".login = humanBeing.login and \"user\".login = ?;";
                    PreparedStatement statement = connection.prepareStatement(query);
                    //name + coordinates + time + realHero + hasToothpick +
                    // impactSpeed + soundtrackName + weaponType + mood + car

                    statement.setString(1, humanBeing.getName());
                    statement.setDouble(2, humanBeing.getCoordinates().getX());
                    statement.setDouble(3, humanBeing.getCoordinates().getY());
                    //statement.setTimestamp(4, Timestamp.valueOf(humanBeing.getCreationDate().toLocalDateTime()));
                    statement.setBoolean(4, humanBeing.getRealHero());
                    statement.setBoolean(5, humanBeing.getHasToothpick());
                    statement.setFloat(6, humanBeing.getImpactSpeed());
                    statement.setString(7, humanBeing.getSoundtrackName());
                    statement.setString(8, humanBeing.getWeaponType().name());
                    statement.setString(9, humanBeing.getMood().name());
                    statement.setLong(10, id);
                    statement.setString(11, username);

                    statement.setString(12, humanBeing.getCar().getName());
                    statement.setBoolean(13, humanBeing.getCar().getCool());
                    statement.setLong(14, id);
                    statement.setString(15, username);

                    int result = statement.executeUpdate();

                    connection.createStatement().execute("COMMIT;");

                    return result > 0; // Если true, значит результат не пустой и записи обновлены
                } catch (SQLException e) {
                    System.out.println("Произошла ошибка при обновлении элемента по id");
                    return false;
                }
        });
    }

    /**
     * Загружает коллекцию из базы данных.
     *
     * @param collectionManager куда загрузить коллекцию
     * @throws DatabaseException если что-то пошло не так при работе с базой данных
     */
    public void loadCollectionFromDatabase(CollectionManager collectionManager) throws DatabaseException {

        handleQuery((connection -> {
            collectionManager.initList();
            try {
                    ResultSet rsCar = connection.createStatement().executeQuery("select * from car");
                    ResultSet rs = connection.createStatement().executeQuery("select * from humanbeing order by id");

                    HumanBeing humanBeing;
                    while (rs.next() && rsCar.next()) {

                        //name + coordinates + realHero + hasToothpick +
                        // impactSpeed + soundtrackName + weaponType + mood + car

                        humanBeing = new HumanBeing(
                                rs.getString("name"),
                                new Coordinates(
                                        rs.getLong("coordinatex"),
                                        rs.getDouble("coordinatey")
                                ),
                                rs.getBoolean("realHero"),
                                rs.getBoolean("hasToothpick"),
                                rs.getFloat("impactSpeed"),
                                rs.getString("soundtrackName"),
                                WeaponType.valueOf(rs.getString("weaponType")),
                                Mood.valueOf(rs.getString("mood")),
                                new Car(
                                        rsCar.getString("name"),
                                        rsCar.getBoolean("cool")
                                )
                        );
                        humanBeing.setId(rs.getInt("id"));
                        collectionManager.add(humanBeing);
                    }
                    System.out.println("Коллекция загружена из базы данных");
                } catch (SQLException e) {
                    System.out.println("Произошла ошибка при загрузки коллекции из бд" + e.getMessage());
                }
        }));
    }

    /**
     * Получает пароль по имени пользователя.
     *
     * @param username имя пользователя
     * @return пароль (хешированный)
     * @throws DatabaseException если что-то пошло не так при работе с базой данных
     */
    public String getPassword(String username) throws DatabaseException {
        return this.handleQuery((Connection connection) -> {
                try {
                    String query = "SELECT (\"password\")" +
                            " FROM \"user\"" +
                            " WHERE \"user\".login = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, username);

                    ResultSet result = statement.executeQuery();

                    if (result.next()) {
                        return result.getString("password");
                    }
                    else {
                        return null;
                    }
                } catch (SQLException e) {
                    System.out.println("Сбой при получении пароля");
                    return null;
                }
        });
    }

    /**
     * Проверяет, существует ли пользователь.
     *
     * @param username имя пользователя
     * @return true, если существует; false, если нет
     */
    public boolean doesUserExist(String username) throws DatabaseException {
        return this.<Boolean>handleQuery((Connection connection) -> {
                try {
                    String query = "SELECT COUNT(*)" +
                            " FROM \"user\"" +
                            " WHERE id = \"user\".id AND \"user\".login = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, username);
                    ResultSet result = statement.executeQuery();

                    result.next();

                    return result.getInt("count") > 0;
                } catch (SQLException e) {
                    System.out.println("Сбой при проверке существования юзера в бд");
                    return false;
                }
        });
    }

    /**
     * Добавляет пользователя в базу данных.
     *
     * @param username имя пользователя
     * @param password пароль (хешированный)
     * @throws DatabaseException если что-то пошло не так при работе с базой данных
     */
    public void addUser(String username, String password) throws DatabaseException {
        handleQuery((Connection connection) -> {
                try {
                    String query = "INSERT INTO \"user\" (\"login\", \"password\")" +
                            "VALUES (?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, username);
                    statement.setString(2, hashEncrypter.encryptString(password + salty));

                    statement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Сбой при добавлении юзера в бд");
                }
        });
    }

    /**
     * Удаляет все элементы, владельцем которых является пользователь.
     *
     * @param username имя пользователя
     * @return список id элементов, которые были удалены
     * @throws DatabaseException если что-то пошло не так при работе с базой данных
     */
    public List<Integer> clear(String username) throws DatabaseException {
        return handleQuery((Connection connection) -> {
                try {
                    String query = "DELETE FROM humanbeing" +
                            " USING \"user\"" +
                            " WHERE \"user\".login = ? and \"user\".login = humanBeing.login" +
                            " RETURNING humanbeing.id;";

                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, username);
                    ResultSet result = statement.executeQuery();

                    ArrayList<Integer> ids = new ArrayList<>();

                    while (result.next()) {
                        ids.add(result.getInt("id"));
                    }
                    return ids;
                } catch (SQLException e) {
                    System.out.println("Сбой при удалении элементов пользователя" + e.getMessage());
                    return null;
                }
        });
    }

    /**
     * @param username имя пользователя
     * @return список id элементов, которые создал пользователь
     * @throws DatabaseException если что-то пошло не так при работе с базой данных
     */
    public List<Long> getIdOfUserElements(String username) throws DatabaseException {
        return handleQuery((Connection connection) -> {
                try {
                    String query = "SELECT humanBeing.id FROM humanBeing, \"user\" " +
                            " WHERE \"user\".login = ? and \"user\".login = humanBeing.login";

                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, username);
                    ResultSet result = statement.executeQuery();

                    ArrayList<Long> ids = new ArrayList<>();

                    while (result.next()) {
                        ids.add((long) result.getInt("id"));
                    }
                    return ids;
                } catch (SQLException e ) {
                    System.out.println("Сбой при получении списков id элементов пользователя" + e.getMessage());
                    return null;
                }
        });
    }


    public boolean validateUserData(String login, String password) throws DatabaseException {
        String realPassword = getPassword(login);

        return hashEncrypter.encryptString(password + salty).equals(realPassword);
    }

    public boolean removeBySpeed(Float humanSpeed, String username) throws DatabaseException {
        return handleQuery((Connection connection) -> {
            String query =
                    "DELETE from humanbeing" +
                            " USING \"user\" " +
                            " WHERE humanBeing.impactSpeed = ?" +
                            "AND \"user\".login = humanBeing.login AND \"user\".login = ?;";
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setFloat(1, humanSpeed);
                statement.setString(2, username);

                int rowsDeleted = statement.executeUpdate();
                return rowsDeleted > 0;
            } catch (SQLException e) {
                System.out.println("Произошла ошибка при удалении элемента по id");
                return false;
            }
        });
    }
}
