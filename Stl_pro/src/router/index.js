import component from "element-plus/es/components/tree-select/src/tree-select-option.mjs"
import {createRouter,createWebHistory} from "vue-router"

const routers=[
    {
        path:'/login',
        name:'login',
        component:()=>import('@/components/Login.vue')
    },
    {
        name: 'index', 
        path: '/Index', 
        component: ()=>import('@/components/Index.vue'), 
        children: [
             {
                name: 'charts',
                path: '/charts', 
                component: ()=>import('@/components/Charts.vue')
             },
             {
                name: 'member',
                path: '/member', 
                component: ()=>import('@/components/Member.vue')
             },
             {
                name: 'admin',
                path: '/admin', 
                component: ()=>import('@/components/Admin.vue')
             },
            {
                name: 'coach',
                path: '/coach', 
                component: ()=>import('@/components/Coach.vue')
             },
             {
                name: 'course',
                path: '/course', 
                component: ()=>import('@/components/Course.vue')
             },
             {
                name: 'courseCalendar',
                path: '/courseCalendar', 
                component: ()=>import('@/components/CourseCalendar.vue')
             },
             {
                name: 'reserve',
                path: '/reserve', 
                component: ()=>import('@/components/Reserve.vue')
             },
         

    ]},
];

const router=createRouter({
    history:createWebHistory(),
    routes:routers
})

export default router