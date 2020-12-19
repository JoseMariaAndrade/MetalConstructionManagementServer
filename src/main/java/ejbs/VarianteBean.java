package ejbs;

import entities.Product;
import entities.Variante;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class VarianteBean {

    @PersistenceContext
    EntityManager entityManager;

    public void create(int codigo, String nomeProduto, String name, double weff_p, double weff_n, double ar, double sigmaC) throws MyEntityNotFoundException, MyEntityExistsException {

        Product produto = entityManager.find(Product.class, nomeProduto);

        if (produto == null) {
            throw new MyEntityNotFoundException("Não existe um produto com o nome "+nomeProduto);
        }

        Variante variante = entityManager.find(Variante.class, codigo);

        if (variante != null) {
            throw new MyEntityExistsException("Já existe uma variante de produto com o código "+codigo);
        }

        List<Variante> variantes = getAll();

        for (Variante vrnt: variantes) {
            if (vrnt.getNome() == name) {
                throw new MyEntityExistsException("Já existe uma variante de produto com o nome "+name);
            }
        }

        Variante novaVariante = new Variante(codigo, produto, name, weff_p, weff_n, ar, sigmaC);

        produto.getVariantes().add(novaVariante);
        entityManager.persist(novaVariante);

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


    public void remove(int codigo) {

        Variante variante = findVariante(codigo);

        if (variante == null) {
            return;
        }

        variante.getProduto().getVariantes().remove(variante);
        entityManager.remove(variante);
    }
}
