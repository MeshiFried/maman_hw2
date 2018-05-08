package techflix;

import org.junit.Test;
import techflix.business.MovieRating;
import techflix.business.ReturnValue;
import techflix.business.Viewer;
import techflix.business.Movie;

import javax.swing.*;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static techflix.business.ReturnValue.*;

public class SimpleTest extends AbstractTest {

    private void initDB_Viewers(int numOfViewers) {
        Viewer[] viewersArray = new Viewer[numOfViewers];
        for (int i = 1; i < viewersArray.length; i++) { // start from i=1! (avoid BAD_PARAMS)
            viewersArray[i] = new Viewer();
            (viewersArray[i]).setId(i);
            (viewersArray[i]).setName("viewer" + i);
            Solution.createViewer(viewersArray[i]);
        }
    }

    private void initDB_Movies(int numOfMovies) {
        Movie[] moviesArray = new Movie[numOfMovies];
        for (int i = 1; i < moviesArray.length; i++) { // start from i=1! (avoid BAD_PARAMS)
            moviesArray[i] = new Movie();
            (moviesArray[i]).setId(i);
            (moviesArray[i]).setName("movie" + i);
            (moviesArray[i]).setDescription("movie" + i + "Description");
            Solution.createMovie(moviesArray[i]);
        }
    }

    // createViewer:

    @Test
    public void createViewer_addOneViewer() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);

        ReturnValue actual = Solution.createViewer(viewer1);
        assertEquals(OK, actual);
    }

    @Test
    public void createViewer_addViewerWithBadParameters_viewerID() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(0);

        ReturnValue actual = Solution.createViewer(viewer1);
        assertEquals(BAD_PARAMS, actual);
    }

    @Test
    public void createViewer_addViewerWithBadParameters_viewerName() {
        Viewer viewer1 = new Viewer();
        viewer1.setName(null);
        viewer1.setId(1);

        ReturnValue actual = Solution.createViewer(viewer1);
        assertEquals(BAD_PARAMS, actual);
    }

    @Test
    public void createViewer_addTwoViewersWithSameID() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);

        ReturnValue actual = Solution.createViewer(viewer1);
        assertEquals(OK, actual);

        actual = Solution.createViewer(viewer1);
        assertEquals(ALREADY_EXISTS, actual);
    }

    // deleteViewer:

    @Test
    public void deleteViewer_deleteExistViewer() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);

        ReturnValue actual = Solution.createViewer(viewer1);
        assertEquals(OK, actual);

        actual = Solution.deleteViewer(viewer1);
        assertEquals(OK, actual);
    }

    @Test
    public void deleteViewer_deleteNonExistViewer() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);

        ReturnValue actual = Solution.deleteViewer(viewer1);
        assertEquals(NOT_EXISTS, actual);
    }

    // getViewer:

    @Test
    public void getViewer_NonExistingViewer() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Viewer viewer = Solution.getViewer(2);
        assertEquals(Viewer.badViewer().getId(), viewer.getId());
        assertEquals(Viewer.badViewer().getName(), viewer.getName());
    }

    @Test
    public void getViewer_Existing() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Viewer viewer = Solution.getViewer(1);
        assertEquals(viewer1.getId(), viewer.getId());
        assertEquals(viewer1.getName(), viewer.getName());
    }

    // updateViewer:

    @Test
    public void updateViewer_NonExistingViewer() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Viewer viewer2 = new Viewer();
        viewer2.setName("viewer2");
        viewer2.setId(2);

        ReturnValue val = Solution.updateViewer(viewer2);
        assertEquals(ReturnValue.NOT_EXISTS, val);

        Viewer viewer3 = new Viewer();
        viewer3.setName("viewer3");
        viewer3.setId(-5);
        val = Solution.updateViewer(viewer3);
        assertEquals(ReturnValue.BAD_PARAMS, val);

        Viewer viewer4 = new Viewer();
        viewer4.setName(null);
        viewer4.setId(1);
        val = Solution.updateViewer(viewer3);
        assertEquals(ReturnValue.BAD_PARAMS, val);
    }

    @Test
    public void updateViewer_Existing() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Viewer viewer2 = new Viewer();
        viewer2.setName("viewer2");
        viewer2.setId(1);

        ReturnValue val = Solution.updateViewer(viewer2);
        assertEquals(ReturnValue.OK, val);
        assertEquals(Solution.getViewer(1).getName(), viewer2.getName());
    }

    // createMovie:

    @Test
    public void createMovie_addMovie() {
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");

        ReturnValue val = Solution.createMovie(movie1);
        assertEquals(ReturnValue.OK, val);
    }

    @Test
    public void createMovie_BadParams() {
        Movie movie1 = new Movie();
        movie1.setId(-4);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");

        ReturnValue val = Solution.createMovie(movie1);
        assertEquals(ReturnValue.BAD_PARAMS, val);

        Movie movie2 = new Movie();
        movie1.setId(2);
        movie1.setName(null);
        movie1.setDescription("Comedy");
        val = Solution.createMovie(movie2);
        assertEquals(ReturnValue.BAD_PARAMS, val);

        Movie movie3 = new Movie();
        movie1.setId(3);
        movie1.setName("The Godfather");
        movie1.setDescription(null);
        val = Solution.createMovie(movie3);
        assertEquals(ReturnValue.BAD_PARAMS, val);
    }

    @Test
    public void createMovie_AlreadyExist() {
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");

        Solution.createMovie(movie1);
        ReturnValue val = Solution.createMovie(movie1);
        assertEquals(ReturnValue.ALREADY_EXISTS, val);
    }

    // deleteMovie:

    @Test
    public void deleteMovie_deleteExistingMovie() {
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");

        Solution.createMovie(movie1);
        ReturnValue val = Solution.deleteMovie(movie1);
        assertEquals(ReturnValue.OK, val);
    }

    @Test
    public void deleteMovie_NotExists() {
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");

        Solution.createMovie(movie1);
        Solution.deleteMovie(movie1);
        ReturnValue val = Solution.deleteMovie(movie1);
        assertEquals(ReturnValue.NOT_EXISTS, val);
    }

    @Test
    public void deleteMovie_BadParams() {
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");

        Solution.createMovie(movie1);

        Movie movie2 = new Movie();
        movie2.setId(-2);
        movie2.setName("Finding Nemo");
        movie2.setDescription("Disney");
        ReturnValue val = Solution.deleteMovie(movie2);
        assertEquals(ReturnValue.BAD_PARAMS, val);
    }

    // getMovie:

    @Test
    public void getMovie_ExistingMovie() {
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");

        Solution.createMovie(movie1);
        Movie movie = Solution.getMovie(1);
        assertEquals(movie1.getDescription(), movie.getDescription());
        assertEquals(movie1.getName(), movie.getName());
    }

    @Test
    public void getMovie_NotExist() {
        Movie movie = Solution.getMovie(1);
        assertEquals(Movie.badMovie(), movie);
    }

    // updateMovie:

    @Test
    public void updateMovie_SuccessTest() {
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");

        Movie movie2 = new Movie();
        movie2.setId(1);
        movie2.setName("Titanic");
        movie2.setDescription("Romance");

        Solution.createMovie(movie1);
        Solution.updateMovie(movie2);
        assertEquals(movie2.getDescription(), Solution.getMovie(1).getDescription());
    }

    @Test
    public void updateMovie_NotExists() {
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");

        ReturnValue val = Solution.updateMovie(movie1);
        assertEquals(ReturnValue.NOT_EXISTS, val);
    }

    @Test
    public void updateMovie_BadParams() {
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription(null);

        ReturnValue val = Solution.updateMovie(movie1);
        assertEquals(ReturnValue.BAD_PARAMS, val);
    }

    // addView:

    @Test
    public void addView_success() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        ReturnValue val = Solution.addView(1, 1);
        assertEquals(ReturnValue.OK, val);
    }

    @Test
    public void addView_ForeignKeyViolation() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        ReturnValue val = Solution.addView(2, 1);
        assertEquals(ReturnValue.NOT_EXISTS, val);
    }

    @Test
    public void addView_AlreadyExists() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        Solution.addView(1, 1);
        ReturnValue val = Solution.addView(1, 1);
        assertEquals(ReturnValue.ALREADY_EXISTS, val);
    }

    // removeView:

    @Test
    public void removeView_success() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        Solution.addView(1, 1);
        ReturnValue val = Solution.removeView(1, 1);
        assertEquals(ReturnValue.OK, val);
    }

    @Test
    public void removeView_NotExists() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        Solution.addView(1, 1);
        ReturnValue val = Solution.removeView(2, 1);
        assertEquals(ReturnValue.NOT_EXISTS, val);

        Solution.removeView(1, 1);
        ReturnValue val2 = Solution.removeView(1, 1);
        assertEquals(ReturnValue.NOT_EXISTS, val2);
    }

    // addMovieRating:

    @Test
    public void addMovieRating_success() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        Solution.addView(1, 1);
        ReturnValue val = Solution.addMovieRating(1, 1, MovieRating.LIKE);
        assertEquals(ReturnValue.OK, val);
    }

    @Test
    public void getMovieViewCount_success() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Viewer viewer2 = new Viewer();
        viewer2.setName("viewer2");
        viewer2.setId(2);
        Solution.createViewer(viewer2);

        Viewer viewer3 = new Viewer();
        viewer3.setName("viewer3");
        viewer3.setId(3);
        Solution.createViewer(viewer3);

        Viewer viewer4 = new Viewer();
        viewer4.setName("viewer4");
        viewer4.setId(4);
        Solution.createViewer(viewer4);

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        Movie movie2 = new Movie();
        movie2.setId(2);
        movie2.setName("Finding Nemo");
        movie2.setDescription("Children");
        Solution.createMovie(movie2);

        Movie movie3 = new Movie();
        movie2.setId(3);
        movie2.setName("The godfather");
        movie2.setDescription("Crime");
        Solution.createMovie(movie3);

        Solution.addView(1, 1);
        Solution.addView(2, 1);
        Solution.addView(3, 1);
        Solution.addView(4, 2);

        int TitanicViewsCount = Solution.getMovieViewCount(1);
        assertEquals(3, TitanicViewsCount);
        int FindingNemoViewsCount = Solution.getMovieViewCount(2);
        assertEquals(1, FindingNemoViewsCount);
        int TheGodfatherViewsCount = Solution.getMovieViewCount(3);
        assertEquals(0, TheGodfatherViewsCount);
    }

    // removeMovieRating:

    @Test
    public void removeMovieRating_NotExists() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);


        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        Solution.addView(1, 1);
        ReturnValue val = Solution.removeMovieRating(1, 1);
        assertEquals(ReturnValue.NOT_EXISTS, val);

    }

    @Test
    public void removeMovieRating_success() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);


        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        Solution.addView(1, 1);
        Solution.addMovieRating(1, 1, MovieRating.LIKE);
        ReturnValue val = Solution.removeMovieRating(1, 1);
        assertEquals(ReturnValue.OK, val);
    }

    // getMovieLikeCount:

    @Test
    public void getMovieLikesCount_success() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Viewer viewer2 = new Viewer();
        viewer2.setName("viewer2");
        viewer2.setId(2);
        Solution.createViewer(viewer2);

        Viewer viewer3 = new Viewer();
        viewer3.setName("viewer3");
        viewer3.setId(3);
        Solution.createViewer(viewer3);

        Viewer viewer4 = new Viewer();
        viewer4.setName("viewer4");
        viewer4.setId(4);
        Solution.createViewer(viewer4);

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        Movie movie2 = new Movie();
        movie2.setId(2);
        movie2.setName("Finding Nemo");
        movie2.setDescription("Children");
        Solution.createMovie(movie2);


        Solution.addView(1, 1);
        Solution.addMovieRating(1, 1, MovieRating.LIKE);
        Solution.addView(2, 1);
        Solution.addMovieRating(2, 1, MovieRating.LIKE);
        Solution.addView(3, 1);
        Solution.addMovieRating(3, 1, MovieRating.DISLIKE);
        Solution.addView(4, 2);

        int TitanicLikesCount = Solution.getMovieLikesCount(1);
        assertEquals(2, TitanicLikesCount);
        int FindingNemoLikesCount = Solution.getMovieLikesCount(2);
        assertEquals(0, FindingNemoLikesCount);
    }

    @Test
    public void getMovieLikesCount_NotExist() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        Solution.addView(1, 1);
        Solution.addMovieRating(1, 1, MovieRating.LIKE);
        int count = Solution.getMovieLikesCount(2);
        assertEquals(0, count);
    }

    // getMovieLikeCount:

    @Test
    public void getMovieDislikesCount_success() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Viewer viewer2 = new Viewer();
        viewer2.setName("viewer2");
        viewer2.setId(2);
        Solution.createViewer(viewer2);

        Viewer viewer3 = new Viewer();
        viewer3.setName("viewer3");
        viewer3.setId(3);
        Solution.createViewer(viewer3);

        Viewer viewer4 = new Viewer();
        viewer4.setName("viewer4");
        viewer4.setId(4);
        Solution.createViewer(viewer4);

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        Movie movie2 = new Movie();
        movie2.setId(2);
        movie2.setName("Finding Nemo");
        movie2.setDescription("Children");
        Solution.createMovie(movie2);


        Solution.addView(1, 1);
        Solution.addMovieRating(1, 1, MovieRating.LIKE);
        Solution.addView(2, 1);
        Solution.addMovieRating(2, 1, MovieRating.LIKE);
        Solution.addView(3, 1);
        Solution.addMovieRating(3, 1, MovieRating.DISLIKE);
        Solution.addView(4, 2);

        int TitanicLikesCount = Solution.getMovieDislikesCount(1);
        assertEquals(1, TitanicLikesCount);
        int FindingNemoLikesCount = Solution.getMovieDislikesCount(2);
        assertEquals(0, FindingNemoLikesCount);
    }

    @Test
    public void getMovieDislikesCount_NotExist() {
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        Solution.addView(1, 1);
        Solution.addMovieRating(1, 1, MovieRating.DISLIKE);
        int count = Solution.getMovieDislikesCount(2);
        assertEquals(0, count);
    }

    // getSimilarViewers:

    @Test
    public void getSimilarViewers_test() {
        initDB_Viewers(10);
        initDB_Movies(25);

        // viewer1:     viewer1=.........       viewer2=9/13=69%        viewer3=7/10=70%        viewer4=7/11=63%
        // viewer5=9/16=56%    viewer6=0/7=0%
        Solution.addView(1, 1);
        Solution.addView(1, 2);
        Solution.addView(1, 3);
        Solution.addView(1, 4);
        Solution.addView(1, 5);
        Solution.addView(1, 6);
        Solution.addView(1, 7);
        Solution.addView(1, 8);
        Solution.addView(1, 9);
        Solution.addView(1, 10);

        // viewer2:     viewer1=9/10=90%        viewer2=.........        viewer3=10/10=100%     viewer4=7/11=63%
        // viewer5=9/16=56%    viewer6=0/7=0%
        Solution.addView(2, 1);
        Solution.addView(2, 2);
        Solution.addView(2, 3);
        Solution.addView(2, 4);
        Solution.addView(2, 5);
        Solution.addView(2, 6);
        Solution.addView(2, 7);
        Solution.addView(2, 8);
        Solution.addView(2, 9);
        Solution.addView(2, 11);
        Solution.addView(2, 12);
        Solution.addView(2, 13);
        Solution.addView(2, 14);

        // viewer3:     viewer1=7/10=70%        viewer2=10/13=76%       viewer3=........    viewer4=7/11=63%
        // viewer5=7/16=43%    viewer6=0/7=0%
        Solution.addView(3, 1);
        Solution.addView(3, 2);
        Solution.addView(3, 3);
        Solution.addView(3, 5);
        Solution.addView(3, 6);
        Solution.addView(3, 8);
        Solution.addView(3, 9);
        Solution.addView(3, 11);
        Solution.addView(3, 12);
        Solution.addView(3, 13);


        // viewer4:     viewer1=3/10=30%        viewer2=7/13=53%        viewer3=7/10=70%    viewer4=........
        // viewer5=11/16=68%    viewer6=4/7=57%
        Solution.addView(4, 1);
        Solution.addView(4, 2);
        Solution.addView(4, 3);
        Solution.addView(4, 5);
        Solution.addView(4, 6);
        Solution.addView(4, 8);
        Solution.addView(4, 9);
        Solution.addView(4, 17);
        Solution.addView(4, 18);
        Solution.addView(4, 19);
        Solution.addView(4, 20);

        // viewer5:     viewer1=10/10=100%      viewer2=9/13=69%        viewer3=7/10=70%    viewer4=11/11=100%
        // viewer5=........  viewer6=6/7=85%
        Solution.addView(5, 1);
        Solution.addView(5, 2);
        Solution.addView(5, 3);
        Solution.addView(5, 4);
        Solution.addView(5, 5);
        Solution.addView(5, 6);
        Solution.addView(5, 7);
        Solution.addView(5, 8);
        Solution.addView(5, 9);
        Solution.addView(5, 17);
        Solution.addView(5, 18);
        Solution.addView(5, 19);
        Solution.addView(5, 20);
        Solution.addView(5, 21);
        Solution.addView(5, 22);
        Solution.addView(5, 23);

        // viewer6:     viewer1=0/10=0%         viewer2=0/13=0%         viewer3=0/10=0%     viewer4=4/11=36%
        //   viewer5=6/16=37%    viewer6=........
        Solution.addView(6, 16);
        Solution.addView(6, 17);
        Solution.addView(6, 18);
        Solution.addView(6, 19);
        Solution.addView(6, 20);
        Solution.addView(6, 21);
        Solution.addView(6, 22);


        // getSimilarViewers(viewer1):
        ArrayList<Integer> list = Solution.getSimilarViewers(1);
        assertEquals(2, list.size());
        assertTrue(list.contains(2));
        assertTrue(list.contains(5));

        // getSimilarViewers(viewer2):
        list = Solution.getSimilarViewers(2);
        assertEquals(1, list.size());
        assertTrue(list.contains(3));

        // getSimilarViewers(viewer3):
        list = Solution.getSimilarViewers(3);
        assertEquals(1, list.size());//
        assertTrue(list.contains(2));

        // getSimilarViewers(viewer4):
        list = Solution.getSimilarViewers(4);
        assertEquals(1, list.size());//
        assertTrue(list.contains(5));

        // getSimilarViewers(viewer5):
        list = Solution.getSimilarViewers(5);
        assertEquals(0, list.size());//

        // getSimilarViewers(viewer6):
        list = Solution.getSimilarViewers(6);
        assertEquals(1, list.size());//
        assertTrue(list.contains(5));

        // getSimilarViewers(viewer10):
        list = Solution.getSimilarViewers(10);
        assertEquals(0, list.size());//

        // getSimilarViewers(viewer100 - not exist):
        list = Solution.getSimilarViewers(100);
        assertEquals(0, list.size());//
    }

    // mostInfluencingViewers:

    @Test
    public void mostInfluencingViewers_test() {
    }

    // getMoviesRecommendations:

    @Test
    public void getMoviesRecommendations_test() {

    }

    // getConditionalRecommendations:

    @Test
    public void getConditionalRecommendations_test() {

    }

}
