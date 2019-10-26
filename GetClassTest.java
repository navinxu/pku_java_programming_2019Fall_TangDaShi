/*================================================================
*   Copyright (C) 2019 Navin Xu. All rights reserved.
*   
*   Filename    ：GetClassTest.java
*   Author      ：Navin Xu
*   E-Mail      ：admin@navinxu.com
*   Create Date ：2019年10月22日
*   Description ：
================================================================*/
public class GetClassTest {
    public static void main(String[] args) {
        Test test = new Test();
        test.PrintClassName(test);
        System.out.println("toString: " + test.toString());
    }
}

class Test {
    public void PrintClassName(Object obj) {
        System.out.println("Class name is " + obj.getClass().getName());
    }

    @Override public String toString() {
        return "Hello:toString";
    }
}
