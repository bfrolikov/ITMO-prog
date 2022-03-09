<template>
    <div id="app">
        <Header :userId="userId" :users="users"/>
        <Middle :posts="posts" :users="users" :comments="comments"/>
        <Footer :userCount="userCount" :postCount="postCount"/>
    </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";

export default {
    name: 'App',
    components: {
        Footer,
        Middle,
        Header
    },
    data: function () {
        return this.$root.$data;
    },
    beforeCreate() {
        this.$root.$on("onEnter", (login, password) => {
            if (password === "") {
                this.$root.$emit("onEnterValidationError", "Password is required");
                return;
            }

            const users = Object.values(this.users).filter(u => u.login === login);
            if (users.length === 0) {
                this.$root.$emit("onEnterValidationError", "No such user");
            } else {
                this.userId = users[0].id;
                this.$root.$emit("onChangePage", "Index");
            }
        });
        this.$root.$on("onRegister", (login, name) => {
            const MIN_LOGIN_LENGTH = 3, MAX_LOGIN_LENGTH = 16;
            const MAX_NAME_LENGTH = 32;
            if (login.length < MIN_LOGIN_LENGTH) {
                this.$root.$emit("onRegisterValidationError", `Login can't be shorter than ${MIN_LOGIN_LENGTH} characters`);
            } else if (login.length > MAX_LOGIN_LENGTH) {
                this.$root.$emit("onRegisterValidationError", `Login can't be longer than ${MAX_LOGIN_LENGTH} characters`);
            } else if (!/^[a-z]+$/.test(login)) {
                this.$root.$emit("onRegisterValidationError", "Login can only contain lowercase letters");
            } else if (!this.isLoginUnique(login)) {
                this.$root.$emit("onRegisterValidationError", "Login already exists");
            } else if (name === "") {
                this.$root.$emit("onRegisterValidationError", "Name is required");
            } else if (name.length > MAX_NAME_LENGTH) {
                this.$root.$emit("onRegisterValidationError", `Name cannot be longer than ${MAX_NAME_LENGTH} characters`);
            } else {
                this.saveNew(this.users, {
                    login, name, admin: false
                })
                this.$root.$emit("onChangePage", "Enter");
            }
        })

        this.$root.$on("onLogout", () => this.userId = null);

        this.$root.$on("onWritePost", (title, text) => {
            if (this.userId) {
                if (!title || title.length < 5) {
                    this.$root.$emit("onWritePostValidationError", "Title is too short");
                } else if (!text || text.length < 10) {
                    this.$root.$emit("onWritePostValidationError", "Text is too short");
                } else {
                    this.saveNew(this.posts, {
                        title, text, userId: this.userId
                    })
                }
            } else {
                this.$root.$emit("onWritePostValidationError", "No access");
            }
        });

        this.$root.$on("onEditPost", (id, text) => {
            if (this.userId) {
                if (!id) {
                    this.$root.$emit("onEditPostValidationError", "ID is invalid");
                } else if (!text || text.length < 10) {
                    this.$root.$emit("onEditPostValidationError", "Text is too short");
                } else {
                    let posts = Object.values(this.posts).filter(p => p.id === parseInt(id));
                    if (posts.length) {
                        posts.forEach((item) => {
                            item.text = text;
                        });
                    } else {
                        this.$root.$emit("onEditPostValidationError", "No such post");
                    }
                }
            } else {
                this.$root.$emit("onEditPostValidationError", "No access");
            }
        });
    },
    methods: {
        getObjectKeysLength: obj => Object.keys(obj).length,
        isLoginUnique: function (login) {
            return !Object.values(this.users).some(user => user.login === login);
        },
        /*generateId: coll => Math.max(...Object.keys(coll)) + 1*/
        saveNew: function (coll, obj) {
            const id = Math.max(...Object.keys(coll)) + 1;
            obj.id = id;
            this.$root.$set(coll, id, obj);
        }
    },
    computed: {
        userCount: function () {
            return this.getObjectKeysLength(this.users)
        },
        postCount: function () {
            return this.getObjectKeysLength(this.posts)
        }
    }
}
</script>

<style>
#app {

}
</style>
