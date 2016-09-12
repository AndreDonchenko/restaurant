package ua.goit.andre.ee9.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.goit.andre.ee9.model.Position;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Andre on 12.09.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/web-context.xml",
                                    "file:src/main/webapp/WEB-INF/application-context.xml",
                                    "file:src/main/webapp/WEB-INF/hibernate-context.xml"})

public class PositionControllerTest {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected ApplicationContext ac;

    PositionController positionController;

    @Before
    public void before() {
        positionController = (PositionController) ac.getBean("positionController");
    }

    @Test
    public void testPositionsGetAll() throws Exception {
        Map<String, Object> model = new HashMap();
        String s = positionController.positions(model);
        Assert.assertTrue("admin/Employee/positions".equals(s));
        Assert.assertTrue(model.get("positions").getClass().equals(ArrayList.class));
        List<Position> list = (ArrayList) model.get("positions");
        Position position = new Position("LaLaLaLa");
        Assert.assertTrue(!list.contains(position));
        positionController.positions(position);
        s = positionController.positions(model);
        list = (ArrayList) model.get("positions");
        System.out.println(list);
        Assert.assertTrue(list.contains(position));
        HttpServletResponse response = new MockHttpServletResponse();
        positionController.positionsDelete("LaLaLaLa", response);
        s = positionController.positions(model);
        list = (ArrayList) model.get("positions");
        Assert.assertTrue(!list.contains(position));
    }
}