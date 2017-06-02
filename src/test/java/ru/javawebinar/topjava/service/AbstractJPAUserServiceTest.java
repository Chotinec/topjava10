package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.repository.JpaUtil;

/**
 * Created by Artiom on 14.05.2017.
 */
public abstract class AbstractJPAUserServiceTest extends AbstractUserServiceTest {

    @Autowired
    protected JpaUtil jpaUtil;

    @Before
    public void setUpJpa() throws Exception {
        jpaUtil.clear2ndLevelHibernateCache();
    }
}
