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
    EntityManager em;

    public void create(int codigo, String nomeProduto, String name, double weff_p, double weff_n, double ar, double sigmaC) {
        Product produto = em.find(Product.class, nomeProduto);
        Variante p = new Variante(codigo, produto, name, weff_p, weff_n, ar, sigmaC);
        em.persist(p);
    }

    public List<Variante> getAll() {
        return em.createNamedQuery("getAllVariants").getResultList();
    }

    public Variante findVariante(int codigo) {
        return em.find(Variante.class, codigo);
    }


}
