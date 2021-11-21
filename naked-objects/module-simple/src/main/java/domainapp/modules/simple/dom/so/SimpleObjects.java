package domainapp.modules.simple.dom.so;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.JDOQLTypedQuery;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jdo.applib.services.IsisJdoSupport_v3_2;

import domainapp.modules.simple.SimpleModule;
import domainapp.modules.simple.types.Name;
import java.io.IOException;
@DomainService(
        nature = NatureOfService.VIEW,
        objectType = "simple.SimpleObjects"
        )
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class SimpleObjects {

    private final RepositoryService repositoryService;
    private final IsisJdoSupport_v3_2 isisJdoSupport;

    public static class ActionDomainEvent extends SimpleModule.ActionDomainEvent<SimpleObjects> {}

    public static class CreateActionDomainEvent extends ActionDomainEvent {}


    /**
     * create() register and create a new SimpleObject
     * 
     * @param name  name of SimpleObject
     * @return      SimpleObject
     */
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT, domainEvent = CreateActionDomainEvent.class)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public SimpleObject create(
            @Name final String name) {
        return repositoryService.persist(SimpleObject.withName(name));
    }


    /**
     * findByName() search SimpleObject by name in DB - return list
     * 
     * @param name  name of SimpleObject
     * @return      List of SimpleObject
     */
    public static class FindByNameActionDomainEvent extends ActionDomainEvent {}
    @Action(semantics = SemanticsOf.SAFE, domainEvent = FindByNameActionDomainEvent.class)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<SimpleObject> findByName(
            @Name final String name
            ) {
        JDOQLTypedQuery<SimpleObject> q = isisJdoSupport.newTypesafeQuery(SimpleObject.class);
        List<SimpleObject> listOfSimpleObjects = null;
        try {
        final QSimpleObject cand = QSimpleObject.candidate();
        q = q.filter(
                cand.name.indexOf(q.stringParameter("name")).ne(-1)
                );
        listOfSimpleObjects = q.setParameter("name", name)
                .executeList();
        } finally {
            try {
                q.close();
            } catch (java.io.IOException e) {}
            finally {
                return listOfSimpleObjects;
            }
        }
    }
    /**
     * findByNameExact() search SimpleObject by name in DB - return exact one
     * 
     * @param name  name of SimpleObject
     * @return      SimpleObject
     */
    @Programmatic
    public SimpleObject findByNameExact(final String name) {
        JDOQLTypedQuery<SimpleObject> q = isisJdoSupport.newTypesafeQuery(SimpleObject.class);
        final QSimpleObject cand = QSimpleObject.candidate();
        SimpleObject simpleObject = null;
        try {
            q = q.filter(
                cand.name.eq(q.stringParameter("name"))
                );
            simpleObject = q.setParameter("name", name)
                .executeUnique();
        } finally {
            try {
                q.close();
            } catch (java.io.IOException e) {}
            finally {
                return simpleObject;
            }
        }
    }

    public static class ListAllActionDomainEvent extends ActionDomainEvent {}
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<SimpleObject> listAll() {
        return repositoryService.allInstances(SimpleObject.class);
    }

    @Programmatic
    public void ping() {
        JDOQLTypedQuery<SimpleObject> q = isisJdoSupport.newTypesafeQuery(SimpleObject.class);
        final QSimpleObject candidate = QSimpleObject.candidate();
        q.range(0,2);
        q.orderBy(candidate.name.asc());
        q.executeList();
        try{q.close();}catch(java.io.IOException e){}
    }


}
