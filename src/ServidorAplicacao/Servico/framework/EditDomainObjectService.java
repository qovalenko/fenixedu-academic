/*
 * Created on 14/Nov/2003
 *  
 */
package ServidorAplicacao.Servico.framework;

import org.apache.commons.beanutils.PropertyUtils;

import DataBeans.InfoObject;
import Dominio.IDomainObject;
import ServidorAplicacao.IServico;
import ServidorAplicacao.Servico.exceptions.FenixServiceException;
import ServidorPersistente.ExcepcaoPersistencia;
import ServidorPersistente.IPersistentObject;
import ServidorPersistente.ISuportePersistente;
import ServidorPersistente.OJB.SuportePersistenteOJB;

/**
 * @author Leonor Almeida
 * @author Sergio Montelobo
 * @author jpvl
 */
public abstract class EditDomainObjectService implements IServico
{
    public Boolean run(Integer objectId, InfoObject infoObject) throws FenixServiceException
    {
        try
        {
            ISuportePersistente sp = SuportePersistenteOJB.getInstance();
            IPersistentObject persistentObject = getPersistentObject(sp);
            IDomainObject oldDomainObject = clone2DomainObject(infoObject);
            IDomainObject newDomainObject =
                (IDomainObject) Class.forName(oldDomainObject.getClass().getName()).newInstance();

            newDomainObject.setIdInternal(oldDomainObject.getIdInternal());
            
            if (canCreate(oldDomainObject))
            {
                persistentObject.simpleLockWrite(newDomainObject);
                PropertyUtils.copyProperties(newDomainObject, oldDomainObject);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (ExcepcaoPersistencia e)
        {
            throw new FenixServiceException(e);
        } catch (Exception e)
        {
            throw new FenixServiceException(e);
        }

    }
    /**
	 * By default returns true
	 * 
	 * @param domainObject
	 * @return
	 */
    protected boolean canCreate(IDomainObject domainObject) throws FenixServiceException
    {
        return true;
    }

    /**
     * 
     * @param sp
     * @return
     */
    protected abstract IPersistentObject getPersistentObject(ISuportePersistente sp);
    
    /**
     * This method invokes the Cloner to convert from InfoObject to IDomainObject 
     * 
     * @param infoObject
     * @return
     */
    protected abstract IDomainObject clone2DomainObject(InfoObject infoObject);

}
