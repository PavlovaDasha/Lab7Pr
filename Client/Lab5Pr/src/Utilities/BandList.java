package Utilities;

import MusicBand.*;

import java.io.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * класс, описывающий команды для управления кколлекцией
 */
public class BandList {
    /**
     * поле элемент коллекции
     */
    private static ArrayList<MusicBand> musicBandList = new ArrayList<MusicBand>();

    public static ArrayList<MusicBand> getMusicBandList() {
        return musicBandList;
    }

    public static void setMusicBandList(ArrayList<MusicBand> musicBandList) {
        BandList.musicBandList = musicBandList;
    }

    /**
     * поле сгенерированный индентификационный номер
     */
    private static int generatedId;
    /**
     * поле дата инициализации
     */
    private static Date initDate;
    public static ArrayList <String> calledScripts = new ArrayList<>();

    public static String filePath;

    /**
     * конструктор, который создает объект коллекции с индентификационным номером 1 и дату инициализации
     */
    public int getSize(){
        return musicBandList.size();
    }

    /**
     * метод, который находит Music Band с введеным индентификационным номером
     * @param id идентификационный номер
     * @return Music Band с введеным идентификационным номером или null
     */
    public MusicBand findById(int id) {
        MusicBand found = null;
        for (MusicBand b : musicBandList) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }


    /**
     * метод для вывода в стандартный поток вывода информации о коллекции
     */
    public String info() {
        return ("Тип: MusicBand.MusicBand\nДата инициализации: "+initDate.toString()+"\nКоличество элементов: "+musicBandList.size()+"\n") ;
    }

    /**
     * метод для вывода в стандартный поток всех элементов коллекции
     */
    public String show() {
        String answer = "";
        for (MusicBand m: musicBandList) {
            answer+=(m)+"\n-----------------------\n";
        }
        return answer;
    }

    /**
     * метод для добавления нового элемента с генерацией идентификационного номера
     * @param band добавляемый элемент
     */
    public void add(MusicBand band) {
        band.setId(this.generatedId++);
        musicBandList.add(band);
    }

    /**
     * метод для добавления нового элемента
     * @param band добавляемый элемент
     * @param id идентификационный номер
     */
    public void add(MusicBand band, int id) {

        MusicBand found = findById(id);
        if (found != null) {
            System.out.println(band.getName());
            System.out.println("id банды конфликтует с существующей в списке бандой");
            System.out.println(String.format("%s id = %d", found.getName(), found.getId()));
            System.out.println("назначаем автоматически");
            int newId = this.generatedId++;
            System.out.println(newId);
            band.setId(newId);
        } else {
            band.setId(id);
            generatedId = Math.max(generatedId, band.getId() + 1);
        }
        musicBandList.add(band);
    }

    /**
     * метод для изменения элемента коллекции, идентификационный номер котрого равен заданному
     * @param id индентификационный номер
     */
    public void update(int id) {
        MusicBand found = null;
        for (MusicBand b : musicBandList) {
            if (b.getId() == id) {
                found = b;
                break;
            }
        }

        if (found == null) {
            System.out.println("id does not exist");
            return;
        }
    }


    public MusicBand findMax() {
        if (musicBandList.size() == 0)
            return null;
        MusicBand max = musicBandList.get(0);
        for (int i = 1; i != musicBandList.size(); ++i)
            if (musicBandList.get(i).compareTo(max) > 0)
                max = musicBandList.get(i);

        return max;
    }

    /**
     * метод, который добавляет новый элемент в коллекцию, если он больше наибольшего элемента этой коллекции
     * @param b элемент коллекции
     * @return true, если элемент добавлен, иначе false
     */
    public boolean addIfMax(MusicBand b) {
        if (b.compareTo(findMax()) > 0) {
            add(b);
            return true;
        } else
            return false;
    }

    /**
     * метод для сортировки элементов коллекции
     */
    public void sort() {
        musicBandList.sort(MusicBand::compareTo);
    }

    /**
     *метод, котрый выводит количество элементов коллекции, количество участников которых меньше заданного
     * @param numberOfParticipants количество участников в музыкальной банде
     */
    public String countLessThanNumberOfParticipants(int numberOfParticipants) {
        return String.valueOf((musicBandList.stream()
                .mapToInt(MusicBand::getNumberOfParticipants)
                .filter(x -> x < numberOfParticipants)
                .count()));
    }

    /**
     * метод, котрый выводит количество элементов коллекции, поле frontMan которых больше заданного
     * @param frontMan человек, состоящий в музыкальной банде
     */
    public String countGreaterThanFrontMan(Person frontMan) {
        return String.valueOf((musicBandList.stream()
                .filter(x -> x.getFrontMan() != null)
                .map(MusicBand::getFrontMan)
                .filter(x -> x.compareTo(frontMan) < 0)
                .count()));
    }

    /**
     *метод, котрый выводит элемент коллекции, количество участников которого равно заданному
     * @param numberOfParticipants количество участников в музыкальной банде
     */
    public String filterByNumberOfParticipants(int numberOfParticipants) {
        String answer = "";
        for (MusicBand m: musicBandList) {
            if (m.getNumberOfParticipants() == numberOfParticipants) {
                answer+=(m);
            }
        }
        return answer;
    }
}