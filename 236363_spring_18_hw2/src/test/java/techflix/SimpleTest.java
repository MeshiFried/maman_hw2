package techflix;

import org.junit.Test;
import techflix.business.MovieRating;
import techflix.business.ReturnValue;
import techflix.business.Viewer;
import techflix.business.Movie;

import javax.swing.*;

import static org.junit.Assert.assertEquals;
import static techflix.business.ReturnValue.*;

public class SimpleTest extends AbstractTest {

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

    // deleteViewer:
    @Test
    public void deleteViewer_deleteNonExistViewer() { //todo: not working!
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);

        ReturnValue actual = Solution.deleteViewer(viewer1);
        assertEquals(NOT_EXISTS, actual);
    }

    @Test
    public void getViewer_NonExistingViewer(){
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Viewer viewer = Solution.getViewer(2);
        assertEquals(Viewer.badViewer().getId(),viewer.getId());
        assertEquals(Viewer.badViewer().getName(),viewer.getName());
    }

    @Test
    public void getViewer_Existing(){
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Viewer viewer = Solution.getViewer(1);
        assertEquals(viewer1.getId(),viewer.getId());
        assertEquals(viewer1.getName(),viewer.getName());
    }

    @Test
    public void updateViewer_NonExistingViewer(){
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Viewer viewer2= new Viewer();
        viewer2.setName("viewer2");
        viewer2.setId(2);

        ReturnValue val =Solution.updateViewer(viewer2);
        assertEquals(ReturnValue.NOT_EXISTS,val);

        Viewer viewer3= new Viewer();
        viewer3.setName("viewer3");
        viewer3.setId(-5);
        val = Solution.updateViewer(viewer3);
        assertEquals(ReturnValue.BAD_PARAMS,val);

        Viewer viewer4= new Viewer();
        viewer4.setName(null);
        viewer4.setId(1);
        val = Solution.updateViewer(viewer3);
        assertEquals(ReturnValue.BAD_PARAMS,val);
    }

    @Test
    public void updateViewer_Existing(){
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Viewer viewer2= new Viewer();
        viewer2.setName("viewer2");
        viewer2.setId(1);

        ReturnValue val =Solution.updateViewer(viewer2);
        assertEquals(ReturnValue.OK,val);
        assertEquals(Solution.getViewer(1).getName(),viewer2.getName());
    }

    @Test
    public void createMovie_addMovie(){
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");

        ReturnValue val = Solution.createMovie(movie1);
        assertEquals(ReturnValue.OK,val);
    }

    @Test
    public void createMovie_BadParams(){
        Movie movie1 = new Movie();
        movie1.setId(-4);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");

        ReturnValue val = Solution.createMovie(movie1);
        assertEquals(ReturnValue.BAD_PARAMS,val);

        Movie movie2 = new Movie();
        movie1.setId(2);
        movie1.setName(null);
        movie1.setDescription("Comedy");
        val = Solution.createMovie(movie2);
        assertEquals(ReturnValue.BAD_PARAMS,val);

        Movie movie3 = new Movie();
        movie1.setId(3);
        movie1.setName("The Godfather");
        movie1.setDescription(null);
        val = Solution.createMovie(movie3);
        assertEquals(ReturnValue.BAD_PARAMS,val);
    }

    @Test
    public void createMovie_AlreadyExist(){
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");

        Solution.createMovie(movie1);
        ReturnValue val = Solution.createMovie(movie1);
        assertEquals(ReturnValue.ALREADY_EXISTS,val);
    }

    @Test
    public void deleteMovie_deleteExistingMovie(){
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");

        Solution.createMovie(movie1);
        ReturnValue val =  Solution.deleteMovie(movie1);
        assertEquals(ReturnValue.OK,val);

    }

    @Test
    public void deleteMovie_NotExists(){
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");

        Solution.createMovie(movie1);
        Solution.deleteMovie(movie1);
        ReturnValue val =  Solution.deleteMovie(movie1);
        assertEquals(ReturnValue.NOT_EXISTS,val);
    }

    @Test
    public void deleteMovie_BadParams(){
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
        assertEquals(ReturnValue.BAD_PARAMS,val);
    }

    @Test
    public void getMovie_ExistingMovie(){
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");

        Solution.createMovie(movie1);
        Movie movie = Solution.getMovie(1);
        assertEquals(movie1.getDescription(),movie.getDescription());
        assertEquals(movie1.getName(),movie.getName());
    }

    @Test
    public void getMovie_NotExist(){
        Movie movie = Solution.getMovie(1);
        assertEquals(Movie.badMovie(),movie);
    }

    @Test
    public void updateMovie_SuccessTest(){
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
        assertEquals(movie2.getDescription(),Solution.getMovie(1).getDescription());
    }

    @Test
    public void updateMovie_NotExists(){
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");

        ReturnValue val = Solution.updateMovie(movie1);
        assertEquals(ReturnValue.NOT_EXISTS,val);
    }

    @Test
    public void updateMovie_BadParams(){
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription(null);

        ReturnValue val = Solution.updateMovie(movie1);
        assertEquals(ReturnValue.BAD_PARAMS,val);
    }

    @Test
    public void addView_success(){
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        ReturnValue val = Solution.addView(1,1);
        assertEquals(ReturnValue.OK,val);
    }

    @Test
    public void addView_ForeignKeyViolation(){
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        ReturnValue val = Solution.addView(2,1);
        assertEquals(ReturnValue.NOT_EXISTS,val);
    }

    @Test
    public void addView_AlreadyExists(){
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        Solution.addView(1,1);
        ReturnValue val = Solution.addView(1,1);
        assertEquals(ReturnValue.ALREADY_EXISTS,val);
    }

    @Test
    public void removeView_success(){
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        Solution.addView(1,1);
        ReturnValue val = Solution.removeView(1,1);
        assertEquals(ReturnValue.OK,val);
    }

    @Test
    public void removeView_NotExists(){
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        Solution.addView(1,1);
        ReturnValue val = Solution.removeView(2,1);
        assertEquals(ReturnValue.NOT_EXISTS,val);

        Solution.removeView(1,1);
        ReturnValue val2 = Solution.removeView(1,1);
        assertEquals(ReturnValue.NOT_EXISTS,val2);
    }

    @Test
    public void addMovieRating_success(){
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        Solution.addView(1,1);
        ReturnValue val = Solution.addMovieRating(1,1,MovieRating.LIKE);
        assertEquals(ReturnValue.OK,val);
    }

    @Test
    public void getMovieViewCount_success(){
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

        Solution.addView(1,1);
        Solution.addView(2,1);
        Solution.addView(3,1);
        Solution.addView(4,2);

        int TitanicViewsCount = Solution.getMovieViewCount(1);
        assertEquals(3,TitanicViewsCount);
        int FindingNemoViewsCount = Solution.getMovieViewCount(2);
        assertEquals(1,FindingNemoViewsCount);
        int TheGodfatherViewsCount = Solution.getMovieViewCount(3);
        assertEquals(0,TheGodfatherViewsCount);
    }

    @Test
    public void removeMovieRating_NotExists(){
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);


        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        Solution.addView(1,1);
        ReturnValue val = Solution.removeMovieRating(1,1);
        assertEquals(ReturnValue.NOT_EXISTS,val);

    }

    @Test
    public void removeMovieRating_success(){
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);


        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        Solution.addView(1,1);
        Solution.addMovieRating(1,1,MovieRating.LIKE);
        ReturnValue val = Solution.removeMovieRating(1,1);
        assertEquals(ReturnValue.OK,val);
    }

    @Test
    public void getMovieLikeCount_success(){
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


        Solution.addView(1,1);
        Solution.addMovieRating(1,1,MovieRating.LIKE);
        Solution.addView(2,1);
        Solution.addMovieRating(2,1,MovieRating.LIKE);
        Solution.addView(3,1);
        Solution.addMovieRating(3,1,MovieRating.DISLIKE);
        Solution.addView(4,2);

        int TitanicLikesCount = Solution.getMovieLikesCount(1);
        assertEquals(2,TitanicLikesCount);
        int FindingNemoLikesCount = Solution.getMovieLikesCount(2);
        assertEquals(0,FindingNemoLikesCount);
    }

    @Test
    public void gerMovieLikesCount_NotExist(){
        Viewer viewer1 = new Viewer();
        viewer1.setName("viewer1");
        viewer1.setId(1);
        Solution.createViewer(viewer1);

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setName("Titanic");
        movie1.setDescription("Drama");
        Solution.createMovie(movie1);

        Solution.addView(1,1);
        Solution.addMovieRating(1,1,MovieRating.LIKE);
        int count = Solution.getMovieLikesCount(2);
        assertEquals(0,count);
    }

}
