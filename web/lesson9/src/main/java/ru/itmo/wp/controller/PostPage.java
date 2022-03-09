package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.CommentDTO;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @Guest
    @GetMapping("/post/{id}")
    public String getPost(@PathVariable String id, Model model) {
        Post post = getPostFromId(id);
        if (post == null) {
            return "NotFoundPage";
        }
        model.addAttribute("post", post);
        model.addAttribute("commentForm", new CommentDTO());
        return "PostPage";
    }

    @PostMapping("/post/{id}/comments")
    public String newComment(@PathVariable String id, HttpSession session, Model model,
                             @Valid @ModelAttribute("commentForm") CommentDTO commentForm,
                             BindingResult bindingResult) {
        Post post = getPostFromId(id);
        User user = getUser(session);
        if (post == null) {
            return "NotFoundPage";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "PostPage";
        }
        postService.writeComment(commentForm, user, post);
        return String.format("redirect:/post/%s", id);
    }

    private Post getPostFromId(String id) {
        try {
            return postService.findById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            //Ignore overflow
            return null;
        }
    }
}
