package com.socialmedia.SocialMedia.business.concretes;

import com.socialmedia.SocialMedia.dataAccess.abstracts.CommentRepository;
import com.socialmedia.SocialMedia.dataAccess.abstracts.LikeRepository;
import com.socialmedia.SocialMedia.dataAccess.abstracts.PostRepository;
import com.socialmedia.SocialMedia.dataAccess.abstracts.UserRepository;
import com.socialmedia.SocialMedia.entitites.concretes.Comment;
import com.socialmedia.SocialMedia.entitites.concretes.Like;
import com.socialmedia.SocialMedia.entitites.concretes.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public UserService(UserRepository userRepository, LikeRepository likeRepository,
                       CommentRepository commentRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveOneUser(User newUser) {
        return this.userRepository.save(newUser);
    }

    public User getOneUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }


    public User updateOneUser(Long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User foundUser = user.get();
            foundUser.setUserName(newUser.getUserName());
            foundUser.setPassword(newUser.getPassword());
            return foundUser;
        } else
            return null;
    }

    public void deleteOneUser(Long userId) {
        this.userRepository.deleteById(userId);
    }


    public User getOneUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<Object> getUserActivity(Long userId) {
        List<Long> postIds = postRepository.findByTopByUserId(userId);
        if (postIds.isEmpty())
            return null;
        List<Comment> comments = commentRepository.findUserCommentByPostId(postIds);
        List<Like> likes = likeRepository.findUserLikeByPostId(postIds);
        List<Object> result = new ArrayList<>();
        result.addAll(comments);
        result.addAll(likes);
        return result;


    }
}

