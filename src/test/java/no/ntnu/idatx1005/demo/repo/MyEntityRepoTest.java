package no.ntnu.idatx1005.demo.repo;

import no.ntnu.idatx1005.demo.data.MyEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyEntityRepoTest {

    @Test
    public void testThatgetMyEntityMethodReturnAnEntiryWithCorrectName() {
        MyEntity e = new MyEntityRepo().getMyEntity("id");
        assertEquals(e.getName(), "name");
    }


    /**
    @Test
    public void testfindMyEntitiesMethodReturnAListOf2Entities() {
        List<MyEntity> e = new MyEntityRepo().findMyEntities("id");
        assertEquals(e.size(), 2);
    }
    */

}