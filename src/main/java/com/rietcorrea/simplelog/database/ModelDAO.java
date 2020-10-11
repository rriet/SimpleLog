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

import com.rietcorrea.simplelog.objects.Model;

import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author riet
 */
public interface ModelDAO {
    
    public List<Model> getAllModels();
    
    public List<String> getAllModelGroups();
    
    public Model getModel(int modelId);
    
    public Model getModel(String modelName);
            
    public boolean modelExist(Model model);
    
    public boolean modelExist(String modelName);
    
    public void insertModel(Model model);

    public void updateModel(Model model);

    public void deleteModel(Model model);
    
    public void deleteModelArray(ObservableList<Model> models);
    
    public Model loadModel(ResultSet rs);
}
