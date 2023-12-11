<template>
    <ApiConfigEdit @submitForm="updateApiConfig" :initialApiConfig="apiConfig"/>
</template>

<script>
import ApiConfigEdit from '../components/ApiConfigEdit.vue'
import request from '../utils/request'

export default {
    name: 'ApiConfigUpdate',
    components: {
        ApiConfigEdit
    },
    data() {
        return {
            apiConfig: {}
        }
    },
    created() {
        this.getApiConfig(this.$route.params.id)
    },
    methods: {
        getApiConfig(id) {
            let url = '/sys/api-configs/' + id
            request(url, {method: 'GET'}).then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        this.apiConfig = data
                    })
                }
            })
        },
        updateApiConfig(apiConfig) {
            let config = {method: 'PUT', body: apiConfig}
            request('/sys/api-configs', config).then(response => {
                if (response.ok) {
                    this.$message.success('更新成功！')
                }
            })
        }
    }
}
</script>

<style scoped>
</style>