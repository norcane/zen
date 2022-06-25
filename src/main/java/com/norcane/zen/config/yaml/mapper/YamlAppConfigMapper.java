package com.norcane.zen.config.yaml.mapper;

import com.norcane.zen.config.model.AppConfig;
import com.norcane.zen.config.yaml.model.YamlAppConfig;

import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface YamlAppConfigMapper {

    AppConfig map(YamlAppConfig source);
}
