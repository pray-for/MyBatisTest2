import dao.IUserDao;
import domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;

import java.io.InputStream;
import java.util.List;

public class MyBatisAnnoTest {
    /**
     * 测试基于注解的 mybatis 使用
     * @param args
     */
    /**
     * 1.获取字节输入流
     * 2.根据字节输入流构建 SqlSessionFactory
     * 3.根据 SqlSessionFactory
     * 4.使用 SQLSession 获取 Dao 的代理对象
     * 5.执行 Dao 的方法
     * 6.释放资源
     */
    public static void main(String[] args) throws Exception{
        //获取字节输入流
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //根据字节输入流构建 SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //根据 SqlSessionFactory
        SqlSession sqlSession = factory.openSession();
        //使用 SQLSession 获取 Dao 的代理对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        //执行 Dao 的方法
        List<User> users = userDao.findAll();
        for (User user : users){
            System.out.println(user);
        }
        //释放资源
        sqlSession.close();
        in.close();
    }
}
