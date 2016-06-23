/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.converter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author douglas
 */
@FacesConverter(value = "converterCalendar")
public class ConverterCalendar implements Converter{
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
       if (string ==null){
           return null;
           
       
    }try{
            Calendar ret = Calendar.getInstance();
            ret.setTime(sdf.parse(string));
            return ret;
}catch(Exception e){
return null;
}
}

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
       Calendar obj = (Calendar) o;
       return sdf.format(obj.getTime());
            
       
    }
    
}
