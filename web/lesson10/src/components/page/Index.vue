<template>
    <ul class="post-list">
        <li v-for="viewPost in viewPosts" :key="viewPost.post.id">
            <Post :post="viewPost.post" :user="viewPost.user" :commentCount="viewPost.commentCount"/>
        </li>
    </ul>
</template>

<script>
import Post from "./post/Post";

export default {
    name: "Index",
    components: {Post},
    props: ["posts", "users", "comments"],
    computed: {
        viewPosts: function () {
            const sortedPosts = Object.values(this.posts).sort((a, b) => b.id - a.id);
            let postComments = new Map();
            sortedPosts.forEach(post => postComments.set(post.id, []))
            Object.values(this.comments).forEach(comment => {
                postComments.get(comment.postId).push(comment);
            })
            return sortedPosts.map(post => ({
                post,
                user: this.users[post.userId],
                commentCount: postComments.get(post.id).length
            }))
        }
    }
}
</script>

<style scoped>

</style>
