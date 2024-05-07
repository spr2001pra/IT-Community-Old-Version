package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    // 根据实体类型返回数据，你是帖子的评论，还是评论的评论等
    // OFFSET 关键字用于指定从结果集(数据库)的哪一行开始返回数据，是索引值，从零开始
    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    // 查询数据的条目数
    int selectCountByEntity(int entityType, int entityId);

    int insertComment(Comment comment);

    Comment selectCommentById(int id);

}
