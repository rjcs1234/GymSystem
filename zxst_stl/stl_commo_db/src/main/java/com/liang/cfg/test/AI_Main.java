package com.liang.cfg.test;

import java.util.*;

/**
 * @program: zxst_stl
 * @ClassName AI_Main
 * @description:
 * @author: 梁海富
 * @create: 2025−06-25 21:58
 * @Version 1.0
 **/


public class AI_Main {

        public static void main(String[] args) {
            Scanner sc =new Scanner(System.in);
            int n=sc.nextInt();
            Light [] lights=new Light[n];
            for(int i=0; i<n;i++){
                int id=sc.nextInt();
                int x1 = sc.nextInt();
                int y1 = sc.nextInt();
                int x2 = sc.nextInt();
                int y2 = sc.nextInt();
                lights[i]=new Light(id,(x1+x2)/2,(y1+y2)/2,(x2-x1)/2);
            }
            System.out.println(getResult(lights));
        }

        public static String getResult(Light[] lights){
            //按照圆心y坐标来选出最高的灯
            Arrays.sort(lights,(a,b)-> a.y-b.y);
            //利用StringJoiner 来给输出答案以空格分开
            StringJoiner ans=new StringJoiner(" ");

            ArrayList<Light> sameRowLights=new ArrayList<>();
            Light base=lights[0];
            sameRowLights.add(base);

            for(int i=1; i<lights.length; i++){
                Light light=lights[i];
                //如果lights[i]的纵坐标和base的纵坐标相差不超过半径，则视为同一行
                if(light.y-base.y <= base.r){
                    sameRowLights.add(light);

                }else{
                    //不是同一行,对同一行的灯按照x升序排序
                    sameRowLights.sort((a,b) -> a.x-b.x);
                    for(Light light1:sameRowLights){
                        String s = Integer.toString(light1.id);
                        ans.add(s);
                    }
                    sameRowLights.clear();
                    //开始新的一行
                    base=light;
                    sameRowLights.add(base);
                }
        }
            // 注意不要漏了最后一行
            if(sameRowLights.size()>0){
                sameRowLights.sort((a,b) -> a.x-b.x);
                for(Light light1:sameRowLights){
                    String s = Integer.toString(light1.id);
                    ans.add(s);
                }
            }
            return  ans.toString();
    }
}
class Light {
    int id;
    int x;
    int y;
    int r;

    public Light(int id, int x, int y, int r) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.r = r;
    }
}