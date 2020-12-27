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

public class AnnotationCRUDTest {
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
        sqlSession.commit();
        sqlSession.close();
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
     * 保存用户
     */
    @Test
    public void testSave(){
        User user = new User();
        user.setUsername("zzz");
        user.setAddress("北京");

        userDao.saveUser(user);
    }

    /**
     * 更新用户
     */
    @Test
    public void testUpdate(){
        User user = new User();
        user.setId(27);
        user.setUsername("eee");
        user.setAddress("上海");
        user.setSex("男");
        user.setBirthday(new Date());

        userDao.updateUser(user);
    }

    /**
     * 删除用户
     */
    @Test
    public void testDelete(){
        userDao.deleteUser(27);
    }

    /**
     * 根据 Id 查询用户信息
     */
    @Test
    public void testFindOne(){
        User user = userDao.findById(26);
        System.out.println(user);
    }

    /**
     * 根据用户名称模糊查询
     */
    @Test
    public void testFindByName(){
        //两种写法（IUserDao.java 中也要改写）
//        List<User> users = userDao.findUserByName("%c%");
        List<User> users = userDao.findUserByName("c");
        for (User user : users){
            System.out.println(user);
        }
    }

    /**
     * 查询用户数量
     */
    @Test
    public void testFindTotal(){
        int total = userDao.findTotalUser();
        System.out.println(total);
    }
}
