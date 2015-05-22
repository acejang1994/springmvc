package com.stl.common.models;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelQuote implements IApiModel<ModelQuote> {
    protected Double volume;
    
    public ModelQuote() {
        
    }
    
    public ModelQuote(Double volume) {
        this.volume = volume;
    }
    
    @Override
    public void mergePropertiesIfNull(ModelQuote mergeFrom) {
        if(null == mergeFrom) {
            return;
        }
        if(null == this.volume) {
            this.volume = mergeFrom.volume;
        }
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object obj) {
        if(! super.equals(obj)) {
            return false;
        }
        final ModelQuote other = (ModelQuote) obj;
        return Objects.equals(this.volume, other.volume);
    }
    
    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Objects.hash(volume);
    }
}
