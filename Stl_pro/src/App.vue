<script setup>

</script>

<template>
  <router-view></router-view>
</template>

<style>
html,
body,
#app {
  padding: 0px;
  height: 100%;
  width: 100%;
  max-width: 100%;
  display: block;
}


.avatar-uploader .el-upload {
  border: 1px dashed #8c939d;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 178px;
  height: 178px;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.avatar-uploader .avatar{
  max-width: 178px;
  max-height: 178px;
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}

.content {
    padding: 15px;
}

.page {
    margin-top: 15px;
}

.warning-row {
    color: brown;
}

.bar {
    margin-bottom: 15px;
}

.el-table {
    --el-table-border-color: var(--el-border-color-lighter);
    /* 边框颜色 */
    --el-table-border: 1px solid var(--el-table-border-color);
    /* 边框 */
    --el-table-text-color: var(--el-text-color-regular);
    /* */
    --el-table-header-text-color: white;
    /* */
    --el-table-row-hover-bg-color: var(--el-fill-color-light);
    /* */
    --el-table-current-row-bg-color: var(--el-color-primary-light-9);
    /* */
    --el-table-header-bg-color: #58A6FE;
    /* */
    --el-table-fixed-box-shadow: var(--el-box-shadow-light);
    /* */
    --el-table-bg-color: var(--el-fill-color-blank);
    /* */
    --el-table-tr-bg-color: var(--el-bg-color);
    /* */
    --el-table-expanded-cell-bg-color: var(--el-fill-color-blank);
    /* */
    --el-table-fixed-left-column: inset 10px 0 10px -10px rgba(0, 0, 0, 0.15);
    --el-table-fixed-right-column: inset -10px 0 10px -10px rgba(0, 0, 0, 0.15);
    --el-table-index: var(--el-index-normal);
}

</style>