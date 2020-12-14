package ejbs;

import entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class UserBean {

    @PersistenceContext
    EntityManager entityManager;

    public User authenticate(String email, String password)
            throws Exception {

        System.out.println("authenticate" + email);
        TypedQuery<User> query = entityManager.createNamedQuery("getUserByEmail", User.class)
                .setParameter("email", email);

        User user = query.getSingleResult();
        if (user != null && user.getPassword().equals(User.hashPassword(password))) {
            return user;
        }
        throw new Exception(String.format("Failed logging in with email %s : unknown email or wrong password", email));
    }
}
