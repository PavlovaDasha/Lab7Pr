package Utilities;

import MusicBand.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SQLConnector {
    private static final String url = "jdbc:postgresql://pg:5432/studs";
    private static final String user = "s286950";
    private static final String password = "wtr242";
//        private static final String url = "jdbc:postgresql://localhost:5432/studs";
//    private static final String user = "postgres";
//    private static final String password = "123456";
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement preparedStatement;
    private static ResultSet rs;

    public static Boolean ConnectionToDB() throws SQLException {
        try {
            connection = DriverManager.getConnection(url, user, password);
            return true;
        } catch (SQLException e) {
            throw e;

        }
    }
    public static Boolean addNewUser(String user, String password) {
        try {
            preparedStatement = connection.prepareStatement("insert into userdb values (?,?)");
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
        e.printStackTrace();
            return false;
        }
    }

    public static Boolean userExist(String user, String password) {

        try {
            preparedStatement = connection.prepareStatement("select *  from userdb d where exists( select * from userdb where d.login = ? and d.password= ?)");
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            } else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void uploadAllBands() {
        try {
            stmt = connection.createStatement();
            stmt.execute("TRUNCATE musicband");
            List<MusicBand> musicBands = BandList.getMusicBandList();
            musicBands.stream().forEach(x -> {
                try {
                    preparedStatement = connection.prepareStatement("INSERT into musicband values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    preparedStatement.setLong(1, x.getId());;
                    preparedStatement.setString(2, x.getName());
                    preparedStatement.setFloat(3, x.getCoordinates().getX());
                    preparedStatement.setFloat(4, x.getCoordinates().getY());
                    preparedStatement.setTimestamp(5, x.getCreationDate());
                        preparedStatement.setLong(6, x.getNumberOfParticipants());
                        preparedStatement.setString(7, x.getGenre().toString());

                    try {
                        preparedStatement.setString(8, x.getFrontMan().getName());
                    } catch (NullPointerException e) {
                        preparedStatement.setString(8, null);
                    }
                    try {
                        preparedStatement.setDouble(9, x.getFrontMan().getLocation().getX());
                    } catch (NullPointerException e) {
                        preparedStatement.setObject(9,  null);
                    }
                    try {
                        preparedStatement.setDouble(10, x.getFrontMan().getLocation().getY());
                    } catch (NullPointerException e) {
                        preparedStatement.setObject(10, null);
                    }
                    try {
                        preparedStatement.setInt(11, x.getFrontMan().getLocation().getZ());
                    } catch (NullPointerException e) {
                        preparedStatement.setObject(11, null);
                    }
                    try {
                        preparedStatement.setString(12, x.getFrontMan().getLocation().getName());
                    } catch (NullPointerException e) {
                        preparedStatement.setString(12, null);
                    }
                    try {
                        preparedStatement.setTimestamp(13, x.getFrontMan().getBirthday());
                    } catch (NullPointerException e) {
                        preparedStatement.setTimestamp(13, null);
                    }
                    preparedStatement.setString(14, x.getUser());
                    preparedStatement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadAllBands() {
        try {
            try {
                BandList.getMusicBandList().clear();
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select * from musicband");
            while (rs.next()) {
                MusicBand musicBand = new MusicBand();
                musicBand.setId(rs.getInt(1));
                musicBand.setName(rs.getString(2));
                Coordinates coordinates = new Coordinates(rs.getLong(3), rs.getLong(4));
                musicBand.setCoordinates(coordinates);
                musicBand.setCreationDate(rs.getTimestamp(5));
                musicBand.setNumberOfParticipants((int) rs.getLong(6));
                musicBand.setGenre(MusicGenre.valueOf(rs.getString(7)));

                Person person = new Person();
                try {
                    if (rs.getString(8).equals("null")) musicBand.setFrontMan(null);
                    else {
                        person.setName(rs.getString(8));
                        Location location = new Location();
                        location.setX(rs.getDouble(9));
                        location.setY(rs.getLong(10));
                        location.setY(rs.getLong(11));
                        location.setName(rs.getString(12));
                        person.setLocation(location);
                        person.setBirthday(rs.getTimestamp(13));
                        musicBand.setFrontMan(person);
                    }
                    } catch (NullPointerException e) {
                }
                musicBand.setUser(rs.getString(14));

                BandList.getMusicBandList().add(musicBand);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static Integer getNewId(){
        try {
            stmt = connection.createStatement();

            rs =stmt.executeQuery("SELECT nextval('id')");
            if (rs.next()) {
                return rs.getInt(1);
            }
            else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}