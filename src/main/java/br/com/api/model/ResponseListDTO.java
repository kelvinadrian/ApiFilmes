package br.com.api.model;

import java.util.List;

public class ResponseListDTO {

    private List<ResponseDTO> min;
    private List<ResponseDTO> max;

    public List<ResponseDTO> getMin() {
        return min;
    }

    public void setMin(List<ResponseDTO> min) {
        this.min = min;
    }

    public List<ResponseDTO> getMax() {
        return max;
    }

    public void setMax(List<ResponseDTO> max) {
        this.max = max;
    }
}
