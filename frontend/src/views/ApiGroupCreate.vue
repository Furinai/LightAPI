<template>
    <ApiGroupEdit @submitForm="createApiGroup" :initialApiGroup="apiGroup"/>
</template>

<script>
import ApiGroupEdit from '../components/ApiGroupEdit.vue'
import request from '../utils/request'

export default {
    name: 'ApiGroupCreate',
    components: {
        ApiGroupEdit
    },
    data() {
        return {
            apiGroup: {}
        }
    },
    methods: {
        createApiGroup(apiGroup) {
            let config = {method: 'POST', body: apiGroup}
            request('/sys/api-groups', config).then(response => {
                if (response.ok) {
                    this.$message.success('新增成功！')
                    this.$router.push({name: 'ApiGroupList'})
                }
            })
        }
    }
}
</script>

<style scoped>
</style>