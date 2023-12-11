<template>
    <div class="mb-2">
        <el-button type="primary" size="small" @click="createApiGroup">
            新增
        </el-button>
    </div>
    <el-table :data="apiGroupList" border>
        <el-table-column prop="name" label="名称"/>
        <el-table-column label="操作" width="400">
            <template #default="scope">
                <el-button type="warning" size="small" @click="updateApiGroup(scope.row)" circle>
                    <el-icon>
                        <edit/>
                    </el-icon>
                </el-button>
                <el-button type="danger" size="small" @click="deleteApiGroup(scope.row)" circle>
                    <el-icon>
                        <delete/>
                    </el-icon>
                </el-button>
            </template>
        </el-table-column>
    </el-table>
</template>

<script>
import request from '../utils/request'
import {Delete, DocumentAdd, Edit} from '@element-plus/icons-vue'

export default {
    name: "ApiGroupList",
    components: {
        Delete,
        DocumentAdd,
        Edit,
    },
    data() {
        return {
            apiGroupList: []
        }
    },
    created() {
        this.getApiGroupList()
    },
    methods: {
        getApiGroupList() {
            request('/sys/api-groups', {method: 'GET'}).then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        this.apiGroupList = data
                    })
                }
            })
        },
        createApiGroup() {
            this.$router.push({name: 'ApiGroupCreate'})
        },
        updateApiGroup(apiGroup) {
            this.$router.push({name: 'ApiGroupUpdate', params: {id: apiGroup.id}})
        },
        deleteApiGroup(apiGroup) {
            this.$confirm('确定删除？').then(() => {
                let url = '/sys/api-groups/' + apiGroup.id
                request(url, {method: 'DELETE'}).then(response => {
                    if (response.ok) {
                        let index = this.apiGroupList.indexOf(apiGroup)
                        this.apiGroupList.splice(index, 1)
                        this.$message.success('删除成功！')
                    }
                })
            })
        }
    }
}
</script>

<style scoped>
</style>