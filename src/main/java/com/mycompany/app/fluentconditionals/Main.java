package com.mycompany.app.fluentconditionals;

/**
 * @author Wiktor Rup
 */
class Main {


    public static void main(String[] args) {

        abc: for(int i=0; i<10; i++)
            abc2: for(int j=0; j<10; j++){
                if(j==3)
                    break abc;
                System.out.println(j);
            }
    }

}
