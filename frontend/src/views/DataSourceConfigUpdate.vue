<template>
    <DataSourceConfigEdit @submitForm="updateDataSourceConfig" :initialDataSourceConfig="dataSourceConfig"/>
</template>

<script>
import DataSourceConfigEdit from '../components/DataSourceConfigEdit.vue'
import request from '../utils/request'

export default {
    name: 'DataSourceConfigUpdate',
    components: {
        DataSourceConfigEdit
    },
    data() {
        return {
            dataSourceConfig: {}
        }
    },
    created() {
        this.getDataSourceConfig(this.$route.params.id)
    },
    methods: {
        getDataSourceConfig(id) {
            let url = '/sys/data-source-configs/' + id
            request(url, {method: 'GET'}).then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        this.dataSourceConfig = data
                    })
                }
            })
        },
        updateDataSourceConfig(dataSourceConfig) {
            let config = {method: 'PUT', body: dataSourceConfig}
            request('/sys/data-source-configs', config).then(response => {
                if (response.ok) {
                    this.$message.success('更新成功！')
                    this.$router.push({name: 'DataSourceConfigList'})
                }
            })
        }
    }
}
</script>

<style scoped>
</style>