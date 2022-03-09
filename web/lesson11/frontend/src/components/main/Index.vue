<template>
    <ul class="post-list">
        <li v-for="post in viewPosts" :key="post.id">
            <Post :post="post"/>
        </li>
    </ul>
</template>

<script>
import Post from "./post/Post";

export default {
    name: "Index",
    components: {Post},
    props: ["posts", "users"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id);
        }
    }
}
</script>

<style scoped>

</style>
