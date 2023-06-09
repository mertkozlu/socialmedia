package com.socialmedia.SocialMedia.dataAccess;

import com.socialmedia.SocialMedia.entitites.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUser_UserIdAndPost_PostId(Long userId, Long postId);

    List<Comment> findByUser_UserId(Long userId);

    List<Comment> findByPost_PostId(Long postId);

    @Query(value = "select 'commented on', c.post_id, u.avatar, u.user_name from "
            + "comment c left join user u on u.id = c.user_id"
            + "where c.post_id :postIds limit 5", nativeQuery = true)
    List<Object> findUserCommentByPostId(@Param("postIds") List<Long> postIds);

}
