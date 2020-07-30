package taweryawer.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import taweryawer.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    EntityManager entityManager;

    private Log log = LogFactory.getLog(UserRepositoryImpl.class);

    @Override
    public User getUserByTelegramIdIfExistsOrCreateIfDoesnt(String telegramId) {
        log.info("Acquiring user " + telegramId);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root).where(cb.equal(root.get("telegramId"), telegramId));
        TypedQuery<User> query = entityManager.createQuery(cq);
        try {
            User user = query.getSingleResult();
            log.info("User " + telegramId + " successfully acquired");
            return user;
        } catch (NoResultException e) {
            log.info("User " + telegramId + " not found, creating...");
            User newUser = createNewUser(telegramId);
            log.info("User " + telegramId + " has been sucessfully created.");
            return newUser;
        }
     }

     private User createNewUser(String telegramId) {
        User user = new User();
        user.setTelegramId(telegramId);
        user.setMachineId(telegramId);
        user.setReputation(50);
        entityManager.persist(user);
        return user;
     }

     public void changeUserNickname(String newNickname, String telegramId) {
        User user = getUserByTelegramIdIfExistsOrCreateIfDoesnt(telegramId);
        log.info("Changing user " + telegramId + " nickname from " + user.getName() + " to " + newNickname);
        user.setName(newNickname);
        entityManager.persist(user);
     }

    @Override
    public void changeUserFriendCode(String newFriendCode, String telegramId) {
        User user = getUserByTelegramIdIfExistsOrCreateIfDoesnt(telegramId);
        log.info("Changing user " + telegramId + " friend code from " + user.getFriendCode() + " to " + newFriendCode);
        user.setFriendCode(newFriendCode);
        entityManager.persist(user);
    }
}
