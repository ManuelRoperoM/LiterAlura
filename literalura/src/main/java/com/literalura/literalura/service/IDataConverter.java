package com.literalura.literalura.service;

import com.fasterxml.jackson.databind.JsonMappingException;

public interface IDataConverter {
    <T> T getData(String json, Class<T> clase) throws JsonMappingException;
}
