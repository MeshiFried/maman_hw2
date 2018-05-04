package techflix;

import org.junit.Test;
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


}
