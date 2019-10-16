/*================================================================
*   Copyright (C) 2019 Navin Xu. All rights reserved.
*   
*   Filename    ：Week06Homework.java
*   Author      ：Navin Xu
*   E-Mail      ：admin@navinxu.com
*   Create Date ：2019年10月16日
*   Description ：
（10分）
Java的异常是比较独特的，它是程序安全稳定的重要措施。本次作业要求自定义异常并使用异常。

（1）自定义一个异常类，注意其继承自系统的异常类，并要求有构造方法；

（2）使用自定义的异常：要求在一个函数中抛出异常，在另一段程序中调用这个函数并捕获异常。


评分标准：

自定义异常类，继承自系统的异常（2分）；

异常类有构造方法（2分）；

异常类的构造方法中有一个链接内部异常（1分）；

有抛出异常（1分）；

在抛出异常的方法中有声明throws异常（1分）；

有异常捕获和处理（2分）；

整体程序比较合理（1分）。
================================================================*/

public class Week06Homework {
    public static void main(String[] args) {
        try {
            /* Person tim = new Person("Tim", 30); */
            /* tim.checkGeneralPersonHeight(1.75); */

            Person trump = new Person("Trump", 65);
            trump.checkGeneralPersonHeight(2.91);
        } catch (NumberException e){
            System.out.println(e.getMessage());
        }
    }
}


@SuppressWarnings("serial")
class NumberException extends Exception {
    NumberException() {
        super();
    }

    NumberException(String message) {
        super(message);
    }

    NumberException(String message, Throwable e) {
        super(message, e);
    }
}

class Person {
    private String name;
    private int age;
    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public void checkGeneralPersonHeight(double height) throws NumberException {
        // 这里的单位是米
        // 0.45 是人类婴儿的正常身高
        // 这里只是打个比方，请勿太认真
        if (height < 0.45 || height > 2.55) {
            throw new NumberException("身高已经超出正常范围");
        } else {
            throw new NumberException("身高符合正常范围");
        }
    }
}
