<script>
export default {
  mounted() {
    // 로그아웃(로컬 스토리지 사용)
    console.log("token invalidate");
    localStorage.removeItem('userToken');
    
    this.$router.push({ name: 'main' });
  }  
}

</script>


<template>
    <div>
        LogOut
    </div>
</template>

<style scoped>

</style>