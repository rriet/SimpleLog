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
package com.rietcorrea.simplelog.objects;

import java.util.ArrayList;
import java.util.List;

import com.rietcorrea.constants.StrLen;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author riet
 */

public class Crew {
    private IntegerProperty crewId = new SimpleIntegerProperty();
    private StringProperty crewName = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty phone = new SimpleStringProperty();
    private StringProperty comments = new SimpleStringProperty();

    public Crew() {
        this.crewId.set(0);
        this.crewName.set("");
        this.email.set("");
        this.phone.set("");
        this.comments.set("");
    }
    
    public Crew resetCrew() {
    	this.crewName.set("");
        this.email.set("");
        this.phone.set("");
        this.comments.set("");
        
        return this;
	}
    
    public Crew(Crew that) {
    	this.setCrewId(that.getCrewId())
    		.setCrewName(that.getCrewName())
    		.setEmail(that.getEmail())
    		.setPhone(that.getPhone())
    		.setComments(that.getComments());
    	
    }
    
    public String[] getCrewArray() {
        List<String> crewArray = new ArrayList<>();
        
        crewArray.add(this.getCrewName());
        crewArray.add(this.getEmail());
        crewArray.add(this.getPhone());
        crewArray.add(this.getComments());
        
        return crewArray.toArray(new String[0]);
    }

    public Integer getCrewId() {
        return crewId.get();
    }
    
    public Crew setCrewId(Integer crewId) {
        this.crewId.set(crewId);
        return this;
    }

    public String getCrewName() {
        return crewName.get().trim();
    }

    public Crew setCrewName(String crewName) {
    	if (crewName != null) {
    		this.crewName.set(crewName.trim().substring(0, Math.min(crewName.length(), StrLen.CREW_NAME_MAX)));
    	}
        return this;
    }
    
    public StringProperty crewNameProperty() {
        return this.crewName;
    }

    public String getEmail() {
        return email.get().trim();
    }

    public Crew setEmail(String email) {
    	if (email != null) {
    		this.email.set(email.trim().substring(0, Math.min(email.length(), StrLen.CREW_EMAIL)));
    	}
        return this;
    }
    
    public StringProperty emailProperty() {
        return this.email;
    }

    public String getPhone() {
        return phone.get().trim();
    }

    public Crew setPhone(String phone) {
    	if (phone != null) {
    		this.phone.set(phone.trim().substring(0, Math.min(phone.length(), StrLen.CREW_PHONE)));
    	}
        return this;
    }
    
    public StringProperty phoneProperty() {
        return this.phone;
    }

    public String getComments() {
        return comments.get().trim();
    }

    public Crew setComments(String comments) {
    	if (comments != null) {
    		this.comments.set(comments.trim().substring(0, Math.min(comments.length(), StrLen.CREW_COMMENTS)));
    	}
        return this;
    }
    
    public StringProperty commentsProperty() {
        return this.comments;
    }
    
    @Override
    public String toString(){
        return this.getCrewName().trim();
    }
}
