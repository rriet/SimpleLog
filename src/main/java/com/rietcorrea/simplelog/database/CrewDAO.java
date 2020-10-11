/*
*    Simple Log - Pilot logbook software
*    Copyright (C) 2018  Ricardo Brito Riet Correa
*
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.rietcorrea.simplelog.database;

import com.rietcorrea.simplelog.objects.Crew;
import java.util.List;

/**
 *
 * @author riet
 */
public interface CrewDAO extends SearchDAO<Crew>{
    
    public List<Crew> getAllCrew();
    
    public List<Crew> findCrew(String searchString);

    public Crew getCrew(int crewId);
    
    public Crew getCrew(String crewName);
    
    public void insertCrew(Crew crew);

    public void updateCrew(Crew crew);

    public void deleteCrew(Crew crew);

    public boolean crewExist(Crew crew);
    
    public boolean crewExist(String crewName);
}
