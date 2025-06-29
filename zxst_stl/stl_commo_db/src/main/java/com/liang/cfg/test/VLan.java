package com.liang.cfg.test;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * @program: zxst_stl
 * @ClassName VLan
 * @description:
 * @author: 梁海富
 * @create: 2025−06-26 15:21
 * @Version 1.0
 **/


public class VLan {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        String [] str=sc.nextLine().split(",");
        //{"20-21","15","18","30","5-10"}
        int remove=Integer.parseInt(sc.nextLine());
        System.out.println(getResult(str,remove));
    }

    public static String getResult(String [] vlanArr,int remove){
        LinkedList<Integer []> vlanList=new LinkedList<>();
        for(String vs: vlanArr){
            String [] parts=vs.split("-");
            //{""20","21"",,"15",}
            Integer []  range =new Integer[parts.length];
            for(int i=0 ;i< parts.length; i++){
                range[i]=Integer.parseInt(parts[i]);
                //[[20,21],[15],[18],[5,10]]
            }
            vlanList.add(range);
        }
        vlanList.sort((a,b) ->a[0] - b[0]);

        for(int i=0; i<vlanList.size();i++){
            Integer []vlan=vlanList.get(i);
            int from=vlan[0];
            if(vlan.length>1){
                int to =vlan[1];
                if(remove<from || remove>to) continue;
                vlanList.remove(i);
                if(remove ==from){
                    vlanList.add(i, generateRange(remove+1,to));
                }else if(remove==to){
                    vlanList.add(i,generateRange(from,remove-1));
                }else{
                    vlanList.add(i,generateRange(remove+1,to));
                    vlanList.add(i,generateRange(from,remove-1));
                }
                break;
            }else if(from==remove){
                vlanList.remove(i);
                break;
            }
        }
// 第一步：创建最终结果的 StringJoiner
        StringJoiner ans = new StringJoiner(",");

        // 第二步：遍历每个 VLAN 组
        for (Integer[] vlan : vlanList) {
            // 第三步：为当前 VLAN 组创建内部 StringJoiner
            StringJoiner sj = new StringJoiner("-");

            // 第四步：将 VLAN 组中的每个整数添加到内部 StringJoiner
            for (Integer v : vlan) {
                sj.add(v.toString());
            }

            // 第五步：将当前 VLAN 组的字符串结果添加到最终结果
            ans.add(sj.toString());
        }
        return ans.toString();
    }

    public static Integer[] generateRange(int from, int to) {
        if (from < to) {
            return new Integer[] {from, to};
        } else {
            return new Integer[] {from};
        }
    }
}
