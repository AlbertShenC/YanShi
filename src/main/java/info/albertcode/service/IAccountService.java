package info.albertcode.service;

import info.albertcode.domain.User;

import java.util.List;

public interface IAccountService {

    /**
     * 查询所有账户信息
     * @return
     */
    public List<User> findAll();

    /**
     * 保存账户信息
     * @param account
     */
    public void saveAccount(User account);
}
