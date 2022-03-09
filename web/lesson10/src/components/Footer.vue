<template>
    <footer>
        <a href="/">Codeforces</a> &copy; 2021 by Mike Mirzayanov. Total users: {{ userCount }}. Total posts:
        {{ postCount }}.
    </footer>
</template>

<script>
export default {
    name: "Footer",
    props: ["postCount", "userCount"]
}
</script>

<style scoped>

</style>