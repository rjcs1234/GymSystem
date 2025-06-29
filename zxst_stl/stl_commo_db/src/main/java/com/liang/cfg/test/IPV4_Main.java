package com.liang.cfg.test;

import java.util.Scanner;

/**
 * @program: zxst_stl
 * @ClassName main
 * @description:
 * @author: 梁海富
 * @create: 2025−06-25 16:03
 * @Version 1.0
 **/


public class IPV4_Main {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        System.out.println(getResult(sc.nextLine()));
    }

    public  static String getResult(String s){
        String [] ip=s.split("#");
        if(ip.length!=4) return "invalid IP";

        if(!Valid(ip[0],1,128)|| !Valid(ip[1],0,255) ||!Valid(ip[2],0,255) ||!Valid(ip[3],0,255)){
            return "invalid IP";
        }

        return Long.parseLong(getHexString(ip[0]) + getHexString(ip[1]) + getHexString(ip[2]) + getHexString(ip[3]),16)+"";
    }


    public static boolean Valid(String s,int from,int to){
        //包含非法
        if(s==null ||s.isEmpty()) return  false;
        for(char c: s.toCharArray()){
            if(c<'0' || c>'9'){
                return  false;
            }
        }

        int num=Integer.parseInt(s);

        String newS=Integer.toString(num);
        if(!s.equals(newS)) return false;

        if(num<from ||num>to){
            return  false;
        }
        return true;
    }

    public  static String getHexString(String num){
        String hexStr=Integer.toHexString(Integer.parseInt(num));
        if(hexStr.length()<2) hexStr="0"+hexStr;
        return hexStr;
    }
}
