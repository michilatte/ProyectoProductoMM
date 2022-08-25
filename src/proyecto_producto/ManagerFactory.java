
package proyecto_producto;

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
}
