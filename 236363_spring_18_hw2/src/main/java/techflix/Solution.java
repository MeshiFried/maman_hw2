package techflix;

import techflix.business.Movie;
import techflix.business.MovieRating;
import techflix.business.ReturnValue;
import techflix.business.Viewer;
import techflix.data.DBConnector;
import techflix.data.PostgresSQLErrorCodes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Solution {

    public static void createTables() {
        createTableViewers();
        createTableMovies();
        createTableViews();
    }

    private static void createTableViewers() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("CREATE TABLE Viewers\n" +
                    "(\n" +
                    "    ViewerID integer NOT NULL,\n" +
                    "    ViewerName text NOT NULL,\n" +
                    "    PRIMARY KEY (ViewerID),\n" +
                    "    CHECK (ViewerID > 0)\n" +
                    ")");
            pstmt.execute();
        } catch (SQLException e) {
            //e.printStackTrace()();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    private static void createTableMovies() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {

            pstmt = connection.prepareStatement("CREATE TABLE Movies\n" +
                    "(\n" +
                    "    MovieID integer NOT NULL CHECK (MovieID > 0),\n" +
                    "    MovieName text NOT NULL,\n" +
                    "    MovieDescription text NOT NULL,\n" +
                    "    PRIMARY KEY (MovieID),\n" +
                    ")");
            pstmt.execute();
        } catch (SQLException e) {
            //e.printStackTrace()();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    private static void createTableViews() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {

            pstmt = connection.prepareStatement("CREATE TABLE Views\n" +
                    "(\n" +
                    "    ViewerID integer NOT NULL CHECK (ViewerID > 0),\n" +
                    "    MovieID integer NOT NULL CHECK (MovieID > 0),\n" +
                    "    Rate text,\n" +
                    "    FOREIGN KEY(ViewerID) REFERENCES Viewers(ViewerID) ON DELETE CASCADE ,\n" +
                    "    FOREIGN KEY(MovieID) REFERENCES Movies(MovieID) ON DELETE CASCADE ,\n" +
                    ")");
            pstmt.execute();
        } catch (SQLException e) {
            //e.printStackTrace()();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }


    public static void clearTables() {
        clearTableViews();
        clearTableMovies();
        clearTableViewers();
    }

    private static void clearTableViewers() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("DELETE FROM Viewers");
            pstmt.execute();
        } catch (SQLException e) {
            //e.printStackTrace()();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    private static void clearTableMovies() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("DELETE FROM Movies");
            pstmt.execute();
        } catch (SQLException e) {
            //e.printStackTrace()();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    private static void clearTableViews() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("DELETE FROM Views");
            pstmt.execute();
        } catch (SQLException e) {
            //e.printStackTrace()();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }


    public static void dropTables() {
        dropTableViews();
        dropTableMovies();
        dropTableViewers();
    }

    private static void dropTableViewers() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS Viewers");
            pstmt.execute();
        } catch (SQLException e) {
            //e.printStackTrace()();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    private static void dropTableMovies() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS Movies");
            pstmt.execute();
        } catch (SQLException e) {
            //e.printStackTrace()();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    private static void dropTableViews() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS Views");
            pstmt.execute();
        } catch (SQLException e) {
            //e.printStackTrace()();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }


    public static ReturnValue createViewer(Viewer viewer) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("INSERT INTO Viewers" +
                    " VALUES (?, ?)");
            pstmt.setInt(1, viewer.getId());
            pstmt.setString(2, viewer.getName());
            pstmt.execute();
        } catch (SQLException e) {
            Integer error = Integer.valueOf(e.getSQLState());
            if (error == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue() || error == PostgresSQLErrorCodes
                    .NOT_NULL_VIOLATION.getValue()) {
                return ReturnValue.BAD_PARAMS;
            } else if (error == PostgresSQLErrorCodes.UNIQUE_VIOLATION.getValue()) {
                return ReturnValue.ALREADY_EXISTS;
            }
            return ReturnValue.ERROR;
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                return ReturnValue.ERROR;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return ReturnValue.ERROR;
            }
        }
        return ReturnValue.OK;
    }

    public static ReturnValue deleteViewer(Viewer viewer) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("DELETE from Viewers" +
                    " WHERE ViewerID =? ");
            pstmt.setInt(1, viewer.getId());
            int affectedRows = pstmt.executeUpdate();
            System.out.println("deleted " + affectedRows + " rows");//todo: problem!
        } catch (SQLException e) {
            Integer error = Integer.valueOf(e.getSQLState());
            //todo: add error of NOT_EXISTS
            return ReturnValue.ERROR;
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                return ReturnValue.ERROR;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return ReturnValue.ERROR;
            }
        }
        return ReturnValue.OK;
    }

    public static ReturnValue updateViewer(Viewer viewer) {

        return null;
    }

    public static Viewer getViewer(Integer viewerId) {

        return null;
    }


    public static ReturnValue createMovie(Movie movie) {

        return null;
    }

    public static ReturnValue deleteMovie(Movie movie) {

        return null;
    }

    public static ReturnValue updateMovie(Movie movie) {
        return null;
    }

    public static Movie getMovie(Integer movieId) {

        return null;
    }


    public static ReturnValue addView(Integer viewerId, Integer movieId) {

        return null;
    }

    public static ReturnValue removeView(Integer viewerId, Integer movieId) {

        return null;
    }

    public static Integer getMovieViewCount(Integer movieId) {

        return null;
    }


    public static ReturnValue addMovieRating(Integer viewerId, Integer movieId, MovieRating rating) {

        return null;
    }

    public static ReturnValue removeMovieRating(Integer viewerId, Integer movieId) {

        return null;
    }

    public static int getMovieLikesCount(int movieId) {

        return -1;
    }

    public static int getMovieDislikesCount(int movieId) {

        return -1;
    }

    public static ArrayList<Integer> getSimilarViewers(Integer viewerId) {

        return null;
    }


    public static ArrayList<Integer> mostInfluencingViewers() {

        return null;
    }


    public static ArrayList<Integer> getMoviesRecommendations(Integer viewerId) {

        return null;
    }


    public static ArrayList<Integer> getConditionalRecommendations(Integer viewerId, int movieId) {

        return null;
    }

}


