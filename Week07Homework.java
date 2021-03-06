/*================================================================
 *   Copyright (C) 2019 Navin Xu. All rights reserved.
 *
 *   Filename    ：Week07Homework.java
 *   Author      ：Navin Xu
 *   E-Mail      ：admin@navinxu.com
 *   Create Date ：2019年10月26日
 *   Description ：
 ================================================================*/
import java.util.ArrayList;
public class Week07Homework {
    public static void main(String[] args) {
        try {
            Bank bank = new Bank("北大银行");
            System.out.println("\n======================================\n");

            Account user1 = new Account("user1", "888888");
            Account user2 = new Account("user2", "888888");
            Account user3 = new Account("user3", "888888");
            Account user4 = new Account("user4", "888888");
            Account user5 = new Account("user5", "888888");

            System.out.println("\n======================================\n");

            bank.setUpAnNewAccount(user1);
            bank.setUpAnNewAccount(user2);
            bank.setUpAnNewAccount(user3);
            bank.setUpAnNewAccount(user4);
            bank.setUpAnNewAccount(user5);

            System.out.println("\n======================================\n");

            // 登录
            bank.login("user1", "888888");
            bank.login("user2", "888888");
            bank.login("user3", "888888");
            bank.login("user4", "888888");
            bank.login("user5", "888888");

            System.out.println("\n======================================\n");

            // 第一次存钱
            user1.depositMoney(10000);
            user2.depositMoney(20000);
            user3.depositMoney(400555);
            user4.depositMoney(450111);
            user5.depositMoney(660011);

            // 获取余额
            user1.getBalance();
            user2.getBalance();
            user3.getBalance();
            user4.getBalance();
            user5.getBalance();

            System.out.println("\n======================================\n");

            // 第二次存钱
            user1.depositMoney(101);
            user2.depositMoney(102);
            user3.depositMoney(1045);
            user4.depositMoney(1041);
            user5.depositMoney(1061);

            user1.getBalance();
            user2.getBalance();
            user3.getBalance();
            user4.getBalance();
            user5.getBalance();

            // 任意用户登录成功均可使用
            bank.showAccountsList(user1);

            System.out.println("\n======================================\n");

            // 取钱
            user1.takeMoney(120);
            user2.takeMoney(220);
            user1.takeMoney(22);
            
            bank.showAccountsList(user1);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
class Account {
    private String userName;
    private String passwd;
    private double balance = 0.0;
    private boolean isLogin = false;

    Account() {}
    Account(String userName, String passwd) throws Exception {
        try {
            this.setUserName(userName);
            this.setPasswd(passwd);
        } catch (Exception e) {
            throw e;
        }
    }

    public void setUserName(String userName) throws Exception {
        if (!userName.isEmpty() && userName.length() >= 2)
            this.userName = userName;
        else
            throw new Exception("用户名最少由两个文字组成，请重新输入！");
    }

    public void setPasswd(String passwd) throws Exception {
        if (!passwd.isEmpty() && passwd.length() == 6)
            try {
                Double.parseDouble(passwd);
                this.passwd = passwd;
            } catch (NumberFormatException e) {
                throw new Exception("密码为六位数字，请重新输入密码！");
            }
        else {
            throw new Exception("密码为六位数字，请重新输入密码！");
        }
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean getIsLogin() {
        return this.isLogin;
    }

    public String getPasswd() { return this.passwd; }

    public String getUserName() {
        return this.userName;
    }

    public double getBalance() {
        return this.balance;
    }

    public void depositMoney(double money) throws Exception {
        if (this.isLogin) {
            if ((new Double(this.balance)).equals(0.0)) {
                this.balance = money;
            } else {
                this.balance += money;
            }
    
            System.out.println("用户：" + this.userName + "，您成功存入 " + money + " 元RMB，账户余额为：" + this.balance + " 元！");
        } else {
            throw new Exception("请登录！");
        }
    }

    public void takeMoney(double money) throws Exception{
        if (this.isLogin) {
            if (this.balance < money) {
                System.out.println("用户：" + this.userName + "，您的取款额超过您账户的余额，此次取款取消！");
            } else {
                this.balance -= money;
                System.out.println("用户：" + this.userName + "，您已经成功取款 " + money + " 元RMB，您账户的余额为 " + this.balance + " ！");
            }
        } else {
            throw new Exception("请登录！");
        }
    }
}

class Bank {
    private String bankName;
    private ArrayList<Account> accounts = new ArrayList<>();

    Bank(String bankName) {
        this.setBankName(bankName);
        System.out.println("欢迎来到 " + this.getBankName() + " ！");
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public ArrayList<Account> getAccountsList() {
        return this.accounts;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setUpAnNewAccount (Account account) throws Exception{
        if (!this.addNewAccount(account)) {
            throw new Exception("添加账户失败！");
        } else {
            System.out.println("成功添加账户，账户信息如下：");
            System.out.println("\t\t用户名：" + account.getUserName());
            // 当然，现实中密码是不会被显示出来的
            System.out.println("\t\t密码：" + account.getPasswd());
        }
    }

    public boolean addNewAccount(Account account) {
        if (!this.accounts.isEmpty()) {
            for (Account elem : this.accounts) {
                if (elem.getUserName() == account.getUserName()) {
                    //throw new Exception("用户 " + elem.getUserName() + " 已经存在！");
                    System.out.println("用户 " + elem.getUserName() + " 已经存在！");
                    return false;
                }
            }
        }
        return this.accounts.add(account);
    }

    /**
     *
     * 查询账户就得先登录
     */
    public boolean login(String userName, String passwd) throws Exception {
        if (this.getAccountsList().isEmpty()) {
            throw new Exception("没有相关账户！");
        }
        boolean isLogin = false;
        for (Account account : this.getAccountsList()) {
            if (account.getUserName() == userName) {
                if (account.getPasswd() == passwd) {
                    System.out.println("密码正确，登录成功！");
                    account.setIsLogin(true);
                    isLogin = true;
                } else {
                    break;
                }
            }
        }

        if (!isLogin)
            throw new Exception("用户名或密码错误，请重新登录！");

        return isLogin;
    }

    public void checkAccount(Account account) {
        if (account.getIsLogin()) {
            System.out.println("欢迎进入 " + this.getBankName() + " 的账户系统！");
            System.out.println("您账户的基本信息如下：");
            System.out.println("\t\t用户名：" + account.getUserName());
            // 当然，现实中密码是不会被显示出来的
            System.out.println("\t\t密码：" + account.getPasswd());
            System.out.println("\t\t账号余额：" + account.getBalance());

        }
    }

    /**
     *
     * 银行的管理员用户才可访问
     * 但是在这里假设所有添加的用户都可访问
     */
    public void showAccountsList(Account userValidate) throws Exception {
        if (userValidate.getIsLogin()) {
            if (this.getAccountsList().isEmpty()) {
                throw new Exception("没有相关账户！");
            }
            System.out.println("以下是银行所有用户的账户信息：");
            for (Account account : this.getAccountsList()) {
                    System.out.println("\t用户名：" + account.getUserName());
                    System.out.println("\t\t密码：" + account.getPasswd());
                    System.out.println("\t\t余额：" + account.getBalance());
                    System.out.println();
            }
        } else {
            throw new Exception("请登录！");
        }
    }
}
