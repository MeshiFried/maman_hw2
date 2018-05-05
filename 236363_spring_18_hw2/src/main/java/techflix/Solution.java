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
                    "    FOREIGN KEY(ViewerID) REFERENCES Viewers(ViewerID) ON DELETE CASCADE ,\n" +
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
        if(viewer.getId() <= 0 || viewer.getName() == null) return ReturnValue.BAD_PARAMS;
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try{
            pstmt = connection.prepareStatement("SELECT Count(*) FROM Viewers WHERE ViewerID = " + viewer.getId());
            ResultSet res = pstmt.executeQuery();
            res.next();
            if(res.getInt(1) !=1) {
                return ReturnValue.NOT_EXISTS;
            }
            pstmt = connection.prepareStatement("UPDATE Viewers " +
                    "SET ViewerName = '" + viewer.getName() + "'" +
                    " WHERE ViewerID = " + viewer.getId());
            pstmt.execute();
            res.close();
        }catch (SQLException e) {
            if(Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue())
            {
                return ReturnValue.BAD_PARAMS;
            }
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.NOT_NULL_VIOLATION.getValue())
            {
                return ReturnValue.BAD_PARAMS;
            }
            else
            {
                return ReturnValue.ERROR;
            }
        }finally {
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
        try{
            pstmt = connection.prepareStatement("SELECT Count(*) FROM Viewers WHERE ViewerID = " + viewerId);
            ResultSet res = pstmt.executeQuery();
            res.next();
            if(res.getInt(1) !=1){
                new_viewer = Viewer.badViewer();
            }else{
                pstmt = connection.prepareStatement("SELECT * FROM Viewers WHERE ViewerID = " + viewerId);
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
        if(movie.getId() <= 0 || movie.getName() == null || movie.getDescription() == null){
            return ReturnValue.BAD_PARAMS;
        }
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("INSERT INTO Movies" +
                    " VALUES(" + movie.getId() + "," +
                    "'" + movie.getName()+ "'" + "," +
                   "'" + movie.getDescription() + "'" + ")");
            pstmt.execute();
        } catch (SQLException e) {
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.UNIQUE_VIOLATION.getValue())
            {
                return ReturnValue.ALREADY_EXISTS;
            }
            if(Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue())
            {
                return ReturnValue.BAD_PARAMS;
            }
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.NOT_NULL_VIOLATION.getValue())
            {
                return ReturnValue.BAD_PARAMS;
            }
            else
            {
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
        if(movie.getId() <=0){
            return ReturnValue.BAD_PARAMS;
        }
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT Count(*) FROM Movies WHERE MovieID = " + movie.getId());
            ResultSet res = pstmt.executeQuery();
            res.next();
            if (res.getInt(1) != 1) {
                return ReturnValue.NOT_EXISTS;
            }
            else {
                pstmt = connection.prepareStatement("DELETE FROM Movies " +
                        "WHERE MovieID = " + movie.getId());
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
        if(movie.getId() <= 0 || movie.getDescription() == null) return ReturnValue.BAD_PARAMS;
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try{
            pstmt = connection.prepareStatement("SELECT Count(*) FROM Movies WHERE MovieID = " + movie.getId());
            ResultSet res = pstmt.executeQuery();
            res.next();
            if(res.getInt(1) !=1) {
                return ReturnValue.NOT_EXISTS;
            }
            pstmt = connection.prepareStatement("UPDATE Movies " +
                    "SET MovieDescription = '" + movie.getDescription() + "'" +
                    " WHERE MovieID = " + movie.getId());
            pstmt.execute();
            res.close();
        }catch (SQLException e) {
            if(Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue())
            {
                return ReturnValue.BAD_PARAMS;
            }
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.NOT_NULL_VIOLATION.getValue())
            {
                return ReturnValue.BAD_PARAMS;
            }
            else
            {
                return ReturnValue.ERROR;
            }
        }finally {
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
        try{
            pstmt = connection.prepareStatement("SELECT Count(*) FROM Movies WHERE MovieID = " + movieId);
            ResultSet res = pstmt.executeQuery();
            res.next();
            if(res.getInt(1) !=1){
                new_movie = Movie.badMovie();
            }else{
                pstmt = connection.prepareStatement("SELECT * FROM Movies WHERE MovieID = " + movieId);
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
        if(viewerId <= 0 ||movieId <= 0){
            return ReturnValue.BAD_PARAMS;
        }
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("INSERT INTO Views" +
                    " VALUES(" + viewerId + "," +
                     movieId + ")");
            pstmt.execute();
        } catch (SQLException e) {
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.UNIQUE_VIOLATION.getValue())
            {
                return ReturnValue.ALREADY_EXISTS;
            }
            /*
            if(Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.CHECK_VIOLATION.getValue())
            {
                return ReturnValue.BAD_PARAMS;
            }
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.NOT_NULL_VIOLATION.getValue())
            {
                return ReturnValue.BAD_PARAMS;
            }*/
            if (Integer.valueOf(e.getSQLState()) == PostgresSQLErrorCodes.FOREIGN_KEY_VIOLATION.getValue())
            {
                return ReturnValue.NOT_EXISTS;
            }
            else
            {
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
        if(viewerId <=0 || movieId <= 0){
            return ReturnValue.BAD_PARAMS;
        }
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT Count(*) FROM Views WHERE ViewerID = " + viewerId +
            " AND MovieID = " + movieId);
            ResultSet res = pstmt.executeQuery();
            res.next();
            if (res.getInt(1) != 1) {
                return ReturnValue.NOT_EXISTS;
            }
            else {
                pstmt = connection.prepareStatement("DELETE FROM Views " +
                        "WHERE ViewerID = " + viewerId +
                        " AND MovieID = " + movieId);
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
        if(movieId <= 0){
            return 0;
        }
        int viewsCount = 0;
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT Count(ViewerID) FROM Views WHERE MovieID = " + movieId + " GROUP BY MovieID ");
            ResultSet res = pstmt.executeQuery();
            res.next();
            viewsCount = res.getInt(1);
            res.close();
        } catch (SQLException e) {
            return 0;
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
        if(viewerId <= 0 || movieId <= 0 || rating == null){
            return ReturnValue.BAD_PARAMS;
        }
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT Count(*) FROM Views WHERE ViewerID = " + viewerId + " AND MovieID = " + movieId);
            ResultSet res = pstmt.executeQuery();
            res.next();
            if(res.getInt(1) !=1) {
                return ReturnValue.NOT_EXISTS;
            }
            pstmt = connection.prepareStatement("UPDATE Views " +
                    "SET Rate = '" + rating + "'" +
                    " WHERE ViewerID = " + viewerId + " AND MovieID = " + movieId);
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
        if(viewerId <=0 || movieId <=0 ){
            return ReturnValue.BAD_PARAMS;
        }
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT Count(*) FROM Views WHERE ViewerID = " + viewerId + " AND MovieID = " + movieId + " AND Rate IS NOT NULL ");
            ResultSet res = pstmt.executeQuery();
            res.next();
            if(res.getInt(1) !=1) {
                return ReturnValue.NOT_EXISTS;
            }
            pstmt = connection.prepareStatement("UPDATE Views " +
                    "SET Rate =  null "+
                    " WHERE ViewerID = " + viewerId + " AND MovieID = " + movieId);
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


