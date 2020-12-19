package ejbs;

import entities.Product;
import entities.Variante;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class VarianteBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(int codigo, String nomeProduto, String name, double weff_p, double weff_n, double ar, double sigmaC) {
        Product produto = entityManager.find(Product.class, nomeProduto);
        Variante p = new Variante(codigo, produto, name, weff_p, weff_n, ar, sigmaC);
        produto.getVariantes().add(p);
        entityManager.persist(p);
    }

    public List<Variante> getAll() {
        return entityManager.createNamedQuery("getAllVariants").getResultList();
    }

    public Variante findVariante(int codigo) {
        return entityManager.find(Variante.class, codigo);
    }

    public Variante getVariante(int codigo) {
        return entityManager.find(Variante.class, codigo);
    }
}
