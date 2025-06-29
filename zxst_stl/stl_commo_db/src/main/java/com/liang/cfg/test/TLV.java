package com.liang.cfg.test;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * @program: zxst_stl
 * @ClassName TLV
 * @description:
 * @author: 梁海富
 * @create: 2025−06-26 10:56
 * @Version 1.0
 **/


public class TLV {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        String target=sc.nextLine();
        String [] s=sc.nextLine().split(" ");
        System.out.println(getResult(s,target));

    }

    public  static String getResult(String []s ,String target){
        int i=0;
        while (i<s.length) {
            String tag = s[i++];
            String temp1 = s[i++];
            String temp2 = s[i++];

            int len = Integer.parseInt(temp2 + temp1, 16);
            ArrayList<String> val = new ArrayList<>();
            for (int j = 0; j < len; j++) {
                val.add(s[i++]);
            }

            StringJoiner sj = new StringJoiner(" ");
            if (tag.equals(target)) {
                for (String str : val) {
                    sj.add(str);
                }
                return  sj.toString();
            }
        }
        return null;
    }
}
