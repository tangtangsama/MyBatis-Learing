package cn.sucrelt.test;

import cn.sucrelt.dao.UserMapper;
import cn.sucrelt.domain.QueryVo;
import cn.sucrelt.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: sucre
 * @date: 2020/08/31
 * @time: 09:22
 */
public class MybatisUserTest {
    private InputStream in;
    private SqlSession sqlSession;
    private UserMapper userMapper;

    /**
     * @throws IOException
     */
    @Before
    public void init() throws IOException {
        //1.读取配置文件
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.使用工厂生产SqlSession对象
        sqlSession = factory.openSession();
        //4.使用SqlSession对象创建Dao接口的代理对象
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    /**
     * @throws IOException
     */
    @After
    public void destroy() throws IOException {
        //提交事务
        sqlSession.commit();

        in.close();
        sqlSession.close();
    }

    /**
     * 测试查询所有方法
     *
     * @throws IOException
     */
    @Test
    public void testFindAll() throws IOException {
        //使用代理对象执行方法
        List<User> users = userMapper.findAll();
        for (User user :
                users) {
            System.out.println(user);
        }
    }

    /**
     * 测试保存方法
     */
    @Test
    public void testSave() throws IOException {
        User user = new User();
        user.setUserName("mybatis last insertId");
        user.setUserBirthday(new Date());
        user.setUserSex("男");
        user.setUserAddress("南京");

        System.out.println("保存操作之前：" + user);
        //使用代理对象执行方法
        userMapper.saveUser(user);

        System.out.println("保存操作之后" + user);
    }

    /**
     * 测试更新方法
     */
    @Test
    public void testUpdate() throws IOException {
        User user = new User();
        user.setUserId(51);
        user.setUserName("mybatis updateUser");
        user.setUserBirthday(new Date());
        user.setUserSex("男");
        user.setUserAddress("南京");

        //使用代理对象执行方法
        userMapper.updateUser(user);
    }

    /**
     * 测试删除方法
     */
    @Test
    public void testDelete() throws IOException {

        //使用代理对象执行方法
        userMapper.deleteUser(54);
    }

    /**
     * 测试查询一个用户方法
     */
    @Test
    public void testFindById() throws IOException {
        //使用代理对象执行方法
        User user = userMapper.findById(45);
        System.out.println(user);
    }

    /**
     * 测试模糊查询
     */
    @Test
    public void testFindByName() throws IOException {
        //使用代理对象执行方法
        // List<User> users = userMapper.findByName("%王%");
        List<User> users = userMapper.findByName("王");
        for (User user :
                users) {
            System.out.println(user);
        }
    }

    /**
     * 测试查询所有记录
     */
    @Test
    public void testFindTotalCount() throws IOException {
        //使用代理对象执行方法
        int totalCount = userMapper.findTotalCount();
        System.out.println("TotalCount:" + totalCount);
    }

    /**
     * 测试根据对象中条件查询
     */
    @Test
    public void testFindByQueryVo() {
        QueryVo vo = new QueryVo();
        User user = new User();
        user.setUserName("%王%");
        vo.setUser(user);

        List<User> users = userMapper.findByVo(vo);
        for (User u : users) {
            System.out.println(u);
        }
    }

    /**
     * 测试查询用户所有账户
     */
    @Test
    public void testFindAllAccountByUser() {
        List<User> users = userMapper.findAllAccountByUser();

        for (User user :
                users) {
            System.out.println("-----------------------");
            System.out.println(user);
            System.out.println(user.getAccounts());
        }
    }
}
