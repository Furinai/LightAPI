<template>
    <ApiGroupEdit @submitForm="updateApiGroup" :initialApiGroup="apiGroup"/>
</template>

<script>
import ApiGroupEdit from '../components/ApiGroupEdit.vue'
import request from '../utils/request'

export default {
    name: 'ApiGroupUpdate',
    components: {
        ApiGroupEdit
    },
    data() {
        return {
            apiGroup: {}
        }
    },
    created() {
        this.getApiGroup(this.$route.params.id)
    },
    methods: {
        getApiGroup(id) {
            let url = '/sys/api-groups/' + id
            request(url, {method: 'GET'}).then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        this.apiGroup = data
                    })
                }
            })
        },
        updateApiGroup(apiGroup) {
            let config = {method: 'PUT', body: apiGroup}
            request('/sys/api-groups', config).then(response => {
                if (response.ok) {
                    this.$message.success('更新成功！')
                    this.$router.push({name: 'ApiGroupList'})
                }
            })
        }
    }
}
</script>

<style scoped>
</style>