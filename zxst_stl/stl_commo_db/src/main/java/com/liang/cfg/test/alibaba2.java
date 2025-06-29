package com.liang.cfg.test;

import java.util.*;

/**
 * @program: zxst_stl
 * @ClassName alibaba2
 * @description:
 * @author: 梁海富
 * @create: 2025−06-27 10:35
 * @Version 1.0
 **/


public class alibaba2 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String []temp=sc.nextLine().split(",");
        int [] arr=new int[temp.length];
        int k=0;
        for(String str: temp){
            int num = Integer.parseInt(str);
            arr[k++]=num;
        }
        System.out.println(getResult(arr));
    }

    public static int getResult(int arr[]){
        int n=arr.length;
        HashMap<Integer,Integer> map= new HashMap<>();

        for(int i=0; i<n; i++){
            int key=arr[i];
            if(map.containsKey(key)){
                int value= map.get(key)+1;
                map.put(key,value);
            }else{
                map.put(key,1);
            }
        }

        List<Integer> values=new ArrayList<>();
        Collection<Integer> values1 = map.values();
        for(Integer num: values1){
            values.add(num);
        }

        values.sort((a,b) -> b-a);
        int sum=0;
        int count=0;

        int half= (int) Math.ceil(arr.length/2.0);
        for(int freq: values){
            sum+=freq;
            count++;
            if(sum>=half){
                return count;
            }
        }
        return  count;
    }
}
