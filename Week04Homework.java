/*================================================================
*   Copyright (C) 2019 Navin Xu. All rights reserved.
*   
*   Filename    ：Week04Homework.java
*   Author      ：Navin Xu
*   E-Mail      ：admin@navinxu.com
*   Create Date ：2019年10月13日
*   Description ：
编写一个小的程序，其中定义一些接口、类、抽象类，定义它们的成员（字段及方法）， 要求使用使用setter/getter, static, final, abstract，@Override等语法要素，并写一个main函数来使用它们。

依照学术诚信条款，我保证此回答为本人原创，所有回答中引用的外部材料已经做了出处标记。

1（10分）
编写一个小的程序，其中定义一些接口、类、抽象类，定义它们的成员（字段及方法）， 要求使用使用setter/getter, static, final, abstract，@Override等语法要素，并写一个main函数来使用它们。这些类、接口可以是围绕以下选题之一

飞翔世界：来一次飞翔接力（即多个可飞翔的对象依次调用）;

动物世界：来一次吃西瓜大赛;

图书馆：模拟一天的借阅过程;

学校：模拟选课过程;

等等

要求写个简要说明。
================================================================*/
/*
 * 
 * 在代码中都标记清楚了，对于题目提到的每一点几乎都完成了。
 * 鸟类会鸣叫，会飞，而鹰会飞得很高，而且越长大越飞得高。
 */
public class Week04Homework {
    public static void main(String[] args) {
        Magpie magpie = new Magpie();  
        magpie.set_name("Magpie");
        magpie.fly();
        Bird.chirp(magpie.get_name());


        Eagle eagle = new Eagle();
        eagle.set_name("Eagle");
        eagle.fly();
        Bird.chirp(eagle.get_name());

    }
}

// 接口
interface Fly{
    public void fly();
}

// 抽象类
abstract class Birds {
    // abstract
    abstract public String get_name();
    abstract public void set_name(String name);
}


// 父类
class Bird extends Birds implements Fly {
    // 字段
    private String name;

    // 方法
    public void fly() {
        System.out.println("The " + this.name + " is flying in the sky.");        
    }

    // getter
    public String get_name() {
        return this.name;
    }

    // setter
    public void set_name(String name) {
        this.name = name;
    }

    // static
    // final
    final public static void chirp(String name) {
        System.out.println("The " + name + " is chirping.");
    }
}

// 子类
// 喜鹊
class Magpie extends Bird {
    Magpie() {
        this.set_name("Magpie");        
    }
}

// 鹰
class Eagle extends Bird {
    Eagle() {
        this.set_name("Eagle");
    }

    // @override
    public void fly() {
        System.out.println("The " + get_name() + " is flying higher and higher");
    }
}
