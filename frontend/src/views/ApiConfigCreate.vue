<template>
    <ApiConfigEdit @submitForm="createApiConfig" :initialApiConfig="apiConfig"/>
</template>

<script>
import ApiConfigEdit from '../components/ApiConfigEdit.vue'
import request from '../utils/request'

export default {
    name: 'ApiConfigCreate',
    components: {
        ApiConfigEdit
    },
    data() {
        return {
            apiConfig: {
                paramConfigList: [],
                cacheable: false,
                listable: false,
                pageable: false,
                enable: false
            }
        }
    },
    methods: {
        createApiConfig(apiConfig) {
            let config = {method: 'POST', body: apiConfig}
            request('/sys/api-configs', config).then(response => {
                if (response.ok) {
                    this.$message.success('新增成功！')
                    this.$router.push({name: 'ApiConfigList'})
                }
            })
        }
    }
}
</script>

<style scoped>
</style>