/*
 ** 2014 May 19
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package info.ata4.unity.serdes;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Unity object that can contain one or more named fields.
 * 
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class UnityObject implements UnityTag<Map<String, UnityTag>> {
    
    private String name;
    private String type;
    private Map<String, UnityTag> fields = new LinkedHashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public Map<String, UnityTag> get() {
        return fields;
    }

    @Override
    public void set(Map<String, UnityTag> value) {
        this.fields = value;
    }
    
    public <T> T getValue(String name, boolean unwrap) {
        UnityTag f = fields.get(name);

        // return null if the field doesn't exist
        if (f == null) {
            return null;
        }
        
        if (unwrap) {
            // unwrap UnityTag
            while (f.get() instanceof UnityTag) {
                f = (UnityTag) f.get();
            }
        }
        
        return (T) f.get();
    }
    
    public <T> T getValue(String name) {
        return getValue(name, true);
    }
    
    public <T> void setValue(String name, T value, boolean unwrap) {
        UnityTag f = fields.get(name);
        
        if (f == null) {
            return;
        }
        
        if (unwrap) {
            // unwrap UnityTag
            while (f.get() instanceof UnityTag) {
                f = (UnityTag) f.get();
            }
        }
        
        f.set(value);
    }
    
    public <T> void setValue(String name, T value) {
        setValue(name, value, true);
    }
    
    public UnityObject getObject(String name) {
        UnityTag tag = getValue(name, false);
        return (UnityObject) tag;
    }
    
    public void setObject(String name, UnityObject value) {
        setValue(name, value, false);
    }
}
