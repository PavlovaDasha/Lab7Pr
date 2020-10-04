package MusicBand;

import Utilities.IoHelper;
import Utilities.Pair;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * класс, описывающий человека из музыкальной банды
 */
public class Person implements Comparable, Serializable {
    /**
     * поле имя человека
     */
    private String name; //Поле не может быть null, Строка не может быть пустой
    /**
     * поле дата рождения человека
     */
    private Timestamp birthday; //Поле может быть null
    /**
     * поле месторасположение человека
     */
    private Location location; //Поле может быть null

    /**
     * конструктор, позволяющий задать имя, дату рождения и местоположение человека из музыкальной банды
     * @param name имя человека
     * @param birthday дата рождения человека
     * @param location месторасположение человека
     */
    public Person (String name, Timestamp birthday, Location location){
        this.name = name;
        this.birthday = birthday;
        this.location = location;
    }

    public Person() {

    }

    /**
     * метод для получения строки с именем, датой рождения и местоположением человека из Music Band в формате csv
     * @return строка с именем, датой рождения и местоположением человека из Music Band в формате csv
     */


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * метод для получения строки с именем, датой рождения и местоположением человека из Music Band
     * @return строка с именем, датой рождения и местоположением человека из Music Band
     */
    @Override
    public String toString() {
        String result = String.format(Locale.US, "name: %s\n\t\t", name);
        if (birthday != null)
            result = result + String.format(Locale.US, "birthday: %s\n\t\t", birthday);
        if (location != null)
            result = result + String.format(Locale.US, "location: %s\n\t\t", location.toString());
        return result;
    }

    /**
     * метод для сравнения человека из Music Band
     * @param o человек из Music Band
     * @return положительное число, если вызывающий объект больше объекта, переданного в качестве параметра; отрицательное число, если вызывающий объект меньше объекта, переданного в качестве параметра; нуль, если объекты равны
     */
    @Override
    public int compareTo(Object o) {
        return ((Person) o).name.compareTo(this.name);
    }

    /**
     * метод, который проверяет правильность введенных полей у человека
     * @param prompt считываемая строка
     * @return результат проверки и человека, если результат проверки true; null, если false
     */
    public static Pair<Boolean, Person> input(String prompt) {
        System.out.println(prompt + ": ");

        String line;
        System.out.println("name: ");

        line = IoHelper.in.nextLine();
        if (line.isEmpty()) {
            return new Pair<>(true, null);
        }

        String name = line;

        Pair<Boolean, Timestamp> dp = IoHelper.getLocalDate("birthday (dd.MM.yyyy)");
        if (!dp.getKey())
            return new Pair<>(false, null);

        Pair<Boolean, Location> lp = Location.input("location");
        if (!lp.getKey())
            return new Pair<>(false, null);

        return new Pair<>(true, new Person(name, dp.getValue(), lp.getValue()));
    }
}
