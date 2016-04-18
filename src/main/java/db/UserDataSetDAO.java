package db;

/**
 * Created by iu6team on 30.03.16.
 */
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import java.util.List;

public class UserDataSetDAO {
    private final Session session;

    public UserDataSetDAO(Session session) {
        this.session = session;
    }

    public List<UserDataSet> getAllUsers() {
        final Criteria criteria = session.createCriteria(UserDataSet.class);
        //noinspection unchecked
        return (List<UserDataSet>) criteria.list();
    }

    public UserDataSet getUser(long id) {
        return session.get(UserDataSet.class, id);
    }

    public void addUser(UserDataSet user) {
        final Transaction trx = session.beginTransaction();
        session.save(user);
        trx.commit();
    }

    public void editUser(UserDataSet userNew, long userId) {
        final Transaction trx = session.beginTransaction();
        final UserDataSet user = session.load(UserDataSet.class, userId);
        if (getUserByLogin(userNew.getLogin()) == null)
            user.setLogin(userNew.getLogin());
        if (getUserByEmail(userNew.getEmail()) == null)
            user.setEmail(userNew.getEmail());
        user.setPassword(userNew.getPassword());
        session.save(user);
        trx.commit();
    }

    public void deleteUser(long userId) {
        final Transaction trx = session.beginTransaction();
        final UserDataSet user = session.load(UserDataSet.class, userId);
        session.delete(user);
        trx.commit();
    }

    public UserDataSet getUserByLogin(String login) {
        final Criteria criteria = session.createCriteria(UserDataSet.class);
        return (UserDataSet) criteria
                .add(Restrictions.eq("login", login))
                .uniqueResult();
    }

    public UserDataSet getUserByEmail(String email) {
        final Criteria criteria = session.createCriteria(UserDataSet.class);
        return (UserDataSet) criteria
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }


}