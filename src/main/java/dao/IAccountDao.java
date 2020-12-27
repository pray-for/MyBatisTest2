package dao;

import domain.Account;
import domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface IAccountDao {
    /**
     * 查询所有账户，并且获取每个账户所所属的用户信息
     * @return
     */
    @Select("select * from account")
    @Results(id="accountMap",value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "uid", property = "uid"),
            @Result(column = "money", property = "money"),
            @Result(property = "user", column = "uid",
                    one = @One(select = "dao.IUserDao.findById",
                                fetchType = FetchType.EAGER))
    })
    List<Account> findAll();

    /**
     * 根据 用户Id 查询 账户信息
     * @param
     * @return
     */
    @Select("select * from account where uid = #{userId}")
    List<Account> findAccountByUid(Integer userId);
}
