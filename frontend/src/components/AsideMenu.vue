<template>
    <el-menu default-active="1" router>
        <el-menu-item index="1" :route="{name: 'ApiConfigList'}">
          <i class="el-icon-house"></i>
          <template #title>接口管理</template>
        </el-menu-item>
        <el-menu-item index="2" :route="{name: 'ApiGroupList'}">
          <i class="el-icon-house"></i>
          <template #title>分组管理</template>
        </el-menu-item>
        <el-menu-item index="3" :route="{name: 'DataSourceConfigList'}">
          <i class="el-icon-house"></i>
          <template #title>数据源管理</template>
        </el-menu-item>
    </el-menu>
</template>

<script>

export default {
  name: "Aside"
}
</script>