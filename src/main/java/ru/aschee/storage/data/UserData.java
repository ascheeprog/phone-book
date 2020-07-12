package ru.aschee.storage.data;

import ru.aschee.domain.User;
import ru.aschee.model.UserInfo;
import ru.aschee.model.request.ListUserRequest;

import javax.annotation.Nonnull;
import java.util.List;

public interface UserData {
    /**
     * Search users by first name, last name and nickname with ordering and sorting
     *
     * @param request user request
     * @return list users
     */
    List<User> findUsersByRequest(@Nonnull ListUserRequest request);

    /**
     * Search user by user id
     *
     * @param id user id
     * @return user if exists
     */
    User findUserById(String id);

    /**
     * Get all entities associated with user
     *
     * @param id user id
     * @return user information
     */
    UserInfo findUserInfoById(String id);

    /**
     * Save user
     *
     * @param user user for save
     * @return user
     */
    User putUser(User user);

    /**
     * Validate unique nickname user
     *
     * @param nickname nickname user
     * @return true if nickname exists
     */
    boolean nicknameIsExists(String nickname);

    /**
     * Delete user by ID
     *
     * @param id user id
     */
    void deleteUser(String id);
}
