package info.albertcode.service.impl;

import info.albertcode.dao.IAccountDao;
import info.albertcode.domain.User;
import info.albertcode.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService{

    @Autowired
    private IAccountDao accountDao;

    @Override
    public List<User> findAll() {
        System.out.println("业务层：查询所有账户信息...");
        return accountDao.findAll();
    }

    @Override
    public void saveAccount(User account) {
        System.out.println("业务层：保存账户...");
        accountDao.saveAccount(account);
    }
}
