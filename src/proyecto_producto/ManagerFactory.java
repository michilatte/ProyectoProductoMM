
package proyecto_producto;

import java.sql.Connection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author miri
 */
public class ManagerFactory {
    private EntityManagerFactory emf=null;
    public EntityManagerFactory getEmf(){
        return emf = Persistence.createEntityManagerFactory("proyecto_productoPU");
    }
    
    // manager

public static final Connection getConnection(final EntityManager entityManager) {
        entityManager.getTransaction().begin();
        Connection connection = entityManager.unwrap(java.sql.Connection.class);
        return connection;

    }
    
}
