package ch.toscanelli.ecommerce.config;

import ch.toscanelli.ecommerce.entity.Country;
import ch.toscanelli.ecommerce.entity.Product;
import ch.toscanelli.ecommerce.entity.ProductCategory;
import ch.toscanelli.ecommerce.entity.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};

        // disable HTTP methods for Product: PUT, POST, DELETE
        disableHttpMethods(Product.class, config, theUnsupportedActions);
        disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);
        disableHttpMethods(Country.class, config, theUnsupportedActions); 
        disableHttpMethods(State.class, config, theUnsupportedActions);

        // expose entity ids
        exposeIds(config);
    }

    private static void disableHttpMethods(Class<?> theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }


    private void exposeIds(RepositoryRestConfiguration config) {
        // get a list of all entity classes from the entity manager
        var entities = entityManager.getMetamodel().getEntities();

        // create an array of the entity types
        var entityClasses = entities.stream()
                .map(Type::getJavaType)
                .toArray(Class[]::new);

        // expose the entity ids for the array of entity/domain types
        config.exposeIdsFor(entityClasses);
    }
}
