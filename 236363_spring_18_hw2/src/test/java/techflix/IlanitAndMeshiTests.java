package techflix;

import org.junit.Test;
import techflix.business.MovieRating;
import techflix.business.ReturnValue;
import techflix.business.Viewer;
import techflix.business.Movie;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static techflix.business.ReturnValue.*;

public class IlanitAndMeshiTests extends AbstractTest {

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
        assertEquals(ReturnValue.NOT_EXISTS, val);

        Viewer viewer4 = new Viewer();
        viewer4.setName(null);
        viewer4.setId(1);
        val = Solution.updateViewer(viewer3);
        assertEquals(ReturnValue.NOT_EXISTS, val);
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
        assertEquals(ReturnValue.NOT_EXISTS, val);
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
        assertEquals(ReturnValue.NOT_EXISTS, val);
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

        count = Solution.getMovieLikesCount(-1);
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

        count = Solution.getMovieDislikesCount(-1);
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

        // getSimilarViewers(viewer1):
        ArrayList<Integer> list = Solution.getSimilarViewers(1);
        assertEquals(0, list.size());

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
        list = Solution.getSimilarViewers(1);
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
        ArrayList<Integer> list = Solution.mostInfluencingViewers();
        assertEquals(0, list.size());

        initDB_Viewers(15);
        initDB_Movies(15);

        // viewer1:     views=4     rates=3     id=1
        Solution.addView(1, 1);
        Solution.addView(1, 2);
        Solution.addView(1, 3);
        Solution.addView(1, 4);
        Solution.addMovieRating(1, 1, MovieRating.LIKE);
        Solution.addMovieRating(1, 2, MovieRating.LIKE);
        Solution.addMovieRating(1, 3, MovieRating.DISLIKE);

        // viewer2:     views=7     rates=4     id=2
        Solution.addView(2, 1);
        Solution.addView(2, 2);
        Solution.addView(2, 3);
        Solution.addView(2, 4);
        Solution.addView(2, 5);
        Solution.addView(2, 6);
        Solution.addView(2, 7);
        Solution.addMovieRating(2, 1, MovieRating.LIKE);
        Solution.addMovieRating(2, 2, MovieRating.LIKE);
        Solution.addMovieRating(2, 3, MovieRating.DISLIKE);
        Solution.addMovieRating(2, 4, MovieRating.DISLIKE);

        // viewer3:     views=2     rates=1     id=3
        Solution.addView(3, 1);
        Solution.addView(3, 2);
        Solution.addMovieRating(3, 1, MovieRating.LIKE);

        // viewer4:     views=11     rates=2     id=4
        Solution.addView(4, 1);
        Solution.addView(4, 2);
        Solution.addView(4, 3);
        Solution.addView(4, 4);
        Solution.addView(4, 5);
        Solution.addView(4, 6);
        Solution.addView(4, 7);
        Solution.addView(4, 8);
        Solution.addView(4, 9);
        Solution.addView(4, 10);
        Solution.addView(4, 11);
        Solution.addMovieRating(4, 1, MovieRating.LIKE);
        Solution.addMovieRating(4, 2, MovieRating.DISLIKE);

        // viewer5:     views=11     rates=0     id=5
        Solution.addView(5, 1);
        Solution.addView(5, 2);
        Solution.addView(5, 3);
        Solution.addView(5, 4);
        Solution.addView(5, 5);
        Solution.addView(5, 6);
        Solution.addView(5, 7);
        Solution.addView(5, 8);
        Solution.addView(5, 9);
        Solution.addView(5, 10);
        Solution.addView(5, 11);

        // viewer6:     views=0     rates=0     id=6

        // viewer7:     views=8     rates=8     id=7
        Solution.addView(7, 1);
        Solution.addView(7, 2);
        Solution.addView(7, 3);
        Solution.addView(7, 4);
        Solution.addView(7, 5);
        Solution.addView(7, 6);
        Solution.addView(7, 7);
        Solution.addView(7, 8);
        Solution.addMovieRating(7, 1, MovieRating.LIKE);
        Solution.addMovieRating(7, 2, MovieRating.DISLIKE);
        Solution.addMovieRating(7, 3, MovieRating.LIKE);
        Solution.addMovieRating(7, 4, MovieRating.DISLIKE);
        Solution.addMovieRating(7, 5, MovieRating.LIKE);
        Solution.addMovieRating(7, 6, MovieRating.DISLIKE);
        Solution.addMovieRating(7, 7, MovieRating.LIKE);
        Solution.addMovieRating(7, 8, MovieRating.DISLIKE);

        // viewer8:     views=4     rates=3     id=8
        Solution.addView(8, 1);
        Solution.addView(8, 2);
        Solution.addView(8, 3);
        Solution.addView(8, 4);
        Solution.addMovieRating(8, 1, MovieRating.LIKE);
        Solution.addMovieRating(8, 2, MovieRating.LIKE);
        Solution.addMovieRating(8, 3, MovieRating.DISLIKE);

        // viewer9:     views=1     rates=1     id=9
        Solution.addView(9, 1);
        Solution.addMovieRating(9, 1, MovieRating.LIKE);

        // viewer10:    views=1     rates=0     id=10
        Solution.addView(10, 1);

        // viewer11:     views=5     rates=3     id=11
        Solution.addView(11, 1);
        Solution.addView(11, 2);
        Solution.addView(11, 3);
        Solution.addView(11, 4);
        Solution.addView(11, 5);
        Solution.addMovieRating(11, 1, MovieRating.LIKE);
        Solution.addMovieRating(11, 2, MovieRating.LIKE);
        Solution.addMovieRating(11, 3, MovieRating.DISLIKE);

        // viewer12:     views=11     rates=1     id=12
        Solution.addView(12, 1);
        Solution.addView(12, 2);
        Solution.addView(12, 3);
        Solution.addView(12, 4);
        Solution.addView(12, 5);
        Solution.addView(12, 6);
        Solution.addView(12, 7);
        Solution.addView(12, 8);
        Solution.addView(12, 9);
        Solution.addView(12, 10);
        Solution.addView(12, 11);
        Solution.addMovieRating(12, 1, MovieRating.LIKE);

        // viewer13:     views=2     rates=1     id=13
        Solution.addView(13, 1);
        Solution.addView(13, 2);
        Solution.addMovieRating(13, 1, MovieRating.LIKE);

        // order:
        // (1)  viewer4:     views=11    rates=2     id=4
        // (2)  viewer12:    views=11    rates=1     id=12
        // (3)  viewer5:     views=11    rates=0     id=5
        // (4)  viewer7:     views=8     rates=8     id=7
        // (5)  viewer2:     views=7     rates=4     id=2
        // (6)  viewer11:    views=5     rates=3     id=11
        // (7)  viewer1:     views=4     rates=3     id=1
        // (8)  viewer8:     views=4     rates=3     id=8
        // (9)  viewer3:     views=2     rates=1     id=3
        // (10) viewer13:    views=2     rates=1     id=13
        // (11) viewer9:     views=1     rates=1     id=9
        // (12) viewer10:    views=1     rates=0     id=10
        // (13) viewer6:     views=0     rates=0     id=6 -- not include! have 0 views
        list = Solution.mostInfluencingViewers();
        assertEquals(10, list.size());
        assertEquals(Integer.valueOf(4), list.get(0));
        assertEquals(Integer.valueOf(12), list.get(1));
        assertEquals(Integer.valueOf(5), list.get(2));
        assertEquals(Integer.valueOf(7), list.get(3));
        assertEquals(Integer.valueOf(2), list.get(4));
        assertEquals(Integer.valueOf(11), list.get(5));
        assertEquals(Integer.valueOf(1), list.get(6));
        assertEquals(Integer.valueOf(8), list.get(7));
        assertEquals(Integer.valueOf(3), list.get(8));
        assertEquals(Integer.valueOf(13), list.get(9));

    }

    // getMoviesRecommendations:

    @Test
    public void getMoviesRecommendations_test() {
        ArrayList<Integer> list = Solution.getMoviesRecommendations(1);
        assertEquals(0, list.size());

        initDB_Viewers(20);
        initDB_Movies(25);

        // viewer1:     movies=1-10
        for (int j = 1; j <= 10; j++) {
            Solution.addView(1, j);
        }
        list = Solution.getMoviesRecommendations(1);
        assertEquals(0, list.size());

        // add 13 similar viewers (ID: 2-14, SimilarMovies:1-8)
        for (int i = 2; i <= 14; i++) {
            for (int j = 1; j <= 8; j++) {
                Solution.addView(i, j);
            }
        }
        list = Solution.getSimilarViewers(1);
        assertEquals(13, list.size());
        for (int i = 2; i <= 14; i++) {
            assertTrue(list.contains(i));
        }

        list = Solution.getMoviesRecommendations(1);
        assertEquals(0, list.size());

        // add views to viewers:

        // movieID=15:   likes=9        dislikes=0
        Solution.addView(2, 15);
        Solution.addMovieRating(2, 15, MovieRating.LIKE);
        Solution.addView(3, 15);
        Solution.addMovieRating(3, 15, MovieRating.LIKE);
        Solution.addView(4, 15);
        Solution.addMovieRating(4, 15, MovieRating.LIKE);
        Solution.addView(5, 15);
        Solution.addMovieRating(5, 15, MovieRating.LIKE);
        Solution.addView(6, 15);
        Solution.addMovieRating(6, 15, MovieRating.LIKE);
        Solution.addView(7, 15);
        Solution.addMovieRating(7, 15, MovieRating.LIKE);
        Solution.addView(8, 15);
        Solution.addMovieRating(8, 15, MovieRating.LIKE);
        Solution.addView(9, 15);
        Solution.addMovieRating(9, 15, MovieRating.LIKE);
        Solution.addView(10, 15);
        Solution.addMovieRating(10, 15, MovieRating.LIKE);

        // movieID=9:   likes=7     --- not include in "getMoviesRecommendations(1)" - viewer1 already view movie9
        Solution.addView(2, 9);
        Solution.addMovieRating(2, 9, MovieRating.LIKE);
        Solution.addView(3, 9);
        Solution.addMovieRating(3, 9, MovieRating.LIKE);
        Solution.addView(4, 9);
        Solution.addMovieRating(4, 9, MovieRating.DISLIKE);
        Solution.addView(6, 9);
        Solution.addMovieRating(6, 9, MovieRating.DISLIKE);
        Solution.addView(7, 9);
        Solution.addMovieRating(7, 9, MovieRating.LIKE);
        Solution.addView(8, 9);
        Solution.addMovieRating(8, 9, MovieRating.DISLIKE);
        Solution.addView(9, 9);
        Solution.addMovieRating(9, 9, MovieRating.LIKE);
        Solution.addView(10, 9);
        Solution.addMovieRating(10, 9, MovieRating.LIKE);
        Solution.addView(12, 9);
        Solution.addMovieRating(10, 9, MovieRating.DISLIKE);
        Solution.addView(12, 9);
        Solution.addMovieRating(10, 9, MovieRating.LIKE);
        Solution.addView(13, 9);
        Solution.addMovieRating(10, 9, MovieRating.LIKE);

        // movieID=11:   likes=7        dislikes=4
        Solution.addView(2, 11);
        Solution.addMovieRating(2, 11, MovieRating.LIKE);
        Solution.addView(3, 11);
        Solution.addMovieRating(3, 11, MovieRating.LIKE);
        Solution.addView(4, 11);
        Solution.addMovieRating(4, 11, MovieRating.DISLIKE);
        Solution.addView(6, 11);
        Solution.addMovieRating(6, 11, MovieRating.DISLIKE);
        Solution.addView(7, 11);
        Solution.addMovieRating(7, 11, MovieRating.LIKE);
        Solution.addView(8, 11);
        Solution.addMovieRating(8, 11, MovieRating.DISLIKE);
        Solution.addView(9, 11);
        Solution.addMovieRating(9, 11, MovieRating.LIKE);
        Solution.addView(10, 11);
        Solution.addMovieRating(10, 11, MovieRating.LIKE);
        Solution.addView(11, 11);
        Solution.addMovieRating(11, 11, MovieRating.DISLIKE);
        Solution.addView(12, 11);
        Solution.addMovieRating(12, 11, MovieRating.LIKE);
        Solution.addView(13, 11);
        Solution.addMovieRating(13, 11, MovieRating.LIKE);

        // movieID=12:   likes=7    "emptyLikes"=2
        Solution.addView(2, 12);
        Solution.addMovieRating(2, 12, MovieRating.LIKE);
        Solution.addView(3, 12);
        Solution.addMovieRating(3, 12, MovieRating.LIKE);
        Solution.addView(4, 12);
        Solution.addView(5, 12);
        Solution.addMovieRating(5, 12, MovieRating.LIKE);
        Solution.addView(6, 12);
        Solution.addMovieRating(6, 12, MovieRating.LIKE);
        Solution.addView(7, 12);
        Solution.addView(8, 12);
        Solution.addMovieRating(8, 12, MovieRating.LIKE);
        Solution.addView(9, 12);
        Solution.addMovieRating(9, 12, MovieRating.LIKE);
        Solution.addView(10, 12);
        Solution.addMovieRating(10, 12, MovieRating.LIKE);

        // movieID=13:   likes=5        dislikes=2
        Solution.addView(2, 13);
        Solution.addMovieRating(2, 13, MovieRating.LIKE);
        Solution.addView(3, 13);
        Solution.addMovieRating(3, 13, MovieRating.DISLIKE);
        Solution.addView(9, 13);
        Solution.addMovieRating(9, 13, MovieRating.LIKE);
        Solution.addView(10, 13);
        Solution.addMovieRating(10, 13, MovieRating.LIKE);
        Solution.addView(11, 13);
        Solution.addMovieRating(11, 13, MovieRating.DISLIKE);
        Solution.addView(12, 13);
        Solution.addMovieRating(12, 13, MovieRating.LIKE);
        Solution.addView(13, 13);
        Solution.addMovieRating(13, 13, MovieRating.LIKE);
        Solution.addView(14, 13);
        Solution.addMovieRating(14, 13, MovieRating.DISLIKE);
        Solution.addView(15, 13);//not similar
        Solution.addMovieRating(15, 13, MovieRating.LIKE);
        Solution.addView(16, 13);//not similar
        Solution.addMovieRating(16, 13, MovieRating.LIKE);
        Solution.addView(17, 13);//not similar
        Solution.addMovieRating(17, 13, MovieRating.LIKE);

        // movieID=18:   likes=5
        Solution.addView(2, 18);
        Solution.addMovieRating(2, 18, MovieRating.LIKE);
        Solution.addView(3, 18);
        Solution.addMovieRating(3, 18, MovieRating.LIKE);
        Solution.addView(4, 18);
        Solution.addMovieRating(4, 18, MovieRating.LIKE);
        Solution.addView(5, 18);
        Solution.addMovieRating(5, 18, MovieRating.LIKE);
        Solution.addView(6, 18);
        Solution.addMovieRating(6, 18, MovieRating.LIKE);
        Solution.addView(15, 18);//not similar
        Solution.addMovieRating(15, 18, MovieRating.LIKE);
        Solution.addView(16, 18);//not similar
        Solution.addMovieRating(16, 18, MovieRating.LIKE);
        Solution.addView(17, 18);//not similar
        Solution.addMovieRating(17, 18, MovieRating.LIKE);

        // movieID=19:   likes=5        dislikes=4
        Solution.addMovieRating(5, 19, MovieRating.DISLIKE);
        Solution.addView(6, 19);
        Solution.addMovieRating(6, 19, MovieRating.DISLIKE);
        Solution.addView(7, 19);
        Solution.addMovieRating(7, 19, MovieRating.DISLIKE);
        Solution.addView(8, 19);
        Solution.addMovieRating(8, 19, MovieRating.DISLIKE);
        Solution.addView(9, 19);
        Solution.addMovieRating(9, 19, MovieRating.LIKE);
        Solution.addView(10, 19);
        Solution.addMovieRating(10, 19, MovieRating.LIKE);
        Solution.addView(11, 19);
        Solution.addMovieRating(11, 19, MovieRating.LIKE);
        Solution.addView(12, 19);
        Solution.addMovieRating(12, 19, MovieRating.LIKE);
        Solution.addView(13, 19);
        Solution.addMovieRating(13, 19, MovieRating.LIKE);
        Solution.addView(14, 19);
        Solution.addMovieRating(14, 19, MovieRating.DISLIKE);
        Solution.addView(15, 19);//not similar
        Solution.addMovieRating(15, 19, MovieRating.LIKE);
        Solution.addView(16, 19);//not similar
        Solution.addMovieRating(16, 19, MovieRating.DISLIKE);
        Solution.addView(17, 19);//not similar
        Solution.addMovieRating(17, 19, MovieRating.LIKE);

        // movieID=20:   likes=4        dislikes=2
        Solution.addView(5, 20);
        Solution.addMovieRating(5, 20, MovieRating.LIKE);
        Solution.addView(6, 20);
        Solution.addMovieRating(6, 20, MovieRating.DISLIKE);
        Solution.addView(7, 20);
        Solution.addMovieRating(7, 20, MovieRating.DISLIKE);
        Solution.addView(8, 20);
        Solution.addMovieRating(8, 20, MovieRating.LIKE);
        Solution.addView(10, 20);
        Solution.addMovieRating(10, 20, MovieRating.LIKE);
        Solution.addView(12, 20);
        Solution.addMovieRating(12, 20, MovieRating.LIKE);
        Solution.addView(15, 20);//not similar
        Solution.addMovieRating(15, 0, MovieRating.LIKE);

        // movieID=21:   likes=3        "emptyLikes"=3
        Solution.addView(7, 21);
        Solution.addMovieRating(7, 21, MovieRating.LIKE);
        Solution.addView(8, 21);
        Solution.addView(9, 21);
        Solution.addView(10, 21);
        Solution.addView(11, 21);
        Solution.addMovieRating(11, 21, MovieRating.LIKE);
        Solution.addView(12, 21);
        Solution.addMovieRating(12, 21, MovieRating.LIKE);

        // movieID=14:   likes=0        "emptyLikes"=1
        Solution.addView(14, 14);
        Solution.addView(15, 14);//not similar
        Solution.addMovieRating(15, 14, MovieRating.LIKE);
        Solution.addView(16, 14);//not similar
        Solution.addMovieRating(16, 14, MovieRating.LIKE);
        Solution.addView(17, 14);//not similar
        Solution.addMovieRating(17, 14, MovieRating.LIKE);

        // movieID=16:   likes=0        dislikes=1
        Solution.addView(14, 16);
        Solution.addMovieRating(14, 16, MovieRating.DISLIKE);

        // movieID=22:   likes=0        dislikes=13
        Solution.addView(2, 22);
        Solution.addMovieRating(2, 22, MovieRating.DISLIKE);
        Solution.addView(3, 22);
        Solution.addMovieRating(3, 22, MovieRating.DISLIKE);
        Solution.addView(4, 22);
        Solution.addMovieRating(4, 22, MovieRating.DISLIKE);
        Solution.addView(5, 22);
        Solution.addMovieRating(5, 22, MovieRating.DISLIKE);
        Solution.addView(6, 22);
        Solution.addMovieRating(6, 22, MovieRating.DISLIKE);
        Solution.addView(7, 22);
        Solution.addMovieRating(7, 22, MovieRating.DISLIKE);
        Solution.addView(8, 22);
        Solution.addMovieRating(8, 22, MovieRating.DISLIKE);
        Solution.addView(9, 22);
        Solution.addMovieRating(9, 22, MovieRating.DISLIKE);
        Solution.addView(10, 22);
        Solution.addMovieRating(10, 22, MovieRating.DISLIKE);
        Solution.addView(11, 22);
        Solution.addMovieRating(11, 22, MovieRating.DISLIKE);
        Solution.addView(12, 22);
        Solution.addMovieRating(12, 22, MovieRating.DISLIKE);
        Solution.addView(13, 22);
        Solution.addMovieRating(13, 22, MovieRating.DISLIKE);
        Solution.addView(14, 22);
        Solution.addMovieRating(14, 22, MovieRating.DISLIKE);
        Solution.addView(15, 22);//not similar
        Solution.addMovieRating(15, 22, MovieRating.LIKE);
        Solution.addView(16, 22);//not similar
        Solution.addMovieRating(16, 22, MovieRating.LIKE);
        Solution.addView(17, 22);//not similar
        Solution.addMovieRating(17, 22, MovieRating.LIKE);

        // movieID=23:   likes=0        dislikes=1
        Solution.addView(2, 23);
        Solution.addMovieRating(2, 23, MovieRating.DISLIKE);

        // movieID=24:   likes=0    "emptyLikes"=1        dislikes=1
        Solution.addView(4, 24);
        Solution.addView(5, 24);
        Solution.addMovieRating(5, 24, MovieRating.DISLIKE);
        Solution.addView(15, 0);//not similar
        Solution.addMovieRating(15, 0, MovieRating.LIKE);

        // order:
        // (1)  movie15:    likes=9     id=15
        // (-)  movie9:    likes=9      id=9    --- not include - viewer1 already view movie9
        // (2)  movie11:    likes=7     id=11
        // (3)  movie12:    likes=7     id=12       "emptyLikes"=2
        // (4)  movie13:    likes=5     id=13
        // (4)  movie18:    likes=5     id=18
        // (6)  movie19:    likes=5     id=19
        // (7)  movie20:    likes=4     id=20
        // (8)  movie21:    likes=3     id=21       "emptyLikes"=3
        // (9)  movie14:    likes=0     id=14       "emptyLikes"=1        dislikes=0
        // (10) movie16:    likes=0     id=16                             dislikes=1
        // (11) movie22:    likes=0     id=22                             dislikes=13
        // (12) movie23:    likes=0     id=23                             dislikes=1
        // (13) movie24:    likes=0     id=24       "emptyLikes"=1        dislikes=1
        list = Solution.getMoviesRecommendations(1);
        assertEquals(10, list.size());
        assertEquals(Integer.valueOf(15), list.get(0));
        assertEquals(Integer.valueOf(11), list.get(1));
        assertEquals(Integer.valueOf(12), list.get(2));
        assertEquals(Integer.valueOf(13), list.get(3));
        assertEquals(Integer.valueOf(18), list.get(4));
        assertEquals(Integer.valueOf(19), list.get(5));
        assertEquals(Integer.valueOf(20), list.get(6));
        assertEquals(Integer.valueOf(21), list.get(7));
        assertEquals(Integer.valueOf(14), list.get(8));
        assertEquals(Integer.valueOf(16), list.get(9));
//        assertEquals(Integer.valueOf(22), list.get(10));
//        assertEquals(Integer.valueOf(23), list.get(11));
//        assertEquals(Integer.valueOf(24), list.get(12));

    }

    // getConditionalRecommendations:

    @Test
    public void getConditionalRecommendations_test1() {
        ArrayList<Integer> list = Solution.getConditionalRecommendations(1, 1);
        assertEquals(0, list.size());

        initDB_Viewers(5);
        initDB_Movies(5);

        // viewer1:     viewed movies: 3      likes movies: none
        Solution.addView(1, 3);
        list = Solution.getConditionalRecommendations(1, 3);
        assertEquals(0, list.size());

        // getSimilarViewers(1):
        list = Solution.getSimilarViewers(1);
        assertEquals(0, list.size());

        // viewer1:     viewed movies: 3      likes movies: 3
        Solution.addMovieRating(1, 3, MovieRating.LIKE);
        list = Solution.getConditionalRecommendations(1, 3);
        assertEquals(0, list.size());


        // viewer2:     viewed movies: 3      likes movies: none
        Solution.addView(2, 3);

        list = Solution.getSimilarViewers(1);
        assertEquals(1, list.size());
        assertEquals(Integer.valueOf(2), list.get(0));

        list = Solution.getConditionalRecommendations(1, 3);
        assertEquals(0, list.size());

        // viewer2:     viewed movies: 3      likes movies: none        dislikes movies: 3
        Solution.addMovieRating(2, 3, MovieRating.DISLIKE);
        list = Solution.getConditionalRecommendations(1, 3);
        assertEquals(0, list.size());

        // viewer2:     viewed movies: 3,2      likes movies: none        dislikes movies: 3
        Solution.addView(2, 2);
        list = Solution.getConditionalRecommendations(1, 3);
        assertEquals(0, list.size());

        // viewer2:     viewed movies: 3,2      likes movies: 3        dislikes movies: none    (now v3 is similarRank)
        Solution.removeMovieRating(2, 3);
        Solution.addMovieRating(2, 3, MovieRating.LIKE);
        list = Solution.getConditionalRecommendations(1, 3);
        assertEquals(1, list.size());
        assertEquals(Integer.valueOf(2), list.get(0));

        // viewer2:     viewed movies: 3,2,4      likes movies: 3,4     dislikes movies: none    (now v3 is similarRank)
        Solution.addView(2, 4);
        Solution.addMovieRating(2, 4, MovieRating.LIKE);
        list = Solution.getConditionalRecommendations(1, 3);
        assertEquals(2, list.size());
        assertEquals(Integer.valueOf(4), list.get(0));
        assertEquals(Integer.valueOf(2), list.get(1));


        // viewer2:     viewed movies: 3,2,4,1      likes movies: 3,4       dislikes movies: 1   (now v3 is similarRank)
        Solution.addView(2, 1);
        Solution.addMovieRating(2, 1, MovieRating.DISLIKE);
        list = Solution.getConditionalRecommendations(1, 3);
        assertEquals(3, list.size());
        assertEquals(Integer.valueOf(4), list.get(0));
        assertEquals(Integer.valueOf(1), list.get(1));
        assertEquals(Integer.valueOf(2), list.get(2));

    }

    @Test
    public void getConditionalRecommendations_test2() {
        ArrayList<Integer> list = Solution.getConditionalRecommendations(1, 1);
        assertEquals(0, list.size());

        initDB_Viewers(20);
        initDB_Movies(25);

        // viewer1:     movies=1-5
        for (int j = 1; j <= 5; j++) {
            Solution.addView(1, j);
        }
        list = Solution.getConditionalRecommendations(1, 1);
        assertEquals(0, list.size());

        // add 16 similar viewers (ID: 2-17, SimilarMovies:1-4)
        for (int i = 2; i <= 17; i++) {
            for (int j = 1; j <= 4; j++) {
                Solution.addView(i, j);
            }
        }
        list = Solution.getSimilarViewers(1);
        assertEquals(16, list.size());
        for (int i = 2; i <= 17; i++) {
            assertTrue(list.contains(i));
        }

        list = Solution.getConditionalRecommendations(1, 1);
        assertEquals(0, list.size());

        // viewer1: add LIKE/DISLIKE to movies 1,2
        Solution.addMovieRating(1, 1, MovieRating.LIKE);
        Solution.addMovieRating(1, 2, MovieRating.DISLIKE);

        list = Solution.getConditionalRecommendations(1, 1);
        assertEquals(0, list.size());
        list = Solution.getConditionalRecommendations(1, 2);
        assertEquals(0, list.size());

// current status:  ( "-"=didn't view    "+"=didn't view   "V"=LIKE    "X"=DISLIKE )
// id ||   m1   m2   m3   m4   m5   m6   m7   m8   m9   m10   m11   m12   m13   m14   m15   m16   m17   m18   m19   m20
// --------------------------------------------------------------------------------------------------------------------
// 1  ||   V    +    +    +    +    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 2  ||   +    X    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 3  ||   +    +    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 4  ||   +    +    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 5  ||   +    +    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 6  ||   +    +    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 7  ||   +    +    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 8  ||   +    +    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 9  ||   +    +    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 10 ||   +    +    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 11 ||   +    +    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 12 ||   +    +    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 13 ||   +    +    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 14 ||   +    +    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 15 ||   +    +    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 16 ||   +    +    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 17 ||   +    +    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// ---------------------------------------------------------------------------------------------------------------------

        // add LIKEs to movies1: viewerID=3-15
        for (int i = 3; i <= 15; i++) { // now viewerID=3-15 are similar rankers to {viewer1,movie1,rate=LIKE}
            Solution.addMovieRating(i, 1, MovieRating.LIKE);
        }
        // add DISLIKEs to movies2: viewerID=5-16
        for (int i = 5; i <= 16; i++) { // now viewerID=3-16 are similar rankers to {viewer1,movie2,rate=DISLIKE}
            Solution.addMovieRating(i, 2, MovieRating.DISLIKE);
        }

        list = Solution.getConditionalRecommendations(1, 1);
        assertEquals(0, list.size());// viewer1 already view movies 1-4
        list = Solution.getConditionalRecommendations(1, 2);
        assertEquals(0, list.size());// viewer1 already view movies 1-4

// current status:  ( "-"=didn't view    "+"=didn't view   "V"=LIKE    "X"=DISLIKE )
// id ||   m1   m2   m3   m4   m5   m6   m7   m8   m9   m10   m11   m12   m13   m14   m15   m16   m17   m18   m19   m20
// --------------------------------------------------------------------------------------------------------------------
// 1  ||   V    +    +    +    +    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 2  ||   +    X    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 3  ||   V    +    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 4  ||   V    +    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 5  ||   V    X    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 6  ||   V    X    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 7  ||   V    X    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 8  ||   V    X    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 9  ||   V    X    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 10 ||   V    X    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 11 ||   V    X    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 12 ||   V    X    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 13 ||   V    X    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 14 ||   V    X    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 15 ||   V    X    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 16 ||   +    X    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 17 ||   +    +    +    +    -    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// ---------------------------------------------------------------------------------------------------------------------


        Solution.addView(4, 6);

        Solution.addView(2, 7);

        for (int i = 4; i <= 5; i++) {
            Solution.addView(i, 8);
        }

        for (int i = 13; i <= 13; i++) {
            Solution.addView(i, 9);
            Solution.addMovieRating(i, 9, MovieRating.LIKE);
        }

        for (int i = 3; i <= 16; i++) {
            Solution.addView(i, 11);
            Solution.addMovieRating(i, 11, MovieRating.LIKE);
        }

        for (int i = 3; i <= 17; i++) {
            Solution.addView(i, 12);
            Solution.addMovieRating(i, 12, MovieRating.LIKE);
        }

        for (int i = 8; i <= 9; i++) {
            Solution.addView(i, 14);
            Solution.addMovieRating(i, 14, MovieRating.LIKE);
        }

        for (int i = 3; i <= 7; i++) {
            Solution.addView(i, 16);
            Solution.addMovieRating(i, 16, MovieRating.LIKE);
        }

        for (int i = 11; i <= 18; i++) {
            Solution.addView(i, 17);
            Solution.addMovieRating(i, 17, MovieRating.LIKE);
        }

        for (int i = 6; i <= 9; i++) {
            Solution.addView(i, 18);
            Solution.addMovieRating(i, 18, MovieRating.LIKE);
        }

        for (int i = 9; i <= 15; i++) {
            Solution.addView(i, 20);
            Solution.addMovieRating(i, 20, MovieRating.LIKE);
        }

        for (int i = 12; i <= 14; i++) {
            Solution.addView(i, 19);
        }

// current status:  ( "-"=didn't view    "+"=didn't view   "V"=LIKE    "X"=DISLIKE )
// id ||   m1   m2   m3   m4   m5   m6   m7   m8   m9   m10   m11   m12   m13   m14   m15   m16   m17   m18   m19   m20
// --------------------------------------------------------------------------------------------------------------------
// 1  ||   V    +    +    +    +    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 2  ||   +    X    +    +    -    -    +    -    -     -     -     -     -     -     -     -     -     -     -     -
// 3  ||   V    +    +    +    -    -    -    -    -     -     V     V     -     -     -     V     -     -     -     -
// 4  ||   V    +    +    +    -    +    -    +    -     -     V     V     -     -     -     V     -     -     -     -
// 5  ||   V    X    +    +    -    -    -    +    -     -     V     V     -     -     -     V     -     -     -     -
// 6  ||   V    X    +    +    -    -    -    -    -     -     V     V     -     -     -     V     -     V     -     -
// 7  ||   V    X    +    +    -    -    -    -    -     -     V     V     -     -     -     V     -     V     -     -
// 8  ||   V    X    +    +    -    -    -    -    -     -     V     V     -     V     -     -     -     V     -     -
// 9  ||   V    X    +    +    -    -    -    -    -     -     V     V     -     V     -     -     -     V     -     V
// 10 ||   V    X    +    +    -    -    -    -    -     -     V     V     -     -     -     -     -     -     -     V
// 11 ||   V    X    +    +    -    -    -    -    -     -     V     V     -     -     -     -     V     -     -     V
// 12 ||   V    X    +    +    -    -    -    -    -     -     V     V     -     -     -     -     V     -     +     V
// 13 ||   V    X    +    +    -    -    -    -    V     -     V     V     -     -     -     -     V     -     +     V
// 14 ||   V    X    +    +    -    -    -    -    -     -     V     V     -     -     -     -     V     -     +     V
// 15 ||   V    X    +    +    -    -    -    -    -     -     V     V     -     -     -     -     V     -     -     V
// 16 ||   +    X    +    +    -    -    -    -    -     -     V     V     -     -     -     -     V     -     -     -
// 17 ||   +    +    +    +    -    -    -    -    -     -     -     V     V     -     -     -     V     -     -     -
// 18 ||   -    -    -    -    -    -    -    -    -     -     -     -     -     -     -     -     V     -     -     -

        list = Solution.getConditionalRecommendations(1, 1);
        assertEquals(10, list.size());
        assertEquals(Integer.valueOf(6), list.get(8));      // 1  VIEWs
        assertEquals(Integer.valueOf(8), list.get(9));      // 2  VIEWs
        assertEquals(Integer.valueOf(9), list.get(7));      // 1  LIKEs
        assertEquals(Integer.valueOf(11), list.get(0));     // 14 LIKEs
        assertEquals(Integer.valueOf(12), list.get(1));     // 14 LIKEs
        assertEquals(Integer.valueOf(14), list.get(6));     // 2  LIKEs
        assertEquals(Integer.valueOf(16), list.get(3));     // 5  LIKEs
        assertEquals(Integer.valueOf(17), list.get(4));     // 5  LIKEs
        assertEquals(Integer.valueOf(18), list.get(5));     // 4  LIKEs
        // assertEquals(Integer.valueOf(19), list.get(10));    // 3  VIEWs  // out of bounds
        assertEquals(Integer.valueOf(20), list.get(2));     // 7  LIKEs


        list = Solution.getConditionalRecommendations(1, 2);
        assertEquals(10, list.size());
        assertEquals(Integer.valueOf(11), list.get(0));     // 12 LIKEs
        assertEquals(Integer.valueOf(12), list.get(1));     // 12 LIKEs
        assertEquals(Integer.valueOf(20), list.get(2));     // 7 LIKEs
        assertEquals(Integer.valueOf(17), list.get(3));      // 6 LIKEs
        assertEquals(Integer.valueOf(18), list.get(4));      // 4 LIKEs
        assertEquals(Integer.valueOf(16), list.get(5));      // 3 LIKEs
        assertEquals(Integer.valueOf(14), list.get(6));      // 2 LIKEs
        assertEquals(Integer.valueOf(9), list.get(7));      // 1 LIKEs
        assertEquals(Integer.valueOf(8), list.get(8));      // 1 VIEWs
        assertEquals(Integer.valueOf(19), list.get(9));      // 3 VIEWs


// ---------------------------------------------------------------------------------------------------------------------

        for (int i = 12; i <= 14; i++) {
            Solution.removeView(i, 19);
        }

// current status:  ( "-"=didn't view    "+"=didn't view   "V"=LIKE    "X"=DISLIKE )
// id ||   m1   m2   m3   m4   m5   m6   m7   m8   m9   m10   m11   m12   m13   m14   m15   m16   m17   m18   m19   m20
// --------------------------------------------------------------------------------------------------------------------
// 1  ||   V    +    +    +    +    -    -    -    -     -     -     -     -     -     -     -     -     -     -     -
// 2  ||   +    X    +    +    -    -    +    -    -     -     -     -     -     -     -     -     -     -     -     -
// 3  ||   V    +    +    +    -    -    -    -    -     -     V     V     -     -     -     V     -     -     -     -
// 4  ||   V    +    +    +    -    +    -    +    -     -     V     V     -     -     -     V     -     -     -     -
// 5  ||   V    X    +    +    -    -    -    +    -     -     V     V     -     -     -     V     -     -     -     -
// 6  ||   V    X    +    +    -    -    -    -    -     -     V     V     -     -     -     V     -     V     -     -
// 7  ||   V    X    +    +    -    -    -    -    -     -     V     V     -     -     -     V     -     V     -     -
// 8  ||   V    X    +    +    -    -    -    -    -     -     V     V     -     V     -     -     -     V     -     -
// 9  ||   V    X    +    +    -    -    -    -    -     -     V     V     -     V     -     -     -     V     -     V
// 10 ||   V    X    +    +    -    -    -    -    -     -     V     V     -     -     -     -     -     -     -     V
// 11 ||   V    X    +    +    -    -    -    -    -     -     V     V     -     -     -     -     V     -     -     V
// 12 ||   V    X    +    +    -    -    -    -    -     -     V     V     -     -     -     -     V     -     -     V
// 13 ||   V    X    +    +    -    -    -    -    V     -     V     V     -     -     -     -     V     -     -     V
// 14 ||   V    X    +    +    -    -    -    -    -     -     V     V     -     -     -     -     V     -     -     V
// 15 ||   V    X    +    +    -    -    -    -    -     -     V     V     -     -     -     -     V     -     -     V
// 16 ||   +    X    +    +    -    -    -    -    -     -     V     V     -     -     -     -     V     -     -     -
// 17 ||   +    +    +    +    -    -    -    -    -     -     -     V     V     -     -     -     V     -     -     -
// 18 ||   -    -    -    -    -    -    -    -    -     -     -     -     -     -     -     -     V     -     -     -

        list = Solution.getConditionalRecommendations(1, 2);
        assertEquals(9, list.size());
        assertEquals(Integer.valueOf(11), list.get(0));     // 12 LIKEs
        assertEquals(Integer.valueOf(12), list.get(1));     // 12 LIKEs
        assertEquals(Integer.valueOf(20), list.get(2));     // 7 LIKEs
        assertEquals(Integer.valueOf(17), list.get(3));     // 6 LIKEs
        assertEquals(Integer.valueOf(18), list.get(4));     // 4 LIKEs
        assertEquals(Integer.valueOf(16), list.get(5));     // 3 LIKEs
        assertEquals(Integer.valueOf(14), list.get(6));     // 2 LIKEs
        assertEquals(Integer.valueOf(9), list.get(7));      // 1 LIKEs
        assertEquals(Integer.valueOf(8), list.get(8));      // 1 VIEWs

    }

    // different issues:

    @Test
    public void test_Views_complex() {

        // part 1: simple case

        // add new Viewer: v1
        Viewer v1 = new Viewer();
        v1.setId(1);
        v1.setName("v1_name");
        assertEquals(ReturnValue.OK, Solution.createViewer(v1));

        // add new Movie: m1
        Movie m1 = new Movie();
        m1.setId(1);
        m1.setName("m1_name");
        m1.setDescription("m1_description");
        assertEquals(ReturnValue.OK, Solution.createMovie(m1));
        assertEquals(Integer.valueOf(0), Solution.getMovieViewCount(1));
        assertEquals(0, Solution.getMovieLikesCount(1));
        assertEquals(0, Solution.getMovieDislikesCount(1));

        // add new View: v1-m1
        assertEquals(ReturnValue.OK, Solution.addView(1, 1));
        assertEquals(Integer.valueOf(1), Solution.getMovieViewCount(1));
        assertEquals(0, Solution.getMovieLikesCount(1));
        assertEquals(0, Solution.getMovieDislikesCount(1));

        // remove View: v1-m1
        assertEquals(ReturnValue.OK, Solution.removeView(1, 1));
        assertEquals(Integer.valueOf(0), Solution.getMovieViewCount(1));
        assertEquals(0, Solution.getMovieLikesCount(1));
        assertEquals(0, Solution.getMovieDislikesCount(1));

        // part 2: add rate before aff view, remove view after remove rate

        // add new Movie: m2
        Movie m2 = new Movie();
        m2.setId(2);
        m2.setName("m2_name");
        m2.setDescription("m2_description");
        assertEquals(ReturnValue.OK, Solution.createMovie(m2));
        assertEquals(Integer.valueOf(0), Solution.getMovieViewCount(2));
        assertEquals(0, Solution.getMovieLikesCount(2));
        assertEquals(0, Solution.getMovieDislikesCount(2));

        // try to add Rate: v1-m2-like >> fail
        assertEquals(ReturnValue.NOT_EXISTS, Solution.addMovieRating(1, 2, MovieRating.LIKE));

        // add new View: v1-m2
        assertEquals(ReturnValue.OK, Solution.addView(1, 2));
        assertEquals(Integer.valueOf(1), Solution.getMovieViewCount(2));
        assertEquals(0, Solution.getMovieLikesCount(2));
        assertEquals(0, Solution.getMovieDislikesCount(2));

        // add Rate: v1-m2-like
        assertEquals(ReturnValue.OK, Solution.addMovieRating(1, 2, MovieRating.LIKE));
        assertEquals(Integer.valueOf(1), Solution.getMovieViewCount(2));
        assertEquals(1, Solution.getMovieLikesCount(2));
        assertEquals(0, Solution.getMovieDislikesCount(2));

        // update Rate: v1-m2-dislike
        assertEquals(ReturnValue.OK, Solution.addMovieRating(1, 2, MovieRating.DISLIKE));
        assertEquals(Integer.valueOf(1), Solution.getMovieViewCount(2));
        assertEquals(0, Solution.getMovieLikesCount(2));
        assertEquals(1, Solution.getMovieDislikesCount(2));

        // remove Rate: v1-m2-dislike:
        assertEquals(ReturnValue.OK, Solution.removeMovieRating(1, 2));
        assertEquals(Integer.valueOf(1), Solution.getMovieViewCount(2));
        assertEquals(0, Solution.getMovieLikesCount(2));
        assertEquals(0, Solution.getMovieDislikesCount(2));

        // remove view: v1-m2:
        assertEquals(ReturnValue.OK, Solution.removeView(1, 2));
        assertEquals(Integer.valueOf(0), Solution.getMovieViewCount(2));
        assertEquals(0, Solution.getMovieLikesCount(2));
        assertEquals(0, Solution.getMovieDislikesCount(2));

        // part 3: remove view without remove rate and check the rate also delete

        // add new Viewer: v2
        Viewer v2 = new Viewer();
        v2.setId(2);
        v2.setName("v2_name");
        assertEquals(ReturnValue.OK, Solution.createViewer(v2));

        // add new View: v2-m2
        assertEquals(ReturnValue.OK, Solution.addView(2, 2));
        assertEquals(Integer.valueOf(1), Solution.getMovieViewCount(2));
        assertEquals(0, Solution.getMovieLikesCount(2));
        assertEquals(0, Solution.getMovieDislikesCount(2));

        // add Rate: v2-m2-dislike
        assertEquals(ReturnValue.OK, Solution.addMovieRating(2, 2, MovieRating.DISLIKE));
        assertEquals(Integer.valueOf(1), Solution.getMovieViewCount(2));
        assertEquals(0, Solution.getMovieLikesCount(2));
        assertEquals(1, Solution.getMovieDislikesCount(2));

        // remove view: v2-m2:
        assertEquals(ReturnValue.OK, Solution.removeView(2, 2));
        assertEquals(Integer.valueOf(0), Solution.getMovieViewCount(2));
        assertEquals(0, Solution.getMovieLikesCount(2));
        assertEquals(0, Solution.getMovieDislikesCount(2));

        // part 4: remove movie without remove view/rate and check the view/rate also delete

        // add new Viewer: v3
        Viewer v3 = new Viewer();
        v3.setId(3);
        v3.setName("v3_name");
        assertEquals(ReturnValue.OK, Solution.createViewer(v3));

        // add new View: v3-m1
        assertEquals(ReturnValue.OK, Solution.addView(3, 1));
        assertEquals(Integer.valueOf(1), Solution.getMovieViewCount(1));
        assertEquals(0, Solution.getMovieLikesCount(1));
        assertEquals(0, Solution.getMovieDislikesCount(1));

        // add Rate: v3-m1-like
        assertEquals(ReturnValue.OK, Solution.addMovieRating(3, 1, MovieRating.DISLIKE));
        assertEquals(Integer.valueOf(1), Solution.getMovieViewCount(1));
        assertEquals(0, Solution.getMovieLikesCount(1));
        assertEquals(1, Solution.getMovieDislikesCount(1));

        // remove Movie: m1:
        assertEquals(ReturnValue.OK, Solution.deleteMovie(m1));
        assertEquals(Integer.valueOf(0), Solution.getMovieViewCount(1));
        assertEquals(0, Solution.getMovieLikesCount(1));
        assertEquals(0, Solution.getMovieDislikesCount(1));

        // part 5: remove viewer without remove view/rate and check the view/rate also delete

        // add new View: v3-m2
        assertEquals(ReturnValue.OK, Solution.addView(3, 2));
        assertEquals(Integer.valueOf(1), Solution.getMovieViewCount(2));
        assertEquals(0, Solution.getMovieLikesCount(2));
        assertEquals(0, Solution.getMovieDislikesCount(2));

        // add Rate: v3-m2-dislike
        assertEquals(ReturnValue.OK, Solution.addMovieRating(3, 2, MovieRating.DISLIKE));
        assertEquals(Integer.valueOf(1), Solution.getMovieViewCount(2));
        assertEquals(0, Solution.getMovieLikesCount(2));
        assertEquals(1, Solution.getMovieDislikesCount(2));

        // remove Viewer: v3:
        assertEquals(ReturnValue.OK, Solution.deleteViewer(v3));
        assertEquals(Integer.valueOf(0), Solution.getMovieViewCount(2));
        assertEquals(0, Solution.getMovieLikesCount(2));
        assertEquals(0, Solution.getMovieDislikesCount(2));
    }

    @Test
    public void check_BAD_PARAM() {
        Viewer v1 = new Viewer();
        v1.setName("v1_name");
        v1.setId(-1);
        assertEquals(NOT_EXISTS, Solution.deleteViewer(v1));

        Movie m1 = new Movie();
        m1.setId(-1);
        m1.setName("m1_name");
        m1.setDescription("m1_description");
        assertEquals(NOT_EXISTS, Solution.deleteMovie(m1));

        v1.setId(1);
        assertEquals(NOT_EXISTS, Solution.addView(1, 1));
        assertEquals(OK, Solution.createViewer(v1));
        assertEquals(NOT_EXISTS, Solution.addView(1, 1));
        assertEquals(NOT_EXISTS, Solution.addView(-1, 1));
        assertEquals(NOT_EXISTS, Solution.addView(1, -1));

        m1.setId(1);
        assertEquals(OK, Solution.createMovie(m1));
        assertEquals(OK, Solution.addView(1, 1));

        assertEquals(NOT_EXISTS, Solution.removeView(-1, 1));
        assertEquals(NOT_EXISTS, Solution.removeView(1, -1));
        assertEquals(OK, Solution.removeView(1, 1));


        assertEquals(NOT_EXISTS, Solution.addMovieRating(1, 1, MovieRating.LIKE));
        assertEquals(OK, Solution.addView(1, 1));
        assertEquals(NOT_EXISTS, Solution.addMovieRating(-1, 1, MovieRating.LIKE));
        assertEquals(NOT_EXISTS, Solution.addMovieRating(1, -1, MovieRating.LIKE));
        assertEquals(OK, Solution.addMovieRating(1, 1, MovieRating.LIKE));

    }
}
