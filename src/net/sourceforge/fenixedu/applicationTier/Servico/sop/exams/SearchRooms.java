package net.sourceforge.fenixedu.applicationTier.Servico.sop.exams;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.FenixServiceException;
import net.sourceforge.fenixedu.dataTransferObject.InfoRoom;
import net.sourceforge.fenixedu.dataTransferObject.inquiries.InfoRoomWithInfoInquiriesRoom;
import net.sourceforge.fenixedu.domain.IRoom;
import net.sourceforge.fenixedu.persistenceTier.ExcepcaoPersistencia;
import net.sourceforge.fenixedu.persistenceTier.ISalaPersistente;
import net.sourceforge.fenixedu.persistenceTier.ISuportePersistente;
import net.sourceforge.fenixedu.persistenceTier.PersistenceSupportFactory;
import pt.utl.ist.berserk.logic.serviceManager.IService;

public class SearchRooms implements IService {

    public List run(String name, String building, Integer floor, Integer type, Integer normal,
            Integer exam) throws FenixServiceException, ExcepcaoPersistencia {

        final ISuportePersistente persistentSupport = PersistenceSupportFactory
                .getDefaultPersistenceSupport();
        final ISalaPersistente persistentRoom = persistentSupport.getISalaPersistente();

        final List<IRoom> rooms = persistentRoom.readSalas(name, building, floor, type, normal, exam);
        
        List<InfoRoom> infoRooms = new ArrayList();
        for (final IRoom room : rooms) {
            infoRooms.add(InfoRoomWithInfoInquiriesRoom.newInfoFromDomain(room));
        }
        return infoRooms;
    }
}