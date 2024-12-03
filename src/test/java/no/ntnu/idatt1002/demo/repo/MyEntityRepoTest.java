package no.ntnu.idatt1002.demo.repo;

import no.ntnu.idatt1002.demo.data.MyEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

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