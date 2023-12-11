<template>
    <el-container>
        <el-aside width="150px">
            <Menu/>
        </el-aside>
        <el-main>
            <router-view/>
        </el-main>
    </el-container>
</template>

<script>
import Menu from '../components/AsideMenu.vue'

export default {
    name: 'Layout',
    components: {
        Menu
    }
}
</script>