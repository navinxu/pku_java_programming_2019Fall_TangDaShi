/*================================================================
*   Copyright (C) 2019 Navin Xu. All rights reserved.
*   
*   Filename    ：Implements.java
*   Author      ：Navin Xu
*   E-Mail      ：admin@navinxu.com
*   Create Date ：2019年10月12日
*   Description ：
================================================================*/

public class Test2 {
    SayHello say;
    Test2(SayHello say) {
        say.sayHello();
    }
}

class SayHello {
    public void sayHello() {
        System.out.println("Hello");
    }
}

interface Inter {
}
