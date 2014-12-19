package org.t0tec.tutorials.helloworld;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.t0tec.tutorials.model.TablePerClass;

public class TablePerClassTest {

    @BeforeClass
    public static void setUpClass() {}

    @AfterClass
    public static void tearDownClass() {}

    @Before
    public void setUp() {}

    @After
    public void tearDown() {}

    @org.junit.Test
    public void testSayHello() {
        TablePerClass instance = new TablePerClass();
        instance.doWork();
        Assert.assertTrue(true);
    }
}
