/*
 * Created on 9/Out/2003
 *
  */
package Dominio;

import java.util.Calendar;

import Util.DiaSemana;

/**
 * @author Ana e Ricardo
 *
 */
public interface IRoomOccupation extends IDomainObject{
	public DiaSemana getDayOfWeek();
	public Calendar getEndTime();
	public Calendar getStartTime();
	public ISala getRoom();
	public IPeriod getPeriod();
	
	public void setDayOfWeek(DiaSemana semana);
	public void setEndTime(Calendar calendar);
	public void setStartTime(Calendar calendar);
	public void setRoom(ISala sala);
	public void setPeriod(IPeriod period);
	
	public boolean roomOccupationForDateAndTime(
						Calendar startDate, Calendar endDate,
						Calendar startTime, Calendar endTime, 
						DiaSemana dayOfWeek);
}
