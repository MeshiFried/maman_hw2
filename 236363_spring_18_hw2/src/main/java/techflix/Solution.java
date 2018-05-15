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

import static java.lang.Math.ceil;

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
                    "    ViewerID integer NOT NULL CHECK (ViewerID > 0),\n" +
                    "    ViewerName text NOT NULL,\n" +
                    "    PRIMARY KEY (ViewerID) \n" +
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
                    "    PRIMARY KEY (MovieID)\n" +
                    ")");
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
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
                    " UNIQUE (ViewerID,MovieID),\n" +
                    "    FOREIGN KEY(ViewerID) REFERENCES Viewers(ViewerID) ON DELETE CASCADE,\n" +
                    "    FOREIGN KEY(MovieID) REFERENCES Movies(MovieID) ON DELETE CASCADE \n" +
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
            if (error == PostgresSQLErrorCodes.UNIQUE_VIOLATION.getValue()) {
                return ReturnValue.ALREADY_EXISTS;
            } else if (error == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue()) {
                return ReturnValue.BAD_PARAMS;
            } else if (error == PostgresSQLErrorCodes.NOT_NULL_VIOLATION.getValue()) {
                return ReturnValue.BAD_PARAMS;
            } else {
                return ReturnValue.ERROR;
            }
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
            pstmt = connection.prepareStatement("SELECT Count(*) FROM Viewers WHERE ViewerID=(?)");
            pstmt.setInt(1, viewer.getId());
            ResultSet res = pstmt.executeQuery();
            res.next();
            if (res.getInt(1) != 1) {
                return ReturnValue.NOT_EXISTS;
            } else {
                pstmt = connection.prepareStatement("DELETE FROM Viewers WHERE ViewerID=(?)");
                pstmt.setInt(1, viewer.getId());
                pstmt.execute();
            }
            res.close();
        } catch (SQLException e) {
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
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT Count(*) FROM Viewers WHERE ViewerID=(?)");
            pstmt.setInt(1, viewer.getId());
            ResultSet res = pstmt.executeQuery();
            res.next();
            if (res.getInt(1) != 1) {
                return ReturnValue.NOT_EXISTS;
            }
            pstmt = connection.prepareStatement("UPDATE Viewers SET ViewerName=(?) WHERE ViewerID=(?)");
            pstmt.setString(1, viewer.getName());
            pstmt.setInt(2, viewer.getId());
            pstmt.execute();
            res.close();
        } catch (SQLException e) {
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue()) {
                return ReturnValue.BAD_PARAMS;
            }
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.NOT_NULL_VIOLATION.getValue()) {
                return ReturnValue.BAD_PARAMS;
            } else {
                return ReturnValue.ERROR;
            }
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

    public static Viewer getViewer(Integer viewerId) {
        Viewer new_viewer = new Viewer();
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM Viewers WHERE ViewerID=(?)");
            pstmt.setInt(1, viewerId);
            ResultSet res = pstmt.executeQuery();
            res.next();
            if (res.getInt(1) != 1) {
                new_viewer = Viewer.badViewer();
            } else {
                pstmt = connection.prepareStatement("SELECT * FROM Viewers WHERE ViewerID=(?)");
                pstmt.setInt(1, viewerId);
                res = pstmt.executeQuery();
                res.next();
                new_viewer.setId(viewerId);
                new_viewer.setName(res.getString("ViewerName"));
            }
            res.close();
        } catch (SQLException e) {
            return Viewer.badViewer();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                return Viewer.badViewer();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return Viewer.badViewer();
            }
        }
        return new_viewer;
    }


    public static ReturnValue createMovie(Movie movie) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("INSERT INTO Movies VALUES (?, ?, ?)");
            pstmt.setInt(1, movie.getId());
            pstmt.setString(2, movie.getName());
            pstmt.setString(3, movie.getDescription());
            pstmt.execute();
        } catch (SQLException e) {
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.UNIQUE_VIOLATION.getValue()) {
                return ReturnValue.ALREADY_EXISTS;
            }
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue()) {
                return ReturnValue.BAD_PARAMS;
            }
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.NOT_NULL_VIOLATION.getValue()) {
                return ReturnValue.BAD_PARAMS;
            } else {
                return ReturnValue.ERROR;
            }
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

    public static ReturnValue deleteMovie(Movie movie) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT Count(*) FROM Movies WHERE MovieID=(?)");
            pstmt.setInt(1, movie.getId());
            ResultSet res = pstmt.executeQuery();
            res.next();
            if (res.getInt(1) != 1) {
                return ReturnValue.NOT_EXISTS;
            } else {
                pstmt = connection.prepareStatement("DELETE FROM Movies WHERE MovieID=(?)");
                pstmt.setInt(1, movie.getId());
                pstmt.execute();
            }
            res.close();
        } catch (SQLException e) {
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

    public static ReturnValue updateMovie(Movie movie) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT Count(*) FROM Movies WHERE MovieID=(?)");
            pstmt.setInt(1, movie.getId());
            ResultSet res = pstmt.executeQuery();
            res.next();
            if (res.getInt(1) != 1) {
                return ReturnValue.NOT_EXISTS;
            }
            pstmt = connection.prepareStatement("UPDATE Movies SET MovieDescription=(?) WHERE MovieID=(?)");
            pstmt.setString(1, movie.getDescription());
            pstmt.setInt(2, movie.getId());
            pstmt.execute();
            res.close();
        } catch (SQLException e) {
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue()) {
                return ReturnValue.BAD_PARAMS;
            }
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.NOT_NULL_VIOLATION.getValue()) {
                return ReturnValue.BAD_PARAMS;
            } else {
                return ReturnValue.ERROR;
            }
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

    public static Movie getMovie(Integer movieId) {
        Movie new_movie = new Movie();
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT Count(*) FROM Movies WHERE MovieID=(?)");
            pstmt.setInt(1, movieId);
            ResultSet res = pstmt.executeQuery();
            res.next();
            if (res.getInt(1) != 1) {
                new_movie = Movie.badMovie();
            } else {
                pstmt = connection.prepareStatement("SELECT * FROM Movies WHERE MovieID=(?)");
                pstmt.setInt(1, movieId);
                res = pstmt.executeQuery();
                res.next();
                new_movie.setId(movieId);
                new_movie.setName(res.getString("MovieName"));
                new_movie.setDescription(res.getString("MovieDescription"));
            }
            res.close();
        } catch (SQLException e) {
            return Movie.badMovie();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                return Movie.badMovie();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return Movie.badMovie();
            }
        }
        return new_movie;
    }


    public static ReturnValue addView(Integer viewerId, Integer movieId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("INSERT INTO Views VALUES(?, ?)");
            pstmt.setInt(1, viewerId);
            pstmt.setInt(2, movieId);
            pstmt.execute();
        } catch (SQLException e) {
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.UNIQUE_VIOLATION.getValue()) {
                return ReturnValue.ALREADY_EXISTS;
            }
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.FOREIGN_KEY_VIOLATION.getValue()) {
                return ReturnValue.NOT_EXISTS;
            }
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue()) {
                return ReturnValue.NOT_EXISTS;
            } else {
                return ReturnValue.ERROR;
            }
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

    public static ReturnValue removeView(Integer viewerId, Integer movieId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT Count(*) FROM Views WHERE ViewerID=(?) AND MovieID=(?)");
            pstmt.setInt(1, viewerId);
            pstmt.setInt(2, movieId);
            ResultSet res = pstmt.executeQuery();
            res.next();
            if (res.getInt(1) != 1) {
                return ReturnValue.NOT_EXISTS;
            } else {
                pstmt = connection.prepareStatement("DELETE FROM Views WHERE ViewerID=(?) AND MovieID=(?)");
                pstmt.setInt(1, viewerId);
                pstmt.setInt(2, movieId);
                pstmt.execute();
            }
            res.close();
        } catch (SQLException e) {
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

    public static Integer getMovieViewCount(Integer movieId) {
        int viewsCount = 0;
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT COUNT(ViewerID) FROM Views WHERE MovieID=(?)");
            pstmt.setInt(1, movieId);
            ResultSet res = pstmt.executeQuery();
            res.next();
            viewsCount = res.getInt(1);
            res.close();
        } catch (SQLException e) {
            return viewsCount;
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                return viewsCount;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return viewsCount;
            }
        }
        return viewsCount;
    }


    public static ReturnValue addMovieRating(Integer viewerId, Integer movieId, MovieRating rating) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM Views WHERE ViewerID=(?) AND MovieID=(?)");
            pstmt.setInt(1, viewerId);
            pstmt.setInt(2, movieId);
            ResultSet res = pstmt.executeQuery();
            res.next();
            if (res.getInt(1) != 1) {
                return ReturnValue.NOT_EXISTS;
            }
            pstmt = connection.prepareStatement("UPDATE Views SET Rate='" + rating + "' WHERE ViewerID=(?) AND " +
                    "MovieID=(?)");
            pstmt.setInt(1, viewerId);
            pstmt.setInt(2, movieId);
            pstmt.execute();
            res.close();
        } catch (SQLException e) {
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

    public static ReturnValue removeMovieRating(Integer viewerId, Integer movieId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM Views WHERE ViewerID=(?) AND MovieID=(?) " +
                    "AND Rate IS NOT NULL");
            pstmt.setInt(1, viewerId);
            pstmt.setInt(2, movieId);
            ResultSet res = pstmt.executeQuery();
            res.next();
            if (res.getInt(1) != 1) {
                return ReturnValue.NOT_EXISTS;
            }
            pstmt = connection.prepareStatement("UPDATE Views SET Rate=NULL WHERE ViewerID=(?) AND MovieID=(?)");
            pstmt.setInt(1, viewerId);
            pstmt.setInt(2, movieId);
            pstmt.execute();
            res.close();
        } catch (SQLException e) {
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

    private static int getMovieRateCount(int movieId, MovieRating rate) {
        int likesCount = 0;
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM Views WHERE MovieID=(?) AND Rate='" + rate + "'");
            pstmt.setInt(1, movieId);
            ResultSet res = pstmt.executeQuery();
            res.next();
            likesCount = res.getInt(1);
            res.close();
        } catch (SQLException e) {
            return likesCount;
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                return likesCount;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return likesCount;
            }
            return likesCount;
        }
    }

    public static int getMovieLikesCount(int movieId) {
        return getMovieRateCount(movieId, MovieRating.LIKE);
    }

    public static int getMovieDislikesCount(int movieId) {
        return getMovieRateCount(movieId, MovieRating.DISLIKE);
    }


    public static ArrayList<Integer> getSimilarViewers(Integer viewerId) {
        ArrayList<Integer> list = new ArrayList<Integer>();

        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {

            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM Views WHERE ViewerID=(?)");
            pstmt.setInt(1, viewerId);
            ResultSet res = pstmt.executeQuery();
            res.next();
            int numberOfViews = res.getInt(1);
            if (numberOfViews == 0) {
                return new ArrayList<Integer>();
            }

            pstmt = connection.prepareStatement("SELECT ViewerID FROM \n" +
                    "( SELECT ViewerID,MovieID FROM Views WHERE MovieID IN \n" +
                    "    ( SELECT MovieID FROM Views WHERE ViewerID=(?) ) \n " +
                    ") AS Temp GROUP BY ViewerID HAVING COUNT(MovieID)>=(?) AND ViewerID!=(?) \n");
            pstmt.setInt(1, viewerId);
            pstmt.setInt(2, (int) ceil(0.75 * numberOfViews));
            pstmt.setInt(3, viewerId);
            res = pstmt.executeQuery();

            while (res.next()) {
                Integer viewerID = res.getInt(1);
                list.add(viewerID);
            }

            res.close();
        } catch (SQLException e) {
            return new ArrayList<Integer>();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                return new ArrayList<Integer>();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return new ArrayList<Integer>();
            }
        }

        return list;
    }


    public static ArrayList<Integer> mostInfluencingViewers() {
        ArrayList<Integer> list = new ArrayList<Integer>();

        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                    "SELECT Viewers.ViewerID AS id, COUNT(Rate) AS ratesCount, COUNT(Views.ViewerID) AS viewsCount " +
                            "\n FROM Viewers LEFT JOIN Views ON (Viewers.ViewerID = Views.ViewerID) \n" +
                            "GROUP BY id HAVING (COUNT(Views.ViewerID)>0) \n" +
                            "ORDER BY viewsCount DESC, ratesCount DESC, id ASC LIMIT 10 \n");
            ResultSet res = pstmt.executeQuery();

            while (res.next()) {
                Integer viewerID = res.getInt(1);
                list.add(viewerID);
            }

            res.close();
        } catch (SQLException e) {
            return new ArrayList<Integer>();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                return new ArrayList<Integer>();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return new ArrayList<Integer>();
            }
        }

        return list;
    }


    public static ArrayList<Integer> getMoviesRecommendations(Integer viewerId) {
        ArrayList<Integer> list = new ArrayList<Integer>();

        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {

            // find SimilarViewers(viewerId):
            pstmt = connection.prepareStatement("SELECT Count(*) FROM Views WHERE ViewerID=(?)");
            pstmt.setInt(1, viewerId);
            ResultSet res = pstmt.executeQuery();
            res.next();
            int numberOfViews = res.getInt(1);

            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS SimilarViewers");
            pstmt.execute();

            pstmt = connection.prepareStatement("CREATE TABLE SimilarViewers(ViewerID) AS \n" +
                    "SELECT ViewerID FROM \n" +
                    "( SELECT ViewerID,MovieID FROM Views WHERE MovieID IN \n" +
                    "    ( SELECT MovieID FROM Views WHERE ViewerID=(?) ) \n " +
                    ") AS Temp GROUP BY ViewerID HAVING COUNT(MovieID)>=(?) AND ViewerID!=(?) \n");
            pstmt.setInt(1, viewerId);
            pstmt.setInt(2, (int) ceil(0.75 * numberOfViews));
            pstmt.setInt(3, viewerId);
            pstmt.execute();

            // find getMoviesRecommendations(viewerId):
            pstmt = connection.prepareStatement
                    ("SELECT MovieID AS id, SUM(CASE Rate WHEN \'LIKE\' THEN 1 ELSE 0 END) AS ratesCount \n" +
                            "FROM Views, SimilarViewers \n WHERE (Views.ViewerID=SimilarViewers.ViewerID \n" +
                            "AND MovieID NOT IN (SELECT MovieID FROM Views WHERE Views.ViewerID=(?)) \n" +
                            "AND (Rate IS NULL OR Rate=\'LIKE\'))" +
                            " GROUP BY id ORDER BY ratesCount DESC, id ASC LIMIT 10");
            pstmt.setInt(1, viewerId);
            res = pstmt.executeQuery();

            while (res.next()) {
                Integer movieID = res.getInt(1);
                list.add(movieID);
            }

            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS SimilarViewers");
            pstmt.execute();

            res.close();
        } catch (SQLException e) {
            return new ArrayList<Integer>();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                return new ArrayList<Integer>();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return new ArrayList<Integer>();
            }
        }

        return list;
    }


    public static ArrayList<Integer> getConditionalRecommendations(Integer viewerId, int movieId) {
        ArrayList<Integer> list = new ArrayList<Integer>();

        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {

            // check if viewerId rate movieId. if not - return empty ArrayList.
            pstmt = connection.prepareStatement("SELECT COUNT(Rate) FROM Views WHERE ViewerID=(?) AND MovieID=(?)");
            pstmt.setInt(1, viewerId);
            pstmt.setInt(2, movieId);
            ResultSet res = pstmt.executeQuery();
            res.next();
            if (res.getInt(1) == 0) {
                return new ArrayList<Integer>();
            }


            // find SimilarViewers(viewerId):
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM Views WHERE ViewerID=(?)");
            pstmt.setInt(1, viewerId);
            res = pstmt.executeQuery();
            res.next();
            int numberOfViews = res.getInt(1);

            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS SimilarViewers");
            pstmt.execute();

            pstmt = connection.prepareStatement("CREATE TABLE SimilarViewers(ViewerID) AS \n" +
                    "SELECT ViewerID FROM \n" +
                    "( SELECT ViewerID,MovieID FROM Views WHERE MovieID IN \n" +
                    "    ( SELECT MovieID FROM Views WHERE ViewerID=(?) ) \n " +
                    ") AS Temp GROUP BY ViewerID HAVING COUNT(MovieID)>=(?) AND ViewerID!=(?) \n");
            pstmt.setInt(1, viewerId);
            pstmt.setInt(2, (int) ceil(0.75 * numberOfViews));
            pstmt.setInt(3, viewerId);
            pstmt.execute();

            // check if similar viewers exist. if not - return empty ArrayList.
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM SimilarViewers");
            res = pstmt.executeQuery();
            res.next();
            if (res.getInt(1) == 0) {
                pstmt = connection.prepareStatement("DROP TABLE IF EXISTS SimilarViewers");
                pstmt.execute();
                return new ArrayList<Integer>();
            }


            // find SimilarRankers(viewerId, movieId):
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS SimilarRankers");
            pstmt.execute();

            pstmt = connection.prepareStatement("CREATE TABLE SimilarRankers(ViewerID) AS \n" +
                    "SELECT SimilarViewers.ViewerID FROM Views, SimilarViewers \n" +
                    "WHERE ((Views.ViewerID=SimilarViewers.ViewerID) AND (MovieID=(?)) AND " +
                    "(Rate=(SELECT Rate FROM Views WHERE (Views.ViewerID=(?) AND MovieID=(?)))))");
            pstmt.setInt(1, movieId);
            pstmt.setInt(2, viewerId);
            pstmt.setInt(3, movieId);
            pstmt.execute();

            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS SimilarViewers");
            pstmt.execute();

            // check if similar rankers exist. if not - return empty ArrayList.
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM SimilarRankers");
            res = pstmt.executeQuery();
            res.next();
            if (res.getInt(1) == 0) {
                pstmt = connection.prepareStatement("DROP TABLE IF EXISTS SimilarRankers");
                pstmt.execute();
                return new ArrayList<Integer>();
            }


            // find getConditionalRecommendations(viewerId, movieId):
            pstmt = connection.prepareStatement
                    ("SELECT MovieID AS id, SUM(CASE Rate WHEN \'LIKE\' THEN 1 ELSE 0 END) AS " +
                            "ratesCount \n FROM Views, SimilarRankers \n " +
                            "WHERE ((Views.ViewerID=SimilarRankers.ViewerID) \n" +
                            "AND (MovieID NOT IN (SELECT MovieID FROM Views WHERE Views.ViewerID=(?))) \n" +
                            "AND (Rate IS NULL OR Rate=\'LIKE\')) " +
                            "GROUP BY id ORDER BY ratesCount DESC, id ASC LIMIT 10");
            pstmt.setInt(1, viewerId);
            res = pstmt.executeQuery();
            while (res.next()) {
                Integer movieID = res.getInt(1);
                list.add(movieID);
            }

            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS SimilarRankers");
            pstmt.execute();

            res.close();
        } catch (SQLException e) {
            return new ArrayList<Integer>();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                return new ArrayList<Integer>();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                return new ArrayList<Integer>();
            }
        }

        return list;
    }

}
