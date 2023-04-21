package com.markerhub.entity;

/**
 * @author pc
 * @date 2023/4/21 17:50
 */

public class UserEventDto {
    private Long userId;
    private Long eventId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
