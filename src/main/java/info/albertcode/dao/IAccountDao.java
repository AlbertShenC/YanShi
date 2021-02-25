package info.albertcode.dao;

import info.albertcode.domain.user.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 账户Dao接口
 */
@Repository("accountDao")
public interface IAccountDao {

    /**
     * 查询所有账户信息
     * @return
     */
    @Select("select * from db_account")
    public List<Account> findAll();

    /**
     * 保存账户信息
     * @param account
     */
    @Insert("insert into account (name, money) values (#{name}, #{money})")
    public void saveAccount(Account account);
}
