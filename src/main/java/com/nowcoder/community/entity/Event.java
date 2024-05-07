package com.nowcoder.community.entity;

import java.util.HashMap;
import java.util.Map;

public class Event {

    private String topic; // 系统通知存入Message数据库表中，其中topic存入conversation_id，其余的以JSON格式存入content
    private int userId; // 触发事件的用户的Id
    private int entityType;
    private int entityId;
    private int entityUserId;
    private Map<String, Object> data = new HashMap<>(); // 用于存储其他难以预料的数据

    public String getTopic() {
        return topic;
    }

    public Event setTopic(String topic) {// 修改set方法以达到链式编程效果，即对象.set.set.set....比构造函数方便灵活，其他set方法类似
        this.topic = topic;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Event setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public Event setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public Event setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityUserId() {
        return entityUserId;
    }

    public Event setEntityUserId(int entityUserId) {
        this.entityUserId = entityUserId;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Event setData(String key, Object value) {// 这里修改时，不需要外界传一个新的map进来，直接在当前map中添加数据，原理同上，链式调取，方便快捷
        this.data.put(key, value);
        return this;
    }

}
