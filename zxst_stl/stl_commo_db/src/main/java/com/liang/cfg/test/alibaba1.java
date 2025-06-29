package com.liang.cfg.test;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * @program: zxst_stl
 * @ClassName alibaba1
 * @description:
 * @author: 梁海富
 * @create: 2025−06-27 09:34
 * @Version 1.0
 **/


public class alibaba1 {
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        String s=sc.nextLine();
        String[] split = s.split(",");
        int []arr= new int[split.length];
        int k=0;
        for(String str:split) {
            int num = Integer.parseInt(str);
            arr[k++] = num;
        }
        System.out.println(getResult(arr));
    }

    public static int getResult(int arr[]){
        IntStream stream = Arrays.stream(arr);
        int sum = stream.sum();
        if(arr.length==1){
            return 0;
        }

        int leftSum=0;
        int rightSum=sum;

        for(int i=0; i< arr.length; i++){
            rightSum-=arr[i];
            if(leftSum==rightSum) return i;
            leftSum+=arr[i];
        }
        return -1;
    }
}
