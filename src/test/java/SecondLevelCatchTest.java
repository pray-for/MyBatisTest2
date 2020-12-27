import dao.IUserDao;
import domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class SecondLevelCatchTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession sqlSession;
    private IUserDao userDao;

    @Before
    public void init() throws Exception{
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @After
    public void destroy() throws Exception{

        in.close();
    }

    /**
     * 查询所有
     */
    @Test
    public void testFindAll(){
        List<User> users = userDao.findAll();
        for (User user : users){
            System.out.println("----------每个用户的信息----------");
            System.out.println(user);
            System.out.println(user.getAccounts());
        }
    }

    /**
     * 根据 Id 查询用户信息
     */
    @Test
    public void testFindOne(){
        User user = userDao.findById(24);
        System.out.println(user);

        sqlSession.close(); //释放一级缓存
        sqlSession = factory.openSession(); //再次打开session
        IUserDao userDao1 = sqlSession.getMapper(IUserDao.class);
        User user1 = userDao1.findById(24);
        System.out.println(user1);

        System.out.println(user == user1);
        sqlSession.close();

    }

}
