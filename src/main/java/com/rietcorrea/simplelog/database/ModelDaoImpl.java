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

import com.rietcorrea.simplelog.LogException;
import com.rietcorrea.simplelog.objects.Model;

import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riet
 */
public class ModelDaoImpl implements ModelDAO {

    private String allModelsFields = "model_id, model_name, model_group, engine_type, mtow, multi_engine, multi_pilot, efis, seaplane";

    @Override
    public List<Model> getAllModels() {
        List<Model> models = new ArrayList<>();
        String statementString = "SELECT " + allModelsFields + " FROM model ORDER BY model_name  COLLATE NOCASE ASC;";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(statementString);){
            
            models = modelsQuery(stm);
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return models;
    }

    @Override
    public List<String> getAllModelGroups() {
        List<String> modelGroups = new ArrayList<>();
        
        String statementString = "SELECT model_group FROM model GROUP BY model_group ORDER BY model_group  COLLATE NOCASE ASC;";
        
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(statementString);
            ResultSet rs = stm.executeQuery();){
            
            while (rs.next()) {
                modelGroups.add(rs.getString("model_group"));
            }
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return modelGroups;
    }

    @Override
    public Model getModel(int modelId) {
        Model model = null;
        String query = "SELECT " + allModelsFields + " FROM model WHERE model_id = ?";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query);){
            
            stm.setInt(1, modelId);

            ResultSet rs = stm.executeQuery();
            model = loadModel(rs);

        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return model;
    }
    
    @Override
    public Model getModel(String modelName) {
        Model model = null;
        String query = "SELECT " + allModelsFields + " FROM model WHERE model_name = ? COLLATE NOCASE";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query);){
            
            stm.setString(1, modelName);
            ResultSet rs = stm.executeQuery();
            if (!rs.isClosed()) {
                model = loadModel(rs);
            }
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return model;
    }

    @Override
    public boolean modelExist(Model model) {
        return getModel(model.getModelName()) != null;
    }
    
    @Override
    public boolean modelExist(String modelName) {
        return getModel(modelName) != null;
    }
    
    @Override
    public void insertModel(Model model) {
        String query = "INSERT INTO model (model_name, model_group, engine_type, mtow, multi_engine, multi_pilot, efis, seaplane) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query);){
            
            stm.setString(1, model.getModelName());
            stm.setString(2, model.getModelGroup());
            stm.setString(3, model.getEngineType());
            stm.setInt(4, model.getMtow());
            stm.setBoolean(5, model.getMultiEngine());
            stm.setBoolean(6, model.getMultiPilot());
            stm.setBoolean(7, model.getEfis());
            stm.setBoolean(8, model.getSeaplane());

            stm.execute();
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
    }

    @Override
    public void updateModel(Model model) {
        String query = "UPDATE model set model_name = ? , model_group = ?, engine_type = ?, mtow = ?, "
                + "multi_engine = ?, multi_pilot = ?, efis = ?, seaplane = ?  WHERE model_id = ?";
        try (Connection connection = SqliteConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query);
        		){
            stm.setString(1, model.getModelName());
            stm.setString(2, model.getModelGroup());
            stm.setString(3, model.getEngineType());
            stm.setInt(4, model.getMtow());
            stm.setBoolean(5, model.getMultiEngine());
            stm.setBoolean(6, model.getMultiPilot());
            stm.setBoolean(7, model.getEfis());
            stm.setBoolean(8, model.getSeaplane());
            stm.setInt(9, model.getModelId());

            stm.execute();
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
    }

    @Override
    public void deleteModel(Model model) {
        String query = "DELETE FROM model WHERE model_id = ?";
        try ( Connection connection = SqliteConnection.getConnection();
              PreparedStatement stm = connection.prepareStatement(query);
        		) {
            
            stm.setInt(1, model.getModelId());
            stm.execute();
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
    }
    
    @Override
    public void deleteModelArray(ObservableList<Model> models) {
    	for (Model model : models) {
    		deleteModel(model);
    	}
    }
    

    private List<Model> modelsQuery(PreparedStatement statement) {
        List<Model> models = new ArrayList<>();

        try (ResultSet rs = statement.executeQuery()){
            
            while (rs.next()) {
                Model nextModel = loadModel(rs);
                models.add(nextModel);
            }
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return models;
    }

    @Override
    public Model loadModel(ResultSet rs) {
        Model nextModel = null;

        try {
            nextModel = new Model()	.setModelId(rs.getInt("model_id"))
						    		.setModelName(rs.getString("model_name"))
						    		.setModelGroup(rs.getString("model_group"))
						    		.setEngineType(rs.getString("engine_type"))
						    		.setMtow(rs.getInt("mtow"))
						    		.setMultiEngine(rs.getBoolean("multi_engine"))
						    		.setMultiPilot(rs.getBoolean("multi_pilot"))
						    		.setEfis(rs.getBoolean("efis"))
						    		.setSeaplane(rs.getBoolean("seaplane"));
        } catch (SQLException ex) {
            LogException.getMessage(ex);
        }
        return nextModel;
    }
}
