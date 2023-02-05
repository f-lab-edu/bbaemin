package org.bbaemin.user.repository;

import org.bbaemin.user.vo.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class UserMemoryRepository implements UserRepository {

    private static final Map<Long, User> map = new ConcurrentHashMap<>();
    private static final AtomicLong id = new AtomicLong(0L);

    public static AtomicLong getId() {
        return id;
    }

    static {
        map.put(getId().incrementAndGet(), User.builder()
                .userId(getId().get())
                .email("user1@email.com")
                .nickname("user1")
                .image(null)
                .phoneNumber("010-1234-5678")
                .build());
        map.put(getId().incrementAndGet(), User.builder()
                .userId(getId().get())
                .email("user2@email.com")
                .nickname("user2")
                .image(null)
                .phoneNumber("010-1111-2222")
                .build());
    }

    public static void clear() {
        map.clear();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public User findById(Long userId) {
        return map.get(userId);
    }

    @Override
    public User insert(User user) {
        Long userId = getId().incrementAndGet();
        user.setUserId(userId);
        map.put(userId, user);
        return user;
    }

    @Override
    public User update(User user) {
        map.put(user.getUserId(), user);
        return user;
    }

    @Override
    public void updateUserDeleted(Long userId) {
        User user = map.get(userId);
        user.setDeleted(true);
        map.put(userId, user);
    }
}
