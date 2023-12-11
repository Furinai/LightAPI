import {createRouter, createWebHistory} from 'vue-router'
import ApiConfigList from '../views/ApiConfigList.vue'
import ApiConfigCreate from '../views/ApiConfigCreate.vue'
import ApiConfigUpdate from '../views/ApiConfigUpdate.vue'
import ApiGroupList from '../views/ApiGroupList.vue'
import ApiGroupCreate from '../views/ApiGroupCreate.vue'
import ApiGroupUpdate from '../views/ApiGroupUpdate.vue'
import DataSourceConfigList from '../views/DataSourceConfigList.vue'
import DataSourceConfigCreate from '../views/DataSourceConfigCreate.vue'
import DataSourceConfigUpdate from '../views/DataSourceConfigUpdate.vue'

const routes = [
    {
        path: '/',
        name: 'Index',
        redirect: {name: 'ApiConfigList'}
    },
    {
        path: '/api-configs/list',
        name: 'ApiConfigList',
        component: ApiConfigList
    },
    {
        path: '/api-configs/create',
        name: 'ApiConfigCreate',
        component: ApiConfigCreate
    },
    {
        path: '/api-configs/update/:id',
        name: 'ApiConfigUpdate',
        component: ApiConfigUpdate
    },
    {
        path: '/api-groups/list',
        name: 'ApiGroupList',
        component: ApiGroupList
    },
    {
        path: '/api-groups/create',
        name: 'ApiGroupCreate',
        component: ApiGroupCreate
    },
    {
        path: '/api-groups/update/:id',
        name: 'ApiGroupUpdate',
        component: ApiGroupUpdate
    },
    {
        path: '/data-source-configs/list',
        name: 'DataSourceConfigList',
        component: DataSourceConfigList
    },
    {
        path: '/data-source-configs/create',
        name: 'DataSourceConfigCreate',
        component: DataSourceConfigCreate
    },
    {
        path: '/data-source-configs/update/:id',
        name: 'DataSourceConfigUpdate',
        component: DataSourceConfigUpdate
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router