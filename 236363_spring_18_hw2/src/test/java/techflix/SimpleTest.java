package techflix;

import org.junit.Test;
import techflix.business.ReturnValue;
import techflix.business.Viewer;

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


}
