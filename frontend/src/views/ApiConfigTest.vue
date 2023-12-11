<template>
    <el-form label-position="top">
        <el-form-item label="URL">
            <el-input v-model="completeRequestPath">
                <template #append>
                    <el-button @click="copyToBoard">复制</el-button>
                </template>
            </el-input>
        </el-form-item>
    </el-form>
</template>

<script>
import request from '../utils/request'

export default {
    name: 'ApiConfigTest',
    data() {
        return {
            completeRequestPath: '',
            apiConfig: {}
        }
    },
    created() {
        this.getApiConfig(this.$route.params.id)
    },
    methods: {
        getApiConfig(id) {
            request('/sys/api-configs/' + id, {method: 'GET'}).then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        this.apiConfig = data
                        let baseUrl = import.meta.env.VITE_BASE_API_URL
                        this.completeRequestPath = baseUrl + this.apiConfig.requestPath
                    })
                }
            })
        },
        testApi() {
            let config = {method: this.apiConfig.requestMethod}
            request(this.apiConfig.requestPath, config).then(response => {
                if (response.ok) {
                    response.json().then(data => {
                    })
                }
            })
        },
        copyToBoard() {
            document.execCommand('copy', false, this.completeRequestPath)
            this.$message.success('复制成功')
        }
    }
}
</script>

<style scoped>
</style>